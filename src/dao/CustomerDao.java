package dao;

import model.Address;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    private int customerCounter = 0;

    public void addCustomer(Customer customer) {
        try (Connection connection = Connector.getConnection()) {
            String query = "INSERT INTO `customer`(`firstName`, `lastName`, `mobileNumber`, `email`, `address`, `userName`, " +
                    "`password`,`age`) " + "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getMobileNumber());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getAddress().toString());
            preparedStatement.setString(6, customer.getUserName());
            preparedStatement.setString(7, customer.getPassword());
            preparedStatement.setInt(8, customer.getAge());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            customerCounter++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> fetchAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = Connector.getConnection()) {
            String query = "SELECT* FROM `customer`";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                customers.add(setCustomer(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerCounter = customers.size();
        return customers;
    }

    private Customer setCustomer(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getInt("id"));
        customer.setFirstName(resultSet.getString("firstName"));
        customer.setLastName(resultSet.getString("lastName"));
        customer.setUserName(resultSet.getString("userName"));
        customer.setMobileNumber(resultSet.getString("mobileNumber"));
        customer.setEmail(resultSet.getString("email"));
        customer.setAge(resultSet.getInt("age"));
        customer.setAddress(buildCustomerAddress(resultSet));
        return customer;
    }

    private Address buildCustomerAddress(ResultSet resultSet) throws SQLException {
        Address customerAddress = new Address();

        customerAddress.setProvince(resultSet.getString("province"));
        customerAddress.setCity(resultSet.getString("city"));
        customerAddress.setStreet(resultSet.getString("street"));
        customerAddress.setZipCode(resultSet.getString("zipCode"));
        return customerAddress;
    }

    public Customer findCustomerByUserName(String userName) {
        Connection connection = Connector.getConnection();
        Customer customer = null;
        try {
            String query = "SELECT* FROM customer WHERE  userName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = setCustomer(resultSet);
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public Customer customerSignInChecking(String userName, String password) {
        Connection connection = Connector.getConnection();
        Customer customer = null;
        try {
            String query = "SELECT customer.id, customer.address FROM customer WHERE  userName = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = setCustomer(resultSet);
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public int getCustomerCounter() {
        return customerCounter;
    }
}