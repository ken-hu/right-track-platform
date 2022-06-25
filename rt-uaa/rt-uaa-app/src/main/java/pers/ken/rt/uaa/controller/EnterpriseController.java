package pers.ken.rt.uaa.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.ken.rt.uaa.dto.resp.EnterpriseListResp;
import pers.ken.rt.uaa.service.EnterpriseService;

import java.util.List;

/**
 * <code> EnterpriseController </code>
 * <desc> EnterpriseController </desc>
 * <b>Creation Time:</b> 2022/2/25 23:57.
 *
 * @author _Ken.Hu
 */
@AllArgsConstructor
@RestController
@Tag(name = "Enterprise")
public class EnterpriseController {
    private EnterpriseService enterpriseService;

    @GetMapping(value = "/enterprise/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EnterpriseListResp> listAll() {
        return enterpriseService.listAll();
    }
}
