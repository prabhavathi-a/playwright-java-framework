package utils;

import com.microsoft.playwright.*;

public class PlaywrightFactory {
    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();


    public  Page initBrowser(String browserName) {

        playwright.set(Playwright.create());
        playwright.get().selectors().setTestIdAttribute("data-test");
        switch (browserName.toLowerCase()) {
            case "chromium":
                browser.set(playwright.get().chromium().launch(
                        new BrowserType.LaunchOptions().setHeadless(false)
                ));
                break;

            case "firefox":
                browser.set(playwright.get().firefox().launch(
                        new BrowserType.LaunchOptions().setHeadless(false)
                ));
                break;

            case "webkit":
                browser.set(playwright.get().webkit().launch(
                        new BrowserType.LaunchOptions().setHeadless(false)
                ));
                break;

            default:
                throw new RuntimeException("Browser not supported: " + browserName);
        }

        context.set(browser.get().newContext());
        page.set(context.get().newPage());

        return getPage();
    }

    public static Playwright getPlaywright() {
        return playwright.get();
    }

    public static Browser getBrowser() {
        return browser.get();
    }

    public static BrowserContext getContext() {
        return context.get();
    }

    public static Page getPage() {
        return page.get();
    }

    // Close all resources
    public void tearDown() {
        if (page.get() != null) {
            page.get().close();
        }
        if (context.get() != null) {
            context.get().close();
        }
        if (browser.get() != null) {
            browser.get().close();
        }
        if (playwright.get() != null) {
            playwright.get().close();
        }
    }
}