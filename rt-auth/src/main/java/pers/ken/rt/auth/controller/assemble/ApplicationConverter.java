package pers.ken.rt.auth.controller.assemble;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.dto.resp.ApplicationCreateResponse;
import pers.ken.rt.auth.dto.resp.ApplicationListResponse;
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
    ApplicationListResponse toList(Application app);

    List<ApplicationListResponse> toList(List<Application> applications);

    ApplicationCreateResponse convert(Application application);
}
