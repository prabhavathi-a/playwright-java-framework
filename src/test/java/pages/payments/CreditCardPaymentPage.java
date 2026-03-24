package pages.payments;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utils.ScreenshotManager;

public class CreditCardPaymentPage {

    private Page page;
    private final Locator creditCardNumberInput;
    private final Locator expiryDateInput;
    private final Locator cvvInput;
    private final Locator cardHolderNameInput;


    public CreditCardPaymentPage(Page page) {
        this.page = page;
        this.creditCardNumberInput = page.getByLabel("Credit Card Number");
        this.expiryDateInput = page.getByLabel("Expiration Date");
        this.cvvInput = page.getByLabel("CVV");
        this.cardHolderNameInput = page.getByLabel("Card Holder Name");
    }
@Step("Entering credit card details with card number: {cardNumber}, expiry date: {expiryDate}, cvv: {cvv} and card holder name: {cardHolderName}")
    public void enterCreditCardDetails(String cardNumber, String expiryDate, String cvv, String cardHolderName) {
        creditCardNumberInput.fill(cardNumber);
        expiryDateInput.fill(expiryDate);
        cvvInput.fill(cvv);
        cardHolderNameInput.fill(cardHolderName);
    ScreenshotManager.takeScreenshot("Entered_Credit_Card_Details");
    }

}
