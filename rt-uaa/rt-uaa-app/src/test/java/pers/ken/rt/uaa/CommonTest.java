package pers.ken.rt.uaa;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("kenhu");
        String dbUrl = "jdbc:postgresql://1.1.1.1:5432/postgres";
        String user = "123";
        String password = "123";

        System.out.println(encryptor.encrypt(dbUrl));
        System.out.println(encryptor.encrypt(user));
        System.out.println(encryptor.encrypt(password));
    }
}
