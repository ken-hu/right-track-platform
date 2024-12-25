package pers.ken.rt.starter.pbac.permission.data;

import java.util.Set;

/**
 * <code> IDataProvider </code>
 * <desc> 数据权限提供 </desc>
 * <b>Creation Time:</b> 2022/8/9 14:31.
 *
 * @author Ken.Hu
 */
public interface DataScopeSettingProvider {

    void save(DataScopeSetting dataScopeSetting);

    DataPermission toDataPermissions(String table, Set<String> ownResources);

}
