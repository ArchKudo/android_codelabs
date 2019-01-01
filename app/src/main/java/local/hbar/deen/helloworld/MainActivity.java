package local.hbar.deen.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnLongClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private SparseIntArray orderList = new SparseIntArray(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.donut_text).setOnLongClickListener(this);
        findViewById(R.id.froyo_text).setOnLongClickListener(this);
        findViewById(R.id.ics_text).setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Integer key = Integer.parseInt(v.getTag().toString());
                orderList.put(key, orderList.get(key, 0) + 1);
                Log.d(LOG_TAG, orderList.toString());
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


}