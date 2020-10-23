package android.example.com.earthquack;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class EarthQuackLoader extends AsyncTaskLoader<ArrayList<EarthQuackData>> {
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2012-01-01&endtime=2012-12-01&minmagnitude=6";

    public EarthQuackLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<EarthQuackData> loadInBackground() {
        return QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);
    }

}
