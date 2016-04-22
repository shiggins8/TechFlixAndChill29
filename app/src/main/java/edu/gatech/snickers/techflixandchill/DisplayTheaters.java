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
 * Activity to display a list view of theaters populated with theaters that are located within a
 * 5000 meter radius circle surrounding Georgia Tech.
 *
 * @version 1.1
 * @author Snickers
 */
public class DisplayTheaters extends Activity {
    protected Factual factual = new Factual("G4OnAN8FsVdfxkVmOYLaeoIliKQpA3x1M6Yk0s0n",
            "yNzcSXs3598IvWSVxvcNCtOoljwAzL88hJbAZFok");
    private ListView lvTheaters;
    private TheaterAdapter adapterTheaters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Note: 16093 meters = 10 miles
        final int radius = 8047;
        // Note: coordinates for center of GaTech campus
        final double latitude = 33.776994;
        final double longitude = -84.400105;
        final int searchLimit = 40;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_theaters);
        lvTheaters = (ListView) findViewById(R.id.theatLV);
        ArrayList<Theater> aRestaurant = new ArrayList<Theater>();
        adapterTheaters = new TheaterAdapter(this, aRestaurant);
        lvTheaters.setAdapter(adapterTheaters);
        TheaterRetrievalTask task = new TheaterRetrievalTask();
        Query query = new Query()
                .within(new Circle(latitude, longitude, radius))
                .limit(searchLimit);

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
                    Theater newTheat = new Theater(name, address, phone, locality, "x miles away");
                    theatArray.add(newTheat);
                }
            }
            ArrayList<Theater> newTheatArray = new ArrayList<Theater>();
            Theater regal = new Theater("Regal Cinemas Atlantic Station 18 ", "Atlantic Station, 261 19th St NW #1250", "Atlanta", "(844) 462-7342", "1.7 miles away");
            Theater tara = new Theater("United Artists Tara Cinemas 4", "Cheshire Square, 2345 Cheshire Bridge Rd NE", "Atlanta", "(844) 462-7342", "5.7 miles away");
            Theater amc = new Theater("AMC Dine-in Theatres Buckhead 6", "Tower Place, Georgia Atlanta Tower Place, 3340 Peachtree Rd NE", "Atlanta", "(404) 467-9619", "6.1 miles away");
            Theater phipps = new Theater("AMC Phipps Plaza 14", "Phipps Plaza,, Phipps Plaza, 3500 Peachtree Rd NE", "Atlanta", "(404) 231-1492", "8.6 miles away");
            Theater dekalb = new Theater("AMC North DeKalb Mall 16", "2042 Lawrenceville Hwy, North Dekalb Mall", "Dekalb", "(404) 634-0451", "9.3 miles away");
            Theater starlight = new Theater("Starlight Six Drive-In Theatre", "2000 Moreland Ave SE", "Atlanta", "(404) 627-5786", "9.4 miles away");
            Theater parkway = new Theater("AMC Parkway Pointe 15", "3101 Cobb Parkway #201", "Atlanta", "(770) 937-0730", "9.9 miles away");
            
            newTheatArray.add(regal);
            newTheatArray.add(tara);
            newTheatArray.add(amc);
            newTheatArray.add(phipps);
            newTheatArray.add(dekalb);
            newTheatArray.add(starlight);
            newTheatArray.add(parkway);
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
