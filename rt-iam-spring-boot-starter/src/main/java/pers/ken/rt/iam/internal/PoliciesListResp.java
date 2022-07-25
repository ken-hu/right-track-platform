package pers.ken.rt.iam.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <code> PoliciesListResp </code>
 * <desc> PoliciesListResp </desc>
 * <b>Creation Time:</b> 2022/8/2 15:27.
 *
 * @author Ken.Hu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoliciesListResp {
    private List<Policy> policies;
}
