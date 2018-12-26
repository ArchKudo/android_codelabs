package local.hbar.deen.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "local.deen.hbar.helloworld.EXTRA.reply";
    private static final String LOG_TAG = SecondActivity.class.getSimpleName();

    private EditText mReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        ((TextView) findViewById(R.id.text_msg)).setText(intent.getStringExtra(MainActivity.EXTRA_SEND));
        mReply = findViewById(R.id.reply_msg);

        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
    }

    public void replyMSG(View view) {
        setResult(RESULT_OK, (new Intent()).putExtra(EXTRA_REPLY, mReply.getText().toString()));
        Log.d(LOG_TAG, "SecondActivity finish()");
        finish();
    }
}
