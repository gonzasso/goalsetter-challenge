package browsers;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import static properties.TestProperties.TEST_PROPERTIES;

public enum BrowserType implements HasCapabilities {

    ANDROID {
        public Capabilities getCapabilities() {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, TEST_PROPERTIES.getPlatformName());
            capabilities.setCapability(MobileCapabilityType.UDID, TEST_PROPERTIES.getDeviceUdid());
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, TEST_PROPERTIES.getPlatformVersion());
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, TEST_PROPERTIES.getAutomationName());
            capabilities.setCapability(MobileCapabilityType.APP, TEST_PROPERTIES.getAppDir());
//            capabilities.setCapability("app","C:\\\\Users\\\\gonza\\\\Documents\\\\Dev\\\\app-universal-qa.apk");

            return capabilities;
        }
    }

}
