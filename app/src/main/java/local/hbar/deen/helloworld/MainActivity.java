package local.hbar.deen.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private TextView coloredTextView;
    private List<String> mColor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coloredTextView = findViewById(R.id.colored_txt);

        Field[] fields = R.color.class.getFields();

        for (Field field : fields) {
            Log.i(LOG_TAG, field.getName());
            mColor.add(field.getName());
        }

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
        String colorName = mColor.get(new Random().nextInt(mColor.size() - 1));
        int colorResource = getResources().getIdentifier(colorName, "color", getApplicationContext().getPackageName());
        int colorId = ContextCompat.getColor(this, colorResource);
        coloredTextView.setTextColor(colorId);
    }

    public int g() {
        return new Random().nextInt(255);
    }
}