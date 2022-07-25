package pers.ken.rt.iam.internal;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <code> Policy </code>
 * <desc> Policy </desc>
 * <b>Creation Time:</b> 2022/1/5 18:23.
 *
 * @author _Ken.Hu
 */
@Data
public class Policy {
    private static final String DEFAULT_POLICY_VERSION = "1.0";

    private String id;
    private String version;
    private String name;
    private String description;
    private List<Statement> statements = new ArrayList<>();
}
