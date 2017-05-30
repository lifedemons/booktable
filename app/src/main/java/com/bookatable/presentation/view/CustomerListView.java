/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.bookatable.presentation.view;

import com.bookatable.presentation.model.CustomerModel;
import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link CustomerModel}.
 */
public interface CustomerListView extends LoadDataView {
    /**
     * Render a customer list in the UI.
     *
     * @param customerModelCollection The collection of {@link CustomerModel} that will be shown.
     */
    void renderCustomerList(Collection<CustomerModel> customerModelCollection);

    /**
     * View a {@link CustomerModel} profile/details.
     *
     * @param customerModel The customer that will be shown.
     */
    void viewCustomer(CustomerModel customerModel);

    /**
     * Highlights text entries in list items.
     *
     * @param textToHighlight text to highlight.
     */
    void highlightTextInList(String textToHighlight);
}
