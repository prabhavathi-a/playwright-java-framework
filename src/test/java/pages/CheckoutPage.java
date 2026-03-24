package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import utils.ScreenshotManager;

import java.util.Map;

public class CheckoutPage {
    private Page page;
    private final Locator proceedToCheckoutButton;
    private final Locator signedinAsText;
    private final Locator billingStreet;
    private final Locator billingCity;
    private final Locator billingState;
    private final Locator billingCountry;
    private final Locator billingZip;
    private final Locator paymentMethod;
    private final Locator orderConfirmButton;
    private final Locator paymentConfirmationMessage;

    public CheckoutPage(Page page) {
        this.page = page;
        proceedToCheckoutButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed to checkout"));
        signedinAsText = page.locator("app-login p");
        billingStreet = page.getByTestId("street");
        billingCity = page.getByTestId("city");
        billingState = page.getByTestId("state");
        billingCountry = page.getByTestId("country");
        billingZip = page.getByTestId("postal_code");
        paymentMethod = page.getByLabel("Payment Method");
        orderConfirmButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm"));
        paymentConfirmationMessage = page.getByTestId("payment-success-message");


    }

    public boolean verifyLoggedInUser(String name) {
        String expectedText = String.format("Hello %s, you are already logged in. You can proceed to checkout.", name);
        return signedinAsText.textContent().trim().equals(expectedText);
    }

    public Map<String, String> getBillingAddressDetails() {
        Map<String, String> billingAddressDetails = Map.of(
                "street", billingStreet.inputValue(),
                "city", billingCity.inputValue(),
                "state", billingState.inputValue(),
                "country", billingCountry.inputValue(),
                "zip", billingZip.inputValue()
        );
        return billingAddressDetails;
    }

    @Step("Entering shipping details: {street}, {city}, {state}, {country}, {zip}")
    public void enterShippingDetails(String street, String city, String state, String country, String zip) {
        if (billingStreet.inputValue() == null || billingStreet.inputValue().isEmpty()) {
            billingStreet.fill(street);
        }
        if (billingCity.inputValue() == null || billingCity.inputValue().isEmpty()) {
            billingCity.fill(city);
        }
        if (billingState.inputValue() == null || billingState.inputValue().isEmpty()) {
            billingState.fill(state);
        }
        if (billingCountry.inputValue() == null || billingCountry.inputValue().isEmpty()) {
            billingCountry.fill(country);
        }
        if (billingZip.inputValue() == null || billingZip.inputValue().isEmpty()) {
            billingZip.fill(zip);
        }
        ScreenshotManager.takeScreenshot("Enter_Shipping_Details");
    }

    @Step("Selecting payment method: {method}")
    public void selectPaymentMethod(String method) {
        paymentMethod.selectOption(method);
        ScreenshotManager.takeScreenshot("Select_Payment_Method_" + method.replace(" ", "_"));
    }

    @Step("Confirming the order")
    public void confirmOrder() {
        orderConfirmButton.click();
        ScreenshotManager.takeScreenshot("Confirm_Order");
    }

    @Step("Getting payment confirmation message")
    public String getPaymentConfirmationMessage() {
        ScreenshotManager.takeScreenshot("Get_Payment_Confirmation_Message");
        return paymentConfirmationMessage.textContent().trim();

    }

    @Step("Proceeding to checkout")
    public void proceedToCheckout() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed to checkout")).click();
        ScreenshotManager.takeScreenshot("Proceed_To_Checkout");
    }
}