package com.goalsetter.api.scripts;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/java/resources/features/api/verify_parent_wallet_info.feature"},
        glue = {"com.goalsetter.api.stepdefs"},
        tags = "@API",
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true,
        stepNotifications = true
)
public class WalletInfoTest {
}
