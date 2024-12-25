package pers.ken.rt.starter.pbac.core;

import pers.ken.rt.starter.pbac.exception.BaseAccessManagerException;

/**
 * The interface Permission exception translation.
 *
 * @ClassName: PermissionExceptionTranform
 * @Created: 2023 -11-16 15:46:42
 * @Description:
 * @Author ken
 */
public interface PermissionExceptionTranslation {
    /**
     * Resolve exception.
     *
     * @param ex the ex
     * @return the exception
     * @throws Exception the exception
     */
    default Exception resolve(BaseAccessManagerException ex) throws Exception {
        return ex;
    }

    default Exception resolve(Exception ex) throws Exception {
        return ex;
    }
}
