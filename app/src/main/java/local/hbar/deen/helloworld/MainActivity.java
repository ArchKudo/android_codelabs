package local.hbar.deen.helloworld;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private TextView coloredTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coloredTextView = findViewById(R.id.colored_txt);

        if (savedInstanceState != null) {
            coloredTextView.setTextColor(savedInstanceState.getInt("color"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putInt("color", coloredTextView.getCurrentTextColor());
    }

    public void changeColor(View view) {
        coloredTextView.setTextColor(Color.argb(g(), g(), g(), g()));
    }

    public int g() {
        return new Random().nextInt(255);
    }
}