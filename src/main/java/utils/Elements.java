package utils;

import java.util.List;
import javax.annotation.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static utils.Fluent.doWithTryCatch;

public class Elements {

    private Elements() {
    }

    public static <T extends WebElement> boolean isStale(final T element) {
        return doWithTryCatch(() -> element.getTagName().isEmpty(), true); // Should return false always unless stalled
    }

    public static <T extends WebElement> boolean isVisible(final T e) {
        return doWithTryCatch(e::isDisplayed, false);
    }

    public static <T> boolean isEmpty(List<T> list) {
        return list != null && list.isEmpty();
    }

    public static <T extends WebElement> boolean isEnabled(final T e) {
        return doWithTryCatch(e::isEnabled, false);
    }

    public static <T extends WebElement> ExpectedCondition<Boolean> visibilityOf(final T e) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return isVisible(e);
            }

            @Override
            public String toString() {
                return format("visibility of element [%s]", e.toString());
            }
        };
    }

    public static <T extends WebElement> ExpectedCondition<Boolean> presenceOf(final T element) {
        return new ExpectedCondition<Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver webDriver) {
                return doWithTryCatch(() -> !element.getAttribute("elementId").isEmpty(), false);
            }

            @Override
            public String toString() {
                return format("presence in DOM of element: %s", element);
            }
        };
    }

    public static <T extends WebElement> ExpectedCondition<Boolean> clickabilityOf(final T e) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return isEnabled(e) && isVisible(e);
            }

            @Override
            public String toString() {
                return format("clickable state of element [%s]", e.toString());
            }
        };
    }

    private static <T extends WebElement> String getElementsAsString(final List<T> elements) {
        return elements == null ?
                "[null list]" :
                isEmpty(elements) ?
                        "[empty list]" :
                        "\n" + elements.stream().map(Object::toString).collect(joining("\n"));
    }

    public static <T extends WebElement> ExpectedCondition<Boolean> listNotToBeEmpty(final List<T> elements) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(@Nullable WebDriver driver) {
                return !isEmpty(elements) && elements.stream().noneMatch(Elements::isStale);
            }

            @Override
            public String toString() {
                return format("list not to be empty: %s", getElementsAsString(elements));
            }
        };
    }

    public static <T extends WebElement> ExpectedCondition<List<T>> listToHaveElements(final List<T> elements) {
        return new ExpectedCondition<List<T>>() {
            @Override
            public List<T> apply(@Nullable WebDriver driver) {
                return listNotToBeEmpty(elements).apply(driver) ? elements : null;
            }

            @Override
            public String toString() {
                return format("list to have elements: %s", getElementsAsString(elements));
            }
        };
    }

}
