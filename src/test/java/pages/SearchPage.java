package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;
import io.qameta.allure.Step;
import utils.ScreenshotManager;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchPage {
    private Page page;
    private final Locator searchInput;
    private final Locator searchButton;
    private final Locator minSlider;
    private final Locator maxSlider;

    public SearchPage(Page page) {
        this.page = page;
        searchInput = page.locator("#search-query");
        searchButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));
        minSlider = page.locator(".ngx-slider-pointer-min");
//        ngx-slider-span ngx-slider-pointer ngx-slider-pointer-min
//        ngx-slider-span ngx-slider-pointer ngx-slider-pointer-min
        maxSlider = page.locator(".ngx-slider-pointer-max");
    }

    @Step("Searching for product: {productName}")
    public void searchByProduct(String productName) {
        searchInput.fill(productName);
        searchButton.click();
        ScreenshotManager.takeScreenshot("Search_By_Product");
    }

    @Step("Searching for products in price range: {minTarget} - {maxTarget}")
    public void searchByPriceRange(int minTarget, int maxTarget) {

        adjustSlider(minSlider, minTarget, true);
        adjustSlider(maxSlider, maxTarget, false);
        ScreenshotManager.takeScreenshot("Search_By_Price_Range");
    }

    private void adjustSlider(Locator slider, int targetValue, boolean isMin) {

        // Step 1: Get current value
        int current = Integer.parseInt(slider.getAttribute("aria-valuenow"));

        // Step 2: Rough move using mouse (FAST)
        BoundingBox box = slider.boundingBox();

        double moveBy = box.width * 0.5; // move 50% as rough step

        page.mouse().move(
                box.x + box.width / 2,
                box.y + box.height / 2
        );
        page.mouse().down();

        if (targetValue > current) {
            page.mouse().move(box.x + moveBy, box.y);
        } else {
            page.mouse().move(box.x - moveBy, box.y);
        }

        page.mouse().up();

        // Step 3: Fine-tune using keyboard (PRECISE)
        slider.focus();

        current = Integer.parseInt(slider.getAttribute("aria-valuenow"));

        while (current != targetValue) {
            if (current < targetValue) {
                slider.press("ArrowRight");
            } else {
                slider.press("ArrowLeft");
            }
            current = Integer.parseInt(slider.getAttribute("aria-valuenow"));
        }
    }


}
