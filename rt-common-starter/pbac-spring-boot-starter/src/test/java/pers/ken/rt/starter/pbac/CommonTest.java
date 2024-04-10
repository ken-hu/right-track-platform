package pers.ken.rt.starter.pbac;

import org.junit.jupiter.api.Test;
import pers.ken.rt.starter.pbac.internal.PolicyContext;

import java.util.List;

/**
 * @ClassName: CommonTest
 * @CreatedTime: 2023/1/10 18:16
 * @Desc:
 * @Author Ken
 */
public class CommonTest {
    @Test
    void permitTest() {
        PolicyContext context = PolicyContext.init("rt-test", List.of());
    }

}
