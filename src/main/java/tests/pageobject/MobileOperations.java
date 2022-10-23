package tests.pageobject;

import annotations.NotVisible;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.TouchAction;
import logging.Logging;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedCondition;
import utils.Elements;
import utils.MyFluentWait;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static driver.Driver.getDriver;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.lang.String.format;
import static java.time.Duration.ofMillis;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static properties.TestProperties.TEST_PROPERTIES;
import static utils.Elements.*;
import static utils.Lazy.lazily;
import static utils.Reflection.getNameForField;

public class MobileOperations implements Logging {

    private final MobileDriver driver;

    public MobileOperations() {
        this.driver = (MobileDriver) getDriver();
    }

    protected void tap(WebElement element) {
        String elementName = getNameForField(this, element);
        if (!elementName.isEmpty()) {
            getLogger().info(format("Clicking on %s", elementName));
        }
        element.click();
    }

    protected void type(WebElement element, String text, final boolean cleanFirst) {
        getLogger().info(format("Typing [%s]%s", text.replaceAll("\\p{C}", ""), cleanFirst ? " (cleaning first)" : ""));
        waitFor(visibilityOf(element));
        if (cleanFirst) {
            element.clear();
        }
        element.sendKeys(text);
    }

    protected void singleScroll() {
        int deviceWidth = driver.manage().window().getSize().getWidth();
        int deviceHeight = driver.manage().window().getSize().getHeight();
        int midX = (deviceWidth / 2);
        int midY = (deviceHeight / 2);
                new TouchAction(driver)
                .press(point(midX,midY))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(midX, 1))
                .release().perform();
    }

    protected <K> Optional<K> waitFor(ExpectedCondition<K> condition) {
        return waitFor(condition, true);
    }

    protected <K> Optional<K> waitFor(ExpectedCondition<K> condition, boolean shouldFail) {
        return waitFor(condition, TEST_PROPERTIES.getExplicitWait(), MILLIS, shouldFail);
    }

    protected <K> Optional<K> waitFor(ExpectedCondition<K> condition, long time, TemporalUnit unit, boolean shouldFail) {
        return new MyFluentWait().waitFor(condition, time, unit, shouldFail);
    }

    public boolean isVisible() {
        return areVisible(getOwnVisibleWebElements());
    }

    protected <K extends WebElement> boolean isVisible(K element) {
        return waitFor(visibilityOf(element), false).orElse(false);
    }

    protected boolean isVisible(WebElement element, long timeOut) {
        return waitFor(visibilityOf(element), timeOut, SECONDS, false).orElse(false);
    }

    private List<WebElement> getWebElementsWithAnnotationsAndDo(Function<Class<? extends Annotation>, List<WebElement>> action) {
        List<WebElement> elements = Stream.of(FindBy.class, FindBys.class, FindAll.class, AndroidFindBy.class, iOSFindBy.class)
                .map(action)
                .flatMap(List::stream)
                .collect(toList());
        return elements;
    }

    public static List<Field> getFieldsAnnotatedWith(Object object, Class<? extends Annotation> annotationClass, boolean includeParents) {
        return getFieldsFilteringBy(object, f -> f.isAnnotationPresent(annotationClass), includeParents);
    }

    public static List<Field> getFieldsFilteringBy(Object object, Predicate<Field> filter, boolean includeParents) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null!");
        }
        List<Field> fields = new ArrayList<>(asList(object.getClass().getDeclaredFields()));
        if (includeParents) {
            Class<?> cls = object.getClass().getSuperclass();
            while (cls != null) {
                fields.addAll(asList(cls.getDeclaredFields()));
                cls = cls.getSuperclass();
            }
        }
        return fields.stream().filter(filter).collect(toList());
    }

    /**
     * Collects all fields in POM annotated with FindBy but not with @NotVisible
     *
     * @return All fields annotated with FindBy but no NotVisible in Page Object
     */
    protected List<WebElement> getOwnVisibleWebElements() {
        return getWebElementsWithAnnotationsAndDo(a -> getVisibleWebElements(a, false));
    }

    private <T extends WebElement> List<T> getVisibleWebElements(Class<? extends Annotation> findBy, boolean includeParents) {
        List<Object> visibleFields = getFieldsAnnotatedWith(this, findBy, includeParents)
                .stream()
                .filter(f -> !f.isAnnotationPresent(NotVisible.class))
                .map(f -> {
                    try {
                        f.setAccessible(true);
                        return f.get(this);
                    } catch (IllegalAccessException e) {
                        getLogger().error(format("Could not retrieve %s annotated with %s!", f.getName(), findBy), e);
                        return null;
                    }
                })
                .collect(toList());

        List<T> elementFields = new ArrayList<>();
        for (Object visibleField : visibleFields) {
            if (visibleField instanceof WebElement) {
                elementFields.add((T) visibleField);
            } else {
                elementFields.addAll((Collection<T>) visibleField);
            }
        }
        return elementFields;
    }

    protected <T extends WebElement> boolean areVisible(List<T> elements) {
        String notFound = elements
                .stream()
                .filter(element -> !isVisible(element))
                .map(element -> getNameForField(this, element))
                .collect(joining(", "));

        StringBuilder message = new StringBuilder();
        message
                .append("checking visibility of elements ")
                .append((format("%s ", elements.stream().map(element -> getNameForField(this, element)).collect(joining(", ")))))
                .append(" at ")
                .append(this.getClass().getSimpleName());

        boolean areVisible = true;
        if (!notFound.isEmpty()) {
            message
                    .append("Element(s) ")
                    .append("NOT FOUND: ")
                    .append(notFound);
            areVisible = false;
        }

        getLogger().info(message.toString());
        return areVisible;
    }

    private static <T extends WebElement> String getElementsAsString(final List<T> elements) {
        return elements == null ?
                "[null list]" :
                isEmpty(elements) ?
                        "[empty list]" :
                        "\n" + elements.stream().map(Object::toString).collect(joining("\n"));
    }

    public static <T extends WebElement> ExpectedCondition<List<T>> visibilityOfAllElements(final List<T> elements) {
        return new ExpectedCondition<List<T>>() {
            @Override
            public List<T> apply(WebDriver webDriver) {
                return elements != null && !elements.isEmpty() && elements.stream().allMatch(Elements::isVisible) ? elements : null;
            }

            @Override
            public String toString() {
                return format("visibility of all elements: %s", getElementsAsString(elements));
            }
        };
    }

    public static <T extends WebElement> ExpectedCondition<List<T>> listToHaveVisibleElements(final List<T> elements) {
        return new ExpectedCondition<List<T>>() {
            @Nullable
            @Override
            public List<T> apply(@Nullable WebDriver d) {
                return visibilityOfAllElements(listToHaveElements(elements).apply(d)).apply(d);
            }

            @Override
            public String toString() {
                return format("list to have visible elements: %s", getElementsAsString(elements));
            }
        };
    }

    protected <R, T extends WebElement> List<R> toListOfEntities(final List<T> elements, Class<R> type, long timeOut) {
        return waitFor(listToHaveVisibleElements(elements), timeOut, MILLIS, false)
                .orElse(emptyList())
                .stream()
                .map(e -> {
                    try {
                        Constructor<R> ctor;
                        try {
                            ctor = type.getConstructor(WebElement.class);
                        } catch (NoSuchMethodException nsme) {
                            ctor = type.getConstructor(MobileElement.class);
                        }
                        return ctor.newInstance(e);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                        getLogger().error(ex.getLocalizedMessage(), ex);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(toList());
    }

    protected <R, T extends WebElement> List<R> toListOfEntities(final List<T> elements, Class<R> type) {
        return toListOfEntities(elements, type, TEST_PROPERTIES.getExplicitWait());
    }

    protected <R, T extends WebElement> List<R> toListOfEntities(final List<T> elements, Function<T, R> as, long timeOut) {
        return waitFor(listToHaveVisibleElements(elements), timeOut, MILLIS, false)
                .orElse(emptyList())
                .stream()
                .map(as)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    protected <R, T extends WebElement> List<R> toListOfEntities(final List<T> elements, Function<T, R> as) {
        return toListOfEntities(elements, as, TEST_PROPERTIES.getExplicitWait());
    }

    protected <T> Supplier<List<T>> toCachedListOfEntities(final List<? extends WebElement> elements, Class<T> type) {
        return lazily(() -> toListOfEntities(elements, type));
    }

    protected <T> Supplier<List<T>> toCachedListOfEntities(final List<? extends WebElement> elements, Class<T> type, long timeOut) {
        return lazily(() -> toListOfEntities(elements, type, timeOut));
    }

    protected <R, T extends WebElement> Supplier<List<R>> toCachedListOfEntities(final List<T> elements, Function<T, R> as) {
        return lazily(() -> toListOfEntities(elements, as));
    }

    protected <R, T extends WebElement> Supplier<List<R>> toCachedListOfEntities(final List<T> elements, Function<T, R> as, long timeOut) {
        return lazily(() -> toListOfEntities(elements, as, timeOut));
    }

    protected String getText(WebElement element) {
        Optional<Boolean> exists = waitFor(visibilityOf(element), false);
        if (exists.isPresent() && exists.get()) {
            return element.getText();
        }
        return "COULD NOT BE FOUND";
    }
}
