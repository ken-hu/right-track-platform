package pers.ken.rt.common.iam;

/**
 * <code> AuthorityContext </code>
 * <desc> AuthorityContext </desc>
 * <b>Creation Time:</b> 2022/3/8 20:43.
 *
 * @author _Ken.Hu
 */
public class IAMAuthContext {
    private static final ThreadLocal<UserAuthority> AUTHORITY = new ThreadLocal<>();

    public static UserAuthority get() {
        return AUTHORITY.get();
    }

    public static void set(UserAuthority userAuthority) {
        AUTHORITY.set(userAuthority);
    }

    public static void remove() {
        AUTHORITY.remove();
    }
}
