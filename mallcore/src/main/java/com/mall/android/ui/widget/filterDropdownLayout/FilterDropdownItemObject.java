package com.mall.android.ui.widget.filterDropdownLayout;

public class FilterDropdownItemObject {

    public int id;
    public String text;
    public String value;
    public String suffix;

    public FilterDropdownItemObject(String text, int id, String value) {
        this.text = text;
        this.id = id;
        this.value = value;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
