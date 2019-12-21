import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class MainTest {
    public static ThreadLocal<EventFiringWebDriver> threadLocal = new ThreadLocal<>();
    private EventFiringWebDriver driver;
    private WebDriverWait wait;

    public static class Listener extends AbstractWebDriverEventListener {
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println("TRY TO FIND - " + by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println("FOUND - " + by);
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println("NOT FOUND - " + throwable);
        }
    }

    @Before
    public void startBrowser() {
        if (threadLocal.get() != null) {
            driver = threadLocal.get();
            wait = new WebDriverWait(driver, 15);
            return;
        }

        System.setProperty("webdriver.chrome.driver", "src/main/resources/mac/chromedriver"); // driver for Mac OS
//        System.setProperty("webdriver.chrome.driver", "src/main/resources/linux/chromedriver"); // driver for Linux OS
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.register(new Listener());
        threadLocal.set(driver);
        wait = new WebDriverWait(driver, 15);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            driver.quit();
            driver = null;
        }));
    }

    @Test
    public void test1() {
        System.out.println("START CLASS - 1 - METHOD - 1");
        driver.get("https://translate.google.com");

        WebElement element = driver.findElement(By.xpath(".//textarea[@id = 'source']"));
        element.sendKeys("testing");
        wait.until(visibilityOfElementLocated(By.xpath(".//div[@class = 'gt-cd gt-cd-mmd']")));
        System.out.println("FINISH CLASS - 1 - METHOD - 1\n");
    }

    @Test
    public void test2() {
        System.out.println("START CLASS - 1 - METHOD - 2");
        driver.get("https://translate.google.com");

        WebElement element = driver.findElement(By.xpath(".//textarea[@id = 'source']"));
        element.sendKeys("testing");
        wait.until(visibilityOfElementLocated(By.xpath(".//div[@class = 'xxx']"))); // invalid locator
        System.out.println("FINISH CLASS - 1 - METHOD - 2\n");
    }
}