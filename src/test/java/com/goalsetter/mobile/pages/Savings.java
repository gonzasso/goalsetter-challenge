package com.goalsetter.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import tests.pageobject.MobilePage;

public class Savings extends MobilePage {

    @AndroidFindBy(xpath = "//*[matches(@content-desc, \"TEXT-.*Savings\")]")
    private WebElement savingsHeaderLabel;

    public String getSavingsHeaderLabel() {
        return getText(savingsHeaderLabel);
    }
}
