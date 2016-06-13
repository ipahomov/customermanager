package com.test.netcracker.dao.impl;


import com.test.netcracker.dao.ICustomerTypeDao;
import com.test.netcracker.dao.exceptions.DaoException;
import com.test.netcracker.model.CustomerType;
import com.test.netcracker.model.TypeCaption;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class implements ICustomerTypeDao interface
 * Created by IPahomov on 22.05.2016.
 */
@Repository
public class CustomerTypeDao extends BaseDao<CustomerType, Long> implements ICustomerTypeDao {

    public List<CustomerType> getAllCustomerTypes() throws DaoException {
        Criteria criteria = getSession().createCriteria(CustomerType.class);
        return (List<CustomerType>) criteria.list();
    }

    public CustomerType getCustomerTypeByCaption(String caption) throws DaoException {
        Criteria criteria = getSession().createCriteria(CustomerType.class);
        criteria.add(Restrictions.eq("customerTypeCaption", TypeCaption.findType(caption)));

        return (CustomerType) criteria.uniqueResult();
    }

}
