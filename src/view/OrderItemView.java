package view;

import dao.ProductDao;
import model.Order;
import model.OrderItem;
import model.Product;
import services.ProductServices;

import java.util.ArrayList;
import java.util.List;

public class OrderItemView {

    public void deleteAnOrderItem(Order order, ArrayList<Product> products) {
        ProductServices productServices = new ProductServices();
        List<OrderItem> orderItems = order.getOrderItems();

        printOrderItems(orderItems);

        System.out.println("Enter id of a product to delete it");
        int id = GetUserInputs.getInBoundDigitalInput(products.size());

        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == id) {
                productServices.getASoledItemBack(orderItem);
                orderItems.remove(orderItem);
                order.setOrderItems(orderItems);
                order.setTotalCost(orderItems);

                System.out.println("✔ Item deleted");
                return;
            }
        }
        System.out.println("❌ No such ID was found in your order" +
                "\n");
    }

    public void printOrderItems(List<OrderItem> orderItems) {
        System.out.println("Your ordered:\n");

        for (OrderItem orderItem : orderItems) {
            System.out.println(orderItem.orderItemForSavingOrder());
        }
    }

    public void addProductToOrder(Order order, ProductDao productDao, ArrayList<Product> products, String category) {
        if (order.getOrderItems().size() >= 5) {
            System.out.println("❌ There are 5 products in your order,\n" +
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

                    List<OrderItem> orderItems = order.getOrderItems();
                    boolean isInList = orderItems.stream().anyMatch(orderItem -> orderItem.getProduct().getId() == id);
                    if (isInList) {
                        updateAnOrderItem(id, count, orderItems);
                    } else {
                        addNewOrderItem(order, products, id, count, orderItems);
                    }
                    products.get(id - 1).setStock(stock - count);
                    productDao.updateProduct(products.get(id - 1));
                    System.out.println("✔ This product added to your order\n");
                } else
                    System.out.println("❌ Sorry... we're ran out of this product\n");
            }
        }
    }

    private void addNewOrderItem(Order order, ArrayList<Product> products, int id, int count, List<OrderItem> orderItems) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCount(count);
        orderItem.setOrder(order);
        orderItem.setProduct(products.get(id - 1));
        orderItems.add(orderItem);
    }

    private void updateAnOrderItem(int id, int count, List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == id) {
                int index = orderItems.indexOf(orderItem);
                count += orderItems.get(index).getCount();
                orderItems.get(index).setCount(count);
                break;
            }
        }
    }
}