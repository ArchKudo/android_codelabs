package local.hbar.deen.helloworld;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
        String[] products = getIntent().getStringArrayExtra("productsText");

        for (int i = 0; i < orderCount.length; i++) {
            ((TextView) findViewById(R.id.content_text))
                    .append(String.format(Locale.getDefault(), "%s: %d\n", products[i], orderCount[i]));
        }

        // Create an adapter for drop-down menu from string-array resource
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.contact_type,
                android.R.layout.simple_spinner_item);

        // Set look of drop-down
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.email_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        ((EditText) findViewById(R.id.phone_text)).setOnEditorActionListener(
                (TextView v, int actionId, KeyEvent event) -> {
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
        );
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void showAlert(View view) {
        (new AlertDialog.Builder(OrderActivity.this))
                .setTitle("Alert").setMessage("Are you sure you want to delete this order?")
                .setPositiveButton("OK", (DialogInterface dialog, int which) ->
                        Toast.makeText(getApplicationContext(), "Order Deleted", Toast.LENGTH_SHORT).show())
                .setNegativeButton("CANCEL", (DialogInterface dialog, int which) ->
                        Toast.makeText(getApplicationContext(), "Cancelled deleting order", Toast.LENGTH_SHORT).show())
                .show();
    }

    public void showCalendar(View view) {
        new DatePickerFragment().show(getSupportFragmentManager(), "datePicker");

    }

    public void processDatePickerResult(int year, int month, int dayOfMonth) {
        Toast.makeText(this, (Integer.toString(year) +
                "/" + Integer.toString(month + 1) +
                "/" + Integer.toString(dayOfMonth)), Toast.LENGTH_SHORT).show();
    }
}

