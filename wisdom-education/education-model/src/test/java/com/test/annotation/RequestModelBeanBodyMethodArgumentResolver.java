package com.test.annotation;

import com.alibaba.fastjson.JSONObject;
import com.education.common.bean.ClassFactory;
import com.education.common.bean.JdkClassFactory;
import com.education.common.utils.RequestUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/7/4 11:41
 * @since 1.0.0
 */
public class RequestModelBeanBodyMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final ClassFactory classFactory = new JdkClassFactory();
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        RequestModelBeanBody requestModelMapParam = parameter.getParameterAnnotation(RequestModelBeanBody.class);
        return requestModelMapParam != null && ModelBean.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)
            throws Exception {
        if (!methodParameter.getParameterType().isAssignableFrom(ModelBean.class)) {
            throw new RuntimeException("ParameterType 类型需要继承父类" + ModelBean.class);
        }
        HttpServletRequest servletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String jsonData = RequestUtils.readData(servletRequest);
        Map dataMap = JSONObject.parseObject(jsonData);
        ModelBean model = (ModelBean) classFactory.createBean(methodParameter.getParameterType());
        dataMap.entrySet().forEach(key -> {
            model.setAttr((String) key, dataMap.get(key));
        });
        return model;
    }
}
