package pers.ken.rt.gw.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <code> RequestLog </code>
 * <desc> RequestLog <desc/>
 * <b>Creation Time:</b> 2021/4/29 23:45.
 *
 * @author _Ken.Hu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLog {
    private String respContentType;
    private String method;
    private String requestId;
    private String url;
    private String queryParams;
    private String requestBody;
    private String responseBody;
    private String httpMethod;
    private int httpStatus;
    private String mediaType;
    private String serverIp;
    private String requestHeaders;
    private String clientIp;
    private String sessionId;
    private long startTimeMillis;
    private long executeTimeMillis;

    @Override
    public String toString() {
        return "RequestLog:" +
                "[requestId]=" + requestId +
                ",[requestUrl]=" + url +
                ",[uriQueryParams]=" + queryParams +
                ",[requestBody]=" + requestBody +
                ",[responseBody]=" + responseBody +
                ",[httpMethod]=" + httpMethod +
                ",[httpStatus]=" + httpStatus +
                ",[mediaType]=" + mediaType +
                ",[serverIp]=" + serverIp +
                ",[headers]=" + requestHeaders +
                ",[clientIp]=" + clientIp +
                ",[sessionId]=" + sessionId +
                ",[startTimeMillis]=" + startTimeMillis +
                ",[handleTimeMillis]=" + executeTimeMillis +
                "}";
    }
}
