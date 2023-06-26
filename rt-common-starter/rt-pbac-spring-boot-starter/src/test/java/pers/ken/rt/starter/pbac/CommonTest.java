package pers.ken.rt.starter.pbac;

import org.junit.jupiter.api.Test;
import pers.ken.rt.starter.pbac.internal.Rn;
import pers.ken.rt.starter.pbac.utils.Jackson;

/**
 * @ClassName: CommonTest
 * @CreatedTime: 2023/1/10 18:16
 * @Desc:
 * @Author Ken
 */
public class CommonTest {
    @Test
    void rnConvertTest() {
        String rnStr = "rt:map:category:1";
        Rn rn = Rn.fromString(rnStr);
        System.out.println(Jackson.toJsonString(rn));
    }

}
