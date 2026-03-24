package tests.apitests;

import base.BaseTest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.SearchPage;
import pages.product.ProductPage;
import pojos.Product_1;

import java.util.HashMap;
import java.util.Iterator;

public class PlaywrightAPITest extends BaseTest {
    Playwright playwright;
    APIRequestContext apiRequestContext;

    @BeforeClass
    public void beforeClass() {
        // This method will run before each test method in this class
        System.out.println("Setting up the test environment...");
        playwright = Playwright.create();
        apiRequestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://api.practicesoftwaretesting.com/")
                .setExtraHTTPHeaders(new HashMap<String, String>() {
                    {
                        put("accept", "application/json");
                    }
                }));

    }

    @Test(dataProvider = "getProducts")
    public void testGetProducts(Product_1 product) {
        System.out.println("Testing product: " + product.getName() + " with price: " + product.getPrice());
        SearchPage homePage = new SearchPage(page);
        homePage.searchByProduct(product.getName());
        ProductPage productPage = new ProductPage(page);
        productPage.openProductDetailsPage(product.getName());

    }

    @DataProvider
    public Iterator<Product_1> getProducts() {
        APIResponse response = apiRequestContext.get("/products?page=2");

        JsonObject jsonObject = new Gson().fromJson(response.text(), JsonObject.class);
        JsonArray data = jsonObject.getAsJsonArray("data");
        return data.asList().stream().map(jsonElement -> {
            JsonObject product = jsonElement.getAsJsonObject();
            System.out.println("Product Name: " + product.get("name").getAsString());
            return new Product_1(product.get("name").getAsString(),
                    product.get("price").getAsDouble());
        }).iterator();
    }
}
