package com.qaframework.pages;

import org.openqa.selenium.By;

/**
 * Login page for the public Swag Labs demo (https://www.saucedemo.com).
 */
public class LoginPage extends BasePage {

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public ProductsPage loginExpectingSuccess(String username, String password) {
        submitCredentials(username, password);
        return new ProductsPage();
    }

    public LoginPage loginExpectingFailure(String username, String password) {
        submitCredentials(username, password);
        return this;
    }

    private void submitCredentials(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }
}
