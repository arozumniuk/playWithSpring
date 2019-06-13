package com.test.config;

import com.rits.cloning.Cloner;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan("com.test")
@PropertySource("classpath:application-${activeSpringProfile:LOCAL}.properties")
@Slf4j
public class Config {

    @Value("${url}")
    private String url;

    @Value("${browser}")
    private String browser;

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
