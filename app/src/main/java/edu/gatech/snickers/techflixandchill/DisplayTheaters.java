package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.factual.driver.Circle;
import com.factual.driver.Factual;
import com.factual.driver.Query;
import com.factual.driver.ReadResponse;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Activity to display a list view of restaurants populated with restaurants from a particular
 * city. Utilizes the factual API, and an ArrayAdapter to fill the list view.
 *
 * @version 1.1
 * @author Scottie
 */
public class DisplayTheaters extends Activity {
    protected Factual factual = new Factual("G4OnAN8FsVdfxkVmOYLaeoIliKQpA3x1M6Yk0s0n",
            "yNzcSXs3598IvWSVxvcNCtOoljwAzL88hJbAZFok");
    private ListView lvTheaters;
    private TheaterAdapter adapterTheaters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_theaters);
        lvTheaters = (ListView) findViewById(R.id.theatLV);
        ArrayList<Theater> aRestaurant = new ArrayList<Theater>();
        adapterTheaters = new TheaterAdapter(this, aRestaurant);
        lvTheaters.setAdapter(adapterTheaters);
        TheaterRetrievalTask task = new TheaterRetrievalTask();
//        Bundle bundle = getIntent().getExtras();
//        String cityName = bundle.getString("CITY");
        Query query = new Query()
                .within(new Circle(33.776994, -84.400105, 5000))
                .limit(40);

        task.execute(query);
    }

    protected class TheaterRetrievalTask extends AsyncTask<Query, Integer, List<ReadResponse>> {
        @Override
        protected List<ReadResponse> doInBackground(Query... params) {
            List<ReadResponse> results = Lists.newArrayList();
            for (Query q : params) {
                results.add(factual.fetch("places-us", q));
            }
            return results;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected void onPostExecute(List<ReadResponse> responses) {
            ArrayList<Theater> theatArray = new ArrayList<Theater>();
            for (ReadResponse response : responses) {
                for (Map<String, Object> theater : response.getData()) {
                    String name = (String) theater.get("name");
                    String address = (String) theater.get("address");
                    String phone = (String) theater.get("tel");
                    String locality = (String) theater.get("locality");
                    Theater newTheat = new Theater(name, address, phone, locality);
                    theatArray.add(newTheat);
                }
            }
            ArrayList<Theater> newTheatArray = new ArrayList<Theater>();
            Theater amc = new Theater("AMC", "123 The Place", "Atlanta", "770-932-9269");
            Theater carmkike = new Theater("Carmike", "123 Ridic", "Atlanta", "654-984-8473");
            newTheatArray.add(amc);
            newTheatArray.add(carmkike);
//            for (Theater theat : theatArray) {
//                adapterTheaters.add(theat);
//            }
            for (Theater theat : newTheatArray) {
                adapterTheaters.add(theat);
            }
            adapterTheaters.notifyDataSetChanged();
        }

    }
}