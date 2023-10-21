package com.test.annotation;

import com.education.common.bean.ClassFactory;
import com.education.common.bean.JdkClassFactory;
import com.education.common.utils.ObjectUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.*;

/**
 * RequestModelMapParam 注解参数注入类
 * 处理GET请求实现自定义map接受参数（由于spring RequestParam注解不支持自定义map类注入）
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/11/30 18:28
 */
public class RequestModelBeanMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final ClassFactory classFactory;
    private ModelBeanConfig modelBeanConfig;

    public RequestModelBeanMethodArgumentResolver() {
        this.classFactory = new JdkClassFactory();
    }

    public RequestModelBeanMethodArgumentResolver(ModelBeanConfig modelBeanConfig, ClassFactory classFactory) {
        this.classFactory = classFactory;
        this.modelBeanConfig = modelBeanConfig;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        RequestModelBeanParam requestModelMapParam = (RequestModelBeanParam)parameter.getParameterAnnotation(RequestModelBeanParam.class);
        return requestModelMapParam != null && ModelBean.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        ResolvableType resolvableType = ResolvableType.forMethodParameter(parameter);
        ModelBean model = null;
        if (ObjectUtils.isNotEmpty(modelBeanConfig)) {
            List<Object> params = new ArrayList<>();
            params.add(modelBeanConfig);
            model = (ModelBean) classFactory.createBean(parameter.getParameterType(), params);
        }
        else {
            model = (ModelBean) classFactory.createBean(parameter.getParameterType());
        }

        if (MultiValueMap.class.isAssignableFrom(parameter.getParameterType())) {
            // MultiValueMap
            Class<?> valueType = resolvableType.as(MultiValueMap.class).getGeneric(1).resolve();
            if (valueType == MultipartFile.class) {
                MultipartRequest multipartRequest = MultipartResolutionDelegate.resolveMultipartRequest(webRequest);
                if (multipartRequest != null) {
                    model.putAll(multipartRequest.getMultiFileMap());
                }
                return model;
            }
            else if (valueType == Part.class) {
                HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
                if (servletRequest != null && MultipartResolutionDelegate.isMultipartRequest(servletRequest)) {
                    Collection<Part> parts = servletRequest.getParts();
                    LinkedMultiValueMap<String, Part> result = new LinkedMultiValueMap<>(parts.size());
                    for (Part part : parts) {
                        result.add(part.getName(), part);
                    }
                    model.putAll(result);
                }
                return model;
            }
            else {
                Map<String, String[]> parameterMap = webRequest.getParameterMap();
                MultiValueMap<String, String> result = new LinkedMultiValueMap<>(parameterMap.size());
                parameterMap.forEach((key, values) -> {
                    for (String value : values) {
                        result.add(key, value);
                    }
                });
                model.putAll(result);
                return model;
            }
        }

        else {
            // Regular Map
            Class<?> valueType = resolvableType.asMap().getGeneric(1).resolve();
            if (valueType == MultipartFile.class) {
                MultipartRequest multipartRequest = MultipartResolutionDelegate.resolveMultipartRequest(webRequest);
                if (multipartRequest != null) {
                    model.putAll(multipartRequest.getFileMap());
                }
                return model;
            }
            else if (valueType == Part.class) {
                HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
                if (servletRequest != null && MultipartResolutionDelegate.isMultipartRequest(servletRequest)) {
                    Collection<Part> parts = servletRequest.getParts();
                    LinkedHashMap<String, Part> result = new LinkedHashMap<>(parts.size());
                    for (Part part : parts) {
                        if (!result.containsKey(part.getName())) {
                            result.put(part.getName(), part);
                        }
                    }
                    model.putAll(result);
                }
                return model;
            }
            else {
                Map<String, String[]> parameterMap = webRequest.getParameterMap();
                Map<String, String> result = new LinkedHashMap<>(parameterMap.size());
                parameterMap.forEach((key, values) -> {
                    if (values.length > 0) {
                        result.put(key, values[0]);
                    }
                });
                model.putAll(result);
                return model;
            }
        }
    }
}
