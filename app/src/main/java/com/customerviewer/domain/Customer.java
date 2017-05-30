package com.customerviewer.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class Customer {

    @Getter
    @Setter
    private int mId;

    @Getter
    @Setter
    private String mTitle;

    @Getter
    @Setter
    private String mUrl;

    @Getter
    @Setter
    private String mThumbnailUrl;
}
