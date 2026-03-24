package pages.payments;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utils.ScreenshotManager;

public class BugNowPayLaterPaymentPage {

    private Page page;
    private final Locator monthlyInstallmentsOption;

    public BugNowPayLaterPaymentPage(Page page) {
        this.page = page;
        this.monthlyInstallmentsOption = page.getByLabel("Monthly Installments");
    }
@Step("Selecting monthly installments option with value: {value}")
    public void selectMonthlyInstallments(int value) {
        String optionText= String.format("%d Monthly Installments", value);
        monthlyInstallmentsOption.selectOption(optionText);
    ScreenshotManager.takeScreenshot("Monthly_Installments_Selected_" + value);
    }
}
