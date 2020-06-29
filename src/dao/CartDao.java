package dao;

import model.cart.Cart;
import model.cart.CartDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class CartDao {

    public void addACart(Cart cart) {
        try (Connection connection = Connector.getConnection()) {
            String query = "INSERT INTO `cart`(`customerId`, `cartItems`, `cartDate`,`province`,`city`, `street`, `zipcode`, `totalCost`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, cart.getCustomer().getId());
            preparedStatement.setString(2, cart.getCartItemsString());
            preparedStatement.setTimestamp(3, cart.getDate());
            preparedStatement.setString(4, cart.getAddress().getProvince());
            preparedStatement.setString(5, cart.getAddress().getCity());
            preparedStatement.setString(6, cart.getAddress().getStreet());
            preparedStatement.setString(7, cart.getAddress().getZipCode());
            preparedStatement.setDouble(8, cart.getTotalCost());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CartDto> findCarts(int customerId, Date startDate, Date endDate) {
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
        ArrayList<CartDto> cartDtos = new ArrayList<>();
        try (Connection connection = Connector.getConnection()) {
            String query = "SELECT `cart`.cartDate , `cart`.cartItems FROM `cart` " +
                    "WHERE customerID = ? AND cartDate BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setDate(2, sqlStartDate);
            preparedStatement.setDate(3, sqlEndDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cartDtos.add(setCartDto(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartDtos;
    }

    private CartDto setCartDto(ResultSet resultSet) {
        CartDto cartDto = new CartDto();
        Timestamp timestamp;
        try {
            timestamp = resultSet.getTimestamp("cartDate");
            cartDto.setDate(new Date(timestamp.getTime()));
            cartDto.setCartItems(resultSet.getString("cartItems"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartDto;
    }
}