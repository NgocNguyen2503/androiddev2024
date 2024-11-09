package vn.edu.usth.weather;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class WeatherActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    private static final String USTH_LOGO_URL = "https://www.usth.edu.vn/uploads/logo_usth_white.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); //khoi tao man hinh
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        ForecastFragment firstFragment = new ForecastFragment();
//        getSupportFragmentManager().beginTransaction().add(
//                R.id.main, firstFragment).commit();
        PagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);

        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);

//        mediaPlayer = MediaPlayer.create(this, R.raw.audio1);
//
//        mediaPlayer.start();
        Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.refresh) {
            //Toast.makeText(this, "refreshing", Toast.LENGTH_SHORT).show();
            new simulate().execute();
            return true;
        } else if (itemId == R.id.setting) {
            Intent intent = new Intent(WeatherActivity.this, PrefActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("weather", "start");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("weather", "Pause");

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("weather", "resume");

    }

    @Override
    protected  void onStop(){
        super.onStop();
        Log.i("weather", "stop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Log.i("weather", "Destroy");
    }

    public class HomePagerAdapter extends FragmentPagerAdapter {
        private final int PAGE_COUNT = 3;
        private final String[] titles = new String[]{"First", "Second", "Third"};

        public HomePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @NonNull
        @Override
        public Fragment getItem(int page) {

                    return new WeatherAndForecastFragment();

            //return new WeatherAndForecastFragment();
        }
        public CharSequence getPageTitle(int page) {
            // returns a tab title corresponding to the specified page
            return titles[page];
        }
    }
    //Prac 14
    private class simulate extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("AsyncTask", "Requesting");
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "some sample json here";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(WeatherActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }




//    Prac 13
//    private void simulate() {
//        final Handler handler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                // This method is executed in the main thread
//                String content = msg.getData().getString("server_response");
//
//                Toast.makeText(WeatherActivity.this, content, Toast.LENGTH_SHORT).show();
//            }
//        };
//
//        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                // Assume that we got our data from the server
//                Bundle bundle = new Bundle();
//                bundle.putString("server_response", "some sample json here");
//
//                // Notify the main thread
//                Message msg = new Message();
//                msg.setData(bundle);
//                handler.sendMessage(msg);
//            }
//        });
//        t.start();
//    }







}
