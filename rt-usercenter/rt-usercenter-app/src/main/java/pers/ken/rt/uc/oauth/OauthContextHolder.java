package pers.ken.rt.uc.oauth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pers.ken.rt.common.utils.BeanMapper;
import pers.ken.rt.uc.oauth.entity.OauthUserDetail;
import pers.ken.rt.uc.usercenter.entity.OauthUser;

/**
 * <name> OauthContextHolder </name>
 * <desc> </desc>
 * Creation Time: 2021/9/28 0:47.
 *
 * @author _Ken.Hu
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OauthContextHolder {
    public static OauthUser getOauthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OauthUserDetail oauthUserDetail = (OauthUserDetail) authentication.getPrincipal();
        return BeanMapper.copyProperties(oauthUserDetail, OauthUser.class);
    }
}
