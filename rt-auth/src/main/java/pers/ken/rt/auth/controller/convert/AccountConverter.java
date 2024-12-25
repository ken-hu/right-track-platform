package pers.ken.rt.auth.controller.convert;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.controller.resp.UserListResp;
import pers.ken.rt.auth.repository.po.Account;

import java.util.List;

/**
 * @ClassName: AccountAssembler
 * @Created: 2024/12/9 20:07
 * @Author ken
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountConverter {
    AccountConverter INSTANCE = Mappers.getMapper(AccountConverter.class);

    UserListResp convert(Account account);

    List<UserListResp> convert(List<Account> accounts);
}
