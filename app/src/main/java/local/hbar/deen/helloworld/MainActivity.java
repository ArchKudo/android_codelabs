package local.hbar.deen.helloworld;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            ((TextView) findViewById(R.id.text_msg)).setText(String.format(Locale.getDefault(),
                    "%s", savedInstanceState.getString("old_text")));
        }

        findViewById(R.id.btn_woke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SleepTask(findViewById(R.id.text_msg)).execute();
                Toast.makeText(getApplicationContext(), "After/Before Woke Text",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("old_text",
                ((TextView) findViewById(R.id.text_msg)).getText().toString());
    }
}

class SleepTask extends AsyncTask<Void, Integer, String> {

    private WeakReference<TextView> mTextView;

    SleepTask(TextView mTextView) {
        this.mTextView = new WeakReference<>(mTextView);
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random random = new Random();
        int delay = 1 + random.nextInt(10 + 1) * 100; // delay > 0


        try {
            for (int i = 0; i <= delay; i += 10) {
                publishProgress(i);
                // s = (n/2)(a+l) => l = 2s/n _(where a = 0)
                Thread.sleep(2 * i / delay);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return String.format(Locale.getDefault(), "Awoke after %d milli-seconds", delay);
    }

    @Override
    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        mTextView.get().setText(String.format(Locale.getDefault(), "%d", progress[0]));
    }
}