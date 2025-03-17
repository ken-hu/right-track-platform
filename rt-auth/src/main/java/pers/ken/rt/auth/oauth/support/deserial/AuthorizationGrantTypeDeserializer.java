package pers.ken.rt.auth.oauth.support.deserial;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.io.IOException;
import java.io.Serial;

public class AuthorizationGrantTypeDeserializer extends StdDeserializer<AuthorizationGrantType> {

    @Serial
    private static final long serialVersionUID = 2884780317780523184L;

    public AuthorizationGrantTypeDeserializer() {
        super(AuthorizationGrantType.class);
    }

    @Override
    public AuthorizationGrantType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String value = node.get("value").asText();
        return new AuthorizationGrantType(value);
    }

}

