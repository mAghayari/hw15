package view;

import model.cart.Cart;
import model.cart.CartItem;
import model.product.Product;
import services.CartServices;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CartView {
    public static void printCart(CartServices cartServices, Cart cart) {
        CartItemView cartItemView = new CartItemView();
        if (cart.getCartItems().isEmpty())
            System.out.println("❌ Your cart is empty\n");
        else {
            List<CartItem> cartItems = cart.getCartItems();
            Comparator<CartItem> comparator = Comparator.comparing(CartItem::getProduct);
            cartItems.sort(comparator);
            cartItemView.printCartItems(cart.getCartItems());
            System.out.println("Total Cost: " + cartServices.getTotalCost(cart) + "\n");
        }
    }

    public Cart deleteOperation(Cart cart, ArrayList<Product> products) {
        CartServices cartServices = new CartServices();
        CartItemView cartItemView = new CartItemView();

        System.out.println("1-Delete An Item\n2-Empty Cart\n3-Main Menu");
        int editItem = GetUserInputs.getInBoundDigitalInput(3);
        if (editItem == 1) {
            cartItemView.deleteAnCartItem(cart, products);
        } else if (editItem == 2) {
            cart = cartServices.cancelCart(cart);
            cart.setCartItems(new ArrayList<>());
        }
        return cart;
    }

    public void finalizeCart(Cart cart) {
        GetUserInputs getUserInputs = new GetUserInputs();
        CartServices cartServices = new CartServices();
        if (cart.getCartItems().isEmpty())
            System.out.println("❌ Your cart is empty\n");
        else {
            cart.setDate();
            System.out.println("Where do you wanna receive your purchase:\n" +
                    "1-My Account Address\n2-Another Address");
            int addressItem = GetUserInputs.getInBoundDigitalInput(2);
            if (addressItem == 1)
                cart.setAddress(cart.getCustomer().getAddress());
            else
                cart.setAddress(getUserInputs.getAddress());
            cartServices.addingCart(cart);
            cart.setCartItems(new ArrayList<>());
            System.out.println("✔ Thank you for your shopping");
        }
    }
}