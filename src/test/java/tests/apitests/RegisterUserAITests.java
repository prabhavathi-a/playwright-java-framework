package tests.apitests;

import base.BaseTest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import net.datafaker.Faker;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.PlaywrightFactory;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import java.util.HashMap;


public class RegisterUserAITests extends BaseTest {
//    Playwright playwright;
    APIRequestContext requestContext;

    record Address(String street, String city, String state, String country, String postal_code) {
    }

    record User(String first_name, String last_name, Address address, String phone, String dob, String password,
                String email) {
    }

    @BeforeMethod
    public void setup() {
        // Initialize any necessary data or state before each test

        requestContext = PlaywrightFactory.getPlaywright().request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://api.practicesoftwaretesting.com/")
                .setExtraHTTPHeaders(new HashMap<String, String>() {
                    {
                        put("accept", "application/json");
                    }
                }));

    }

    @Test
    public void testRegisterUser() {
        // Implement the test logic for registering a user using AI-generated data
        // For example, you can use a library like Faker to generate random user data
        // and then send a POST request to the registration endpoint of the API.

        // Example pseudo-code:
        Faker faker = new Faker();

        String randomFirstName = faker.name().firstName();
        String randomLastName = faker.name().firstName();
        String randomStreetName = faker.address().streetName();
        String randomCity = faker.address().city();
        String randomState = faker.address().state();
        String randomCountry = faker.address().country();
        String randomPostalCode = faker.address().zipCode();
        Address address = new Address(randomStreetName, randomCity, randomState, randomCountry, randomPostalCode);
        String randomPhone = faker.phoneNumber().phoneNumber();
        int age = faker.number().numberBetween(18, 50);

        LocalDate dob = LocalDate.now()
                .minusYears(age)
                .minusDays(faker.number().numberBetween(0, 365));
        String randomDOB = dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String randomEmail = faker.internet().emailAddress();
//        String randomPassword = faker.internet().password();
        String randomPassword = generatePassword();
        User user = new User(randomFirstName, randomLastName, address, randomPhone, randomDOB, randomPassword, randomEmail);
        String userjson=new Gson().toJson(user);
        System.out.println("user json: " + userjson);

        APIResponse response = requestContext.post("/users/register", RequestOptions.create().setData(user));
        JsonObject jsonObject=new Gson().fromJson(response.text(), JsonObject.class);
//        String password=jsonObject.get("password").getAsString();
        System.out.println("Password set was: " + jsonObject.get("password"));
        System.out.println("User was: " + jsonObject.get("email"));



    }
    public String generatePassword() {
        String password;
Faker faker = new Faker();
        do {
            password = faker.internet().password(8, 12, true, true, true);
        } while (!password.matches(".*[a-z].*")); // ensure at least one lowercase

        return password;
    }
    @AfterMethod
    public void tearDown() {
        // Clean up any resources or state after each test
        requestContext.dispose();

    }

}
