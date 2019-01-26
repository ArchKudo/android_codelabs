package local.hbar.deen.helloworld;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

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

        findViewById(R.id.search_btn).setOnClickListener((View v) -> {
                    String queryString = editText.getText().toString();
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(v.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    ConnectivityManager connectivityManager =
                            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = null;
                    if (connectivityManager != null) {
                        networkInfo = connectivityManager.getActiveNetworkInfo();
                    }
                    if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
//                        new FetchBookTask(titleTextView, authorTextView).execute(queryString);

                        Bundle queryBundle = new Bundle();
                        queryBundle.putString("queryString", queryString);
                        getSupportLoaderManager().restartLoader(0, queryBundle, this);
                        titleTextView.setText(getString(R.string.loading_text));
                        authorTextView.setText("");
                    } else {
                        if (queryString.length() == 0) {
                            titleTextView.setText(getString(R.string.empty_text));
                            authorTextView.setText("");
                        } else {
                            titleTextView.setText(getString(R.string.network_text));
                            authorTextView.setText("");
                        }
                    }
                }
        );

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        String queryString = null;
        if (bundle != null) {
            queryString = bundle.getString("queryString");
        }
        return new BookLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {

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
                titleTextView.setText(title);
                authorTextView.setText(authors);
                Log.i("FetchBookTask", String.format(Locale.getDefault(),
                        "Title: %s, Authors: %s", title, authors));
            } else {
                titleTextView.setText(R.string.default_text);
                authorTextView.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            titleTextView.setText(R.string.default_text);
            authorTextView.setText("");
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

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


class BookLoader extends AsyncTaskLoader<String> {

    private String mQueryString;

    BookLoader(@NonNull Context context, String queryString) {
        super(context);
        this.mQueryString = queryString;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfo(mQueryString);
    }
}