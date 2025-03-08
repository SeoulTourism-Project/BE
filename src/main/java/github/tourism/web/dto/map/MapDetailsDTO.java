package github.tourism.web.dto.map;

import github.tourism.data.entity.map.Map;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MapDetailsDTO {

    private Integer map_id;
    private String place_name;
    private String place_image;
    private String place_location;
    private String place_details_info;
    private String operation_time;
    private String close_date;
    private String place_contact_num;

    //프론트에서 미구현
//    private String category;
    private BigDecimal lat;
    private BigDecimal lng;
    private boolean isFavorite;
    private Integer likemarkCount;

    public MapDetailsDTO(Map map) {
        this.map_id = map.getMapId();
        this.place_name = map.getPlace_name();
        this.place_image = map.getPlace_image();
        this.place_details_info = map.getPlace_info();
        this.operation_time = map.getOperation_time();
        this.close_date = map.getClose_date();
        this.place_location = map.getPlace_location();
        this.place_contact_num = map.getPlace_tel();
//        this.category = map.getCategory();
        this.lat = map.getLat();
        this.lng = map.getLng();
        this.likemarkCount = map.getLikemarkCount();
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
