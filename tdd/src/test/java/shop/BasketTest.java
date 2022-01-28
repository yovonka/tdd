package shop;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasketTest {

    private static final double PRICE_DELTA = 0.001;

    private Item item;
    private Basket basket;

    @Before
    public void setItem() {
        basket = new Basket();
        item = new Item("Ball", 5.5);
    }

    @Test
    public void countItemWhenAny() {
        assertEquals(0, basket.countItem(item));
    }

    @Test
    public void addFirstItemToBasket() {
        basket.addItem(item);
        assertTrue(basket.haveItem(item));
    }

    @Test
    public void addAnotherItemToBasket() {
        basket.addItem(item);
        basket.addItem(item);
        assertEquals(2, basket.countItem(item));
    }

    @Test
    public void addMoreItemToBasket() {
        basket.addItems(item, 2);
        assertEquals(2, basket.countItem(item));
    }

    @Test(expected = NullPointerException.class)
    public void addNonPositiveAmountItemToBasket() {
        basket.addItems(item, -2);
    }

    @Test
    public void removeItemFromBasketWhenMoreThanOne() {
        basket.addItem(item);
        basket.addItem(item);
        basket.removeItem(item);
        assertEquals(1, basket.countItem(item));
    }

    @Test
    public void removeLastItemFromBasket() {
        basket.addItem(item);
        basket.removeItem(item);
        assertFalse(basket.haveItem(item));
    }

    @Test(expected = NullPointerException.class)
    public void removeWhenItemNotInBasket() {
        basket.removeItem(item);
    }

    @Test
    public void removeAllItemFromBasket() {
        basket.addItems(item, 2);
        basket.removeItems(item, 2);
        assertFalse(basket.haveItem(item));
    }

    @Test
    public void removeMoreItemFromBasket() {
        basket.addItems(item, 3);
        basket.removeItems(item, 2);
        assertEquals(1, basket.countItem(item));
    }

    @Test(expected = NullPointerException.class)
    public void removeMoreItemThanHaveInBasket() {
        basket.addItems(item, 2);
        basket.removeItems(item, 3);
    }

    @Test
    public void calculateOrderWhenNotEmptyBasket() {
        basket.addItem(item);
        basket.addItem(item);
        assertEquals(basket.calculateOrder().doubleValue(), 11.0, PRICE_DELTA);

    }

    @Test(expected = NullPointerException.class)
    public void calculateOrderForEmptyBasket() {
        basket.calculateOrder();
    }

    @Test()
    public void showBasket() {
        basket.addItem(item);
        basket.addItem(item);
        assertEquals("Ball 2szt.", basket.showOrder());
    }

    @Test(expected = NullPointerException.class)
    public void showOrderForEmptyBasket() {
        basket.showOrder();
    }
}