package services;

import dao.CartDao;
import model.cart.Cart;
import model.cart.CartDto;
import model.cart.CartItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartServices {

    public Cart cancelCart(Cart cart) {
        ProductServices productServices = new ProductServices();
        List<CartItem> cartItems = cart.getCartItems();

        productServices.getAllSoledItemsBack(cartItems);
        return cart;
    }

    public double getTotalCost(Cart cart) {
        cart.setTotalCost(cart.getCartItems());
        return cart.getTotalCost();
    }

    public void addingCart(Cart cart) {
        CartDao cartDao = new CartDao();
        cartDao.addACart(cart);
    }

    public ArrayList<CartDto> getCartsOfAPeriod(int customerId, Date startDate, Date endDate) {
        CartDao cartDao = new CartDao();
        return cartDao.findCarts(customerId, startDate, endDate);
    }
}