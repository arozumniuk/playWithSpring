package com.test.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rits.cloning.Cloner;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan("com.test")
@PropertySource("classpath:application-${activeSpringProfile:LOCAL}.properties")
@Slf4j
public class Config {

    @Value("${url}")
    private String url;

    @Value("${activeSpringProfile:LOCAL}")
    private String activeSpringProfile;

    @Value("${browser}")
    private String browser;


    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Bean
    public Cloner getCloner() {
        return new Cloner();
    }

    @Bean(name = "apiTester")
    public RequestSpecification getRequestSpecification() {
        RequestSpecBuilder builder = new RequestSpecBuilder();

        RestAssured.config = new RestAssuredConfig().objectMapperConfig(
                new ObjectMapperConfig().jackson2ObjectMapperFactory(
                        (type, s) -> getObjectMapper()
                ));

        builder
                .setContentType(ContentType.JSON)
                .setBaseUri(url)
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter());

        return builder.build();
    }

    @Bean()
    public WebDriver getDriver() {
        WebDriver driver = null;

        switch (browser) {
            case "chrome":

                String opSystem = System.getProperty("os.name");
                ChromeOptions options = new ChromeOptions();

                options.addArguments("start-maximized", "--ignore-certificate-errors");

                if (opSystem.contains("Linux")) {
                    options.addArguments("disable-gpu");
                    options.addArguments("no-sandbox");
                }

                log.info("Your system is: " + opSystem);

                if (opSystem.contains("Mac")) {
                    System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver");
                } else if (opSystem.contains("Linux")) {
                    System.setProperty("webdriver.chrome.driver", "./resources/chromedriver_linux");
                } else if (opSystem.contains("Win")) {
                    System.setProperty("webdriver.chrome.driver", "./resources/chromedriver.exe");
                } else {
                    log.error("Your system is: " + opSystem + ", no chrome driver for it");
                }
                driver = new ChromeDriver(options);
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                break;
        }
        return driver;
    }
}
