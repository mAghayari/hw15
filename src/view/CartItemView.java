package view;

import dao.ProductDao;
import model.cart.Cart;
import model.cart.CartItem;
import model.product.Product;
import services.ProductServices;

import java.util.ArrayList;
import java.util.List;

public class CartItemView {

    public void deleteAnCartItem(Cart cart, ArrayList<Product> products) {
        ProductServices productServices = new ProductServices();
        List<CartItem> cartItems = cart.getCartItems();

        printCartItems(cartItems);

        System.out.println("Enter id of a product to delete it");
        int id = GetUserInputs.getInBoundDigitalInput(products.size());

        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getId() == id) {
                productServices.getASoledItemBack(cartItem);
                cartItems.remove(cartItem);
                cart.setCartItems(cartItems);
                cart.setTotalCost(cartItems);

                System.out.println("✔ Item deleted");
                return;
            }
        }
        System.out.println("❌ No such ID was found in your cart" +
                "\n");
    }

    public void printCartItems(List<CartItem> cartItems) {
        System.out.println("Your ordered:\n");

        for (CartItem cartItem : cartItems) {
            System.out.println(cartItem.orderItemForSavingOrder());
        }
    }

    public void addProductToCart(Cart cart, ProductDao productDao, ArrayList<Product> products, String category) {
        if (cart.getCartItems().size() >= 5) {
            System.out.println("❌ There are 5 products in your cart,\n" +
                    "Finalize them or remove some then continue shopping.");
        } else {
            ArrayList<Product> categoryProducts = ProductView.getProperProducts(category, products);
            ProductView.printProducts(categoryProducts);

            System.out.println("1-Choose a product\n2-Category Menu");
            int item = GetUserInputs.getInBoundDigitalInput(2);
            if (item == 1) {
                System.out.println("Enter a product Id:");
                int id = ProductView.getProperProductID(categoryProducts);
                int stock = products.get(id - 1).getStock();
                if (stock != 0) {
                    System.out.println("How much do you wanna by?(must be in stock's bound)");
                    int count = GetUserInputs.getInBoundDigitalInput(stock);

                    List<CartItem> cartItems = cart.getCartItems();
                    boolean isInList = cartItems.stream().anyMatch(cartItem -> cartItem.getProduct().getId() == id);
                    if (isInList) {
                        updateAnCartItem(id, count, cartItems);
                    } else {
                        addNewCartItem(cart, products, id, count, cartItems);
                    }
                    products.get(id - 1).setStock(stock - count);
                    productDao.updateProduct(products.get(id - 1));
                    System.out.println("✔ This product added to your cart\n");
                } else
                    System.out.println("❌ Sorry... we're ran out of this product\n");
            }
        }
    }

    private void addNewCartItem(Cart cart, ArrayList<Product> products, int id, int count, List<CartItem> cartItems) {
        CartItem cartItem = new CartItem();
        cartItem.setCount(count);
        cartItem.setCart(cart);
        cartItem.setProduct(products.get(id - 1));
        cartItems.add(cartItem);
    }

    private void updateAnCartItem(int id, int count, List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getId() == id) {
                int index = cartItems.indexOf(cartItem);
                count += cartItems.get(index).getCount();
                cartItems.get(index).setCount(count);
                break;
            }
        }
    }
}