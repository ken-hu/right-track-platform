package pers.ken.rt.gw.log;

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

    public static final String REQUEST_ID_HEADER = "x-request-id";
    public static final String REQUEST_START_TIME_HEADER = "x-request-start";
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String TRACE_ID_HEADER = "traceId";
}
