package pers.ken.rt.common.iam;

import lombok.Data;

import java.util.List;

/**
 * <code> Condition </code>
 * <desc> Condition </desc>
 * <b>Creation Time:</b> 2022/1/24 23:05.
 *
 * @author _Ken.Hu
 */
@Data
public class Condition {
    protected String type;
    protected String conditionKey;
    protected List<String> values;
}