package services;

import model.customer.Customer;
import model.log.OperationLog;
import util.Utility;

import java.util.Date;

public class OperationLogServices {

    public OperationLog getOperationLog(Customer desiredCustomer) {
        OperationLog operationLog = new OperationLog();
        CartServices cartServices = new CartServices();

        Date startDate = Utility.minusAMonth();
        Date endDate = Utility.addADay(new Date());

        operationLog.setCustomer(desiredCustomer);
        operationLog.setOperation(cartServices.getCartsOfAPeriod(desiredCustomer.getId(), startDate, endDate));

        return operationLog;
    }
}