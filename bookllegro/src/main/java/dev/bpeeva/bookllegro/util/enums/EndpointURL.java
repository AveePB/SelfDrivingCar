package dev.bpeeva.bookllegro.util.enums;

public enum EndpointURL {
    //Uniform Resource Locators
    AUTHZ("/authz"),
    ACCOUNT("/account"),
    ORDER("/order"),
    ASSORTMENT("/assortment");

    String str;

    //Constructor
    EndpointURL(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }

}
