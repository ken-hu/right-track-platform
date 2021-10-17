package pers.ken.rt.common.utils;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
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
        T target = getTargetClass(source, clazz);

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
            T target = getTargetClass(sources, clazz);
            copyProperties(source, target);
            list.add(target);
        }
        return list;
    }

    private static <T> T getTargetClass(Object source, Class<T> clazz) {
        T target;
        try {
            target = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new BeanInstantiationException(clazz,
                    String.format("BeanMapper convert fail !! from %s to %s", source.getClass().getName(), clazz.getName()), e);
        }
        return target;
    }

}