package com.test.netcracker.dao.impl;


import com.test.netcracker.dao.ICustomerDao;
import com.test.netcracker.dao.exceptions.DaoException;
import com.test.netcracker.model.Customer;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class inmplements ICUstomerDao interface
 * Created by IPahomov on 20.05.2016.
 */
@Repository
public class CustomerDao extends BaseDao<Customer, Long> implements ICustomerDao {

    public List<Customer> findLastCustomers(int firstResult, int maxResult) throws DaoException {
        Criteria criteria = getSession().createCriteria(Customer.class);
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResult);
        criteria.addOrder(Order.desc("modifiedWhen"));

        return (List<Customer>) criteria.list();
    }

    public List<Customer> findCustomers(String firstName, String lastName) throws DaoException {
        Criteria criteria = getSession().createCriteria(Customer.class);
        criteria.add(Restrictions.ilike("firstNameMetaphone", firstName, MatchMode.ANYWHERE))
                .add(Restrictions.ilike("lastNameMetaphone", lastName, MatchMode.ANYWHERE))
                .addOrder(Order.desc("modifiedWhen"));

        return (List<Customer>) criteria.list();
    }

}
