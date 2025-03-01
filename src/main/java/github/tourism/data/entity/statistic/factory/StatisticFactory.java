package github.tourism.data.entity.statistic.factory;

import github.tourism.data.entity.map.Map;
import github.tourism.data.entity.statistic.Statistic;
import org.springframework.stereotype.Component;

@Component
public class StatisticFactory {
    public Statistic createStatistic(Map map) {
        return Statistic.builder()
                .mapId(map.getMapId())
                .placeName(map.getPlace_name())
                .placeLikeNum(map.getLikemarkCount())
                .placeImage(map.getPlace_image())
                .category(map.getCategory())
                .userRanking(0)
                .build();
    }
}