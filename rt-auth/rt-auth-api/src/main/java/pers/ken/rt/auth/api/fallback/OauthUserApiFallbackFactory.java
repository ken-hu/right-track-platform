package pers.ken.rt.auth.api.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import pers.ken.rt.common.exception.MicroServiceException;
import pers.ken.rt.common.exception.ServiceCode;
import pers.ken.rt.auth.api.OauthUserApi;

/**
 * <code> OauthUserApiFallbackFactory </code>
 * <desc> OauthUserApiFallbackFactory </desc>
 * <b>Creation Time:</b> 2021/10/18 23:56.
 *
 * @author _Ken.Hu
 */
@Slf4j
public class OauthUserApiFallbackFactory implements FallbackFactory<OauthUserApi> {
    @Override
    public OauthUserApi create(Throwable cause) {
        return username -> {
            throw new MicroServiceException(ServiceCode.INTERNAL_ERROR);
        };
    }
}
