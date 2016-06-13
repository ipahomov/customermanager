package com.test.netcracker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.logging.Logger;

/**
 * Start point of web-app. Contains main console.
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CustomerManager implements EntryPoint {
    private static final Logger log = Logger.getLogger("EntryPoint");
    public static Label console = new Label();


    public void onModuleLoad() {
        RootPanel.get("console").add(console);

        //SearchBar for search customers and display list of results into a table
        CustomerSearch.init();
        log.info("Search initialized");

        CustomerSearch.refreshTable();

    }

}

