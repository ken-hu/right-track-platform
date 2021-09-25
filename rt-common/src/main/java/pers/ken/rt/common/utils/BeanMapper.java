package pers.ken.rt.common.utils;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * <name> BeanMapper </name>
 * <desc> Bean转换类，集成SpringBoot的BeanUtils,简单增强，支持集合 </desc>
 * Creation Time: 2021/9/19 17:37.
 *
 * @author _Ken.Hu
 */
public class BeanMapper extends BeanUtils {

    public static <T> T copyProperties(Object source, Class<T> clazz) {
        T target = null;
        try {

            target = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeanInstantiationException(clazz,
                    "BeanMapper convert fail !! from " + source.getClass().getName() + " to " + clazz.getName(),
                    e);
        }
        copyProperties(source, target);
        return target;
    }

    public static <T> T copyProperties(Object source, Supplier<T> target) {
        T t = target.get();
        copyProperties(source, t);
        return t;
    }

    public static <S, T> List<T> copyListProperties(List<S> sources, Class<T> clazz) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T target = null;
            try {
                target = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new BeanInstantiationException(clazz,
                        "BeanMapper convert fail !! from " + source.getClass().getName() + " to " + clazz.getName(),
                        e);
            }
            copyProperties(source, target);
            list.add(target);
        }
        return list;
    }

}