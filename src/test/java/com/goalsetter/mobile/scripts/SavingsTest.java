package com.goalsetter.mobile.scripts;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/java/resources/features/mobile/savings/verify_savings_info.feature"},
        glue = {"com.goalsetter.mobile.stepdefs"},
        tags = "@Android",
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true,
        stepNotifications = true
)
public class SavingsTest {
}
