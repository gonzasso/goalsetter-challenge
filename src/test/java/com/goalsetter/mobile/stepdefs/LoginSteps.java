package com.goalsetter.mobile.stepdefs;

import com.goalsetter.mobile.pages.Landing;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import logging.Logging;

import static properties.TestProperties.TEST_PROPERTIES;

public class LoginSteps implements Logging {

    private Scenario scenario;
    private Landing landingPage;
    private Hook hook;

    public LoginSteps(Hook hook) {
        this.hook = hook;
    }

    @BeforeStep
    public void setUp(Scenario scenarioVal) {
        this.scenario = scenarioVal;
    }

    @Given("I login to the app")
    public void login() {
        this.landingPage = new Landing();
        this.landingPage.logIn()
                .loginWithEmail()
                .typeEmail(TEST_PROPERTIES.getAuthEmail())
                .typePassword(TEST_PROPERTIES.getAuthPassword())
                .login();
    }
}
