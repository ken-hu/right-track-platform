package pers.ken.rt.auth.domain.exception;

import pers.ken.rt.common.exception.ErrorCodeInterface;
import pers.ken.rt.common.exception.ServiceException;

/**
 * @ClassName: UserException
 * @CreatedTime: 2023/1/16 18:22
 * @Desc:
 * @Author Ken
 */
public class UserException extends ServiceException {

    public UserException(ErrorCodeInterface errorCode) {
        super(errorCode);
    }
}
