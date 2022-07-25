package pers.ken.rt.test.iam;

import pers.ken.rt.iam.internal.AbstractResource;

/**
 * <code> MultiResource </code>
 * <desc> MultiResource </desc>
 * <b>Creation Time:</b> 2022/8/19 15:57.
 *
 * @author Ken.Hu
 */
public class CategoryResource extends AbstractResource {
    private final String city;
    private final String category;

    public CategoryResource(String city, String category) {
        super(String.format("map:city/%s/category/%s", city, category));
        this.city = city;
        this.category = category;
    }
}
