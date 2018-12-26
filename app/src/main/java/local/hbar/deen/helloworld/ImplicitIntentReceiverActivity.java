package local.hbar.deen.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ImplicitIntentReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit_intent_receiver);
        Intent intent = getIntent();
        Uri uri = ((Intent) intent).getData();
        if (uri != null) {
            String url = "URI: " + ((Uri) uri).toString();
            ((TextView) findViewById(R.id.text_view_uri)).setText(url);
        }
    }
}
