package pers.ken.rt.starter.pbac.internal;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
@Builder
public class PolicyDocument {
    private static final String DEFAULT_POLICY_VERSION = "1.0";

    private String id;
    private String version;
    private String name;
    private String description;
    private List<Statement> statements = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Statement {
        public Statement(Effect effect) {
            this.effect = effect;
            this.id = null;
        }

        private String id;
        private Effect effect;
        @Builder.Default
        private List<String> actions = new ArrayList<>();
        private List<String> resources;
        private Map<String, Object> conditions;
    }


    @Getter
    public enum Effect {
        /**
         * 同意，拒绝
         */
        Allow,
        Deny
    }
}
