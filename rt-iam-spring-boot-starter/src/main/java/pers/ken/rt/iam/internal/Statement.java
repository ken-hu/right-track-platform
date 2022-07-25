package pers.ken.rt.iam.internal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <code> Statement </code>
 * <desc> Statement </desc>
 * <b>Creation Time:</b> 2022/1/5 18:24.
 *
 * @author _Ken.Hu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statement {

    public enum Effect {
        /**
         * 同意，显式拒绝
         */
        Allow,
        Deny
    }

    public Statement(Effect effect) {
        this.effect = effect;
        this.id = null;
    }

    private String id;
    private Effect effect;
    private List<String> actions = new ArrayList<>();
    private List<String> resources;
}
