package github.tourism.web.dto.statistic;

import com.querydsl.core.annotations.QueryProjection;
import github.tourism.data.entity.statistic.Gender_Statistic;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenderTop7DTO {
    private String country;
    private int totalPopulation;
    private int malePopulation;
    private int femalePopulation;

    @QueryProjection
    public GenderTop7DTO(String country, int total_Population, int male_Population,
                         int female_Population) {
        this.country = country;
        this.totalPopulation = total_Population;
        this.malePopulation = male_Population;
        this.femalePopulation = female_Population;
    }
}
