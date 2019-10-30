package com.zf.easyboot.config;

import com.zf.easyboot.common.xss.XssFilter;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * 关于系统配置的conf
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/12.
 */
@Configuration
public class SysConfig {

    /**
     * 新增去除非法请求方式只保留get/post 方式
     *
     * @return
     */
    @Bean
    public ConfigurableServletWebServerFactory configurableServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addContextCustomizers(context -> {

            SecurityConstraint securityConstraint = new SecurityConstraint();
            securityConstraint.setUserConstraint("CONFIDENTIAL");
            SecurityCollection collection = new SecurityCollection();
            collection.addPattern("/*");
            collection.addMethod("HEAD");
            collection.addMethod("PUT");
            collection.addMethod("DELETE");
            /*collection.addMethod("OPTIONS");*/
            collection.addMethod("TRACE");
            collection.addMethod("COPY");
            collection.addMethod("SEARCH");
            collection.addMethod("PROPFIND");
            securityConstraint.addCollection(collection);
            context.addConstraint(securityConstraint);
        });

        factory.addConnectorCustomizers(connector -> {
            connector.setAllowTrace(true);
        });
        return factory;
    }

    /**
     * springboot项目,部署到服务器后,运行一段时间后,处理一些文件上传的接口时,后报异常
     * https://blog.csdn.net/llibin1024530411/article/details/79474953
     * <p>
     * 文件上传临时路径
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "temp";
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }


    /**
     * xss注入
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }


}
