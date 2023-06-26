package pers.ken.rt.starter.pbac.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class Policy {
    private static final String DEFAULT_POLICY_VERSION = "1.0";

    private String id;
    private String version;
    private String name;
    private String description;
    private List<Statement> statements = new ArrayList<>();
}
