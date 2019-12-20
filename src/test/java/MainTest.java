import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainTest {
    private WebDriver driver;

    @Before
    public void startBrowser() {
        driver = new ChromeDriver();
    }

    @Test
    public void test1() {
        driver.get("http://google.com");
    }

    @After
    public void finishBrowser() {
        driver.quit();
    }
}