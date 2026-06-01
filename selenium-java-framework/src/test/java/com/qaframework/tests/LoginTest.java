package com.qaframework.tests;

import com.qaframework.base.BaseTest;
import com.qaframework.pages.LoginPage;
import com.qaframework.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Valid credentials land the user on the products page")
    public void validLoginShowsProducts() {
        ProductsPage products = new LoginPage()
                .loginExpectingSuccess("standard_user", "secret_sauce");

        Assert.assertTrue(products.isLoaded(), "Products page should be displayed after valid login");
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"locked_out_user", "secret_sauce", "locked out"},
                {"standard_user", "wrong_password", "do not match"},
                {"", "secret_sauce", "Username is required"},
                {"standard_user", "", "Password is required"},
        };
    }

    @Test(dataProvider = "invalidCredentials",
            description = "Invalid credentials surface a clear error message")
    public void invalidLoginShowsError(String username, String password, String expectedFragment) {
        LoginPage loginPage = new LoginPage()
                .loginExpectingFailure(username, password);

        Assert.assertTrue(loginPage.isErrorDisplayed(), "An error message should be displayed");
        Assert.assertTrue(
                loginPage.getErrorMessage().toLowerCase().contains(expectedFragment.toLowerCase()),
                "Error should mention '" + expectedFragment + "' but was: " + loginPage.getErrorMessage());
    }
}
