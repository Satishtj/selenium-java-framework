package com.qaframework.tests;

import com.qaframework.base.BaseTest;
import com.qaframework.pages.CartPage;
import com.qaframework.pages.LoginPage;
import com.qaframework.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test(description = "Adding items updates the cart badge and the cart contents")
    public void addItemsReflectsInCart() {
        ProductsPage products = new LoginPage()
                .loginExpectingSuccess("standard_user", "secret_sauce");

        products.addItemToCart("sauce-labs-backpack")
                .addItemToCart("sauce-labs-bike-light");

        Assert.assertEquals(products.getCartItemCount(), 2, "Cart badge should show 2 items");

        CartPage cart = products.openCart();
        Assert.assertTrue(cart.isLoaded(), "Cart page should be displayed");
        Assert.assertEquals(cart.getItemCount(), 2, "Cart should list 2 items");
        Assert.assertTrue(cart.containsItem("Sauce Labs Backpack"), "Backpack should be in the cart");
    }
}
