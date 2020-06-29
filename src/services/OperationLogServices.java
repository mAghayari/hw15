package services;

import model.Customer;
import model.OperationLog;
import util.Utility;

import java.util.Date;

public class OperationLogServices {

    public OperationLog getOperationLog(Customer desiredCustomer) {
        OperationLog operationLog = new OperationLog();
        OrderServices orderServices = new OrderServices();

        Date startDate = Utility.minusAMonth();
        Date endDate = Utility.addADay(new Date());

        operationLog.setCustomer(desiredCustomer);
        operationLog.setOperation(orderServices.getOrdersOfAPeriod(desiredCustomer.getId(), startDate, endDate));

        return operationLog;
    }
}