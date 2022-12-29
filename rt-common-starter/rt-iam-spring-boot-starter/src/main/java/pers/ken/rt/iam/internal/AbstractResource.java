package pers.ken.rt.iam.internal;

/**
 * <code> PolicyResource </code>
 * <desc> PolicyResource </desc>
 * <b>Creation Time:</b> 2022/8/8 11:01.
 *
 * @author Ken.Hu
 */
public abstract class AbstractResource {
    private final String resource;

    public AbstractResource(String resource) {
        this.resource = resource;
    }

    public String getId() {
        return resource;
    }

}
