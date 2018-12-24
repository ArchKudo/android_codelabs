package local.hbar.deen.helloworld;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private View mScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScrollView = findViewById(R.id.nested_scroll_view);

        // Logging
        Log.d(LOG_TAG, "Hello, Scrolling Text!");
    }

    public void goUp(View view) {
        ((ScrollView) mScrollView).fullScroll(ScrollView.FOCUS_UP);
    }
}
