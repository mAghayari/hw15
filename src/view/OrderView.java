package view;

import model.Order;
import model.OrderItem;
import model.Product;
import services.OrderServices;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderView {
    public static void printOrder(OrderServices orderServices, Order order) {
        OrderItemView orderItemView = new OrderItemView();
        if (order.getOrderItems().isEmpty())
            System.out.println("❌ Your order is empty\n");
        else {
            List<OrderItem> orderItems = order.getOrderItems();
            Comparator<OrderItem> comparator = Comparator.comparing(OrderItem::getProduct);
            orderItems.sort(comparator);
            orderItemView.printOrderItems(order.getOrderItems());
            System.out.println("Total Cost: " + orderServices.getTotalCost(order) + "\n");
        }
    }

    public Order deleteOperation(Order order, ArrayList<Product> products) {
        OrderServices orderServices = new OrderServices();
        OrderItemView orderItemView = new OrderItemView();

        System.out.println("1-Delete An Item\n2-Empty Order\n3-Main Menu");
        int editItem = GetUserInputs.getInBoundDigitalInput(3);
        if (editItem == 1) {
            orderItemView.deleteAnOrderItem(order, products);
        } else if (editItem == 2) {
            order = orderServices.cancelOrder(order);
            order.setOrderItems(new ArrayList<>());
        }
        return order;
    }

    public void finalizeOrder(Order order) {
        GetUserInputs getUserInputs = new GetUserInputs();
        OrderServices orderServices = new OrderServices();
        if (order.getOrderItems().isEmpty())
            System.out.println("❌ Your order is empty\n");
        else {
            order.setDate();
            System.out.println("Where do you wanna receive your purchase:\n" +
                    "1-My Account Address\n2-Another Address");
            int addressItem = GetUserInputs.getInBoundDigitalInput(2);
            if (addressItem == 1)
                order.setAddress(order.getCustomer().getAddress());
            else
                order.setAddress(getUserInputs.getAddress());
            orderServices.addingOrder(order);
            order.setOrderItems(new ArrayList<>());
            System.out.println("✔ Thank you for your shopping");
        }
    }
}