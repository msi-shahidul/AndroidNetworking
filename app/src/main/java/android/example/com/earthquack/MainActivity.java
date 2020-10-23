package android.example.com.earthquack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private EarthQuackAdapter mArrayAdapter;

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2012-01-01&endtime=2012-12-01&minmagnitude=6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doSomeTaskAsync();

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        mArrayAdapter = new EarthQuackAdapter(MainActivity.this,  new ArrayList<EarthQuackData>());
        earthquakeListView.setAdapter(mArrayAdapter);


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String urlAdd = mArrayAdapter.getItem(i).getUrlAddress();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlAdd));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    public void doSomeTaskAsync() {
        HandlerThread ht = new HandlerThread("MyHandlerThread");
        ht.start();
        final Handler asyncHandler = new Handler(ht.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Object response = msg.obj;
                doSomethingOnUi(response);
            }
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.obj = QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);
                asyncHandler.sendMessage(message);
            }
        };
        asyncHandler.post(runnable);
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */

    private void doSomethingOnUi(final Object response) {
        Handler uiThread = new Handler(Looper.getMainLooper());
        uiThread.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<EarthQuackData> dd = (ArrayList<EarthQuackData>)response;
                mArrayAdapter.addAll(dd);
            }
        });
    }


}