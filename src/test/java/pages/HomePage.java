package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import utils.ScreenshotManager;

public class HomePage {

    private Page page;
    private final Locator sortByOption;
    private final Locator menubar;

    public HomePage(Page page) {
        this.page = page;
        sortByOption = page.getByTestId("sort");
        menubar = page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main menu"));
    }

    @Step("Sorting products by: {option}")
    public void sortBy(String option) {
        sortByOption.selectOption(option);
        page.waitForLoadState(LoadState.NETWORKIDLE);
        ScreenshotManager.takeScreenshot("Sort_Products_By_" + option.replace(" ", "_"));
    }


}