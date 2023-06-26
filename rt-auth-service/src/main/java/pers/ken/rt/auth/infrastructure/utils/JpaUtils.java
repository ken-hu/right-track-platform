package pers.ken.rt.auth.infrastructure.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.stream.Stream;

/**
 * @author Ken
 * @className: JpaUtils
 * @createdTime: 2023/3/18 23:21
 * @desc:
 */
public class JpaUtils {
    public static void copyNotNullProperties(Object src,Object target){
        BeanUtils.copyProperties(src,target,getNullPropertyNames(src));
    }

    private static String[] getNullPropertyNames(Object object) {
        final BeanWrapperImpl wrapper = new BeanWrapperImpl(object);
        return Stream.of(wrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(propertyName -> wrapper.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
