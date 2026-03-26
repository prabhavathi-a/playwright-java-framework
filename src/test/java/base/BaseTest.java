package base;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.PlaywrightFactory;
import utils.ScreenshotManager;

import java.nio.file.Path;

public class BaseTest {

    protected Page page;
    protected BrowserContext context;
    PlaywrightFactory  playwrightFactory;
    public BaseTest() {
        playwrightFactory = new PlaywrightFactory();
    }

    @BeforeClass
    public void classSetup() {
        playwrightFactory.initPlaywright();
    }

    @BeforeMethod
    public void setup() {
        page = playwrightFactory.initBrowser("chromium");
        PlaywrightFactory.getContext().tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page.navigate("https://practicesoftwaretesting.com/");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        ScreenshotManager.takeScreenshot("Before_Teardown_" + result.getMethod().getMethodName());
        String testName = result.getMethod().getMethodName();
        Path path = Path.of("target/traces", testName + ".zip");
        PlaywrightFactory.getContext().tracing().stop(new Tracing.StopOptions()
                .setPath(path));
        playwrightFactory.tearDown();
    }

    @AfterClass
    public void classTearDown() {
        playwrightFactory.closePlaywright();
    }
}