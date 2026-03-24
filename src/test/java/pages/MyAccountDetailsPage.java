package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utils.ScreenshotManager;

public class MyAccountDetailsPage {
    private final Page page;
    private final Locator myAccountDetailsHeader;


    public MyAccountDetailsPage(Page page) {
        this.page = page;
        this.myAccountDetailsHeader = page.locator("//h1[@data-test='page-title'][text()='My account']");
    }

    @Step("Verifying My Account Details page is displayed after successful login")
    public boolean isMyAccountDetailsPageDisplayed() {
        page.waitForCondition(myAccountDetailsHeader::isVisible);
        ScreenshotManager.takeScreenshot("My_Account_Details_Page_Displayed");
        return myAccountDetailsHeader.isVisible();
    }
}
