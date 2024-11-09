package vn.edu.usth.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastFragment extends Fragment {

    private ImageView imageViewLogo;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForecastFragment newInstance(String param1, String param2) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        TextView day = new TextView(getContext());
//        day.setText("thursday");
//
//        ImageView img = new ImageView(getContext());
//        img.setImageResource(R.drawable.clear);
//
//        View v = new View(getContext());
//        v.setBackgroundColor(0x20FF0000);
//
//        LinearLayout view = new LinearLayout(getContext());
//        view.setOrientation(LinearLayout.VERTICAL);
//        view.addView(day);
//        view.addView(img);
//        view.addView(v);
//        return view;



        //return inflater.inflate(R.layout.fragment_forecast, container, false);

        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        // Initialize the ImageView for the logo
        imageViewLogo = view.findViewById(R.id.imageViewLogo);

        // Start downloading the USTH logo
        new DownloadLogoTask().execute("https://cdn.haitrieu.com/wp-content/uploads/2022/11/Icon-Truong-Dai-hoc-Khoa-hoc-va-Cong-nghe-Ha-Noi-635x794.png");

        return view;
    }
    private class DownloadLogoTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            // Notify the user that the download is starting
            Toast.makeText(getContext(), "Downloading USTH logo...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                // Initialize URL
                URL url = new URL(params[0]);

                // Make a request to the server
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                // Receive and decode the response
                InputStream is = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);

                // Disconnect the connection
                connection.disconnect();

                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                // Set the downloaded logo on the ImageView
                imageViewLogo.setImageBitmap(bitmap);
                Toast.makeText(getContext(), "Logo downloaded successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to download logo", Toast.LENGTH_SHORT).show();
            }
        }
    }
}