package com.test.netcracker.dao;


import com.test.netcracker.dao.exceptions.DaoException;
import com.test.netcracker.model.CustomerType;

import java.util.List;

/**
 * Interface for customer type actions
 * Created by IPahomov on 27.05.2016.
 */
public interface ICustomerTypeDao extends IBaseDao<CustomerType, Long> {

    /**
     * Getting all types
     *
     * @return
     * @throws DaoException
     */
    List<CustomerType> getAllCustomerTypes() throws DaoException;

    /**
     * Get customer type by caption
     *
     * @param caption String
     * @return customer type
     * @throws DaoException
     */
    CustomerType getCustomerTypeByCaption(String caption) throws DaoException;
}
