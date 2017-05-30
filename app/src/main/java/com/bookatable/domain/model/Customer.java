package com.bookatable.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This is Domain Layer model, which should fully correspond to the App's Domain.
 */
@Accessors(prefix = "m") public class Customer {

  @Getter @Setter private int mId;

  @Getter @Setter private String mFirstName;

  @Getter @Setter private String mLastName;
}
