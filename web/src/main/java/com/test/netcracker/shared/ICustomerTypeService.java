package com.test.netcracker.shared;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.test.netcracker.model.CustomerType;

import java.util.List;


/**
 * Interface for customer types service
 * Created by IPahomov on 03.06.2016.
 */
@RemoteServiceRelativePath("services/customerTypeService")
public interface ICustomerTypeService extends RemoteService {
    /**
     * Get all customers type
     *
     * @return list of customer types
     */
    List<CustomerType> getAllCustomerTypes();

    /**
     * Get customer type by caption
     *
     * @param caption String
     * @return customer type
     */
    CustomerType getCustomerTypeByCaption(String caption);

    /**
     * Get customer type by id
     *
     * @param id of customer type
     * @return Long id of customer type
     */
    CustomerType getCustomerType(Long id);

    /**
     * Add new customer type
     * @param customerType to add
     * @return Long id of added type
     */
    Long addCustomerType(CustomerType customerType);

    /**
     * Check if it present customer types by default.
     * If not - add three types:
     * - Small/Medium Business
     * - Residental
     * - Enterprise
     */
    void checkTypes();
}
