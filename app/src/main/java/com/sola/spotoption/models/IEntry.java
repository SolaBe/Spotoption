package com.sola.spotoption.models;

/**
 * Created by Sola2Be on 13.07.2017.
 */

public interface IEntry {

    boolean isDivider();

    String getName();

    void setName(String name);

    boolean isChecked();

    void setChecked(boolean checked);
}
