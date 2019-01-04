package local.hbar.deen.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String LOG_TAG = OrderActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        int[] orderCount = getIntent().getIntArrayExtra("orderCount");
        ((TextView) findViewById(R.id.content_text)).setText(String.format(Locale.getDefault(), "Donuts: %d\nFroyo: %d\nIce-cream Sandwich: %d\n",
                orderCount[0],
                orderCount[1],
                orderCount[2]));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.labels_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.email_spinner)).setAdapter(adapter);

        ((EditText) findViewById(R.id.phone_text)).setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEND) {
                            String phoneNo = null;
                            EditText editText = findViewById(R.id.phone_text);
                            if (editText != null) {
                                phoneNo = "tel:" + editText.getText().toString();

                            }
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse(phoneNo));
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            } else {
                                Log.w(LOG_TAG, "Can't handle implicit intent for phone no:" + phoneNo);
                            }
                            return true;
                        }
                        return false;
                    }
                }
        );
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
