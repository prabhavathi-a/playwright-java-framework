package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import utils.ScreenshotManager;

public class NavigationPage {


    private Page page;

    private final Locator menubar;
    private final Locator homeLink;
    private final Locator categorieslink;
    private final Locator contactLink;
    private final Locator signinLink;
    private final Locator cartLink;

    public NavigationPage(Page page) {
        this.page = page;
        menubar = this.page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main menu"));
        homeLink = this.page.getByTestId("nav-home");
        categorieslink = this.page.getByTestId("nav-categories");
        contactLink = this.page.getByTestId("nav-contact");
        signinLink = this.page.getByTestId("nav-sign-in");
        cartLink = this.page.getByTestId("nav-cart");
    }

    @Step("Opening home page")
    public HomePage openHomePage() {
        homeLink.click();
        ScreenshotManager.takeScreenshot("Open_Home_Page");
        return new HomePage(page);
    }

    @Step("Selecting by category: {category}")
    public void selectCategories(String category) {
        categorieslink.selectOption(category);
        ScreenshotManager.takeScreenshot("Select_Product_Category");
    }

    @Step("Opening contact page")
    public void openContactPage() {
        contactLink.click();
        ScreenshotManager.takeScreenshot("Open_Contact_Page");
    }

    @Step("Opening sign-in page")
    public SigninPage openSignInPage() {
        page.getByTestId("nav-sign-in").click();
        ScreenshotManager.takeScreenshot("Open_Signin_Page");
        return new SigninPage(page);
    }

    @Step("Opening Cart Page")
    public CartPage openCartPage() {
        cartLink.click();
        ScreenshotManager.takeScreenshot("Open_Cart_Page");
        return new CartPage(page);
    }
}
