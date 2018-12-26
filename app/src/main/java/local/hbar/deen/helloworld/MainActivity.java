package local.hbar.deen.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private EditText mWebsite;
    private EditText mLocation;
    private EditText mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebsite = findViewById(R.id.edit_text_web);
        mLocation = findViewById(R.id.edit_text_loc);
        mMessage = findViewById(R.id.edit_text_msg);

    }


    public void launchBrowser(View view) {
        String url = mWebsite.getText().toString();
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if(intent.resolveActivity(getPackageManager())!= null) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Can\'t handle website uri: " + uri);
        }
    }

    public void launchMap(View view) {
        String loc = mLocation.getText().toString();
        Uri uri = Uri.parse("geo:0,0?q=" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {

            Log.d(LOG_TAG, "Can\'t handle location uri: " + uri);
        }
    }

    public void shareText(View view) {
        String msg = mMessage.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(R.string.share)
                .setText(msg)
                .startChooser();
    }
}
