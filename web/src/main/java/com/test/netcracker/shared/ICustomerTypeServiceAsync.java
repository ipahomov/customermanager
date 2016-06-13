package com.test.netcracker.shared;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.test.netcracker.model.CustomerType;

import java.util.List;

public interface ICustomerTypeServiceAsync {
    void getAllCustomerTypes(AsyncCallback<List<CustomerType>> async);

    void getCustomerTypeByCaption(String caption, AsyncCallback<CustomerType> async);

    void getCustomerType(Long id, AsyncCallback<CustomerType> async);

    void addCustomerType(CustomerType customerType, AsyncCallback<Long> async);

    void checkTypes(AsyncCallback<Void> async);
}
