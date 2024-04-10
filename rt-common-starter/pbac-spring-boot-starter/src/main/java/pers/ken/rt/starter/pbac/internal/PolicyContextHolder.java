package pers.ken.rt.starter.pbac.internal;

/**
 * Creation Time: 2022/11/14 14:47.
 *
 * @author _Ken.Hu
 */
public class PolicyContextHolder {
    private static final ThreadLocal<PolicyContext> AUTHORITY = new ThreadLocal<>();

    public static PolicyContext get() {
        return AUTHORITY.get();
    }

    public static void set(PolicyContext detail) {
        AUTHORITY.set(detail);
    }

    public static void remove() {
        AUTHORITY.remove();
    }
}
