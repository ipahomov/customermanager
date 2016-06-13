package com.test.netcracker.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.test.netcracker.model.Customer;

import java.util.List;

public interface ICustomerServiceAsync {
    /**
     * Get list of last customers in data base limited by first result and max results.
     *
     * @param firstResult start position
     * @param maxResult   max per page
     */
    void findLastCustomers(int firstResult, int maxResult, AsyncCallback<List<Customer>> async);

    void addCustomer(Customer customer, AsyncCallback<Long> async);

    void updateCustomer(Customer customer, AsyncCallback<Void> async);

    void deleteCustomer(Customer customer, AsyncCallback<Void> async);

    void getCustomer(Long id, AsyncCallback<Customer> async);

    void findCustomers(String firstName, String lastName, AsyncCallback<List<Customer>> async);

    void findAllCustomers(AsyncCallback<List<Customer>> async);

    void getIPAddress(AsyncCallback<String> async);
}
