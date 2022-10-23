package com.goalsetter.mobile.pages;

import annotations.NotVisible;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import tests.pageobject.MobilePage;

public class Landing extends MobilePage {

    @AndroidFindBy(accessibility = "BTN-Login")
    private WebElement loginButton;

    @AndroidFindBy(accessibility = "BTN-Signup")
    private WebElement signUpButton;

    @NotVisible
    @AndroidFindBy(accessibility = "BUTTON_EMAIL")
    private WebElement loginWithEmailButton;

    @NotVisible
    @AndroidFindBy(accessibility = "BUTTON_PHONE_NUMBER")
    private WebElement loginWithPhoneButton;

    public Landing logIn() {
        tap(loginButton);
        return this;
    }

    public Login loginWithEmail() {
        tap(loginWithEmailButton);
        return new Login();
    }
}
