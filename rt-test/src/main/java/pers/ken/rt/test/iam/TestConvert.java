package pers.ken.rt.test.iam;

import pers.ken.rt.iam.internal.ResourceConvert;
import pers.ken.rt.iam.internal.Rn;

/**
 * <code> TestConvert </code>
 * <desc> TestConvert </desc>
 * <b>Creation Time:</b> 2022/8/8 17:40.
 *
 * @author Ken.Hu
 */
public class TestConvert implements ResourceConvert<TestResource> {

    @Override
    public TestResource convert(Rn resource) {
        return null;
    }

    @Override
    public String resourceId() {
        return TestResourceId.TEST;
    }
}