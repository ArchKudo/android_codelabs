package local.hbar.deen.helloworld;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ArrayList<String>> players = new ArrayList<>();


        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open("data" +
                    ".csv"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.readLine();
            String line;
            ArrayList<String> props;
            while ((line = bufferedReader.readLine()) != null) {
                props = new ArrayList<>(Arrays.asList(line.split(",")));

                players.add(props);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Cannot read CSV file");
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        PlayerRecyclerAdapter mAdapter = new PlayerRecyclerAdapter(players);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder original,
                                  @NonNull RecyclerView.ViewHolder target) {
                int from = original.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(players, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                players.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


}