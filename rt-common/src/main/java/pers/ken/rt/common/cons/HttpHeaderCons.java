package pers.ken.rt.common.cons;

/**
 * <code>RequestHeaderCons</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/5/1 0:07.
 *
 * @author _Ken.Hu
 */
public class HttpHeaderCons {
    private HttpHeaderCons() {
    }

    public static final String REQUEST_ID = "X-Request-ID";
    public static final String REQUEST_START_TIME_HEADER = "X-Request-Start";
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
}
