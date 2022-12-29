package pers.ken.rt.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.auth.service.RoleService;

/**
 * <name> RoleController </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 22:51.
 *
 * @author _Ken.Hu
 */
@RestController
@AllArgsConstructor
@Tag(name = "Role")
public class RoleController {
    private RoleService roleService;

    @PutMapping("/role")
    public void roleCreate() {
        roleService.create();
    }
}
