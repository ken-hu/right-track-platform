package pers.ken.rt.auth.controller.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.ken.rt.common.exception.ErrorCode;
import pers.ken.rt.common.model.ErrorResponse;

/**
 * @ClassName: CustomErrorController
 * @Created: 2024/12/5 23:24
 * @Author ken
 */
@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    @ResponseBody
    public ResponseEntity<?> handleError(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.of(ErrorCode.FAILED, "Unknown error")
        );
    }

}
