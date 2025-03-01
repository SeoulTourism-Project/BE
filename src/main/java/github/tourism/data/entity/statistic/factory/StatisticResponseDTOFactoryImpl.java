package github.tourism.data.entity.statistic.factory;

import github.tourism.data.entity.statistic.Gender_Statistic;
import github.tourism.data.entity.statistic.Purpose_Statistic;
import github.tourism.data.entity.statistic.RankPlace;
import github.tourism.data.entity.statistic.VisitList;
import github.tourism.web.dto.statistic.*;
import org.springframework.stereotype.Component;

@Component
public class StatisticResponseDTOFactoryImpl implements StatisticResponseDTOFactory {

    @Override
    public GenderResponseDTO createGenderResponseDTO(Gender_Statistic statistic) {
        GenderResponseDTO dto = new GenderResponseDTO();
        dto.setYear(statistic.getYear());
        dto.setMonth(statistic.getMonth());
        dto.setContinent(statistic.getContinent());
        dto.setCountry(statistic.getCountry());
        dto.setTotalPopulation(statistic.getTotal_population());
        dto.setPreviousTotalPopulation(statistic.getPrevious_total_population());
        dto.setTotalIncrement(statistic.getTotal_increment());
        dto.setMalePopulation(statistic.getMale_population());
        dto.setMaleRatio(statistic.getMale_ratio());
        dto.setFemalePopulation(statistic.getFemale_population());
        dto.setFemaleRatio(statistic.getFemale_ratio());
        return dto;
    }


    @Override
    public PurposeResponseDTO createPurposeResponseDTO(Purpose_Statistic statistic) {
        PurposeResponseDTO dto = new PurposeResponseDTO();
        dto.setYear(statistic.getYear());
        dto.setMonth(statistic.getMonth());
        dto.setContinent(statistic.getContinent());
        dto.setCountry(statistic.getCountry());
        dto.setTotalPopulation(statistic.getTotal_population());
        dto.setPreviousTotalPopulation(statistic.getPrevious_total_population());
        dto.setTotalIncrement(statistic.getTotal_increment());
        dto.setTotalRatio(statistic.getTotal_ratio());
        dto.setTravelPopulation(statistic.getTravel_population());
        dto.setTravelRatio(statistic.getTravel_ratio());
        dto.setCommercialPopulation(statistic.getCommercial_population());
        dto.setCommercialRatio(statistic.getCommercial_ratio());
        dto.setPublicPopulation(statistic.getPublic_population());
        dto.setPublicRatio(statistic.getPublic_ratio());
        dto.setStudyPopulation(statistic.getStudy_population());
        dto.setStudyRatio(statistic.getStudy_ratio());
        dto.setEtcPopulation(statistic.getEtc_population());
        dto.setEtcRatio(statistic.getEtc_ratio());
        return dto;
    }


    @Override
    public RankPlaceResponseDTO createRankPlaceResponseDTO(RankPlace rankPlace) {
        RankPlaceResponseDTO dto = new RankPlaceResponseDTO();
        dto.setRank(rankPlace.getRank());
        dto.setCityName(rankPlace.getCityName());
        dto.setTownshipName(rankPlace.getTownshipName());
        dto.setPlaceName(rankPlace.getPlaceName());
        dto.setYearMonth(rankPlace.getYearMonth());
        dto.setVisitNum(rankPlace.getVisitNum());
        dto.setVisitTotalNum(rankPlace.getVisitTotalNum());
        return dto;
    }

    @Override
    public VisitListResponseDTO createVisitListResponseDTO(VisitList visitList) {
        VisitListResponseDTO dto = new VisitListResponseDTO();
        dto.setRank(visitList.getRank());
        dto.setCityName(visitList.getCityName());
        dto.setTownshipName(visitList.getTownshipName());
        dto.setAdministrativeName(visitList.getAdministrativeName());
        dto.setVisitNum(visitList.getVisitNum());
        dto.setPreviousTotalPopulation(visitList.getPreviousTotalPopulation());
        dto.setRatio(visitList.getRatio());
        dto.setYearMonth(visitList.getYearMonth());
        return dto;
    }
}
