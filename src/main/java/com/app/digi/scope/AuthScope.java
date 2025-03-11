package com.app.digi.scope;

public enum AuthScope {

    DIGI_CUSTOMER("239802");

    final String value;

    AuthScope(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
