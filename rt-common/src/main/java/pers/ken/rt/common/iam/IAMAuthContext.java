package pers.ken.rt.common.iam;

/**
 * <code> AuthorityContext </code>
 * <desc> AuthorityContext </desc>
 * <b>Creation Time:</b> 2022/3/8 20:43.
 *
 * @author _Ken.Hu
 */
public class IAMAuthContext {
    private static final ThreadLocal<UserDetail> AUTHORITY = new ThreadLocal<>();

    public static UserDetail get() {
        return AUTHORITY.get();
    }

    public static void set(UserDetail userDetail) {
        AUTHORITY.set(userDetail);
    }

    public static void remove() {
        AUTHORITY.remove();
    }
}
