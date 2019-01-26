package local.hbar.deen.helloworld;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView titleTextView;
    TextView authorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.title_input);
        titleTextView = findViewById(R.id.title_text);
        authorTextView = findViewById(R.id.author_text);

        findViewById(R.id.search_btn).setOnClickListener((View v) ->
                new FetchBookTask(titleTextView, authorTextView).execute(editText.getText().toString()));

    }
}

class FetchBookTask extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> titleTextWeakReference;
    private WeakReference<TextView> authorTextWeakReference;

    FetchBookTask(TextView titleText, TextView authorText) {
        this.titleTextWeakReference = new WeakReference<>(titleText);
        this.authorTextWeakReference = new WeakReference<>(authorText);
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }


    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            int i = 0;
            String title = null;
            String authors = null;

            while (i < jsonArray.length() && (authors == null && title == null)) {
                JSONObject bookObject = jsonArray.getJSONObject(i);
                JSONObject volumeObject = bookObject.getJSONObject("volumeInfo");

                try {
                    title = volumeObject.getString("title");
                    authors = volumeObject.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i += 1;
            }

            if (title != null && authors != null) {
                titleTextWeakReference.get().setText(title);
                authorTextWeakReference.get().setText(authors);
                Log.i("FetchBookTask", String.format(Locale.getDefault(),
                        "Title: %s, Authors: %s", title, authors));
            } else {
                titleTextWeakReference.get().setText(R.string.default_text);
                authorTextWeakReference.get().setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            titleTextWeakReference.get().setText(R.string.default_text);
            authorTextWeakReference.get().setText("");
        }
    }
}

class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    // Base URL for Books API.
    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    // Parameter for the search string.
    private static final String QUERY_PARAM = "q";
    // Parameter that limits search results.
    private static final String MAX_RESULTS = "maxResults";
    // Parameter to filter by print type.
    private static final String PRINT_TYPE = "printType";

    static String getBookInfo(String queryString) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String bookJSONString = null;

        try {
            Uri bookUri = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();
            URL requestURL = new URL(bookUri.toString());
            httpURLConnection = (HttpURLConnection) requestURL.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            if (stringBuilder.length() == 0) {
                return null;
            }

            bookJSONString = stringBuilder.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
        } finally {

            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.toString());
                }
            }
        }
        return bookJSONString;
    }
}