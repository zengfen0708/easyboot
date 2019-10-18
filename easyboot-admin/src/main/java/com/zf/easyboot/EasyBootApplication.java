package com.zf.easyboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Slf4j
@SpringBootApplication
public class EasyBootApplication implements CommandLineRunner {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context =
                SpringApplication.run(EasyBootApplication.class, args);
        Environment env = context.getEnvironment();

        String hostname = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String applicationName = env.getProperty("server.servlet.context-path");


        log.info("\n\t****Spring boot启动初始化[{}]个bean***********" +
                        "\n\tLocal: \t\thttp://localhost:{}\n\t" +
                        "External:\thttp://{}:{}\n\t" +
                        "Doc文档地址:  http://{}:{}{}/doc.html\n" +
                        "\t************************************",
                context.getBeanDefinitionNames().length,
                port,
                hostname,
                port,
                hostname,
                port,
                applicationName);
    }


    /**
     *
     * 默认启动该项目
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        //log.info("启动后运行");
    }

}
