package com.sola.spotoption.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sola.spotoption.R;
import com.sola.spotoption.models.CountryEntry;
import com.sola.spotoption.models.DividerEntry;
import com.sola.spotoption.models.IEntry;
import com.sola.spotoption.models.IUpdate;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Sola2Be on 13.07.2017.
 */

public class CountriesAdapter extends BaseAdapter implements
        View.OnClickListener{

    private final static int TYPE_COUNT = 2;
    private final static int TYPE_DIVIDER = 1;
    private final static int TYPE_COUNTRY = 0;
    private List<IEntry> entries;
    private LayoutInflater inflater;
    private IUpdate updInterface;

    public CountriesAdapter(@NonNull Context context, @NonNull List<IEntry> objects,
            IUpdate update) {
        entries = objects;
        inflater = LayoutInflater.from(context);
        this.updInterface = update;
        insertDividers();
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Nullable
    @Override
    public IEntry getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return entries.get(position).isDivider() ? TYPE_DIVIDER : TYPE_COUNTRY;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        IEntry entry = getItem(position);
        switch (getItemViewType(position)) {
            case TYPE_COUNTRY:
                if (v == null) {
                    holder = new ViewHolder();
                    v = inflater.inflate(R.layout.list_item, parent, false);
                    holder.textView = (TextView) v.findViewById(R.id.name);
                    holder.checkBox = (CheckBox) v.findViewById(R.id.checkBox);
                    v.setTag(holder);
                } else
                    holder = (ViewHolder) v.getTag();
                holder.textView.setText(entry.getName());
                holder.checkBox.setChecked(entry.isChecked());
                holder.checkBox.setOnClickListener(this);
                holder.checkBox.setTag(position);
                break;
            case TYPE_DIVIDER:
                if (v == null) {
                    holder = new ViewHolder();
                    v = inflater.inflate(R.layout.divider_item, parent, false);
                    holder.textView = (TextView) v.findViewById(R.id.divider);
                    v.setTag(holder);
                } else
                    holder = (ViewHolder) v.getTag();
                holder.textView.setText(entry.getName());
                break;
        }
        return v;
    }

    private void insertDividers() {
        HashSet<String> dividers = new HashSet<>();
        for (IEntry country : entries) {
            dividers.add(country.getName().substring(0, 1).toUpperCase());
        }
        Iterator<String> it = dividers.iterator();
        while (it.hasNext()) {
            DividerEntry div = new DividerEntry();
            div.setName(it.next());
            entries.add(div);
        }

        Collections.sort(entries, new Comparator<IEntry>() {

            @Override
            public int compare(IEntry e1, IEntry e2) {
                return e1.getName().compareToIgnoreCase(e2.getName());
            }
        });
    }

    @Override
    public void onClick(View view) {
        CountryEntry entry = (CountryEntry) getItem((Integer) view.getTag());
        boolean isChecked = ((CheckBox) view).isChecked();
        String name = entry.getName();
        entry.setChecked(isChecked);
        notifyDataSetChanged();
        updInterface.updateRow(name, isChecked ? 1 : 0);
    }


    class ViewHolder {

        public TextView textView;
        public CheckBox checkBox;

    }
}
