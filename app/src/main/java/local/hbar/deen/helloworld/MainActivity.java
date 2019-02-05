package local.hbar.deen.helloworld;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int val = this.getPreferences(Context.MODE_PRIVATE).getInt("num", -1);

        if (val != -1) {
            ((EditText) findViewById(R.id.number_input)).setHint(String.format(Locale.getDefault(), "Previously saved: %d", val));
        }

        findViewById(R.id.save_btn).setOnClickListener(
                (View v) -> {
                    this.getPreferences(Context.MODE_PRIVATE)
                            .edit()
                            .putInt("num",
                                    Integer.parseInt(((EditText) findViewById(R.id.number_input)).getText().toString()))
                            .apply();
                }
        );
    }
}
