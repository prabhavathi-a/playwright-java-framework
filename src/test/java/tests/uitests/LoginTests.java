package tests.uitests;

import base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MyAccountDetailsPage;
import pages.SigninPage;
import pages.NavigationPage;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
@Feature("Login Tests")
public class LoginTests extends BaseTest {
@Story("Valid Login Test")
    @Test
    public void validLoginTest() {
        NavigationPage navigationPage = new NavigationPage(page);
        SigninPage signinPage=navigationPage.openSignInPage();
        MyAccountDetailsPage myAccountDetailsPage=signinPage.login("customer@practicesoftwaretesting.com", "welcome01");

        Assert.assertTrue(myAccountDetailsPage.isMyAccountDetailsPageDisplayed());
    }
@Story("Invalid Login Test")
    @Test
    public void invalidLoginTest() {
        NavigationPage navigationPage = new NavigationPage(page);
        SigninPage signinPage=navigationPage.openSignInPage();
        signinPage.login("customer@practicesoftwaretesting.com","abcd1234");
        assertThat(signinPage.getSigninAlertMessage()).isEqualTo("Invalid email or password");
    }
@Story("Invalid Email Format Login Test")
    @Test
    public void invalidEmailFormatLoginTest() {
        NavigationPage navigationPage = new NavigationPage(page);
        SigninPage signinPage=navigationPage.openSignInPage();
        signinPage.login("customer","abcd1234");
        assertThat(signinPage.getInvalidEmailAlertMessage()).isEqualTo("Email format is invalid");
    }
}