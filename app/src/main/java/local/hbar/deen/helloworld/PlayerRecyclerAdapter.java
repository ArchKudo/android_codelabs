package local.hbar.deen.helloworld;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerRecyclerAdapter extends
        RecyclerView.Adapter<PlayerRecyclerAdapter.PlayerViewHolder> {

    private ArrayList<ArrayList<String>> players;

    PlayerRecyclerAdapter(ArrayList<ArrayList<String>> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public PlayerRecyclerAdapter.PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                                     int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.player_card,
                viewGroup, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder playerViewHolder, int i) {
        ArrayList<String> player = players.get(i);
        playerViewHolder.name.setText(player.get(2));
        playerViewHolder.nationality.setText(player.get(5));
        playerViewHolder.club.setText(player.get(9));
        playerViewHolder.rating.setText(player.get(7));
        playerViewHolder.age.setText(player.get(3));

    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView name, club, nationality, age, rating;

        PlayerViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.text_name);
            club = view.findViewById(R.id.text_club);
            nationality = view.findViewById(R.id.text_nationality);
            age = view.findViewById(R.id.num_age);
            rating = view.findViewById(R.id.num_rating);
        }
    }
}
