package pers.ken.rt.auth.interfaces.facade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.auth.application.service.UserAppService;
import pers.ken.rt.auth.domain.entity.valueobj.UserStatus;
import pers.ken.rt.auth.interfaces.dto.req.UserUpdateRequest;
import pers.ken.rt.auth.interfaces.dto.resp.UserItemResponse;
import pers.ken.rt.common.model.PageQuery;
import pers.ken.rt.common.model.PageResult;

/**
 * The type User controller.
 *
 * @author Ken
 * @className: UserController
 * @createdTime: 2023 /3/7 1:48
 * @desc:
 */
@Tag(name = "users")
@RestController
@AllArgsConstructor
public class UserController {
    private UserAppService userAppService;

    @Operation(summary = "ListUsers")
    @GetMapping("/v1/users")
    public PageResult<UserItemResponse> list(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer size,
                                             @RequestParam(required = false) String orderBy,
                                             @RequestParam(defaultValue = "desc") String sort) {
        return userAppService.pageQuery(PageQuery.of(page, size, orderBy, sort));
    }


    @Operation(summary = "CreateUser")
    @PostMapping("/v1/users")
    public void create() {

    }


    @Operation(summary = "UpdateUser")
    @PutMapping("/v1/users/{id}")
    public void update(@PathVariable Long id,
                       @RequestBody UserUpdateRequest request) {

    }

    @Operation(summary = "UpdateUserStatus")
    @PutMapping("/v1/users/{id}/status/{status}")
    public void updateStatus(@PathVariable Long id, @PathVariable String status) {
        userAppService.updateUserStatus(id, UserStatus.convert(status));
    }
}
