package github.tourism.data.entity.statistic;

import jakarta.persistence.*;
import lombok.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UserStatistics")
@Builder
@ToString
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_statistics_id")
    private Integer statisticsId;
    @Column(name = "map_id")
    private Integer mapId;
    @Column(name = "user_ranking")
    private Integer userRanking;
    @Column(name = "place_name")
    private String placeName;
    @Column(name = "place_like_num")
    private Integer placeLikeNum;
    @Column(name = "category")
    private String category;
    @Column(name = "place_image")
    private String placeImage;
}
