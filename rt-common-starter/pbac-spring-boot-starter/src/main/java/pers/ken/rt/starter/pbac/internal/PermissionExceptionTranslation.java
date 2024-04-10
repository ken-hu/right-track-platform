package pers.ken.rt.starter.pbac.internal;

/**
 * @ClassName: PermissionExceptionTranform
 * @Created: 2023-11-16 15:46:42
 * @Description:
 * @Author ken
 */
public interface PermissionExceptionTranslation {
    default Exception resolve(Exception ex) throws Exception {
        return ex;
    }
}
