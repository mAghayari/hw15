package model;


import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private Address address;
    private String userName;
    private String password;
    private int age;
    List<Order> orders = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer " + id +
                "\nfirstName: " + firstName +
                "\nlastName: " + lastName +
                "\nmobileNumber: " + mobileNumber +
                "\nemail: " + email +
                "\naddress: " + address +
                "\nage: " + age +
                "\nOrders: " + orders.toString() +
                "\n";
    }

    public String getCustomerStringForReport() {
        return "Customer " + id +
                "\nfirstName: " + firstName +
                "\nlastName: " + lastName +
                "\nmobileNumber: " + mobileNumber +
                "\nemail: " + email +
                "\naddress: " + address +
                "\nage: " + age +
                "\n--------------------------------\n";
    }
}