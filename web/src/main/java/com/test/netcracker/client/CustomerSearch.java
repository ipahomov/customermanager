package com.test.netcracker.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.view.client.*;
import com.test.netcracker.model.Customer;
import com.test.netcracker.shared.ICustomerService;
import com.test.netcracker.shared.ICustomerServiceAsync;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class search customers and fill cell table.
 * Send selected customer to customer form.
 * Created by IPahomov on 11.06.2016.
 */
public class CustomerSearch {
    private static final Logger log = Logger.getLogger("CustomerSearch");
    private final static ICustomerServiceAsync customerService = GWT.create(ICustomerService.class);
    private static CustomerFormCustom customerForm = new CustomerFormCustom();
    private final static int MAX_RESULTS = 10; // per page

    public static void init() {

        // fill multiword suggest oracle
        final MultiWordSuggestOracle fNames = new MultiWordSuggestOracle();
        final MultiWordSuggestOracle lNames = new MultiWordSuggestOracle();

        customerService.findAllCustomers(new AsyncCallback<List<Customer>>() {
            @Override
            public void onFailure(Throwable throwable) {
                CustomerManager.console.setText("Cannot get names as suggestions");
                log.log(Level.SEVERE, throwable.getMessage());
            }

            @Override
            public void onSuccess(List<Customer> customers) {
                for (Customer customer : customers) {
                    fNames.add(customer.getFirstName());
                    lNames.add(customer.getLastName());
                }
            }
        });

        // add oracles to suggest boxes
        //first names
        final SuggestBox tbSearchFirstName = new SuggestBox(fNames);
        RootPanel.get("tbsFirstName").add(tbSearchFirstName);
        tbSearchFirstName.setFocus(true);

        // last names
        final SuggestBox tbSearchLastName = new SuggestBox(lNames);
        RootPanel.get("tbsLastName").add(tbSearchLastName);

        // Search button
        Button bSearch = new Button("Search");
        bSearch.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String firstName = tbSearchFirstName.getValue();
                String lastName = tbSearchLastName.getValue();

                if ((firstName != null) && (lastName != null) && (!firstName.isEmpty()) && (!lastName.isEmpty())) {
                    customerService.findCustomers(firstName, lastName, new AsyncCallback<List<Customer>>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Error get customers");
                            log.log(Level.SEVERE, "Error get customers: " + throwable.getMessage());
                        }

                        @Override
                        public void onSuccess(List<Customer> customerList) {
                            fillCellTable(customerList);
                        }
                    });
                }

            }
        });
        RootPanel.get("bsSearch").add(bSearch);

        // Clear button
        Button bClear = new Button("Clear");
        bClear.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                tbSearchFirstName.setText("");
                tbSearchLastName.setText("");
                refreshTable();
            }
        });
        RootPanel.get("bsClear").add(bClear);


    }

    // refresh table
    public static void refreshTable() {
        customerService.findLastCustomers(0, MAX_RESULTS, new AsyncCallback<List<Customer>>() {
            @Override
            public void onFailure(Throwable throwable) {
                CustomerManager.console.setText("Error find last customers");
                log.log(Level.SEVERE, "Error find last customers" + throwable.getMessage());
            }

            @Override
            public void onSuccess(List<Customer> customerList) {
                fillCellTable(customerList);
            }
        });
    }

    // key provider to trace customer by id
    private static final ProvidesKey<Customer> KEY_PROVIDER =
            new ProvidesKey<Customer>() {
                @Override
                public Object getKey(Customer customer) {
                    return customer.getCustomerId();
                }
            };

    // cell table
    final static CustomersCellTable customersCellTable = new CustomersCellTable(KEY_PROVIDER);

    private static void fillCellTable(List<Customer> customerList) {
        customersCellTable.setCustomers(customerList);

        // Add a selection model to handle user selection.
        final SingleSelectionModel<Customer> selectionModel
                = new SingleSelectionModel<Customer>(KEY_PROVIDER);
        customersCellTable.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(
                new SelectionChangeEvent.Handler() {
                    public void onSelectionChange(SelectionChangeEvent event) {
                        Customer selected = selectionModel.getSelectedObject();
                        if (selected != null) {
                            CustomerManager.console.setText("You selected " + selected.getFirstName() + " " + selected.getLastName());
                            customerForm.setCustomer(selected);
                        }
                    }
                });
        RootPanel.get("cellTable").add(customersCellTable);
    }


}
