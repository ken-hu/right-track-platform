package pers.ken.rt.auth.dto.resp;

import lombok.Data;

/**
 * <code> EnterpriseVO </code>
 * <desc> EnterpriseVO </desc>
 * <b>Creation Time:</b> 2022/2/25 23:59.
 *
 * @author _Ken.Hu
 */
@Data
public class EnterpriseListResp {
    private String name;
    private String description;
    private Long createdTime;
    private Integer userTotalNum;
    private Integer userUsedNum;

}
