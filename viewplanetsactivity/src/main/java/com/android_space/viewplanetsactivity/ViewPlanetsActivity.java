package com.android_space.viewplanetsactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.space.PlanetarySystem;

import java.util.Iterator;

public class ViewPlanetsActivity extends ActionBarActivity {
    public final static String SYSTEM_NAME = "com.android_space.viewplanetsactivity.SYSTEM_NAME";
    public final static String PLANETS_ARRAY = "com.android_space.viewplanetsactivity.PLANETS_ARRAY";
    public static final String TAG = "ViewPlanetsActivity:";

    private PlanetarySystem planetarySystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_planets);


        //http://developer.android.com/training/basics/intents/filters.html
        //provides some info on letting other apps call your activity
        //this documentation includes a bit more than I'll include here
        //however, it's a useful reference

        //example of getting parameters/info from invoking activity
        Intent intent = getIntent();
        String systemName = intent.getStringExtra(this.SYSTEM_NAME);
        Bundle planetsBundle = this.getIntent().getExtras();
        if (systemName != null && planetsBundle != null) {
            buildPlanetarySystem(systemName, planetsBundle.getStringArray(this.PLANETS_ARRAY));
        } else if (systemName != null) {
            buildPlanetarySystem(systemName, null);
        }

        //show system on screen
        showPlanetarySystem();
    }

    // name - name of planetary system
    //planets - string array of planets. Use null if no planets
    private void buildPlanetarySystem(String name, String[] planets) {
        planetarySystem = new PlanetarySystem(name);

        if (planets != null) {
            for (String planetName : planets) {
                planetarySystem.addPlanet(planetName);
            }
        }
    }

    private void showPlanetarySystem() {
        StringBuilder sb = new StringBuilder();
        if (planetarySystem == null) {
            sb.append("No planetary system to show!");
        } else {
            sb.append(planetarySystem.getName() + "\n\n");
            Iterator<String> planetNamesIter = planetarySystem.getPlanetNames();
            while (planetNamesIter.hasNext()) {
                sb.append(planetNamesIter.next() + "\n");
            }
        }

        TextView helloTextView = (TextView) findViewById(R.id.hello_message);
        helloTextView.setText(sb.toString());
    }

    public void sendActivityResult(View view) {
        String systemName = (planetarySystem != null) ? planetarySystem.getName() : "no planetary system!";
        Log.i(TAG, "sendActivityResult: returning the result: " + systemName
                + ", with result code: " + Activity.RESULT_OK);
        Intent result = new Intent("com.android_space.RESULT_ACTION");
        //http://developer.android.com/training/sharing/send.html
        result.setAction(Intent.ACTION_SEND);
        result.putExtra(Intent.EXTRA_TEXT, systemName);
        result.setType("text/plain");
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_planets, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
