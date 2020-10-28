package android.example.com.earthquack;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class EarthQuackLoader extends AsyncTaskLoader<ArrayList<EarthQuackData>> {
    private String mUrl;
    public EarthQuackLoader(Context context, String urlStr) {
        super(context);
        mUrl = urlStr;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<EarthQuackData> loadInBackground() {
        return QueryUtils.fetchEarthquakeData(mUrl);
    }

}
