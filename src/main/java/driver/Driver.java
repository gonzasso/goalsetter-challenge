package driver;

import browsers.BrowserType;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;
import static properties.TestProperties.TEST_PROPERTIES;

public final class Driver {

    private static final ThreadLocal<WebDriver> DRIVERS = new ThreadLocal<>();

    public static void populateDriver(BrowserType browser) throws MalformedURLException {

        if (DRIVERS.get() == null) {

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);

//            AppiumServiceBuilder appiumLocalService = new AppiumServiceBuilder()
//                    .withCapabilities(desiredCapabilities)
//                    .usingAnyFreePort()
//                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
//                    .withArgument(GeneralServerFlag.LOG_LEVEL, "error");
//
//
//            AppiumDriverLocalService service = appiumLocalService.build();
//
//            service.start();

            URL serverUrl = new URL(TEST_PROPERTIES.getRemoteServerUrl());

            AndroidDriver<WebElement> driver = new AndroidDriver(serverUrl, browser.getCapabilities());
            driver.manage().timeouts().implicitlyWait(TEST_PROPERTIES.getImplicitWait(), SECONDS);

            DRIVERS.set(driver);
        }
    }

    /**
     * Gets the instance previously created and stored at thread-level of the getDriver.
     *
     * @return the {@link Driver}
     */
    public static WebDriver getDriver() {
        return DRIVERS.get();
    }

    /**
     * Dispose the getDriver, releasing the session between the client and the server.
     * The platform will be closed.
     */
    public static void dispose() {
        DRIVERS.get().quit();
        DRIVERS.remove();
    }
}
