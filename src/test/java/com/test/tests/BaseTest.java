package com.test.tests;

import com.test.config.Config;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(classes = Config.class)
public class BaseTest extends AbstractTestNGSpringContextTests {

}
