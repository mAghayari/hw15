package view;

import dao.ProductDao;
import model.Admin;
import model.Customer;
import model.Product;
import model.Order;
import services.OrderServices;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        OrderItemView orderItemView = new OrderItemView();
        OrderServices orderServices = new OrderServices();
        CustomerView customerView = new CustomerView();
        AdminView adminView = new AdminView();

        while (true) {
            Customer customer;
            Order order = new Order();
            Admin admin = new Admin();
            System.out.println("1)SignIn\n2)SignUp");
            int enterItem = GetUserInputs.getInBoundDigitalInput(2);
            if (enterItem == 1) {
                System.out.println("1-Admin SignIn \n2-User SignIn");
                int signInItem = GetUserInputs.getInBoundDigitalInput(2);
                if (signInItem == 1) {
                    admin = adminView.adminSignIn();
                } else {
                    customer = customerView.customerSignIn();
                    order.setCustomer(customer);
                }
            } else {
                System.out.println("1-Admin SignUp\n2-User SignUp");
                int signUpItem = GetUserInputs.getInBoundDigitalInput(2);
                if (signUpItem == 1) {
                    admin = adminView.adminSignUp();
                } else {
                    customer = customerView.customerSignUp();
                    order.setCustomer(customer);
                }
            }
            if (!Objects.equals(order.getCustomer(), null)) {
                ProductDao productDao = new ProductDao();
                ArrayList<Product> products;
                mainMenu:
                while (true) {
                    System.out.println("Main Menu:");
                    System.out.println("1-Products category's List\n2-Delete Order Items\n3-Print Order\n4-Finalize Order\n5-SignOut\n6-Exit\nChoose an item:");
                    int mainMenuItem = GetUserInputs.getInBoundDigitalInput(6);
                    subMenu:
                    while (true) {
                        products = productDao.fetchAllProducts();
                        switch (mainMenuItem) {
                            case 1:
                                System.out.println("1-Electronics\n2-Readings\n3-Shoes\n4-Main Menu");
                                int categoryItem = GetUserInputs.getInBoundDigitalInput(4);
                                switch (categoryItem) {
                                    case 1:
                                        orderItemView.addProductToOrder(order, productDao, products, "ELECTRONICS");
                                        break;
                                    case 2:
                                        orderItemView.addProductToOrder(order, productDao, products, "READINGS");
                                        break;
                                    case 3:
                                        orderItemView.addProductToOrder(order, productDao, products, "SHOES");
                                        break;
                                    case 4:
                                        break subMenu;
                                }
                                break;
                            case 2:
                                if (order.getOrderItems().isEmpty())
                                    System.out.println("your Order is empty\n");
                                else {
                                    OrderView orderView = new OrderView();
                                    order = orderView.deleteOperation(order, products);
                                }
                                break subMenu;
                            case 3:
                                OrderView.printOrder(orderServices, order);
                                break subMenu;
                            case 4:
                                if (order.getOrderItems().isEmpty())
                                    System.out.println("your Order is empty\n");
                                else {
                                    OrderView orderView = new OrderView();
                                    orderView.finalizeOrder(order);
                                }
                                break subMenu;
                            case 5:
                                break mainMenu;
                            case 6:
                                System.exit(0);
                        }
                    }
                }
            } else if (!Objects.equals(admin.getAdminName(), null))
                adminView.adminMenu();
        }
    }
}