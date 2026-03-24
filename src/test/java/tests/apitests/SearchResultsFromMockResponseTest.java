package tests.apitests;

import base.BaseTest;
import com.microsoft.playwright.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.SearchPage;
import pages.product.ProductPage;
import tests.mockResponses.MockResponseForSearch;
import utils.PlaywrightFactory;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchResultsFromMockResponseTest extends BaseTest {
//    Playwright playwright_api;
    APIRequestContext requestContext;
    @BeforeClass
    public void beforeClassSetup() {
        // Initialize any necessary data or state before each test
//         playwright_api = PlaywrightFactory.playwright;
        requestContext = PlaywrightFactory.getPlaywright().request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://api.practicesoftwaretesting.com/")
                .setExtraHTTPHeaders(new HashMap<String, String>() {
                    {
                        put("accept", "application/json");
                    }
                }));

    }
    @Test
    public void searchResultsFromMockResponseTest_SingleProduct() {
        // Mock the search API response
        page.route("**/search?q=pliers", route -> {
            route.fulfill(new Route.FulfillOptions()
                .setStatus(200)
                .setContentType("application/json")
                .setBody(MockResponseForSearch.MOCK_RESPONSE_FOR_SINGLE_PRODUCT)
            );
        });

        // Perform the search action on the UI
        SearchPage searchPage = new SearchPage(page);
        searchPage.searchByProduct("pliers");
        ProductPage productPage=new ProductPage(page);


        // Verify that the UI displays the mocked search results
        assertThat(productPage.getProductCount()).isEqualTo(1);

    }

    @Test
    public void searchResultsFromMockResponseTest_NoProduct() {
        // Mock the search API response
        page.route("**/search?q=abcde", route -> {
            route.fulfill(new Route.FulfillOptions()
                    .setStatus(200)
                    .setContentType("application/json")
                    .setBody(MockResponseForSearch.MOCK_RESPONSE_FOR_NO_PRODUCT)
            );
        });

        // Perform the search action on the UI
        SearchPage searchPage = new SearchPage(page);
        searchPage.searchByProduct("abcde");
        ProductPage productPage=new ProductPage(page);


        // Verify that the UI displays the mocked search results
        assertThat(productPage.getProductCount()).isEqualTo(0);

    }
}
