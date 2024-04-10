package pers.ken.rt.auth.interfaces.facade;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.auth.application.service.UserAppService;
import pers.ken.rt.auth.interfaces.dto.req.UserListReq;
import pers.ken.rt.auth.interfaces.dto.req.UserUpdateRequest;
import pers.ken.rt.auth.interfaces.dto.resp.UserItemResponse;
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

    @GetMapping("/v1/users/me")
    public void me() {

    }

    @GetMapping("/v1/users")
    public PageResult<UserItemResponse> list(UserListReq req) {
        return userAppService.pageQuery(req);
    }


    @PostMapping("/v1/users")
    public void create() {

    }


    @PutMapping("/v1/users/{id}")
    public void update(@PathVariable Long id,
                       @RequestBody UserUpdateRequest request) {

    }

    @PutMapping("/v1/users/{id}/status/enabled")
    public void enableUser(@PathVariable Long id) {
    }

    @PutMapping("/v1/users/{id}/status/disabled")
    public void disableUser(@PathVariable Long id) {
    }
}
