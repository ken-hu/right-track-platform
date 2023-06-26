package pers.ken.rt.starter.pbac.anno;

import pers.ken.rt.starter.pbac.internal.Policy;
import pers.ken.rt.starter.pbac.permission.data.DataScope;
import pers.ken.rt.starter.pbac.permission.data.DataScopeSetting;

import java.util.List;

/**
 * <code> IDataProvider </code>
 * <desc> 数据权限提供 </desc>
 * <b>Creation Time:</b> 2022/8/9 14:31.
 *
 * @author Ken.Hu
 */
public interface IDataProvider {

    /**
     * Define data scope.
     *
     * @param dataScopeSetting the data scope setting
     */
    void defineDataScopeSetting(DataScopeSetting dataScopeSetting);

    /**
     * Gets data scope.
     *
     * @return the data scope
     */
    List<DataScope> fromContext();

    /**
     * Gets data scope.
     *
     * @param policies   the policies
     * @param resourceId the resource id
     * @return the data scope
     */
    DataScope generateDataScope(List<Policy> policies, String resourceId);
}
