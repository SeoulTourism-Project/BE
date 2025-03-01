package github.tourism.service.statistic;

import github.tourism.data.entity.statistic.Gender_Statistic;
import github.tourism.data.entity.statistic.Purpose_Statistic;
import github.tourism.data.entity.statistic.RankPlace;
import github.tourism.data.entity.statistic.VisitList;
import github.tourism.data.entity.statistic.factory.StatisticResponseDTOFactory;
import github.tourism.data.repository.statistic.Gender_Repository;
import github.tourism.data.repository.statistic.PurposeRepository;
import github.tourism.data.repository.statistic.RankPlaceRepository;
import github.tourism.data.repository.statistic.VisitListStatisticRepository;
import github.tourism.web.dto.statistic.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticService {

    private final Gender_Repository gender_Repository;
    private final PurposeRepository purpose_Repository;
    private final RankPlaceRepository rankPlaceRepository;
    private final VisitListStatisticRepository visitListStatisticRepository;

    private final StatisticResponseDTOFactory statisticResponseDTOFactory;

    // 성별테이블 통계 전체 조회
    public List<GenderResponseDTO> getGenderStatistics() {
        List<Gender_Statistic> genderStatistics = gender_Repository.findAll();
        return genderStatistics.stream()
                .map(statisticResponseDTOFactory::createGenderResponseDTO)
                .collect(Collectors.toList());
    }

    //성별테이블에서 연도와 월에따라 상위7개 총인구 순위 조회
    public GenderTop7ResponseDTO getGenderTop7ByMonth(int year, int month) {
        List<GenderTop7DTO> top7List = gender_Repository.findTop7ByYearAndMonth(year, month);

        return new GenderTop7ResponseDTO(year,month,top7List);
    }
    //성별테이블에서 연도에 따라 각나라의 총 월 방문객수를 합산하여 총 방문객,총 성별 방문객을 조회
    public List<GenderTop7DTO> getGenderTop7ByYear(int year) {
        return gender_Repository.findTop7CountriesByYear(year);
    }

    // 목적 통계 전체 조회
    public List<PurposeResponseDTO> getPurposeStatistics() {
        List<Purpose_Statistic> purposeStatistics = purpose_Repository.findAll();
        return purposeStatistics.stream()
                .map(statisticResponseDTOFactory::createPurposeResponseDTO)
                .collect(Collectors.toList());
    }

    //목적 통계에서 월별 총 방문객수 상위 7개 나라 데이터 조회
    public PurposeTop7ResponseDTO getTop7PurposeByMonth(int year, int month) {

        List<PurposeTop7DTO> top7List = purpose_Repository.findTop7ByMonth(year, month);

        return new PurposeTop7ResponseDTO(year,month,top7List);
    }

    //목적 통계에서 년별 총 방문객수 상위 7개 나라 데이터 조회
    public List<PurposeTop7DTO> getTop7PurposeByYear(int year) {
        return purpose_Repository.findTop7ByYear(year);
    }

    public List<RankPlaceResponseDTO> getRankPlace() {
        List<RankPlace> rankPlaces = rankPlaceRepository.findAll();

        Map<String, Optional<RankPlace>> latestRankPlacesMap = rankPlaces.stream()
                .collect(Collectors.groupingBy(RankPlace::getPlaceName,
                        Collectors.reducing((rankPlace1, rankPlace2) ->
                                rankPlace1.getYearMonth().compareTo(rankPlace2.getYearMonth()) > 0 ? rankPlace1 : rankPlace2)));

        List<RankPlace> latestRankPlaces = latestRankPlacesMap.values().stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        return latestRankPlaces.stream()
                .sorted(Comparator.comparingInt(RankPlace::getVisitNum).reversed())
                .map(statisticResponseDTOFactory::createRankPlaceResponseDTO)
                .collect(Collectors.toList());
    }

    public List<RankPlaceResponseDTO> getDiffMonthRankPlace(int year, int month) {
        String yearMonth = String.format("%04d%02d", year, month);

        List<RankPlace> rankPlaces = rankPlaceRepository.findByYearMonth(yearMonth);

        return rankPlaces.stream()
                .map(statisticResponseDTOFactory::createRankPlaceResponseDTO)
                .collect(Collectors.toList());
    }

    public List<VisitListResponseDTO> getVisitListStatistics() {
        List<VisitList> visitLists = visitListStatisticRepository.findAll();
        return visitLists.stream()
                .sorted(Comparator.comparingInt(VisitList::getVisitNum).reversed())
                .map(statisticResponseDTOFactory::createVisitListResponseDTO)
                .collect(Collectors.toList());
    }

}
