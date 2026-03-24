package tests.uitests;

import base.BaseTest;
import io.qameta.allure.Feature;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import pages.*;
import pages.product.ProductDetailsPage;
import pages.product.ProductPage;
import pojos.Product;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@Feature("Cart Functionality")
public class CartTests extends BaseTest {
    Map<String, Integer> products = Map.of("Combination Pliers", 3, "Bolt Cutters", 4);

    @Test
    public void verifyCartDetails() {
        SearchPage searchPage = new SearchPage(page);
        NavigationPage navigationPage = new NavigationPage(page);
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
    }
}