package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import pojos.Product;
import utils.ScreenshotManager;

import java.util.List;

public class CartPage {

    private final Page page;
    private final Locator lineitems;
    private final Locator cartTotalPrice;

    public CartPage(Page page) {
        this.page = page;
        lineitems = page.locator("app-cart tbody tr");
        this.cartTotalPrice = page.getByTestId("cart-total");
    }

    public void goToCart() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart")).click();

    }
@Step("Getting count of items in the cart")
    public int getCartItemsCount() {
        return page.getByTestId("cart-quantity").innerText().isEmpty() ? 0 : Integer.parseInt(page.getByTestId("cart-quantity").innerText());
    }
@Step("Getting total price of items in the cart")
    public double getCartTotalPrice() {
        String totalText = cartTotalPrice.innerText().replace("$", "");
        return Double.parseDouble(totalText);
    }
@Step("Proceeding to checkout")
    public void proceedToCheckout() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed to checkout")).click();
    ScreenshotManager.takeScreenshot("Proceed_To_Checkout");
    }

    public void continueShopping() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue shopping")).click();
        ScreenshotManager.takeScreenshot("Continue_Shopping");
    }
@Step("Removing item from cart: {productName}")
    public void removeItemFromCart(String productName) {
        Locator lineitem = lineitems.filter(new Locator.FilterOptions().setHasText(productName));
        lineitem.locator(".btn btn-danger").click();
    ScreenshotManager.takeScreenshot("Remove_Item_From_Cart");
    }
@Step("Getting list of product details in the cart")
    public List<Product> getLineItems() {
        waitForLineItemsToLoad();
        String cartproduct = lineitems.first().getByTestId("product-title").innerText();
        System.out.println("Cart product: " + cartproduct);
    ScreenshotManager.takeScreenshot("Cart_Line_Items");
        return lineitems.all().stream()
                .map(lineitem -> {
                    String productName = lineitem.getByTestId("product-title").innerText().strip().replace("\u00A0", "");
                    String quantity = lineitem.getByTestId("product-quantity").inputValue();
                    String price = lineitem.getByTestId("product-price").innerText();
                    String lineitemTotal = lineitem.getByTestId("line-price").innerText();
                    return new Product(productName, Integer.parseInt(quantity), Double.parseDouble(price.replace("$", "")), Double.parseDouble(lineitemTotal.replace("$", "")));
                })
                .toList();
    }

    private void waitForLineItemsToLoad() {
        page.waitForCondition(() -> lineitems.first().isVisible());
    }
}