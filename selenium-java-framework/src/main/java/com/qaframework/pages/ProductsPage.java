package com.qaframework.pages;

import org.openqa.selenium.By;

/**
 * Inventory (products) page shown after a successful login on Swag Labs.
 */
public class ProductsPage extends BasePage {

    private final By pageTitle = By.cssSelector(".title");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.cssSelector(".shopping_cart_link");

    public boolean isLoaded() {
        return isDisplayed(pageTitle) && "Products".equals(getText(pageTitle));
    }

    public ProductsPage addItemToCart(String itemName) {
        String id = "add-to-cart-" + itemName.toLowerCase().replace(" ", "-");
        click(By.id(id));
        return this;
    }

    public int getCartItemCount() {
        if (!isDisplayed(cartBadge)) {
            return 0;
        }
        return Integer.parseInt(getText(cartBadge).trim());
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage();
    }
}
