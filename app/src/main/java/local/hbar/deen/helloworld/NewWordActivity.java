package local.hbar.deen.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        findViewById(R.id.btn_save).setOnClickListener((View view) -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(((EditText) findViewById(R.id.input_new_word)).getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                replyIntent.putExtra("reply",
                        ((EditText) findViewById(R.id.input_new_word)).getText().toString());
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}
