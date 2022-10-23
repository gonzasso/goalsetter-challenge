package com.goalsetter.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import tests.pageobject.MobileComponent;

public abstract class AccountCard extends MobileComponent {

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"BTN-SAVINGS\"]")
    protected WebElement savingsButton;

    @AndroidFindBy(accessibility = "BTN-INVESTMENT")
    protected WebElement investmentButton;

    @AndroidFindBy(accessibility = "BTN-StartNow")
    protected WebElement investmentStartNowButton;

    protected AccountCard(WebElement container) {
        super(container);
    }

    public Savings tapSavings() {
        tap(savingsButton);
        return new Savings();
    }
}
