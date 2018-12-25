package local.hbar.deen.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "local.deen.hbar.helloworld.EXTRA.reply";
    private EditText mReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        ((TextView) findViewById(R.id.text_msg)).setText(intent.getStringExtra(MainActivity.EXTRA_SEND));
        mReply = findViewById(R.id.reply_msg);
    }

    public void replyMSG(View view) {
        setResult(RESULT_OK, (new Intent()).putExtra(EXTRA_REPLY, mReply.getText().toString()));
        finish();
    }
}
