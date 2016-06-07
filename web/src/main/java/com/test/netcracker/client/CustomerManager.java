package com.test.netcracker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.test.netcracker.model.Customer;
import com.test.netcracker.model.CustomerType;
import com.test.netcracker.model.TypeCaption;
import com.test.netcracker.shared.ICustomerService;
import com.test.netcracker.shared.ICustomerServiceAsync;
import com.test.netcracker.shared.ICustomerTypeService;
import com.test.netcracker.shared.ICustomerTypeServiceAsync;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Start point of web-app. Contains main operations.
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CustomerManager implements EntryPoint {
    private static Logger log = Logger.getLogger("EntryPoint");
    private final static ICustomerServiceAsync customerService = GWT.create(ICustomerService.class);
    private final static ICustomerTypeServiceAsync customerTypeService = GWT.create(ICustomerTypeService.class);
    private final static String[] TITLES = {"Mr", "Ms", "Mrs", "Dr"};
    public static FlexTable flexTable = new FlexTable();


    public void onModuleLoad() {

        initializeSearch();
        initializeCustomer();
        //refreshTable();


    }

    /**
     * SearchBar for search customers and display list of results into a table
     */
    private void initializeSearch() {
        RootPanel.get("lsFirstName").add(new Label(" First Name:"));
        RootPanel.get("lsLastName").add(new Label(" Last Name:"));

        final TextBox tbSearchFirstName = new TextBox();
        RootPanel.get("tbsFirstName").add(tbSearchFirstName);

        final TextBox tbSearchLastName = new TextBox();
        RootPanel.get("tbsLastName").add(tbSearchLastName);

        Button bSearch = new Button("Search");
        bSearch.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String firstName = tbSearchFirstName.getValue();
                String lastName = tbSearchLastName.getValue();

                fillTable(firstName, lastName);
            }
        });

        RootPanel.get("bsSearch").add(bSearch);

        Button bClear = new Button("Clear");
        bClear.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                tbSearchFirstName.setText("");
                tbSearchLastName.setText("");
                flexTable.removeAllRows();
            }
        });

        RootPanel.get("bsClear").add(bClear);
    }

    static void fillTable(String firstName, String lastName) {
        RootPanel.get("sTable").add(flexTable);
        customerService.findCustomers(firstName, lastName, new AsyncCallback<List<Customer>>() {
            public void onFailure(Throwable throwable) {
                Window.alert("NOOOOO");
            }

            public void onSuccess(List<Customer> customerList) {
                flexTable.removeAllRows();

                flexTable.setTitle("Customers list");
                flexTable.setText(0, 0, "Title");
                flexTable.setText(0, 1, "First Name");
                flexTable.setText(0, 2, "Last Name");
                int row = flexTable.getRowCount();

                for (Customer customer : customerList) {
                    flexTable.setText(row, 0, customer.getTitle());
                    flexTable.setText(row, 1, customer.getFirstName());
                    flexTable.setText(row, 2, customer.getLastName());
                }
            }
        });

    }

    /**
     * Form for add/update/delete customers
     */
    void initializeCustomer() {
        log.info("Starting initialize customer");
        final ListBox lbTitle = new ListBox();
        final TextBox tbFirstName = new TextBox();
        final TextBox tbLastName = new TextBox();
        final ListBox lbTypes = new ListBox();

        for (int i = 0; i < TITLES.length; i++) {
            lbTitle.addItem(TITLES[i]);
        }
        RootPanel.get("lbTitle").add(lbTitle);

        RootPanel.get("tbFirstName").add(tbFirstName);
        RootPanel.get("tbLastName").add(tbLastName);


        final Button addButton = new Button("Save");
        RootPanel.get("btnSave").add(addButton);

        RootPanel.get("lbTypes").add(lbTypes);
        for (TypeCaption type : TypeCaption.values()) {
            lbTypes.addItem(type.getType());
        }

        /**
         * Add button
         */
        addButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String title = lbTitle.getValue(lbTitle.getSelectedIndex());
                String firstName = tbFirstName.getText();
                String lastName = tbLastName.getText();
                String type = lbTypes.getValue(lbTypes.getSelectedIndex());

                boolean run = false;
                if (title != null && !title.isEmpty()) run = true;
                if (firstName != null && !firstName.isEmpty()) run = true;
                if (lastName != null && !lastName.isEmpty()) run = true;
                if (type != null && !type.isEmpty()) run = true;

                log.info(String.valueOf(run));
                log.log(Level.INFO, "Fields: " + title + " " + firstName + " " + lastName + " " + type);

                if (run) {
                    final Customer customer = new Customer();
                    customer.setTitle(title);
                    customer.setFirstName(firstName);
                    customer.setLastName(lastName);
                    customerTypeService.getCustomerTypeByCaption(type, new AsyncCallback<CustomerType>() {
                        public void onFailure(Throwable throwable) {
                            Window.alert("Error");
                        }

                        public void onSuccess(CustomerType customerType) {
                            customer.setCustomerType(customerType);
                        }
                    });
                    log.info("Customer to save: " + customer.toString());

                    customerService.addCustomer(customer, new AsyncCallback<Long>() {
                        public void onFailure(Throwable throwable) {
                            Window.alert(throwable.getMessage());
                        }

                        public void onSuccess(Long aLong) {
                            Window.alert("OK");
                            tbFirstName.setText("");
                            tbLastName.setText("");
                        }
                    });
                }
            }
        });


        //updButton.addClickHandler(new ClickHandler() {
        //delButton.addClickHandler(new ClickHandler() {

    }

}

