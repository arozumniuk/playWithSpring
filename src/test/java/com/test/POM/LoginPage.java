package com.test.POM;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginPage {

    @Value("${url}")
    private String url;

    @Autowired
    WebDriver driver;

    public void open() {
        log.info("Navigating to: " + url);
        log.info(url);
        System.out.println(driver);

        driver.get(url);
    }
}