package pers.ken.rt.auth.repository.po;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PolicyContent
 * @Created: 2024/12/24 15:13
 * @Author ken
 */
@Data
public class PolicyContent {
    private String id;
    private String version;
    private String name;
    private String description;
    private List<Statement> statements = new ArrayList<>();

    @Data
    public static class Statement {
        private String id;
        private String effect;
        private List<String> actions = new ArrayList<>();
        private List<String> resources;
        private Map<String, Object> conditions;
    }
}
