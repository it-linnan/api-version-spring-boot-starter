package priv.ln.api.version.spring.boot.rule.http;

import priv.ln.api.version.spring.boot.annotation.ApiVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.*;
import java.util.Map;

/**
 * 接口URL版本路由管理
 *
 * @author linnan
 * @version 2019/2/16
 */
@Slf4j
public class ApiVersionUrlRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    public static final String VERSION_PREFIX = "/{version}";
    public static final String PATH_KEY = "path";
    public static final String VALUE_CACHE_KEY = "valueCache";

    @Override
    protected RequestCondition<ApiVersionUrlRequestCondition> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createCondition(apiVersion);
    }

    @Override
    protected RequestCondition<ApiVersionUrlRequestCondition> getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createCondition(apiVersion);
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = createRequestMappingInfo(method);
        if (info != null) {
            RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
        }
        return info;
    }

    @Nullable
    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(element, ApiVersion.class);
        if (apiVersion != null) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(requestMapping);
            Class<? extends InvocationHandler> clazz = invocationHandler.getClass();
            try {
                Field declaredField = clazz.getDeclaredField(VALUE_CACHE_KEY);
                declaredField.setAccessible(true);
                Map<String, Object> valueCache = (Map) declaredField.get(invocationHandler);
                String[] path = requestMapping.path();
                if (path != null) {
                    for (int i = 0; i < path.length; i++) {
                        path[i] = VERSION_PREFIX + path[i];
                    }
                    valueCache.put(PATH_KEY, path);
                    declaredField.set(invocationHandler, valueCache);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("这是不可能发生的事。。", e);
            }
        }
        RequestCondition<?> condition = (element instanceof Class ?
                getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element));
        return (requestMapping != null ? createRequestMappingInfo(requestMapping, condition) : null);
    }

    private RequestCondition<ApiVersionUrlRequestCondition> createCondition(ApiVersion apiVersion) {
        return apiVersion == null ? null : new ApiVersionUrlRequestCondition(apiVersion.version());
    }
}
