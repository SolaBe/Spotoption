package com.sola.spotoption.models;

/**
 * Created by Sola2Be on 13.07.2017.
 */

public class CountryEntry implements IEntry{

    public CountryEntry(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    private String name;

    private boolean isChecked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean isDivider() {
        return false;
    }
}
