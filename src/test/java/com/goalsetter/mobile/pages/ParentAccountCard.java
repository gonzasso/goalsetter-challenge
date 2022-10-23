package com.goalsetter.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ParentAccountCard extends AccountCard {

    @AndroidFindBy(accessibility = "BTN-WALLET")
    private WebElement walletButton;

    protected ParentAccountCard(WebElement container) {
        super(container);
    }

    public ParentWallet tapWallet() {
        tap(walletButton);
        return new ParentWallet();
    }
}
