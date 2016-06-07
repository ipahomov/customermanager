package com.test.netcracker;


import com.test.netcracker.dao.ICustomerDao;
import com.test.netcracker.dao.ICustomerTypeDao;
import com.test.netcracker.dao.exceptions.DaoException;
import com.test.netcracker.model.Customer;
import com.test.netcracker.model.CustomerType;
import com.test.netcracker.model.TypeCaption;
import org.apache.commons.codec.language.Metaphone;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Test customers actions
 * Created by IPahomov on 21.05.2016.
 */
@ContextConfiguration("/beans-TestDao.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerDaoTest {
    private static final Logger log = Logger.getLogger(CustomerDaoTest.class);

    @Autowired
    private ICustomerDao customerDao;

    @Autowired
    private ICustomerTypeDao customerTypeDao;

    private Customer customer;
    private CustomerType customerType;
    private Metaphone metaphone;

    @Before
    public void setCustomer() throws DaoException {
        customer = new Customer();
        customer.setTitle("Mr");
        customer.setFirstName("Firstnametestdao");
        customer.setLastName("Lastnametestdao");
        customer.setModifiedWhen(new Date());

        metaphone = new Metaphone();
        metaphone.setMaxCodeLen(20);
        customer.setFirstNameMetaphone(metaphone.encode("Firstnametestdao"));
        customer.setLastNameMetaphone(metaphone.encode("LastnametestDao"));

        customerType = new CustomerType();
        customerType.setCustomerTypeCaption(TypeCaption.ENTERPRISE);
        Set<Customer> customerSet = new HashSet<Customer>();
        customerSet.add(customer);
        customerType.setCustomers(customerSet);
        customerTypeDao.create(customerType);
        log.info(customerType);

        customer.setCustomerType(customerType);

    }

    @Test
    public void testFindLastTenCustomers() throws Exception {
        customerDao.create(customer);
        List<Customer> customerList = customerDao.findLastCustomers(0, 10);
        assertNotNull(customerList);
        log.info(customerList);
    }

    @Test
    public void testCreate() throws Exception {
        Long id = customerDao.create(customer);
        log.info(customer);

        assertNotNull(customerDao.read(id));
    }

    @Test
    public void testUpdate() throws Exception {
        Long id = customerDao.create(customer);
        Customer customerForUpdate = customerDao.read(id);
        log.info("Original customer: " + customerForUpdate);

        customerForUpdate.setTitle("Ms");
        customerType.setCustomerTypeCaption(TypeCaption.SMALL);
        customerForUpdate.setCustomerType(customerType);
        customerDao.update(customerForUpdate);

        Customer customerUpdated = customerDao.read(id);
        log.info("Updated customer: " + customerForUpdate);
        assertNotNull(customerUpdated);
        assertEquals("Ms", customerUpdated.getTitle());
        assertEquals("Small/Medium Business", customerUpdated.getCustomerType().getCustomerTypeCaption().getType());

    }

    @Test
    public void testRead() throws Exception {
        Long id = customerDao.create(customer);
        log.info(id);
        assertNotNull(customerDao.read(id));
    }

    @Test
    public void testDelete() throws Exception {
        Long id = customerDao.create(customer);
        Customer customerForDelete = customerDao.read(id);
        customerDao.delete(customerForDelete);

        assertNull(customerDao.read(id));
    }
}