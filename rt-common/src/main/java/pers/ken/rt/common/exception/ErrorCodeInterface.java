package pers.ken.rt.common.exception;

/**
 * <code> ErrorCodeInterface </code>
 * <desc> ErrorCodeInterface </desc>
 * <b>Creation Time:</b> 2022/5/12 22:55.
 *
 * @author Ken.Hu
 */
public interface ErrorCodeInterface {
    /**
     * Gets code.
     *
     * @return the code
     */
    int getCode();


    /**
     * Gets message.
     *
     * @return the message
     */
    String getMessage();

    /**
     * Detail string.
     *
     * @return the string
     */
    String getDetail();
}
