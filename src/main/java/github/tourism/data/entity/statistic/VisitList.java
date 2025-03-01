package github.tourism.data.entity.statistic;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "visitlist")
@Builder
@ToString
public class VisitList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitlist_id")
    private Integer visitListId;
    @Column(name = "rank")
    private Integer rank;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "township_name")
    private String townshipName;
    @Column(name = "administrative_name")
    private String administrativeName;
    @Column(name = "visit_num")
    private Integer visitNum;
    @Column(name = "previous_total_population")
    private Integer previousTotalPopulation;
    @Column(name = "ratio")
    private Double ratio;
    @Column(name = "year_month")
    private String yearMonth;

}
