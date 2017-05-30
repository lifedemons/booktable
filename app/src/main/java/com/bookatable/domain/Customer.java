package com.bookatable.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m") public class Customer {

  @Getter @Setter private int mId;

  @Getter @Setter private String mFirstName;

  @Getter @Setter private String mLastName;
}
