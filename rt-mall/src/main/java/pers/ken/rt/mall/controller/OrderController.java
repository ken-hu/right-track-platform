package pers.ken.rt.mall.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TenantController
 * @Created: 2024/11/3 20:58
 * @Author ken
 */
@RestController
public class OrderController {
    @GetMapping("/v1/tenants/{tenantId}/orders")
    @Operation(summary = "租户下的订单")
    public void tenantOrderList(@PathVariable Integer tenantId) {
    }
}
