package com.test.netcracker.dao;


import com.test.netcracker.dao.exceptions.DaoException;
import com.test.netcracker.model.Customer;

import java.util.List;

/**
 * Interface for customer operations in database
 * Created by IPahomov on 20.05.2016.
 */
public interface ICustomerDao extends IBaseDao<Customer, Long> {

    /**
     * Get list of last customers in data base limited by first result and max results.
     *
     * @param firstResult start position
     * @param maxResult   max per page
     * @return list of customers
     * @throws DaoException
     */
    List<Customer> findLastCustomers(int firstResult, int maxResult) throws DaoException;

    /**
     * Find customer by firstName and lastName
     *
     * @param firstName customer
     * @param lastName  customer
     * @return list of matches customers
     * @throws DaoException
     */
    List<Customer> findCustomers(String firstName, String lastName) throws DaoException;
}
