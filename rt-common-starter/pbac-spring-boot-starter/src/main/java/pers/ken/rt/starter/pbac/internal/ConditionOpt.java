package pers.ken.rt.starter.pbac.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: ConditionOpt
 * @Created: 2024/7/23
 * @Author ken
 */
@AllArgsConstructor
@Getter
public enum ConditionOpt {

    /**
     * Copy from aliyun documents
     */
    StringEquals("String", "StringEquals"),
    StringNotEquals("String", "StringNotEquals"),
    StringEqualsIgnoreCase("String", "StringEqualsIgnoreCase"),
    StringNotEqualsIgnoreCase("String", "StringNotEqualsIgnoreCase"),

    NumericEquals("String", "StringNotEqualsIgnoreCase"),
    NumericNotEquals("String", "StringNotEqualsIgnoreCase"),

    IpAddress("IPAddress", "IpAddress"),
    NotIpAddress("IPAddress", "NotIpAddress");


    private final String type;
    private final String value;


}
