package pers.ken.rt.starter.pbac.permission.data;

import java.util.List;

/**
 * @ClassName: DataScopeContext
 * @Created: 2025/3/12 17:45
 * @Author ken
 */
public class DataPermissionContextHolder {

    private static final ThreadLocal<List<DataPermission>> AUTHORITY = new ThreadLocal<>();

    public static List<DataPermission> get() {
        return AUTHORITY.get();
    }

    public static void set(List<DataPermission> detail) {
        AUTHORITY.set(detail);
    }

    public static void remove() {
        AUTHORITY.remove();
    }

}
