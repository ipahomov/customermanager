package com.test.netcracker.shared;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.test.netcracker.model.Customer;

import java.util.List;

/**
 * Interface for customer service.
 * Created by IPahomov on 22.05.2016.
 */
@RemoteServiceRelativePath("services/customerService")
public interface ICustomerService extends RemoteService {

    /**
     * Get list of last customers in data base limited by first result and max results.
     *
     * @param firstResult start position
     * @param maxResult   max per page
     * @return list of customers
     */
    List<Customer> findLastCustomers(int firstResult, int maxResult);

    /**
     * Add Customer
     *
     * @param customer
     * @return
     */
    Long addCustomer(Customer customer);

    /**
     * Update customer
     *
     * @param customer
     */
    void updateCustomer(Customer customer);

    /**
     * Delete Customer
     *
     * @param customer
     */
    void deleteCustomer(Customer customer);

    /**
     * Get Customer
     *
     * @param id
     * @return
     */
    Customer getCustomer(Long id);

    /**
     * Find customers by first name and last name
     *
     * @param firstName
     * @param lastName
     * @return
     */
    List<Customer> findCustomers(String firstName, String lastName);
}
