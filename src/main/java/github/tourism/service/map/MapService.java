package github.tourism.service.map;

import github.tourism.data.entity.favPlace.FavPlace;
import github.tourism.data.entity.map.Map;
import github.tourism.data.entity.user.User;
import github.tourism.data.repository.favPlace.FavPlaceRepository;
import github.tourism.data.repository.map.MapRepository;
import github.tourism.data.repository.user.UserRepository;
import github.tourism.web.advice.ErrorCode;
import github.tourism.web.dto.map.MapDetailsDTO;
import github.tourism.web.dto.map.MapsDTO;
import github.tourism.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MapService {

    private final MapRepository mapRepository;
    private final FavPlaceRepository favPlaceRepository;
    private final UserRepository userRepository;

    //전체 조회
    public Page<MapsDTO> getAllMaps(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Map> maps = mapRepository.findAll(pageable);
        Page<MapsDTO> mapPage = maps.map(MapsDTO::new);

        return mapPage;
    }

    //카테고리별로 맵 조회
    public Page<MapsDTO> getMapsByCategory(int page, int size, String category) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Map> mapsBycategory = mapRepository.findByCategory(pageable, category);
        Page<MapsDTO> mapsDTOPage = mapsBycategory.map(MapsDTO::new);
        return mapsDTOPage;
    }

    //상세 페이지 조회
    public MapDetailsDTO getMapDetail(Integer mapId, Integer userId) {
        Map map = mapRepository.findById(mapId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MAPS_NOT_FOUNDED));

        MapDetailsDTO mapDetailsDTO = new MapDetailsDTO(map);

        //사용자가 로그인 했는지 체크여부에 따라 찜 여부 확인
        if (userId != null) {
            boolean isFavorite = favPlaceRepository.existsByUserUserIdAndMapMapId(userId, mapId);
            mapDetailsDTO.setFavorite(isFavorite);
        } else {
            mapDetailsDTO.setFavorite(false); // 로그인되지 않은 경우
        }

        return mapDetailsDTO;
    }
    
    //찜 Toggle
    @Transactional
    public boolean toggleFavoritePlace(Integer userId, Integer mapId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUNDED));

        Map map = mapRepository.findById(mapId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MAPS_NOT_FOUNDED));

        Optional<FavPlace> findFavPlace = favPlaceRepository.findByUserAndMap(user, map);


        if (findFavPlace.isPresent()) {
            //찜 제거
            favPlaceRepository.delete(findFavPlace.get());
            return false;
        } else {
            // 찜 등록
            FavPlace favPlace = new FavPlace(map,user);
            favPlaceRepository.save(favPlace);
            return true;
        }
    }

    // placeName으로 likeMarkCount 조회
    public Integer getLikesByPlaceName(String placeName) {
        Map map = mapRepository.findMapByPlaceName(placeName)
                .orElseThrow(() -> new IllegalArgumentException("찜한 장소가 없습니다."));

        return map.getLikemarkCount();
    }



}
