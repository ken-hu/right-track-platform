package pers.ken.rt.test.iam;

import lombok.Getter;
import pers.ken.rt.iam.internal.AbstractResource;

/**
 * <code> MultiResource </code>
 * <desc> MultiResource </desc>
 * <b>Creation Time:</b> 2022/8/19 15:57.
 *
 * @author Ken.Hu
 */
@Getter
public class CategoryResource extends AbstractResource {
    private final String cityCode;
    private final String icCode;

    public CategoryResource(String cityCode, String icCode) {
        super(String.format("map:city_code/%s/ic_code/%s", cityCode, icCode));
        this.cityCode = cityCode;
        this.icCode = icCode;
    }
}
