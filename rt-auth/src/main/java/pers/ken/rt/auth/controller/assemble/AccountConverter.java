package pers.ken.rt.auth.controller.assemble;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.dto.resp.ListUserResponse;
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

    ListUserResponse convert(Account account);

    List<ListUserResponse> convert(List<Account> accounts);
}
