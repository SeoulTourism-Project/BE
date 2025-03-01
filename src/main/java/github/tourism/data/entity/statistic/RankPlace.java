package github.tourism.data.entity.statistic;


import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rank_place")
@Builder
@ToString
public class RankPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Integer rankId;
    @Column(name = "rank")
    private Integer rank;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "township_name")
    private String townshipName;
    @Column(name = "place_name")
    private String placeName;
    @Column(name = "year_month")
    private String yearMonth;
    @Column(name = "visit_num")
    private Integer visitNum;
    @Column(name = "visit_total_num")
    private Integer visitTotalNum;
}
