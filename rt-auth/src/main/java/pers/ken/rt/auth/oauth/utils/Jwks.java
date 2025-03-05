package pers.ken.rt.auth.oauth.utils;

import com.nimbusds.jose.jwk.*;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.SecretKey;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * Name: Jwks
 * Creation Time: 2022/12/29 20:21.
 *
 * @author Ken
 */
public class Jwks {

    private Jwks() {
    }

    public static void main(String[] args) throws Exception {
        // 生成 RSA 密钥对（带固定 kid）
        KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID("ken-right-track-platform-jws")
            .build();

        JWKSet jwkSet = new JWKSet(rsaKey);
        try (FileWriter writer = new FileWriter("C:\\Users\\DELL\\Desktop\\jwks.json")) {
            writer.write(jwkSet.toString(false));
        }
    }

    public static RSAKey generateRsa() {
        KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
    }

    public static String getRsaStringFromClassPath(String filePath) {
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            // 将 InputStream 读取为字符串
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JWKSet from ClassPath:" + filePath, e);
        }
    }

    public static ECKey generateEc() {
        KeyPair keyPair = KeyGeneratorUtils.generateEcKey();
        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
        ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
        Curve curve = Curve.forECParameterSpec(publicKey.getParams());
        return new ECKey.Builder(curve, publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
    }

    public static OctetSequenceKey generateSecret() {
        SecretKey secretKey = KeyGeneratorUtils.generateSecretKey();
        return new OctetSequenceKey.Builder(secretKey)
            .keyID(UUID.randomUUID().toString())
            .build();
    }
}
