package pers.ken.rt.iam.dto.req;

import lombok.Data;

import java.util.List;

/**
 * <code> TestPolicyCreateReq </code>
 * <desc> TestPolicyCreateReq </desc>
 * <b>Creation Time:</b> 2022/8/21 21:59.
 *
 * @author Ken.Hu
 */
@Data
public class TestPolicyCreateReq {
    private List<String> cities;
    private List<String> categories;
}
