package github.tourism.web.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

@Data
@AllArgsConstructor
public class MapStatisticDTO {
    private String placeName;
    private String placeImage;
    private Integer placeLikeNum;
}