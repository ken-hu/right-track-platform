package pers.ken.rt.auth.controller.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PolicyListResp
 * @Created: 2024/12/9 18:14
 * @Author ken
 */
@Data
public class PolicyDetailResp {
    private String id;
    private String version;
    private String name;
    private String description;
    private List<Statement> statements = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Statement {
        private String id;
        private String effect;
        private List<String> actions = new ArrayList<>();
        private List<String> resources;
        private Map<String, Object> conditions;
    }


}
