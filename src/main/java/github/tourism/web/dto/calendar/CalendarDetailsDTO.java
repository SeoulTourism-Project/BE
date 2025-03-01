package github.tourism.web.dto.calendar;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CalendarDetailsDTO {
    private Integer calendarDetailsId;
//    private String placeName;
    private LocalDateTime scheduleTime;
    private LocalDateTime scheduleEndTime;
    private String memo;
    private String favPlaceName; // FavPlace에서 가져온 이름
    private String favPlaceImage; // FavPlace에서 가져온 이미지
    private String favPlaceLocation; // FavPlace에서 가져온 위치
    private Integer mapId;

    public CalendarDetailsDTO(Integer calendarDetailsId, LocalDateTime scheduleTime,
                              LocalDateTime scheduleEndTime,
                              String memo, String favPlaceName, String favPlaceImage, String favPlaceLocation, Integer mapId) {
        this.calendarDetailsId = calendarDetailsId;
//        this.placeName = placeName
        this.scheduleTime = scheduleTime;
        this.scheduleEndTime = scheduleEndTime;
        this.memo = memo;
        this.favPlaceName = favPlaceName;
        this.favPlaceImage = favPlaceImage;
        this.favPlaceLocation = favPlaceLocation;
        this.mapId = mapId;
    }
}
