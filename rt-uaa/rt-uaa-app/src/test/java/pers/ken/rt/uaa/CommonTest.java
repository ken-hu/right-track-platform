package pers.ken.rt.uaa;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;

/**
 * <name> CommonTest </name>
 * <desc> CommonTest </desc>
 * Creation Time: 2021/10/7 16:45.
 *
 * @author _Ken.Hu
 */
class CommonTest {
    @Test
    void passwordTest() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

    @Test
    void encodeConfig() {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean match = antPathMatcher.match("mars:adcode/*", "mars:adcode/1");
        if (match) {
            System.out.println("Nice");
        }else {
            System.out.println("So bad");
        }
    }
}
