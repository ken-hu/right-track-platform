package pers.ken.rt.common.iam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <code> Statement </code>
 * <desc> Statement </desc>
 * <b>Creation Time:</b> 2022/1/24 22:18.
 *
 * @author _Ken.Hu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statement {
    @Getter
    public enum Effect {
        /**
         * 同意，显式拒绝
         */
        Allow,
        Deny
    }

    private String id;
    private Effect effect;
    private List<Action> actions = new ArrayList<>();
    private List<Resource> resources;
    private List<Condition> conditions = new ArrayList<Condition>();


    public Statement(Effect effect) {
        this.effect = effect;
        this.id = null;
    }
}
