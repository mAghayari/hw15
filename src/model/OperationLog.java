package model;

import java.util.ArrayList;

public class OperationLog {
    private ArrayList<OrderDto> operations;
    private Customer customer;

    public ArrayList<OrderDto> getOperation() {
        return operations;
    }

    public void setOperation(ArrayList<OrderDto> operations) {
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
        for (OrderDto orderDto : operations) {
            result += orderDto.toString();
        }
        return result;
    }
}