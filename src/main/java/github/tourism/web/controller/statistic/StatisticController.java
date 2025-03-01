package github.tourism.web.controller.statistic;


import github.tourism.service.statistic.StatisticService;
import github.tourism.web.advice.ApiResponse;
import github.tourism.web.dto.statistic.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    //프론트 요청에 의해 맵핑 주소 변경함
    @GetMapping("/genderByAll")
    public ResponseEntity<ApiResponse<List<GenderResponseDTO>>> showGenderStatistic() {
        try {
            List<GenderResponseDTO> genderStatistics = statisticService.getGenderStatistics();
            return ResponseEntity.ok(ApiResponse.onSuccess(genderStatistics));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.onFailure(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    //프론트 요청에 의해 맵핑 주소를 이것만 씀.
    //성별통계-월별 방문인구 많은순으로 상위 7개 나라 조회
    @GetMapping("/gender")
    public ResponseEntity<GenderTop7ResponseDTO> getGenderTop7ByMonth(
            @RequestParam int year, @RequestParam int month) {
        try {
            GenderTop7ResponseDTO responseDTO = statisticService.getGenderTop7ByMonth(year, month);
            return ResponseEntity.ok(responseDTO);
        }   catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //성별통계-연도별 나라의 총방문객수 상위7개 합산 데이터 조회
    @GetMapping("/genderTop7ByYear")
    public ResponseEntity<ApiResponse<List<GenderTop7DTO>>> getGenderTop7ByYear(
            @RequestParam int year) {
        try {
            List<GenderTop7DTO> top7ByYear = statisticService.getGenderTop7ByYear(year);
            return ResponseEntity.ok(ApiResponse.onSuccess(top7ByYear));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.onFailure(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }



    //프론트 요청에 의해 맵핑주소를 변경
    @GetMapping("/purposeByAll")
    public ResponseEntity<ApiResponse<List<PurposeResponseDTO>>> showPurposeStatistic() {
        try {
            List<PurposeResponseDTO> purposeStatistics = statisticService.getPurposeStatistics();
            return ResponseEntity.ok(ApiResponse.onSuccess(purposeStatistics));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.onFailure(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    //목적통계-월별 탑7 나라 조회
    @GetMapping("/purpose")
    public ResponseEntity<PurposeTop7ResponseDTO> getPurposeTop7ByMonth(
            @RequestParam int year, @RequestParam int month) {

        //프론트엔드 API 요청 양식에 따라 수정함.
        try {
            PurposeTop7ResponseDTO responseDTO = statisticService.getTop7PurposeByMonth(year, month);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //목적통계-연도별 탑7 나라 데이터 합산 조회
    @GetMapping("/purposeTop7ByYear")
    public ResponseEntity<ApiResponse<List<PurposeTop7DTO>>> getPurposeTop7ByYear(
            @RequestParam int year) {
        try {
            List<PurposeTop7DTO> top7PurposeByYear = statisticService.getTop7PurposeByYear(year);
            return ResponseEntity.ok(ApiResponse.onSuccess(top7PurposeByYear));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.onFailure(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    // 많이 방문한 여행지
    @GetMapping("/rankplace")
    public ResponseEntity<ApiResponse<List<RankPlaceResponseDTO>>> showRankPlaceStatistic() {
        try {
            List<RankPlaceResponseDTO> rankPlaceStatistics = statisticService.getRankPlace();
            return ResponseEntity.ok(ApiResponse.onSuccess(rankPlaceStatistics));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.onFailure(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }
    @GetMapping("/rankplace/{year}/{month}")
    public ResponseEntity<ApiResponse<List<RankPlaceResponseDTO>>> showDiffMonthRankPlaceStatistic(@PathVariable int year, @PathVariable int month) {
        try {
            List<RankPlaceResponseDTO> rankPlaceStatistics = statisticService.getDiffMonthRankPlace(year, month);
            return ResponseEntity.ok(ApiResponse.onSuccess(rankPlaceStatistics));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.onFailure(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    // 많이 방문한 동네
    @GetMapping("/visitlist")
    public ResponseEntity<ApiResponse<List<VisitListResponseDTO>>> showVisitListStatistic() {
        try {
            List<VisitListResponseDTO> visitListStatistics = statisticService.getVisitListStatistics();
            return ResponseEntity.ok(ApiResponse.onSuccess(visitListStatistics));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.onFailure(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }


}
