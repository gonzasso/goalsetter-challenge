package com.goalsetter.mobile.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import tests.pageobject.MobilePage;

import java.util.List;
import java.util.function.Supplier;

import static utils.Lazy.lazily;

public class Home extends MobilePage {

    @AndroidFindBy(xpath = "//android.widget.ScrollView//android.view.ViewGroup/android.view.View/parent::*")
    private List<WebElement> accountCardContainers;

    private final Supplier<ParentAccountCard> parentAccountCard = lazily(() -> new ParentAccountCard(accountCardContainers.get(0)));

    private final Supplier<ChildAccountCard> childAccountCard = lazily(() -> new ChildAccountCard(accountCardContainers.get(1)));

    public ParentAccountCard getParentCard() {
        return parentAccountCard.get();
    }

    public ChildAccountCard getChildCard() {
        isVisible(accountCardContainers.get(0));
        singleScroll();
        return childAccountCard.get();
    }
}
