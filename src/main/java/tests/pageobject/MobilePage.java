package tests.pageobject;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import logging.Logging;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static driver.Driver.getDriver;
import static java.lang.String.format;
import static java.time.Duration.ofSeconds;

public class MobilePage extends MobileOperations implements Logging {

    protected MobilePage() {
        getLogger().info(format("At page: %s", this));
        WebDriver driver = getDriver();
        AppiumFieldDecorator decoratorFactory = new AppiumFieldDecorator(driver, ofSeconds(20));
        PageFactory.initElements(decoratorFactory, this);
    }
}
