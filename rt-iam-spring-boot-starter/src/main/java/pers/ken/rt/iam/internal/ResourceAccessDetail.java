package pers.ken.rt.iam.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.ken.rt.iam.permission.data.IDataProvider;

import java.util.List;

/**
 * Creation Time: 2022/11/14 15:27.
 *
 * @author _Ken.Hu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceAccessDetail {
    private List<Policy> policies;
    private List<IDataProvider> dataProviders;
}
