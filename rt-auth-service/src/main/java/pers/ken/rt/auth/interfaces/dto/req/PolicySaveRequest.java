package pers.ken.rt.auth.interfaces.dto.req;

import jakarta.validation.constraints.NotBlank;

/**
 * Name: PolicyCreateReq
 * Creation Time: 2023/1/7 23:35.
 *
 * @author Ken
 */
public record PolicySaveRequest(Long id, String version, @NotBlank String policyDocument) {
}
