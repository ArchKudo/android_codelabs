package local.hbar.deen.helloworld;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private EditText mEditText;
    public static final String EXTRA_MSG = "local.deen.hbar.helloworld.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.editText_main);
        // Logging
        Log.d(LOG_TAG, "Hello, Two Activities!");
    }

    public void launchSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        String msg = mEditText.getText().toString();
        intent.putExtra(EXTRA_MSG, msg);
        startActivity(intent);
    }
}
