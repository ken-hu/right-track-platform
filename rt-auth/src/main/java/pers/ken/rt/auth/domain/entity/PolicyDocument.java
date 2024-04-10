package pers.ken.rt.auth.domain.entity;

import lombok.Data;
import pers.ken.rt.auth.domain.entity.valueobj.Statement;
import pers.ken.rt.common.utils.Jackson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ken
 * @className: Policy
 * @createdTime: 2023/3/7 23:59
 * @desc:
 */
@Data
public class PolicyDocument {
    private static final String DEFAULT_POLICY_VERSION = "1.0";
    private String id;
    private String version;
    private List<Statement> statements = new ArrayList<>();

    public static PolicyDocument createFromJson(String json) {
        return Jackson.fromJsonString(json, PolicyDocument.class);
    }
}
