package github.tourism.service.statistic;


import github.tourism.data.entity.map.Map;
import github.tourism.data.entity.statistic.Statistic;
import github.tourism.data.entity.statistic.factory.StatisticFactory;
import github.tourism.data.repository.favPlace.FavPlaceRepository;
import github.tourism.data.repository.map.MapRepository;
import github.tourism.data.repository.statistic.UserStatisticRepository;
import github.tourism.web.advice.ErrorCode;
import github.tourism.web.dto.statistic.MapStatisticDTO;
import github.tourism.web.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStatisticService {
    private final UserStatisticRepository userStatisticRepository;
    private final MapRepository mapRepository;
    private final StatisticFactory statisticFactory;
    @Transactional
    public List<Statistic> updatePlaceAndLikeCount(String category) {
            List<Map> maps = mapRepository.findByCategory(category);
            if (maps.isEmpty()) {
                throw new BadRequestException(ErrorCode.MAPS_NOT_FOUNDED);
            }

            List<Statistic> statistics = new ArrayList<>();

            for (Map currentMap : maps) {
                if (currentMap.getLikemarkCount() > 0) {
                    Statistic statistic = statisticFactory.createStatistic(currentMap);
                    statistics.add(statistic);
                }
            }

            if (statistics.isEmpty()) {
                throw new BadRequestException(ErrorCode.MAPS_NOT_FOUNDED);
            }

            setRanking(statistics);

            userStatisticRepository.saveAll(statistics);

            List<Statistic> filteredStatistics = userStatisticRepository.findByCategory(category).stream()
                    .filter(stat -> stat.getPlaceLikeNum() > 0)
                    .collect(Collectors.toMap(Statistic::getPlaceName, stat -> stat, (existing, replacement) -> existing))
                    .values().stream()
                    .sorted(Comparator.comparingInt(Statistic::getUserRanking))
                    .collect(Collectors.toList());

            return filteredStatistics;
        }

        private void setRanking(List<Statistic> statistics) {
            statistics.sort(Comparator.comparingInt(Statistic::getPlaceLikeNum).reversed());

            int currentRank = 1;
            for (int i = 0; i < statistics.size(); i++) {
                Statistic stat = statistics.get(i);
                if (i > 0 && !stat.getPlaceLikeNum().equals(statistics.get(i - 1).getPlaceLikeNum())) {
                    currentRank = i + 1;
                }
                stat.setUserRanking(currentRank);
            }
        }

    public List<MapStatisticDTO> getTopStatisticsByCategory(String category) {
        List<Statistic> topStatistics = userStatisticRepository.findByCategoryOrderByPlaceLikeNum(category);

        return topStatistics.stream()
                .sorted(Comparator.comparingInt(Statistic::getPlaceLikeNum).reversed())
                .limit(3)
                .map(statistic -> new MapStatisticDTO(statistic.getPlaceName(), statistic.getPlaceImage(), statistic.getPlaceLikeNum()))
                .collect(Collectors.toList());
    }
}
