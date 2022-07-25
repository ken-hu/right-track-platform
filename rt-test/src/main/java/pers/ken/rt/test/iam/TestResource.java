package pers.ken.rt.test.iam;

import lombok.Getter;
import pers.ken.rt.iam.internal.AbstractResource;

/**
 * <code> TestResource </code>
 * <desc> TestResource </desc>
 * <b>Creation Time:</b> 2022/8/8 17:05.
 *
 * @author Ken.Hu
 */
@Getter
public class TestResource extends AbstractResource {

    private final String value;

    public TestResource(String value) {
        super(String.format("map:test/%s", value));
        this.value = value;
    }
}
