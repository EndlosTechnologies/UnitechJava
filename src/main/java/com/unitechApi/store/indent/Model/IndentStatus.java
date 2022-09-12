package com.unitechApi.store.indent.Model;

public enum IndentStatus {
    REJECT("reject"),
    CANCEL("cancel"),
    GM("gm"),
    ADMIN("admin"),
    ADMIN_LAST("admin last"),
    ACCOUNT ("account"),
    DONE("Done"),
    STORE("store"),
    ;
    private String value;
    IndentStatus(String value) {
        this.value=value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }
}
