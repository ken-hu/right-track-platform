package pers.ken.rt.auth.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import pers.ken.rt.auth.oauth.utils.AccountContext;

import java.time.LocalDateTime;

/**
 * @ClassName: BaseMetaObjectHandler
 * @Created: 2025/2/8 11:05
 * @Author ken
 */
@Component
@Slf4j
public class BaseMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "created_at", LocalDateTime.class, LocalDateTime.now());

        this.strictInsertFill(metaObject, "created_by", String.class, getUsername());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updated_at", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updated_by", String.class, getUsername());
    }


    private String getUsername() {
        try {
            return AccountContext.getUsername();
        } catch (Exception e) {
            log.warn("set created_by/updated_by field failed,cause:{}", e.getMessage());
        }
        return "default";
    }
}
