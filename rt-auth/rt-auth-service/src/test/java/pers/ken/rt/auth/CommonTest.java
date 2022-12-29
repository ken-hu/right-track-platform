package pers.ken.rt.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.pattern.PathPatternParser;

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
        PathPatternParser parser = PathPatternParser.defaultInstance;
//        PathPattern parse = parser.parse("channel:mars:.*");
//        boolean match = parse.matches(PathContainer.parsePath("channel:mars:adcode/1/icCode/2"));
//        boolean match = antPathMatcher.match("mars:adcode/*/icCode/1*", "mars:adcode/1/icCode/2");
        boolean match = antPathMatcher.match("channel:mars:*", "channel:mars:getDesc");
        if (match) {
            System.out.println("Nice");
        }else {
            System.out.println("So bad");
        }
    }
}
