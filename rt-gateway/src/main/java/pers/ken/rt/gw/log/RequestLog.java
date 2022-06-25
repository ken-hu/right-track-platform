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
        return new StringBuilder()
                .append("RequestLog:")
                .append("[requestId]=").append(requestId)
                .append(",[requestUrl]=").append(url)
                .append(",[uriQueryParams]=").append(queryParams)
                .append(",[requestBody]=").append(requestBody)
                .append(",[responseBody]=").append(responseBody)
                .append(",[httpMethod]=").append(httpMethod)
                .append(",[httpStatus]=").append(httpStatus)
                .append(",[mediaType]=").append(mediaType)
                .append(",[serverIp]=").append(serverIp)
                .append(",[headers]=").append(requestHeaders)
                .append(",[clientIp]=").append(clientIp)
                .append(",[sessionId]=").append(sessionId)
                .append(",[startTimeMillis]=").append(startTimeMillis)
                .append(",[handleTimeMillis]=").append(executeTimeMillis)
                .append("}")
                .toString();
    }
}
