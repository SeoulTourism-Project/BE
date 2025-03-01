package github.tourism.web.dto.goods;

import github.tourism.data.entity.goods.Goods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodDetailResponse {

    private Integer goodId;
    private String goodName;
    private String goodImage;
    private String categoryName;
    private Integer goodPrice;
    private Integer goodStock;
    private String goodDescription;

    public static GoodDetailResponse from(Goods goods) {
        return new GoodDetailResponse(
                goods.getGoodId(),
                goods.getGoodsName(),
                goods.getGoodsImage(),
                goods.getCategory(),
                goods.getPrice(),
                goods.getStockQuantity(),
                goods.getSummary()
        );
    }
}
