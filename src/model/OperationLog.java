package model;

import model.Customer;
import model.CartDto;

import java.util.ArrayList;

public class OperationLog {
    private ArrayList<CartDto> operations;
    private Customer customer;

    public ArrayList<CartDto> getOperation() {
        return operations;
    }

    public void setOperation(ArrayList<CartDto> operations) {
        this.operations = operations;
    }

    public Customer getCustomerDto() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        String result = "customer with userName " + customer.getUserName() + "\n";
        for (CartDto cartDto : operations) {
            result += cartDto.toString();
        }
        return result;
    }
}