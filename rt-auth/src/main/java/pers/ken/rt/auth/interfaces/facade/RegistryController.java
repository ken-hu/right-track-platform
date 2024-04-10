package pers.ken.rt.auth.interfaces.facade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.auth.interfaces.dto.req.RegistryRequest;

/**
 * @author Ken
 * @className: RegistryController
 * @createdTime: 2023/3/8 1:09
 * @desc:
 */
@RestController
@AllArgsConstructor
@Tag(name = "registry")
public class RegistryController {
    @Operation(summary = "RegistryAccount")
    @PostMapping("/v1/user/registry")
    public void registry(@RequestBody RegistryRequest req) {

    }
}
