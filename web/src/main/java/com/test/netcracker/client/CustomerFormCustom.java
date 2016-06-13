package com.test.netcracker.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.test.netcracker.model.Customer;
import com.test.netcracker.model.CustomerType;
import com.test.netcracker.model.TypeCaption;
import com.test.netcracker.shared.ICustomerService;
import com.test.netcracker.shared.ICustomerServiceAsync;
import com.test.netcracker.shared.ICustomerTypeService;
import com.test.netcracker.shared.ICustomerTypeServiceAsync;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Customer form to add new customers or update and delete old.
 * To update/delete customer required search needed customer.
 * Created by IPahomov on 13.06.2016.
 */
public class CustomerFormCustom {
    private static final Logger log = Logger.getLogger("CustomerFormCustom");
    private static final ICustomerServiceAsync customerService = GWT.create(ICustomerService.class);
    private static final ICustomerTypeServiceAsync customerTypeService = GWT.create(ICustomerTypeService.class);
    private static final String[] TITLES = {"Mr", "Ms", "Mrs", "Dr"};
    private static final ListBox lbTitle = new ListBox();
    private static final TextBox tbFirstName = new TextBox();
    private static final TextBox tbLastName = new TextBox();
    private static final ListBox lbTypes = new ListBox();
    private Customer customer;

    public CustomerFormCustom() {

        // fill title list box
        for (int i = 0; i < TITLES.length; i++) {
            lbTitle.addItem(TITLES[i]);
        }
        RootPanel.get("lbTitle").add(lbTitle);
        RootPanel.get("tbFirstName").add(tbFirstName);
        RootPanel.get("tbLastName").add(tbLastName);

        // check if is it present customer types
        checkTypes();

        // fill customer types list box
        RootPanel.get("lbTypes").add(lbTypes);
        for (TypeCaption type : TypeCaption.values()) {
            lbTypes.addItem(type.getType());
        }

        // add button
        final Button addButton = new Button("Save");
        RootPanel.get("btnSave").add(addButton);
        addButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                final Customer customer = new Customer();
                validateData(customer);

                customerService.addCustomer(customer, new AsyncCallback<Long>() {
                    public void onFailure(Throwable throwable) {
                        CustomerManager.console.setText("Can not add customer");
                        log.log(Level.SEVERE, "Can not add customer " + throwable.getMessage());
                    }

                    public void onSuccess(Long aLong) {

                        /*customerService.getIPAddress(new AsyncCallback<String>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                CustomerManager.console.setText("Can not get client ip address");
                            }

                            @Override
                            public void onSuccess(String s) {
                                CustomerManager.console.setText("Added new customer by " + s);
                                log.info("Added new customer " + customer + " by " + s);
                            }
                        });*/

                        tbFirstName.setText("");
                        tbLastName.setText("");
                        CustomerSearch.refreshTable();
                    }
                });
            }
        });

        // Update button
        final Button updButton = new Button("Update");
        RootPanel.get("btnUpdate").add(updButton);
        updButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                validateData(customer);

                customerService.updateCustomer(customer, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        CustomerManager.console.setText("Can not add customer");
                        log.log(Level.SEVERE, "Can not update customer " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(Void aVoid) {
                        CustomerManager.console.setText("Customer updated successfully");
                        tbFirstName.setText("");
                        tbLastName.setText("");
                        CustomerSearch.refreshTable();
                    }
                });
            }
        });

        // Delete button
        final Button delButton = new Button("Delete");
        RootPanel.get("btnDelete").add(delButton);
        delButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                customerService.deleteCustomer(customer, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        CustomerManager.console.setText("Error delete customer " + customer.getFirstName());

                    }

                    @Override
                    public void onSuccess(Void aVoid) {
                        CustomerManager.console.setText("Customer deleted successfully");
                        tbFirstName.setText("");
                        tbLastName.setText("");
                        CustomerSearch.refreshTable();
                    }
                });
            }
        });

    }

    private void validateData(final Customer customer) {
        String title = lbTitle.getValue(lbTitle.getSelectedIndex());
        String firstName = tbFirstName.getText();
        String lastName = tbLastName.getText();
        final String type = lbTypes.getValue(lbTypes.getSelectedIndex());
        log.info("Fields: " + title + " " + firstName + " " + lastName + " " + type);

        boolean run = false;
        if (title != null && !title.isEmpty()) run = true;
        if (firstName != null && !firstName.isEmpty()) run = true;
        if (lastName != null && !lastName.isEmpty()) run = true;
        if (type != null && !type.isEmpty()) run = true;

        log.info("Can run:" + String.valueOf(run));

        if (run) {
            customer.setTitle(title);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customerTypeService.getCustomerTypeByCaption(type, new AsyncCallback<CustomerType>() {
                public void onFailure(Throwable throwable) {
                    CustomerManager.console.setText("Error get customer type by caption " + type);
                    log.log(Level.SEVERE, "Error get customer type by caption " + throwable.getMessage());
                }

                public void onSuccess(CustomerType customerType) {
                    customer.setCustomerType(customerType);
                }
            });
            log.info("Customer to submit: " + customer.toString());

        }
    }

    // set customer from search
    public void setCustomer(Customer customer) {
        this.customer = customer;
        String title = customer.getTitle();
        for (int i = 0; i < TITLES.length; i++) {
            if (title.equals(TITLES[i])) {
                lbTitle.setSelectedIndex(i);
                break;
            }
        }
        tbFirstName.setText(customer.getFirstName());
        tbLastName.setText(customer.getLastName());
    }

    // check customer types
    private void checkTypes() {
        customerTypeService.checkTypes(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                CustomerManager.console.setText("Error loaded customer types");
                log.log(Level.SEVERE, throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
                CustomerManager.console.setText("Ready");
            }
        });
    }

}
