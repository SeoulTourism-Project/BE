package github.tourism.web.dto.statistic;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurposeTop7DTO {
    private String country;
    private int totalPopulation;
    private int travelPopulation;
    private int commercialPopulation;
    private int publicPopulation;
    private int studyPopulation;
    private int etcPopulation;


    @QueryProjection
    public PurposeTop7DTO(String country, int total_population, int travel_population, int commercial_population, int public_population, int study_population, int etc_population) {
        this.country = country;
        this.totalPopulation = total_population;
        this.travelPopulation = travel_population;
        this.commercialPopulation = commercial_population;
        this.publicPopulation = public_population;
        this.studyPopulation = study_population;
        this.etcPopulation = etc_population;
    }
}


