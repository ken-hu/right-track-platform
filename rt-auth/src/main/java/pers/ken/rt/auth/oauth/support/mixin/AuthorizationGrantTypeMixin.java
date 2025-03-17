package pers.ken.rt.auth.oauth.support.mixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pers.ken.rt.auth.oauth.support.deserial.AuthorizationGrantTypeDeserializer;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonDeserialize(using = AuthorizationGrantTypeDeserializer.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class AuthorizationGrantTypeMixin {

    @JsonCreator
    AuthorizationGrantTypeMixin(@JsonProperty("value") String value) {
    }
}
