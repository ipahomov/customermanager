package com.test.netcracker.client;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ProvidesKey;
import com.test.netcracker.model.Customer;
import com.test.netcracker.shared.ICustomerService;
import com.test.netcracker.shared.ICustomerServiceAsync;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Custom cell table to show list of customers.
 * Table uses key selection.
 * Created by IPahomov on 12.06.2016.
 */
public class CustomersCellTable extends CellTable<Customer> {
    private static final Logger log = Logger.getLogger("CustomerSearch");
    private final static ICustomerServiceAsync customerService = GWT.create(ICustomerService.class);

    List<Customer> customers = Collections.EMPTY_LIST;
    private Customer customer;

    public CustomersCellTable(ProvidesKey<Customer> keyProvider) {
        super(keyProvider);
        this.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

        // Title
        TextColumn<Customer> title = new TextColumn<Customer>() {
            @Override
            public String getValue(Customer customer) {
                return customer.getTitle();
            }
        };
        this.addColumn(title, "Title");

        // First Name
        TextColumn<Customer> firstName = new TextColumn<Customer>() {
            @Override
            public String getValue(Customer customer) {
                return customer.getFirstName();
            }
        };
        this.addColumn(firstName, "First Name");

        // Last Name
        TextColumn<Customer> lastName = new TextColumn<Customer>() {
            @Override
            public String getValue(Customer customer) {
                return customer.getLastName();
            }
        };
        this.addColumn(lastName, "Last Name");

        // Date modified when
        DateCell modifiedDate = new DateCell(DateTimeFormat.getFormat("HH:mm:ss dd.MM.yyyy"));
        Column<Customer, Date> dateColumn = new Column<Customer, Date>(modifiedDate) {
            @Override
            public Date getValue(Customer customer) {

                return customer.getModifiedWhen();
            }
        };
        this.addColumn(dateColumn, "Modified when");

        // Customer type
        TextColumn<Customer> type = new TextColumn<Customer>() {
            @Override
            public String getValue(Customer customer) {
                return "Type";
            }
        };
        this.addColumn(type, "Type");

    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;

        this.setRowCount(this.customers.size(), true);
        this.setRowData(0, customers);
        log.info("List of customers to cell table: " + customers);
    }

}
