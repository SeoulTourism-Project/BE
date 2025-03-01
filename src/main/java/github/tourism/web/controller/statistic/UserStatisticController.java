package github.tourism.web.controller.statistic;

import github.tourism.config.util.webSocket.WebSocketController;
import github.tourism.data.entity.statistic.Statistic;
import github.tourism.service.statistic.UserStatisticService;
import github.tourism.web.advice.ApiResponse;
import github.tourism.web.dto.statistic.MapStatisticDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/statistic")
public class UserStatisticController {
    private final UserStatisticService userStatisticService;

    @GetMapping("/{category}")
    public ResponseEntity<ApiResponse<List<Statistic>>> getStatistics(@PathVariable String category) {
        List<Statistic> statistics = userStatisticService.updatePlaceAndLikeCount(category);
        return ResponseEntity.ok(ApiResponse.onSuccess(statistics));
    }

    @GetMapping("/{category}/top")
    public ResponseEntity<ApiResponse<List<MapStatisticDTO>>> getTopStatisticsByCategory(@PathVariable String category) {
        List<MapStatisticDTO> topStatistics = userStatisticService.getTopStatisticsByCategory(category);
        return ResponseEntity.ok(ApiResponse.onSuccess(topStatistics));
    }
}


