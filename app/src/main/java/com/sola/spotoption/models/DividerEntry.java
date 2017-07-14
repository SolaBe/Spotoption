package com.sola.spotoption.models;

/**
 * Created by Sola2Be on 13.07.2017.
 */

public class DividerEntry implements IEntry {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void setChecked(boolean checked) {

    }

    @Override
    public boolean isDivider() {
        return true;
    }
}
