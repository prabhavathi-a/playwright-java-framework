package pages.product;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import io.qameta.allure.Step;
import utils.ScreenshotManager;

public class ProductDetailsPage {

    private Page page;
    private final Locator addToCartButton;
    private final Locator addToFavoritesButton;
    private final Locator increaseQuantityButton;
    private final Locator decreaseQuantityButton;
    private final Locator productTitle;
    private final Locator productQuantityInput;
    private final Locator productPrice;
    private final Locator addToCartAlertMsg;

    public ProductDetailsPage(Page page) {
        this.page = page;
        this.addToCartButton = page.getByTestId("add-to-cart");
        this.addToFavoritesButton = page.getByTestId("add-to-favorites");
        this.increaseQuantityButton = page.getByTestId("increase-quantity");
        this.decreaseQuantityButton = page.getByTestId("decrease-quantity");
        this.productTitle = page.getByTestId("product-name");
        this.productQuantityInput = page.getByTestId("quantity");
        this.productPrice = page.getByTestId("unit-price");
        this.addToCartAlertMsg = page.getByLabel("Product added to shopping cart.");
    }

    @Step("Adding product to cart with quantity: {quantity}")
    public void addToCart(int quantity) {
        productQuantityInput.fill(String.valueOf(quantity));
        addToCartButton.click();
        page.waitForCondition(() -> addToCartAlertMsg.isVisible());
        page.waitForCondition(() -> !addToCartAlertMsg.isVisible());
        ScreenshotManager.takeScreenshot("Add_Product_To_Cart_Quantity_" + quantity);

    }

    @Step("Adding product to favorites")
    public void addToFavorites() {
        addToFavoritesButton.click();
        ScreenshotManager.takeScreenshot("Add_Product_To_Favorites");
    }

    @Step("Getting product title from product details page")
    public String getProductTitle() {
        ScreenshotManager.takeScreenshot("Get_Product_Title");
        return productTitle.innerText();
    }

    @Step("Getting product quantity from product details page")
    public int getProductQuantity() {
        ScreenshotManager.takeScreenshot("Get_Product_Quantity");
        return Integer.parseInt(productQuantityInput.inputValue());
    }

    @Step("Getting product price from product details page")
    public double getProductPrice() {

        ScreenshotManager.takeScreenshot("Get_Product_Price");
        return Double.parseDouble(productPrice.innerText().replace("$", ""));
    }

}
