package com.sola.spotoption.views;

import static android.view.View.GONE;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sola.spotoption.adapters.CountriesAdapter;
import com.sola.spotoption.models.CountryEntry;
import com.sola.spotoption.models.IEntry;
import com.sola.spotoption.models.IUpdate;
import com.sola.spotoption.utils.DBHelper;
import com.sola.spotoption.R;
import com.sola.spotoption.loaders.CountriesLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<String>>, IUpdate{

    private DBHelper db;
    private ListView listView;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        db = new DBHelper(this, DBHelper.TABLE_NAME, null, DBHelper.CUR_VERSION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pb.setVisibility(GONE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        pb.setVisibility(View.VISIBLE);
        listView.setVisibility(GONE);
        return new CountriesLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
        List<IEntry> entries = db.getData();
        if (entries.size() == 0) {
            if (data != null) {
                db.fillData(data);
                for (String country : data) {
                    entries.add(new CountryEntry(country, false));
                }
            } else {
                Toast.makeText(this, "Can't load data from server", Toast.LENGTH_SHORT).show();
            }
        }
        CountriesAdapter adapter = new CountriesAdapter(this, entries, this);
        listView.setAdapter(adapter);
        pb.setVisibility(GONE);
        listView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {

    }


    @Override
    public void updateRow(String name, int checked) {
        db.updateRow(name, checked);
    }
}
