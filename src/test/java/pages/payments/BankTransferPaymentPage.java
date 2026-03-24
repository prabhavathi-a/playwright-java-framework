package pages.payments;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utils.ScreenshotManager;

public class BankTransferPaymentPage {
    private Page page;
    private Locator bankNameInput;
    private Locator accountNumberInput;
    private Locator accountNameInput;

    public BankTransferPaymentPage(Page page) {
        this.page = page;
        this.bankNameInput = page.getByLabel("bank_name");

        this.accountNumberInput = page.getByLabel("account-number");
        this.accountNameInput = page.getByLabel("account-name");
    }

    @Step("Filling bank transfer details with bank name: {bankName}, account number: {accountNumber} and account name: {accountName}")
    public void fillBankTransferDetails(String bankName, String accountNumber, String accountName) {
        bankNameInput.fill(bankName);
        accountNumberInput.fill(accountNumber);
        accountNameInput.fill(accountName);
        ScreenshotManager.takeScreenshot("Filled_Bank_Transfer_Details");
    }
}
