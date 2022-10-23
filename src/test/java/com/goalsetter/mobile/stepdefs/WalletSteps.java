package com.goalsetter.mobile.stepdefs;

import com.goalsetter.mobile.pages.Home;
import com.goalsetter.mobile.pages.ParentWallet;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import logging.Logging;
import org.junit.Assert;

public class WalletSteps implements Logging {

    private Scenario scenario;
    private ParentWallet wallet;
    private Hook hook;

    public WalletSteps(Hook hook) {
        this.hook = hook;
    }


    @BeforeStep
    public void setUp(Scenario scenarioVal) {
        this.scenario = scenarioVal;
    }

    @When("I tap on parent wallet button on Home Page")
    public void accessParentWallet() {
        Home home = new Home();
        this.wallet = home.getParentCard().tapWallet();
    }

    @Then("Parent wallet header should be {string}")
    public void verifyWalletHeader(String walletName) {
        Assert.assertEquals(walletName, this.wallet.getWalletBalanceHeader());
    }

    @And("Wallet balance should be {string}")
    public void verifyWalletBalance(String walletBalance) {
        Assert.assertEquals(walletBalance, this.wallet.getWalletBalance());
    }
}
