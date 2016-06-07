package com.test.netcracker.server;

import com.test.netcracker.dao.ICustomerDao;
import com.test.netcracker.dao.exceptions.DaoException;
import com.test.netcracker.model.Customer;
import com.test.netcracker.shared.ICustomerService;
import org.apache.commons.codec.language.Metaphone;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Class implementing ICustomerService interface
 * Realizes methods with customers operations
 * Created by IPahomov on 22.05.2016.
 */
@Service("customerService")
@Transactional
public class CustomerService implements ICustomerService {
    private static final Logger log = Logger.getLogger(CustomerService.class);

    @Autowired
    private ICustomerDao customerDao;

    public Long addCustomer(Customer customer) {
        Long id = null;
        customer.setFirstNameMetaphone(metaphoneEncodeName(customer.getFirstName()));
        customer.setLastNameMetaphone(metaphoneEncodeName(customer.getLastName()));
        customer.setModifiedWhen(new Date());
        try {
            id = customerDao.create(customer);
        } catch (DaoException e) {
            log.error("Error create customer: " + e);
        }
        return id;
    }

    public void updateCustomer(Customer customer) {
        customer.setFirstNameMetaphone(metaphoneEncodeName(customer.getFirstName()));
        customer.setLastNameMetaphone(metaphoneEncodeName(customer.getLastName()));
        customer.setModifiedWhen(new Date());
        try {
            customerDao.update(customer);
        } catch (DaoException e) {
            log.error("Error update customer: " + e);
        }
    }

    public Customer getCustomer(Long id) {
        Customer customer = new Customer();
        try {
            customer = customerDao.read(id);
        } catch (DaoException e) {
            log.error("Error get customer: " + e);
        }
        return customer;
    }

    public List<Customer> findCustomers(String firstName, String lastName) {
        List<Customer> customerList = Collections.EMPTY_LIST;
        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            try {
                customerList = customerDao.findCustomers(
                        metaphoneEncodeName(firstName),
                        metaphoneEncodeName(lastName));
            } catch (DaoException e) {
                log.error("Error find customers: " + e);
            }
        }

        return customerList;
    }

    public void deleteCustomer(Customer customer) {
        try {
            customerDao.delete(customer);
        } catch (DaoException e) {
            log.error("Error delete customer: " + e);
        }
    }

    public String metaphoneEncodeName(String name) {
        String indexName = "";
        Metaphone metaphone = new Metaphone();
        metaphone.setMaxCodeLen(20);

        if (name != null && !name.isEmpty()) {
            indexName = metaphone.encode(name);
        }

        return indexName;
    }

    public List<Customer> findLastCustomers(int firstResult, int maxResult) {
        List<Customer> customerList = Collections.EMPTY_LIST;
        try {
            customerList = customerDao.findLastCustomers(firstResult, maxResult);
        } catch (DaoException e) {
            log.error("Error get limeted list of customers: " + e);
        }
        return customerList;
    }
}
