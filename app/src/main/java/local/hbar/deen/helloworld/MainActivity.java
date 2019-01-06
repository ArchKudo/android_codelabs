package local.hbar.deen.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static int[] orderCount = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        findViewById(R.id.donut_text).setOnLongClickListener(this);
        findViewById(R.id.froyo_text).setOnLongClickListener(this);
        findViewById(R.id.ics_text).setOnLongClickListener(this);
        findViewById(R.id.fab_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                intent.putExtra("orderCount", orderCount);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onLongClick(View v) {
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                orderCount[Integer.parseInt(v.getTag().toString()) - 1] += 1;
                Log.d(LOG_TAG, Arrays.toString(orderCount));
                return true;
            }
        });
        return true;
    }

    public void showDesc(View view) {
        final Integer tag = Integer.parseInt(view.getTag().toString());
        String[] sweets = getResources().getStringArray(R.array.sweets);
        Toast.makeText(this, "Long press to add " + sweets[tag - 1], Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
        return true;
    }
}