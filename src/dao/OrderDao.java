package dao;

import model.Order;
import model.OrderDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class OrderDao {

    public void addAnOrder(Order order) {
        try (Connection connection = Connector.getConnection()) {
            String query = "INSERT INTO `order`(`customerId`, `orderItems`, `orderDate`,`province`,`city`, `street`, `zipcode`, `totalCost`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, order.getCustomer().getId());
            preparedStatement.setString(2, order.getOrderItemsString());
            preparedStatement.setTimestamp(3, order.getDate());
            preparedStatement.setString(4, order.getAddress().getProvince());
            preparedStatement.setString(5, order.getAddress().getCity());
            preparedStatement.setString(6, order.getAddress().getStreet());
            preparedStatement.setString(7, order.getAddress().getZipCode());
            preparedStatement.setDouble(8, order.getTotalCost());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<OrderDto> findOrders(int customerId, Date startDate, Date endDate) {
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
        ArrayList<OrderDto> orderDtos = new ArrayList<>();
        try (Connection connection = Connector.getConnection()) {
            String query = "SELECT `order`.orderDate , `order`.orderItems FROM `order` " +
                    "WHERE customerID = ? AND orderDate BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setDate(2, sqlStartDate);
            preparedStatement.setDate(3, sqlEndDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderDtos.add(setOrderDto(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDtos;
    }

    private OrderDto setOrderDto(ResultSet resultSet) {
        OrderDto orderDto = new OrderDto();
        Timestamp timestamp;
        try {
            timestamp = resultSet.getTimestamp("orderDate");
            orderDto.setDate(new Date(timestamp.getTime()));
            orderDto.setOrderItems(resultSet.getString("orderItems"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDto;
    }
}