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
    private String requestId;
    private String requestUrl;
    private String uriQueryParams;
    private String requestBody;
    private String responseBody;
    private String httpMethod;
    private int httpStatus;
    private String mediaType;
    private String serverIp;
    private String headers;
    private String clientIp;
    private String sessionId;
    private long startTimeMillis;
    private long handleTimeMillis;

    @Override
    public String toString() {
        return new StringBuilder()
                .append("RequestLog{")
                .append("\n [requestId]=").append(requestId)
                .append("\n [requestUrl]=").append(requestUrl)
                .append("\n [uriQueryParams]=").append(uriQueryParams)
                .append("\n [requestBody]=").append(requestBody)
                .append("\n [responseBody]=").append(responseBody)
                .append("\n [httpMethod]=").append(httpMethod)
                .append("\n [httpStatus]=").append(httpStatus)
                .append("\n [mediaType]=").append(mediaType)
                .append("\n [serverIp]=").append(serverIp)
                .append("\n [headers]=").append(headers)
                .append("\n [clientIp]=").append(clientIp)
                .append("\n [sessionId]=").append(sessionId)
                .append("\n [startTimeMillis]=").append(startTimeMillis)
                .append("\n [handleTimeMillis]=").append(handleTimeMillis)
                .append("\n }")
                .toString();
    }
}
