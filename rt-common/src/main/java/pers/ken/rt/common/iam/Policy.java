package pers.ken.rt.common.iam;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.ken.rt.common.iam.internal.JsonPolicyReader;

import java.util.ArrayList;
import java.util.List;

/**
 * <code> Policy </code>
 * <desc> Policy </desc>
 * <b>Creation Time:</b> 2022/1/24 22:19.
 *
 * @author _Ken.Hu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Policy {
    private static final String DEFAULT_POLICY_VERSION = "1.0";

    private String id;
    private String version;
    private List<Statement> statements = new ArrayList<>();

    public static Policy fromJson(String jsonStr){
        return new JsonPolicyReader().createPolicyFromJsonString(jsonStr);
    }
}
