package pers.ken.rt.uaa.common;

import lombok.Getter;
import pers.ken.rt.common.iam.Resource;

/**
 * <code> EnterpriseResource </code>
 * <desc> EnterpriseResource </desc>
 * <b>Creation Time:</b> 2022/3/8 21:59.
 *
 * @author _Ken.Hu
 */
@Getter
public class EnterpriseResource extends Resource implements UcResource {

    private final String id;

    public EnterpriseResource(String id) {
        super(String.format("rt:uc:enterprise:%s", id));
        this.id = id;
    }

    @Override
    public String type() {
        return "enterprise";
    }
}
