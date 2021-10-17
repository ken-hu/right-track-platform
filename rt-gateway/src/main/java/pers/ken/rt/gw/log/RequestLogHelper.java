package pers.ken.rt.gw.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/**
 * <code>RequestLogHelper</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/5/1 0:06.
 *
 * @author _Ken.Hu
 */
@Slf4j
public class RequestLogHelper {

    private static final long EMPTY_RECORD_TIME = -1L;

    private RequestLogHelper() {

    }

    public static Charset getMediaTypeCharset(@Nullable MediaType mediaType) {
        if (Objects.nonNull(mediaType) && mediaType.getCharset() != null) {
            return mediaType.getCharset();
        } else {
            return StandardCharsets.UTF_8;
        }
    }

    public static long getStartTime(HttpHeaders headers) {
        String startTimeStr = headers.getFirst(HttpHeaderCons.REQUEST_START_TIME_HEADER);
        return !StringUtils.isEmpty(startTimeStr) ? Long.parseLong(startTimeStr) : EMPTY_RECORD_TIME;
    }

    public static long getHandleTime(long startTime) {
        long nowTimeMillis = System.currentTimeMillis();
        return startTime == EMPTY_RECORD_TIME ? EMPTY_RECORD_TIME : (nowTimeMillis - startTime);
    }

    public static boolean isNormalRequest(String schema){
        return HttpHeaderCons.HTTP.equals(schema) || HttpHeaderCons.HTTPS.equals(schema);
    }

    public static Mono<Void> doRecord(RequestLog requestLog) {
        log.info("\n Request log print:{}",requestLog);
        return Mono.empty();
    }

    public static boolean isUploadFile(@Nullable MediaType mediaType) {
        if (Objects.isNull(mediaType)) {
            return false;
        }
        return mediaType.equals(MediaType.MULTIPART_FORM_DATA)
                || mediaType.equals(MediaType.IMAGE_GIF)
                || mediaType.equals(MediaType.IMAGE_JPEG)
                || mediaType.equals(MediaType.IMAGE_PNG)
                || mediaType.equals(MediaType.MULTIPART_MIXED)
                || mediaType.equals(MediaType.APPLICATION_OCTET_STREAM);
    }

    public static String genRequestId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
