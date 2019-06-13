package com.test.tests;

import com.test.POM.LoginPage;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void test() {
        new LoginPage().open();
    }
}
