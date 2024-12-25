package pers.ken.rt.starter.pbac.permission.api;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.ken.rt.common.model.PageResponse;
import pers.ken.rt.common.model.TreeResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pers.ken.rt.starter.pbac.cons.ActionCons.HttpMethodName;
import static pers.ken.rt.starter.pbac.cons.ActionCons.OperationName;

/**
 * @ClassName: ActionProvider
 * @Created: 2024/7/2
 * @Author ken
 */
public class ActionProvider {
    private static final Pattern VAL_PATTERN = Pattern.compile("\\{[^}]*\\}");
    private static final Pattern VERSION_PATTERN = Pattern.compile("/v(\\d+(?:\\.\\d+)?)");
    private static final String START_CHAR = "/";

    private static final ConcurrentHashMap<String, String> ACTION_NAME_CACHE = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, String> getActionNameCache() {
        return ACTION_NAME_CACHE;
    }

    private static String getCacheKey(String method, String uri) {
        return String.format("%s:%s", method, uri);
    }

    private static String fromCache(String key) {
        return ACTION_NAME_CACHE.get(key);
    }

    private static String getURI(Method method) {
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof RequestMapping) {
                return ((RequestMapping) annotation).value()[0];
            }
            if (annotation instanceof GetMapping) {
                return ((GetMapping) annotation).value()[0];
            }
            if (annotation instanceof PostMapping) {
                return ((PostMapping) annotation).value()[0];
            }
            if (annotation instanceof PutMapping) {
                return ((PutMapping) annotation).value()[0];
            }
            if (annotation instanceof DeleteMapping) {
                return ((DeleteMapping) annotation).value()[0];
            }
        }
        throw new IllegalArgumentException("uri can not be null");
    }

    private static String getHttpMethod(Method method) {
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof RequestMapping) {
                return ((RequestMapping) annotation).method()[0].name();
            }
            if (annotation instanceof GetMapping) {
                return HttpMethodName.GET;
            }
            if (annotation instanceof PostMapping) {
                return HttpMethodName.POST;
            }
            if (annotation instanceof PutMapping) {
                return HttpMethodName.PUT;
            }
            if (annotation instanceof DeleteMapping) {
                return HttpMethodName.DELETE;
            }
        }
        return null;
    }

    public static String convertURItoActionName(String serviceCode, Method method) {
        return convertURItoActionName(serviceCode, getHttpMethod(method), getURI(method), method.getReturnType());
    }

    public static String convertURItoActionName(String serviceCode, String method, String uri, Class<?> respType) {
        String key = getCacheKey(method, uri);

        String actionFromCache = fromCache(key);
        if (StringUtils.isNotBlank(actionFromCache)) {
            return actionFromCache;
        }

        String removeFirstCharUri = uri;
        if (!uri.startsWith(START_CHAR)) {
            removeFirstCharUri = START_CHAR + uri;
        }

        // 抓取版本号
        String version = StringUtils.EMPTY;
        Matcher versionMatcher = VERSION_PATTERN.matcher(removeFirstCharUri);
        if (versionMatcher.find()) {
            version = "V" + versionMatcher.group(1);
        }
        removeFirstCharUri = versionMatcher.replaceAll("");

        // 去掉变量
        Matcher valMatcher = VAL_PATTERN.matcher(removeFirstCharUri);
        removeFirstCharUri = valMatcher.replaceAll("");

        removeFirstCharUri = StringUtils.replace(removeFirstCharUri, "/", "_");
        removeFirstCharUri = StringUtils.replace(removeFirstCharUri, "-", "_");
        removeFirstCharUri = StringUtils.replace(removeFirstCharUri, ".", "_");
        String actionName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, removeFirstCharUri) + version;
        if (HttpMethodName.POST.equalsIgnoreCase(method)) {
            actionName = OperationName.CREATE + actionName;
        }
        if (HttpMethodName.PUT.equalsIgnoreCase(method)) {
            actionName = OperationName.UPDATE + actionName;
        }
        if (HttpMethodName.DELETE.equalsIgnoreCase(method)) {
            actionName = OperationName.DELETE + actionName;
        }
        if (HttpMethodName.GET.equalsIgnoreCase(method)) {
            //todo PageResult 不通用.. 最好是用户自定义
            if (Collection.class.isAssignableFrom(respType) || PageResponse.class.isAssignableFrom(respType) || TreeResponse.class.isAssignableFrom(respType)) {
                actionName = OperationName.LIST + actionName;
            } else {
                actionName = OperationName.GET + actionName;
            }
        }
        actionName = String.format("%s:%s", serviceCode, actionName);
        ACTION_NAME_CACHE.put(key, actionName);
        return actionName;
    }
}
