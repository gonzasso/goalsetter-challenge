package com.goalsetter.mobile.stepdefs;

import com.goalsetter.mobile.pages.Home;
import com.goalsetter.mobile.pages.Savings;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import logging.Logging;
import org.junit.Assert;

public class SavingsSteps implements Logging {

    private Scenario scenario;
    private Savings savingsPage;
    private Hook hook;

    public SavingsSteps(Hook hook) {
        this.hook = hook;
    }


    @BeforeStep
    public void setUp(Scenario scenarioVal) {
        this.scenario = scenarioVal;
    }

    @When("I tap on child Savings button on Home Page")
    public void accessChildSavings() {
        Home home = new Home();
        this.savingsPage = home.getChildCard().tapSavings();
    }

    @Then("Child Savings header should be {string}")
    public void verifySavingsHeader(String savingsHeader) {
        Assert.assertEquals(savingsHeader, this.savingsPage.getSavingsHeaderLabel());
    }
}
