package tests.uitests;

import base.BaseTest;
import io.qameta.allure.Feature;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import pages.*;
import pages.payments.CreditCardPaymentPage;
import pages.product.ProductDetailsPage;
import pages.product.ProductPage;
import pojos.Product;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@Feature("Checkout Flow")
public class CheckoutTests extends BaseTest {
    Map<String, Integer> products = Map.of("Combination Pliers", 3, "Bolt Cutters", 4);

    @Test
    public void verifyCheckoutFlow() {
        NavigationPage navigationPage = new NavigationPage(page);
        SigninPage signinPage=navigationPage.openSignInPage();
        MyAccountDetailsPage myAccountDetailsPage=signinPage.login("customer@practicesoftwaretesting.com", "welcome01");
        myAccountDetailsPage.isMyAccountDetailsPageDisplayed();
        navigationPage.openHomePage();
        SearchPage searchPage = new SearchPage(page);

        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            searchPage.searchByProduct(entry.getKey());
            ProductPage product = new ProductPage(page);
            ProductDetailsPage productDetailsPage = product.openProductDetailsPage(entry.getKey());
            productDetailsPage.addToCart(entry.getValue());
            navigationPage.openHomePage();
        }
        CartPage cart = navigationPage.openCartPage();
        List<Product> cartItems = cart.getLineItems();
        assertThat(cartItems.size()).isEqualTo(products.size());
        SoftAssertions softly = new SoftAssertions();

        for (String product : products.keySet()) {
            Product cartProduct = cartItems.stream()
                    .filter(cartItem -> {
                        return cartItem.productName().equalsIgnoreCase(product);
                    })
                    .findFirst()
                    .orElse(null);
            softly.assertThat(cartProduct).isNotNull();
            if (cartProduct != null) {
                softly.assertThat(cartProduct.productName()).isEqualToIgnoringCase(product);
                softly.assertThat(cartProduct.quantity()).isEqualTo(products.get(product));
                softly.assertThat(cartProduct.price()).isGreaterThan(0);
                softly.assertThat(cartProduct.lineitemTotal()).isEqualTo(cartProduct.price() * cartProduct.quantity());
            }
        }

        softly.assertAll();
            cart.proceedToCheckout();
            CheckoutPage checkoutPage=new CheckoutPage(page);
            checkoutPage.proceedToCheckout();
            checkoutPage.enterShippingDetails("John Doe", "123 Main St", "Anytown", "12345", "USA");
            checkoutPage.proceedToCheckout();
            checkoutPage.selectPaymentMethod("Credit Card");
        CreditCardPaymentPage creditCardPaymentPage=new CreditCardPaymentPage(page);

        creditCardPaymentPage.enterCreditCardDetails("4111-1111-1111-1111", "12/2027", "123","John Doe");
            checkoutPage.confirmOrder();
            assertThat(checkoutPage.getPaymentConfirmationMessage()).contains("Payment was successful");
    }
}