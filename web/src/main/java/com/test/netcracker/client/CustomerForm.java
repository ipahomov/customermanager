package com.test.netcracker.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
 * Created by IPahomov on 12.06.2016.
 */
public class CustomerForm {
    private static final Logger log = Logger.getLogger("CustomerSearch");
    private final static ICustomerServiceAsync customerService = GWT.create(ICustomerService.class);
    private final static ICustomerTypeServiceAsync customerTypeService = GWT.create(ICustomerTypeService.class);
    private final static String[] TITLES = {"Mr", "Ms", "Mrs", "Dr"};
    final static ListBox lbTitle = new ListBox();
    final static TextBox tbFirstName = new TextBox();
    final static TextBox tbLastName = new TextBox();
    final static ListBox lbTypes = new ListBox();

    public static void init() {
        log.info("Starting initialize customer");


        for (int i = 0; i < TITLES.length; i++) {
            lbTitle.addItem(TITLES[i]);
        }
        RootPanel.get("lbTitle").add(lbTitle);

        RootPanel.get("tbFirstName").add(tbFirstName);
        RootPanel.get("tbLastName").add(tbLastName);


        checkTypes();

        RootPanel.get("lbTypes").add(lbTypes);
        /*customerTypeService.getAllCustomerTypes(new AsyncCallback<List<CustomerType>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error get customer types");
            }

            @Override
            public void onSuccess(List<CustomerType> customerTypes) {
                log.info("CUSTOMER TYPES: " + customerTypes);
                for(CustomerType type : customerTypes){
                    lbTypes.addItem(type.getCustomerTypeCaption().getType());
                }
            }
        });*/
        for (TypeCaption type : TypeCaption.values()) {
            lbTypes.addItem(type.getType());
        }


        final Button addButton = new Button("Save");
        RootPanel.get("btnSave").add(addButton);

        final Button delButton = new Button("Delete");
        RootPanel.get("btnDelete").add(addButton);

        /**
         * Add button
         */
        addButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String title = lbTitle.getValue(lbTitle.getSelectedIndex());
                String firstName = tbFirstName.getText();
                String lastName = tbLastName.getText();
                String type = lbTypes.getValue(lbTypes.getSelectedIndex());
                log.info("Fields: " + title + " " + firstName + " " + lastName + " " + type);

                boolean run = false;
                if (title != null && !title.isEmpty()) run = true;
                if (firstName != null && !firstName.isEmpty()) run = true;
                if (lastName != null && !lastName.isEmpty()) run = true;
                if (type != null && !type.isEmpty()) run = true;

                log.info("Can add:" + String.valueOf(run));

                if (run) {
                    final Customer customer = new Customer();
                    customer.setTitle(title);
                    customer.setFirstName(firstName);
                    customer.setLastName(lastName);
                    customerTypeService.getCustomerTypeByCaption(type, new AsyncCallback<CustomerType>() {
                        public void onFailure(Throwable throwable) {
                            CustomerManager.console.setText("Error get customer type by caption");
                            log.log(Level.SEVERE, "Error get customer type by caption " + throwable.getMessage());
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

                            CustomerManager.console.setText("Added new customer");
                            CustomerSearch.customersCellTable.redraw();
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

    public static void setCustomer(Customer customer){
        String title = customer.getTitle();
        for (int i = 0; i<TITLES.length; i++){
            if (title.equals(TITLES[i])) {
                lbTitle.setSelectedIndex(i);
                break;
            }
        }
        tbFirstName.setText(customer.getFirstName());
        tbLastName.setText(customer.getLastName());
    }


    private static void checkTypes() {
        customerTypeService.checkTypes(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                CustomerManager.console.setText("Error loaded customer types");
                log.log(Level.SEVERE, throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
                CustomerManager.console.setText("All good");
            }
        });
    }
}
