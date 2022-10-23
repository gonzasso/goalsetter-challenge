package properties;

import browsers.BrowserType;
import java.io.IOException;
import java.util.Properties;

import static browsers.BrowserType.ANDROID;
//import static browsers.BrowserType.CHROME;
import static java.lang.System.getProperty;
import static java.lang.System.getenv;

/**
 * TestProperties manages (load / return) the test properties.
 */
public enum TestProperties {

    TEST_PROPERTIES;

    private static final String ENVIRONMENT = "environment";
    private static final String BASE_URL = "base.url";
    private static final String REMOTE_SERVER_URL = "remote.server.url";
    private static final String IMPLICIT_WAIT = "implicit.wait";
    private static final String EXPLICIT_WAIT = "explicit.wait";
    private static final String POLLING_EVERY = "polling.every";
    private static final String PAGE_LOAD_TIMEOUT = "page.load.timeout";
    private static final String SCRIPT_TIMEOUT = "script.timeout";
    private static final String BROWSER = "browser";
    private static final String DEFAULT_ENVIRONMENT = "qa";
    private static final String AUTH_EMAIL = "auth.email";
    private static final String AUTH_PASSWORD = "auth.password";

    private static final String PLATFORM_NAME = "platform.name";
    private static final String PLATFORM_VERSION = "platform.version";
    private static final String AUTOMATION_NAME = "automation.name";
    private static final String APP_DIR = "app.dir";
    private static final String DEVICE_UDID = "device.udid";

    private final Properties properties = new Properties();

    /**
     * Returns the base URL.
     *
     * @return the application base URL as {@link String}
     */
    public String getBaseUrl() {
        return getTestProperty(BASE_URL);
    }

    /**
     * Returns the remote server URL.
     *
     * @return the remote server URL to execute the automation as {@link String}
     */
    public String getRemoteServerUrl() {
        return getTestProperty(REMOTE_SERVER_URL);
    }

    /**
     * Returns the WebDriver implicit wait.
     *
     * @return the implicit wait as {@link Long}
     */
    public Long getImplicitWait() {
        return Long.valueOf(getTestProperty(IMPLICIT_WAIT));
    }

    /**
     * Returns the WebDriver explicit wait.
     *
     * @return the explicit wait as {@link Long}
     */
    public Long getExplicitWait() {
        return Long.valueOf(getTestProperty(EXPLICIT_WAIT));
    }

    /**
     * Returns the polling every modifier to apply to the explicit waits.
     *
     * @return the polling every as {@link Long}
     */
    public Long getPollingEvery() {
        return Long.valueOf(getTestProperty(POLLING_EVERY));
    }

    /**
     * Returns the page load timeout.
     *
     * @return the page load timeout as {@link Long}
     */
    public Long getPageLoadTimeout() {
        return Long.valueOf(getTestProperty(PAGE_LOAD_TIMEOUT));
    }

    /**
     * Returns the script timeout.
     *
     * @return the script timeout as {@link Long}
     */
    public Long getScriptTimeout() {
        return Long.valueOf(getTestProperty(SCRIPT_TIMEOUT));
    }

    /**
     * Returns the authentication email.
     *
     * @return the authentication email as {@link String}
     */
    public String getAuthEmail() {
        return getTestProperty(AUTH_EMAIL);
    }

    /**
     * Returns the authentication password.
     *
     * @return the authentication password as {@link String}
     */
    public String getAuthPassword() {
        return getTestProperty(AUTH_PASSWORD);
    }

    /**
     * Returns the platform name.
     *
     * @return the platform name as {@link String}
     */
    public String getPlatformName() {
        return getTestProperty(PLATFORM_NAME);
    }


    /**
     * Returns the platform version.
     *
     * @return the platform version as {@link String}
     */
    public String getPlatformVersion() {
        return getTestProperty(PLATFORM_VERSION);
    }

    /**
     * Returns the automation name.
     *
     * @return the automation name as {@link String}
     */
    public String getAutomationName() {
        return getTestProperty(AUTOMATION_NAME);
    }

    /**
     * Returns the device UDID.
     *
     * @return the device UDID as {@link String}
     */
    public String getDeviceUdid() {
        return getTestProperty(DEVICE_UDID);
    }


    /**
     * Returns the app dir.
     *
     * @return the app dir as {@link String}
     */
    public String getAppDir() {
        return getTestProperty(APP_DIR);
    }


    /**
     * Gets the browser name.
     * The supported browsers are based on {@link BrowserType}
     *
     * @return the browser enumeration representing the browser name
     */
    public BrowserType getBrowser() {
        String browser = get(BROWSER);
        if (browser == null) {
            return ANDROID;
        } else {
            return BrowserType.valueOf(browser.toUpperCase());
        }
    }

    /**
     * Gets the test property from the property file represented as [environment].properties.
     *
     * @param propertyName the property name
     * @return the property value
     */
    private String getTestProperty(String propertyName) {
        if (properties.isEmpty()) {
            try {
                StringBuilder propertyFile = new StringBuilder();
                String environment = get(ENVIRONMENT);
                if (environment == null) {
                    environment = DEFAULT_ENVIRONMENT;
                }
                propertyFile.append(environment).append(".properties");
                properties.load(this.getClass().getClassLoader().getResourceAsStream(propertyFile.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(propertyName);
    }

    /**
     * Gets value from {@link System#getProperty(String)}, or from {@link System#getenv()}.
     *
     * @param key the key
     * @return the value
     */
    private String get(String key) {
        String environment = getProperty(key);
        if (environment == null) {
            environment = getenv(key);
        }
        return environment;
    }
}
