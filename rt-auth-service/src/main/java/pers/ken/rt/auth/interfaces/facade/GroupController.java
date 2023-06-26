package pers.ken.rt.auth.interfaces.facade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.application.service.GroupAppService;

/**
 * @author Ken
 * @className: GroupController
 * @createdTime: 2023/3/7 2:02
 * @desc:
 */
@Tag(name = "group")
@RestController
@AllArgsConstructor
public class GroupController {

    private GroupAppService groupAppService;

    @Operation(summary = "ListGroups")
    @GetMapping("/v1/groups")
    public void pageQuery(@RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer size) {

    }

    @Operation(summary = "CreateUser")
    @PostMapping("/v1/groups")
    public void create() {
    }

    @Operation(summary = "UpdateUser")
    @PutMapping("/v1/groups/{id}")
    public void update(@PathVariable("id") Long id) {
    }

    @Operation(summary = "DeleteUser")
    @DeleteMapping("/v1/groups/{id}")
    public void delete(@PathVariable("id") Long id) {
    }

    @Operation(summary = "AddUserToGroup")
    @PutMapping("/v1/groups/{id}/users")
    public void addUserToGroup(@PathVariable("id") Long id) {

    }

    @Operation(summary = "RemoveUserFromGroup")
    @DeleteMapping("/v1/groups/{id}/users")
    public void removeUserFromGroup(@PathVariable("id") Long id) {

    }
}
