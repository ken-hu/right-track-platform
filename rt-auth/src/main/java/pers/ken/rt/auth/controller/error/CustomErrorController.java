package pers.ken.rt.auth.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;

/**
 * @ClassName: CustomErrorController
 * @Created: 2024/12/5 23:24
 * @Author ken
 */
@Controller
public class CustomErrorController implements ErrorController {
//    @RequestMapping("/error")
//    @ResponseBody
//    public ResponseEntity<?> handleError(HttpServletRequest request) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                ErrorResponse.of(ErrorCode.FAILED, "Unknown error")
//        );
//    }

}
