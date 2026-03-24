package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import utils.ScreenshotManager;

public class SigninPage {

    private Page page;
    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator signinAlert;
    private final Locator invalidEmailAlert;


    public SigninPage(Page page) {
        this.page = page;
        this.emailInput = page.getByTestId("email");
        this.passwordInput = page.getByLabel("Password *", new Page.GetByLabelOptions().setExact(true));
        this.loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        this.signinAlert=page.getByTestId("login-error");
        invalidEmailAlert=page.locator("#email-error");
    }

    @Step("Logging in with email: {email}")
    public MyAccountDetailsPage login(String email, String password) {
        emailInput.fill(email);
        passwordInput.fill(password);
        loginButton.click();
        ScreenshotManager.takeScreenshot("Valid_Login_Attempt");
        return new MyAccountDetailsPage(page);
    }

    @Step("Getting sign-in alert message")
    public String getSigninAlertMessage() {
        ScreenshotManager.takeScreenshot("Invalid_Login_Attempt");
        return signinAlert.textContent().trim();
    }
    @Step("Getting invalid email alert message")
    public String   getInvalidEmailAlertMessage() {
        ScreenshotManager.takeScreenshot("Invalid_Email_Login_Attempt");
        return invalidEmailAlert.textContent().trim();
    }

}