package com.goalsetter.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import tests.pageobject.MobilePage;

public class ParentWallet extends MobilePage {

    @AndroidFindBy(xpath = "//android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.TextView[1]")
    private WebElement walletHeaderLabel;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.TextView[3]")
    private WebElement walletBalanceLabel;

    public String getWalletBalanceHeader() {
        return getText(walletHeaderLabel);
    }

    public String getWalletBalance() {
        return getText(walletBalanceLabel);
    }
}
