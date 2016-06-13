package com.test.netcracker;

import com.test.netcracker.dao.ICustomerTypeDao;
import com.test.netcracker.model.CustomerType;
import com.test.netcracker.model.TypeCaption;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test customers types actions
 * Created by IPahomov on 03.06.2016.
 */
@ContextConfiguration("/beans-dao.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerTypeDaoTest {
    private static final Logger log = Logger.getLogger(CustomerTypeDaoTest.class);
    private CustomerType customerType;

    @Autowired
    private ICustomerTypeDao customerTypeDao;

    @Before
    public void setCustomerType() {
        customerType = new CustomerType();
        customerType.setCustomerTypeCaption(TypeCaption.RESIDENTAL);
    }

    @Test
    public void checkType(){
        String type = "Small/Medium Business";
        TypeCaption tc = TypeCaption.findType(type);
        log.info(tc);
    }


    @Test
    public void testGetAllCustomerTypes() throws Exception {
        //customerTypeDao.create(customerType);
        //customerTypeDao.create(new CustomerType());
        //customerTypeDao.create(new CustomerType());

        List<CustomerType> customerTypes = customerTypeDao.getAllCustomerTypes();
        log.info("All customer types: " + customerTypes);
        assertEquals("Size", 3, customerTypes.size());

        for (CustomerType type : customerTypes){
            System.out.println(type.getCustomerTypeCaption().getType());
        }
    }

    @Test
    public void testCreate() throws Exception {
        Long id = customerTypeDao.create(customerType);
        log.info(customerType);
        assertNotNull(customerTypeDao.read(id));
    }

    @Test
    public void testUpdate() throws Exception {
        Long id = customerTypeDao.create(customerType);
        log.info("Original customer type: " + customerType);

        CustomerType customerTypeForUpdate = customerTypeDao.read(id);
        customerTypeForUpdate.setCustomerTypeCaption(TypeCaption.ENTERPRISE);
        customerTypeDao.update(customerTypeForUpdate);

        CustomerType customerTypeUpdated = customerTypeDao.read(id);
        log.info("Updated customer type: " + customerTypeUpdated);
        assertNotNull(customerTypeUpdated);
        assertEquals("Enterprise", customerTypeUpdated.getCustomerTypeCaption().getType());
    }

    @Test
    public void testRead() throws Exception {
        Long id = customerTypeDao.create(customerType);
        assertNotNull(customerTypeDao.read(id));
    }

    @Test
    public void testDelete() throws Exception {
        Long id = customerTypeDao.create(customerType);
        CustomerType customerTypeForDelete = customerTypeDao.read(id);
        customerTypeDao.delete(customerTypeForDelete);

        assertNull(customerTypeDao.read(id));
    }

    @Test
    public void testGetCustomerTypeByCaption() throws Exception {
        //customerTypeDao.create(customerType);
        //System.out.println(customerType.getCustomerTypeCaption());
        //System.out.println(customerType.getCustomerTypeCaption().getType());
        CustomerType type = customerTypeDao.getCustomerTypeByCaption("Small/Medium Business");
        log.info(type);
    }
}