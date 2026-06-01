package com.qaframework.pages;

import org.openqa.selenium.By;

/**
 * Cart page on Swag Labs, reached from the cart icon on the products page.
 */
public class CartPage extends BasePage {

    private final By pageTitle = By.cssSelector(".title");
    private final By cartItems = By.cssSelector(".cart_item");

    public boolean isLoaded() {
        return isDisplayed(pageTitle) && "Your Cart".equals(getText(pageTitle));
    }

    public int getItemCount() {
        return driver.findElements(cartItems).size();
    }

    public boolean containsItem(String itemName) {
        By itemLabel = By.xpath("//div[@class='inventory_item_name' and normalize-space()='" + itemName + "']");
        return isDisplayed(itemLabel);
    }
}
