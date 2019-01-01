package local.hbar.deen.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class OrderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        int[] orderCount = getIntent().getIntArrayExtra("orderCount");
        ((TextView) findViewById(R.id.order_txt)).setText(String.format(Locale.getDefault(), "Donuts: %d\nFroyo: %d\nIce-cream Sandwich: %d\n",
                orderCount[0],
                orderCount[1],
                orderCount[2]));
    }
}
