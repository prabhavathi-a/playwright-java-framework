package utils;

import com.microsoft.playwright.*;

public class PlaywrightFactory {
    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();

    public void initPlaywright() {
        playwright.set(Playwright.create());
        playwright.get().selectors().setTestIdAttribute("data-test");
    }

    public Page initBrowser(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chromium":
                boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true"));
                browser.set(playwright.get().chromium().launch(
                        new BrowserType.LaunchOptions().setHeadless(isHeadless)
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

    public static Playwright getPlaywright() { return playwright.get(); }
    public static Browser getBrowser() { return browser.get(); }
    public static BrowserContext getContext() { return context.get(); }
    public static Page getPage() { return page.get(); }

    public void tearDown() {
        if (page.get() != null) page.get().close();
        if (context.get() != null) context.get().close();
        if (browser.get() != null) browser.get().close();
    }

    public void closePlaywright() {
        if (playwright.get() != null) playwright.get().close();
    }
}