package com.test.netcracker.shared;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.test.netcracker.model.CustomerType;

import java.util.List;


/**
 * Created by IPahomov on 03.06.2016.
 */
@RemoteServiceRelativePath("services/customerTypeService")
public interface ICustomerTypeService extends RemoteService {
    /**
     * Get all customers type
     *
     * @return
     */
    List<CustomerType> getAllCustomerTypes();

    /**
     * Get customer type by caption
     *
     * @param caption
     * @return
     */
    CustomerType getCustomerTypeByCaption(String caption);

    /**
     * Get customer type
     *
     * @param id
     * @return
     */
    CustomerType getCustomerType(Long id);
}
