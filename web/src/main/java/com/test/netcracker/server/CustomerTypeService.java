package com.test.netcracker.server;

import com.test.netcracker.dao.ICustomerTypeDao;
import com.test.netcracker.dao.exceptions.DaoException;
import com.test.netcracker.model.CustomerType;
import com.test.netcracker.shared.ICustomerTypeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Class implementing ICustomerTypeService interface
 * Realizes methods with customer types operations
 * Created by IPahomov on 03.06.2016.
 */
@Service("customerTypeService")
@Transactional
public class CustomerTypeService implements ICustomerTypeService {
    private static final Logger log = Logger.getLogger(CustomerTypeService.class);

    @Autowired
    private ICustomerTypeDao customerTypeDao;


    public List<CustomerType> getAllCustomerTypes() {
        List<CustomerType> customerTypes = Collections.EMPTY_LIST;
        try {
            customerTypes = customerTypeDao.getAllCustomerTypes();
        } catch (DaoException e) {
            log.error("Error get all types: " + e);
        }
        return customerTypes;
    }

    public CustomerType getCustomerTypeByCaption(String caption) {
        CustomerType type = null;
        if (caption != null && !caption.isEmpty()) {
            try {
                type = customerTypeDao.getCustomerTypeByCaption(caption);
            } catch (DaoException e) {
                log.error("Error get type: " + e);
            }
        }

        return type;
    }

    public CustomerType getCustomerType(Long id) {
        CustomerType type = null;
        try {
            type = customerTypeDao.read(id);
        } catch (DaoException e) {
            log.error("Error get type: " + e);
        }
        return type;
    }

}
