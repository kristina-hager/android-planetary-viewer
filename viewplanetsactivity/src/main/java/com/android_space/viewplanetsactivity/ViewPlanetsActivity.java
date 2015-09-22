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

public class ViewPlanetsActivity extends ActionBarActivity {
    public final static String SYSTEM_NAME = "com.android_space.viewplanetsactivity.SYSTEM_NAME";
    public final static String PLANETS_ARRAY = "com.android_space.viewplanetsactivity.PLANETS_ARRAY";
    public static final String TAG = "ViewPlanetsActivity:";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_planets);

        StringBuilder stringBuilder = new StringBuilder();

        //http://developer.android.com/training/basics/intents/filters.html
        //provides some info on letting other apps call your activity
        //this documentation includes a bit more than I'll include here
        //however, it's a useful reference

        //example of getting parameters/info from invoking activity
        stringBuilder.append("Information from calling application:\n");
        Intent intent = getIntent();
        String systemName = intent.getStringExtra(this.SYSTEM_NAME);
        if (systemName != null) {
            stringBuilder.append("planetary system name: " + systemName + "\n");
        }

        Bundle planetsBundle = this.getIntent().getExtras();
        if (planetsBundle != null) {
            String[] planetNames = planetsBundle.getStringArray(this.PLANETS_ARRAY);
            if (planetNames != null) {
                stringBuilder.append("with planets: \n");
                for (String planetName : planetNames) {
                    stringBuilder.append(planetName + "\n");
                }
            }
        }

        //TODO - hook up to planetarysystems lib

        //example of using library hello
//        stringBuilder.append("\nMessage from the library in use:\n");
//        stringBuilder.append(HelloManager.getGreetings());

        TextView helloTextView = (TextView) findViewById(R.id.hello_message);
        helloTextView.setText(stringBuilder.toString());

    }

    public void sendActivityResult(View view) {
        String systemName = "temp system name";
        Log.i(TAG, "sendActivityResult: returning the result: " + systemName
                + ", with result code: " + Activity.RESULT_OK);
        Intent result = new Intent("com.android_space.RESULT_ACTION");
        //http://developer.android.com/training/sharing/send.html
        //TODO: is putExtra the best way for a library to return a result?
        result.setAction(Intent.ACTION_SEND);
        result.putExtra(Intent.EXTRA_TEXT, "You made a planetary system named: " + systemName);
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
