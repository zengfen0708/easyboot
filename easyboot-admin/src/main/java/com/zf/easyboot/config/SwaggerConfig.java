package com.zf.easyboot.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableSwaggerBootstrapUi;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.security.jwt.JwtConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.List;

/**
 * swagger2配置信息
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/12.
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUi
@Import(BeanValidatorPluginsConfiguration.class)
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class SwaggerConfig implements WebMvcConfigurer {

    @Resource
    private JwtConfig jwtConfig;

    @Value("${swagger.enabled}")
    private Boolean enabled;


    /**
     * 需要添加这个，否则会出现找到的错误
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket createRestApi() {

        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = Lists.newArrayList();
        parameterBuilder.name(jwtConfig.getHeader()).description("token令牌").modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(true).build();
        parameters.add(parameterBuilder.build());


        //配置统一的返回信息(自定义统一响应信息)
        List<ResponseMessage> messageList = Lists.newArrayList();
        messageList.add(new ResponseMessageBuilder().code(500).message("服务器发生异常,请刷新重试").build());
        messageList.add(new ResponseMessageBuilder().code(401).message("您没有权限访问").build());
        messageList.add(new ResponseMessageBuilder().code(0).message("OK").build());


        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .enable(enabled)
                .apiInfo(apiInfo())
                .groupName("默认接口")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey()))
                .globalResponseMessage(RequestMethod.GET, messageList)
                .globalResponseMessage(RequestMethod.POST, messageList)
                .useDefaultResponseMessages(false);
        return docket;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }


    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference(jwtConfig.getHeader(), authorizationScopes));
    }

    private ApiKey apiKey() {
        return
                new ApiKey(jwtConfig.getHeader(), jwtConfig.getHeader(), "header");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(CommonConstant.SYSTEM_NAME + "接口文档系统")
                .description(CommonConstant.SYSTEM_NAME + "接口文档系统")
                .contact(new Contact("疯信子",
                        "https://blog.csdn.net/str0708",
                        "zengms0708@gmail.com"))
                .version("1.0.0")
                .build();
    }


}
