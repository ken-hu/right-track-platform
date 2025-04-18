package pers.ken.rt.auth.controller.assemble;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.dto.resp.GetUserDetailResponse;
import pers.ken.rt.auth.dto.resp.ListUserResponse;
import pers.ken.rt.auth.oauth.model.AuthUserDetails;
import pers.ken.rt.auth.oauth.utils.AccountContext;
import pers.ken.rt.auth.repository.po.Account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: AccountAssembler
 * @Created: 2024/12/9 20:07
 * @Author ken
 */
@Mapper
public interface AccountConverter {
    AccountConverter INSTANCE = Mappers.getMapper(AccountConverter.class);

    ListUserResponse convert(Account account);

    List<ListUserResponse> convert(List<Account> accounts);

    default GetUserDetailResponse toDetail(Account account) {
        AuthUserDetails userDetails = AccountContext.getUserDetails();
        return GetUserDetailResponse.builder()
            .userId(account.getId())
            .nickname(account.getNickname())
            .username(account.getUsername())
            .tenantId(userDetails.getTenantId())
            .tenantCode(userDetails.getTenantCode())
            .roles(userDetails.getRoles())
            .deptCodes(Optional.ofNullable(account.getDeptCodes()).map(Arrays::asList).orElse(new ArrayList<>()))
            .build();
    }

    ;
}
