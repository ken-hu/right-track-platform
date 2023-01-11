package pers.ken.rt.common.web;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;
import pers.ken.rt.common.utils.SnowflakeIdHelper;

import java.io.Serializable;

/**
 * @ClassName: SnowflakeIdentifierGenerator
 * @CreatedTime: 2023/1/16 18:49
 * @Desc:
 * @Author Ken
 */
@Component
public class SnowflakeIdentifierGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return SnowflakeIdHelper.nextId();
    }
}
