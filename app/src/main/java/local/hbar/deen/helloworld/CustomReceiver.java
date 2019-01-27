package local.hbar.deen.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();

        if (intentAction != null) {
            Toast.makeText(context,
                    intentAction.equals(Intent.ACTION_POWER_CONNECTED)
                            ? "Power Connected"
                            : "Power Disconnected",
                    Toast.LENGTH_SHORT)
                    .show();
        }


    }
}
