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
     * @param customer to add
     * @return id of added customer
     */
    Long addCustomer(Customer customer);

    /**
     * Update customer
     *
     * @param customer to update
     */
    void updateCustomer(Customer customer);

    /**
     * Delete Customer
     *
     * @param customer to delete
     */
    void deleteCustomer(Customer customer);

    /**
     * Get customer by id
     *
     * @param id of customer
     * @return customer
     */
    Customer getCustomer(Long id);

    /**
     * Find customers by first name and last name
     *
     * @param firstName customer
     * @param lastName  customer
     * @return list of matching customers
     */
    List<Customer> findCustomers(String firstName, String lastName);

    /**
     * Find all customers
     *
     * @return list of customers
     */
    List<Customer> findAllCustomers();


}
