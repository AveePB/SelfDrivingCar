package dev.bpeeva.bookllegro.util.enums;

public enum Account {
    //Account types
    CUSTOMER("CUSTOMER"),
    SELLER("SELLER");

    String str;

    //Constructor
    Account(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }

}
