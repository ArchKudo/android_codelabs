package local.hbar.deen.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDesc(View view) {
        final Integer tag = Integer.parseInt(view.getTag().toString());
        String[] sweets = getResources().getStringArray(R.array.sweets);
        Toast.makeText(this, sweets[tag - 1], Toast.LENGTH_SHORT).show();

    }
}