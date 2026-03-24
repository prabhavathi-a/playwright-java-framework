package pages.payments;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utils.ScreenshotManager;

public class GiftCardPaymentPage {
    private Page page;
    private final Locator giftCardNumberInput;
    private final Locator giftCardValidationCodeInput;

    public GiftCardPaymentPage(Page page) {
        this.page = page;
        this.giftCardNumberInput = page.getByLabel("Gift Card Number");
        this.giftCardValidationCodeInput = page.getByLabel("Validation Code");
    }
@Step("Filling gift card details with number: {giftCardNumber} and validation code: {validationCode}")
    public void fillGiftCardDetails(String giftCardNumber, String validationCode) {
        giftCardNumberInput.fill(giftCardNumber);
        giftCardValidationCodeInput.fill(validationCode);
    ScreenshotManager.takeScreenshot("Filled_Gift_Card_Details");
    }
}
