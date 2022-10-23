package com.goalsetter.mobile.stepdefs;

import driver.Driver;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import logging.Logging;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static driver.Driver.getDriver;
import static driver.Driver.populateDriver;
import static properties.TestProperties.TEST_PROPERTIES;

public class Hook implements Logging {

    @Before
    public void setup() {
        try{
            populateDriver(TEST_PROPERTIES.getBrowser());
        } catch (Exception e) {
            getLogger().error(e.getLocalizedMessage(), e);
        }
    }

    @AfterStep
    public void addScreenshot(Scenario scenario){
        if(scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "image");
        }
    }

    @After
    public void tearDown() {
        Driver.dispose();
    }
}
