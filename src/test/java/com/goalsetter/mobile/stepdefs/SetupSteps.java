package com.goalsetter.mobile.stepdefs;

import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import logging.Logging;

import static driver.Driver.populateDriver;
import static properties.TestProperties.TEST_PROPERTIES;

public class SetupSteps implements Logging {

    private Scenario scenario;

    @BeforeStep
    public void setUp(Scenario scenarioVal) {
        this.scenario = scenarioVal;
    }

    @Given("I open the Goalsetter app")
    public void startServerAndPopulateDriver() {
        try{
            populateDriver(TEST_PROPERTIES.getBrowser());
        } catch (Exception e) {
            getLogger().error(e.getLocalizedMessage(), e);
        }
    }}
