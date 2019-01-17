package local.hbar.deen.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Player> players = new ArrayList<>();



        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open("data.csv"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.readLine();
            String line;
            String[] props;
            while((line = bufferedReader.readLine()) != null) {
                props = line.split(",");
                Player player = new Player(props[2],
                        props[5],
                        props[9],
                        Integer.parseInt(props[7]),
                        Integer.parseInt(props[3]));
                players.add(player);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Cannot read CSV file");
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PlayerRecyclerAdapter(players));
    }
}