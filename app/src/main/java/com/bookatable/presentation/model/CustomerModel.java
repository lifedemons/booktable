package com.bookatable.presentation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This is Presentation Layer model, which should corresponds to Model in Model-View-Presenter
 * pattern and can hold some View related state inside.
 */
@Accessors(prefix = "m") public class CustomerModel {

  @Getter @Setter private int mId;

  @Getter @Setter private String mFirstName;

  @Getter @Setter private String mLastName;
}
