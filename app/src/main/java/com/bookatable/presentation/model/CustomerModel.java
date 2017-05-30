package com.bookatable.presentation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m") public class CustomerModel {

  @Getter @Setter private int mId;

  @Getter @Setter private String mFirstName;

  @Getter @Setter private String mLastName;
}
