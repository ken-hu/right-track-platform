package pers.ken.rt.iam.internal;

/**
 * Creation Time: 2022/11/14 14:47.
 *
 * @author _Ken.Hu
 */
public class AuthContext {
    private static final ThreadLocal<ResourceAccessDetail> AUTHORITY = new ThreadLocal<>();


    public static ResourceAccessDetail get() {
        return AUTHORITY.get();
    }

    public static void set(ResourceAccessDetail detail) {
        AUTHORITY.set(detail);
    }

    public static void remove() {
        AUTHORITY.remove();
    }
}
