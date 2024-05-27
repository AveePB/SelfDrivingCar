package dev.bpeeva.bookllegro.util.enums;

public enum Scope {
    //Customer Account Scopes
    CHANGE_USERNAME("update:username"),
    CHANGE_PASSWORD("update:password"),
    DEACTIVATE_ACCOUNT("delete:account"),

    //Customer Product Scopes
    GET_PRODUCT("read:product"), //ALSO SELLER SCOPE

    //Customer Order Scopes
    PLACE_ORDER("create:order"),
    CANCEL_ORDER("delete:order"), //ALSO SELLER SCOPE
    GET_ORDER("read:order"), //ALSO SELLER SCOPE

    //Seller Product Scopes
    ADD_ASSORTMENT("create:assortment"),
    RESTOCK_ASSORTMENT("update:assortment"),
    REMOVE_ASSORTMENT("delete:assortment"),

    //Seller Order Scopes
    UPDATE_ORDER("update:order");

    String str;

    //Constructor
    Scope(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
