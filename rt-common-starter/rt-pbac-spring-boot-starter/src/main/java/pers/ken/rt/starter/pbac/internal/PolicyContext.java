package pers.ken.rt.starter.pbac.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Creation Time: 2022/11/14 15:27.
 *
 * @author _Ken.Hu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PolicyContext {
    private List<Policy> policies;
    private Map<String, String> accessResources;
}
