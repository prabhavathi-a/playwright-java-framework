package tests.uitests;

import base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.NavigationPage;
import pages.SearchPage;
import pages.product.ProductPage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@Feature("Search Functionality")
public class SearchTests extends BaseTest {
    private final String searchtext = "Pliers";
    private final int minPrice = 10;
    private final int maxPrice = 50;
@Story("Search products by keyword")
    @Test
    public void verifySearchResultsByKeyword() {
        NavigationPage navigationPage = new NavigationPage(page);
        HomePage homePage = navigationPage.openHomePage();
        SearchPage searchPage = new SearchPage(page);
        searchPage.searchByProduct(searchtext);
        ProductPage productPage = new ProductPage(page);
        assertThat(productPage.getProductCount()).isGreaterThan(0);
        System.out.println("search text: "+searchtext);
        productPage.getProductNames().stream().forEach(name->System.out.println(name.toLowerCase()));
        assertThat(productPage.getProductNames().stream().peek(System.out::println).allMatch(s -> s.toLowerCase().contains(searchtext.toLowerCase()))).isEqualTo(true);

    }
@Story("Search products by price range")
    @Test
    public void verifySearchResultsByPriceRange(){

        SearchPage searchPage = new SearchPage(page);
        searchPage.searchByPriceRange(minPrice,maxPrice);
        ProductPage productPage = new ProductPage(page);
        assertThat(productPage.getProductCount()).isGreaterThan(0);
        assertThat(productPage.getProductPrices().stream()
                .allMatch(productPrice ->
                        productPrice>(double) minPrice && productPrice<(double) maxPrice)).isEqualTo(true);
    }

}
