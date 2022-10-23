package com.goalsetter.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import tests.pageobject.MobilePage;

public class Login extends MobilePage {

    @AndroidFindBy(accessibility = "TEXT-InputEmail")
    private WebElement emailInput;

    @AndroidFindBy(accessibility = "TEXT-InputPassword")
    private WebElement passwordInput;

    @AndroidFindBy(accessibility = "BTN-Login")
    private WebElement loginButton;

    public Login typeEmail(String email) {
        type(emailInput, email, false);
        return this;
    }

    public Login typePassword(String password) {
        type(passwordInput, password, false);
        return this;
    }

    public Home login() {
        tap(loginButton);
        return new Home();
    }
}
