package github.tourism.web.controller.map;

import github.tourism.service.map.MapService;
import github.tourism.service.user.security.CustomUserDetails;
import github.tourism.web.dto.map.MapDetailsDTO;
import github.tourism.web.dto.map.MapsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maps")
public class MapController {

    private final MapService mapService;


    //전체 관광지 조회용
    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllMaps(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "12") int size)
    {
        Page<MapsDTO> allMaps = mapService.getAllMaps(page, size);

        //프론트측에서 요청한 양식대로 변경
        Map<String, Object> response = new HashMap<>();
        response.put("totalPages", allMaps.getTotalPages());
        response.put("currentPage", allMaps.getNumber());
        response.put("maps", allMaps.getContent());

        return ResponseEntity.ok(response);
    }

    //카테고리별로 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<PagedModel<MapsDTO>> getMapsByCategory(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "12") int size,
            @PathVariable String category) {
        Page<MapsDTO> mapsByCategory = mapService.getMapsByCategory(page, size, category);
        return ResponseEntity.ok(new PagedModel<>(mapsByCategory));
    }


    //맵 상세조회
    @GetMapping("/{mapId}")
    public ResponseEntity<MapDetailsDTO> getMapDetail(@PathVariable Integer mapId) {

        //현재 사용자 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Integer userId = null;

        if(authentication != null && authentication.isAuthenticated()){
            Object principal = authentication.getPrincipal();
            System.out.println("🔍 authentication.getPrincipal() 결과: " + principal);

            if(principal instanceof CustomUserDetails){
              CustomUserDetails userDetails = (CustomUserDetails) principal;
              userId = Integer.valueOf(userDetails.getUserId());
            System.out.println("로그인한 사용자 요청 - userId: " + userId);
            } else {
                System.out.println("비회원 조회 - anonymousUser(principal: " + principal +")");
            }
        } else {
            System.out.println("비회원 조회 요청 - authentication null");
        }

        MapDetailsDTO mapDetails = mapService.getMapDetail(mapId,userId);
        return ResponseEntity.ok(mapDetails);
    }


    //찜 토글하기
    @PreAuthorize("isAuthenticated()") //로그인 체크
    @PostMapping("/{mapId}/new")
    public ResponseEntity<Boolean> toggleFavPlace(@AuthenticationPrincipal CustomUserDetails user,
                                                    @PathVariable Integer mapId) {

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Integer userId = Integer.valueOf(user.getUserId());
        boolean isFavorite = mapService.toggleFavoritePlace(userId, mapId);

        return ResponseEntity.ok(isFavorite);
    }

    // placeName으로 likeMarkCount 조회
    @GetMapping("/{placeName}/likes")
    public ResponseEntity<Integer> getPlaceLikes(@PathVariable String placeName) {
        Integer likeMarkCount = mapService.getLikesByPlaceName(placeName);

        return ResponseEntity.ok(likeMarkCount);
    }





}
