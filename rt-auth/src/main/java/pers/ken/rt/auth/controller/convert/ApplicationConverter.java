package pers.ken.rt.auth.controller.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.controller.req.ApplicationCreateReq;
import pers.ken.rt.auth.controller.resp.ApplicationListResp;
import pers.ken.rt.auth.repository.po.Application;

import java.util.List;

/**
 * @ClassName: ApplicationAssembler
 * @Created: 2024/12/11 17:46
 * @Author ken
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApplicationConverter {
    ApplicationConverter INSTANCE = Mappers.getMapper(ApplicationConverter.class);

    @Mapping(source = "appCode", target = "code")
    ApplicationListResp toList(Application app);

    List<ApplicationListResp> toList(List<Application> applications);


    @Mapping(target = "appCode", source = "code")
    Application convert(ApplicationCreateReq req);

}
