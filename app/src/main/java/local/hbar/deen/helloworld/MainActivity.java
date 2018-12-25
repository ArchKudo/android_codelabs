package local.hbar.deen.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int TEXT_REQUEST = 1;
    public static final String EXTRA_SEND = "local.deen.hbar.helloworld.EXTRA.send";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private EditText mSend;
    private TextView mTextHeader;
    private TextView mTextMSG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSend = findViewById(R.id.editText_main);
        mTextHeader = findViewById(R.id.text_header);
        mTextMSG = findViewById(R.id.text_msg);

        // Logging
        Log.d(LOG_TAG, "Hello, Two Activities!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: Check for null / Why isn't hasExtra enough?
        if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK && (data.hasExtra(SecondActivity.EXTRA_REPLY))) {
            mTextHeader.setVisibility(View.VISIBLE);
            mTextMSG.setVisibility(View.VISIBLE);
            mTextMSG.setText(data.getStringExtra(SecondActivity.EXTRA_REPLY));
        }
    }

    public void launchSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        String msg = mSend.getText().toString();
        intent.putExtra(EXTRA_SEND, msg);
        startActivityForResult(intent, TEXT_REQUEST);
    }
}
