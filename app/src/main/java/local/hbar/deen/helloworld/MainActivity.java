package local.hbar.deen.helloworld;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Locale;
import java.util.function.Function;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Function<String, Integer> getPrefs =
                (String key) -> this.getPreferences(Context.MODE_PRIVATE).getInt(key, -1);


        if (getPrefs.apply("num") != -1) {
            ((EditText) findViewById(R.id.number_input))
                    .setHint(String.format(Locale.getDefault(),
                            "Previously saved: %d",
                            getPrefs.apply("num")));
        }

        findViewById(R.id.save_btn).setOnClickListener(
                (View v) -> {
                    this.getPreferences(Context.MODE_PRIVATE)
                            .edit()
                            .putInt("num",
                                    Integer.parseInt(((EditText) findViewById(R.id.number_input))
                                            .getText().toString()))
                            .apply();

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(v.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                    ((EditText) findViewById(R.id.number_input))
                            .setHint(String.format(Locale.getDefault(),
                                    "Currently saved: %s", getPrefs.apply("num")));

                    ((EditText) findViewById(R.id.number_input)).setText("");
                }
        );
    }
}
