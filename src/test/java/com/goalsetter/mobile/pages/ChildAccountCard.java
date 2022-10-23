package com.goalsetter.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ChildAccountCard extends AccountCard {

    @AndroidFindBy(accessibility = "BTN-ALLOWANCE")
    private WebElement allowanceButton;

    protected ChildAccountCard(WebElement container) {
        super(container);
    }

    public void tapAllowance() {
        tap(allowanceButton);
    }
}
