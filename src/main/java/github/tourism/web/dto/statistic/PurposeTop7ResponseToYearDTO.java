package github.tourism.web.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PurposeTop7ResponseToYearDTO {
    private int year;
    private List<PurposeTop7DTO> data;
}
