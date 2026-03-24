package pages.product;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import utils.ScreenshotManager;

import java.util.List;
import java.util.stream.Collectors;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProductPage {

    private Page page;
    private final Locator productCards;

    public ProductPage(Page page) {
        this.page = page;
        this.productCards = page.locator(".card");
    }


    @Step("Opening product details page for the first product")
    public void openProductDetailsPageFirstProduct() {
        Locator firstCard = productCards.first();
        firstCard.click();
        ScreenshotManager.takeScreenshot("Open_Product_Details_First_Product");
    }

    @Step("Opening product details page for product: {productName}")
    public ProductDetailsPage openProductDetailsPage(String productName) {
        Locator productCard = page.locator(".card").filter(new Locator.FilterOptions().setHasText(productName));
        productCard.click();
        ScreenshotManager.takeScreenshot("Open_Product_Details_" + productName.replace(" ", "_"));
        return new ProductDetailsPage(page);
    }

    @Step("Getting count of products displayed on the product page")
    public int getProductCount() {
        ScreenshotManager.takeScreenshot("Get_Product_Count");
        return productCards.count();
    }

    @Step("Getting list of product names displayed on the product page")
    public List<String> getProductNames() {
        Locator productNames = page.locator(".card-title");
        productNames.first().waitFor();

        assertThat(productNames.first()).isVisible();
        ScreenshotManager.takeScreenshot("Get_Product_Names");
        return productNames.allInnerTexts();
    }

    @Step("Getting list of product prices displayed on the product page")
    public List<Double> getProductPrices() {
        assertThat(productCards.first()).isVisible();
        ScreenshotManager.takeScreenshot("Get_Product_Prices");
        return productCards.all().stream().map(card -> Double.parseDouble(card.getByTestId("product-price").innerText().replace("$", ""))).collect(Collectors.toList());
    }
}