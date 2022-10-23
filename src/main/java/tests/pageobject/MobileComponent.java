package tests.pageobject;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.function.Supplier;

import static driver.Driver.getDriver;
import static java.lang.String.format;
import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.support.PageFactory.initElements;
import static utils.Lazy.lazily;

public class MobileComponent extends MobileOperations {

    private final Supplier<WebElement> container;

    protected MobileComponent(WebElement container) {
        initComponent(getVisibleContainer(container));
        this.container = lazily(() -> container);
    }

    private void initComponent(WebElement container) {
        WebDriver driver = getDriver();
        AppiumFieldDecorator decoratorFactory = new AppiumFieldDecorator(driver, ofSeconds(20));
        initElements(decoratorFactory, this);
    }

    protected WebElement getContainer() {
        return container.get();
    }

    private WebElement getVisibleContainer(WebElement container) {
        if (!isVisible(container, 4)) {
            String message = format("Unable to create the component '%s' using  element container '%s'",
                    this.getClass().getSimpleName(), container.toString());
            getLogger().error(message);
            throw new IllegalStateException(message);
        }
        return container;
    }
}
