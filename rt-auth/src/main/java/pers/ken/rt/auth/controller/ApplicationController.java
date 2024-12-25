package pers.ken.rt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.controller.convert.ApplicationConverter;
import pers.ken.rt.auth.controller.req.ApplicationCreateReq;
import pers.ken.rt.auth.controller.req.AssignApplicationReq;
import pers.ken.rt.auth.controller.resp.ApplicationListResp;
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
    public void create(@RequestBody @Validated ApplicationCreateReq req) {
        Application application = ApplicationConverter.INSTANCE.convert(req);
        applicationService.create(application);
    }

    @Operation(summary = "应用列表")
    @GetMapping("/v1/applications")
    public List<ApplicationListResp> list() {
        List<Application> applications = applicationService.listByTenant();
        return ApplicationConverter.INSTANCE.toList(applications);
    }

    @Operation(summary = "分配租户应用")
    @PostMapping("/v1/tenant/{tenantId}/applications")
    @AccessManager
    public void assignUserApplications(@PathVariable Integer tenantId,
                                       @RequestBody AssignApplicationReq req) {
        applicationService.assignApplications(tenantId, req.getApplicationIds());
    }
}
