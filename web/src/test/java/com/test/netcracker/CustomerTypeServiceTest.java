package com.test.netcracker;

import com.test.netcracker.model.CustomerType;
import com.test.netcracker.model.TypeCaption;
import com.test.netcracker.shared.ICustomerTypeService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for server services of customer type
 * Created by IPahomov on 13.06.2016.
 */
@ContextConfiguration("/beans-TestServices.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerTypeServiceTest {
    private final static Logger log = Logger.getLogger(CustomerTypeServiceTest.class);

    @Autowired
    private ICustomerTypeService customerTypeService;


    @Test
    public void testGetCustomerTypeByCaption() throws Exception {
        List<String> types = new ArrayList<>();
        for(TypeCaption typeCaption : TypeCaption.values()){
            types.add(typeCaption.getType());
        }
        log.info("Types list: " + types);
        CustomerType customerType = customerTypeService.getCustomerTypeByCaption(types.get(2));
        log.info("Customer type " + customerType);


    }
}