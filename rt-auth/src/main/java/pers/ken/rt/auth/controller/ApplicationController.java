package pers.ken.rt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.auth.controller.assemble.ApplicationConverter;
import pers.ken.rt.auth.dto.req.ApplicationCreateRequest;
import pers.ken.rt.auth.dto.resp.ApplicationCreateResponse;
import pers.ken.rt.auth.dto.resp.ApplicationListResponse;
import pers.ken.rt.auth.repository.po.Application;
import pers.ken.rt.auth.service.ApplicationService;
import pers.ken.rt.starter.pbac.annotation.AccessManager;

import java.util.List;

/**
 * @ClassName: ApplicationController
 * @Created: 2024/12/7 18:18
 * @Author ken
 */
@Tag(name = "application", description = "应用")
@RestController
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @AccessManager
    @Operation(summary = "创建应用")
    @PostMapping("/v1/applications")
    public ApplicationCreateResponse applicationCreate(@RequestBody @Validated ApplicationCreateRequest request) {
        Application application = applicationService.createApplication(request);
        return ApplicationConverter.INSTANCE.convert(application);
    }

    @Operation(summary = "应用列表")
    @GetMapping("/v1/applications")
    public List<ApplicationListResponse> listApplications() {
        List<Application> applications = applicationService.listApplications();
        return ApplicationConverter.INSTANCE.toList(applications);
    }
}
