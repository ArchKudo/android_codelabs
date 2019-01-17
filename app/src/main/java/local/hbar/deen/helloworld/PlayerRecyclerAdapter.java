package local.hbar.deen.helloworld;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class PlayerRecyclerAdapter extends
        RecyclerView.Adapter<PlayerRecyclerAdapter.PlayerViewHolder> {

    private ArrayList<Player> players;

    PlayerRecyclerAdapter(ArrayList<Player> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public PlayerRecyclerAdapter.PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.player_card, viewGroup, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder playerViewHolder, int i) {
        Player player = players.get(i);
        playerViewHolder.name.setText(player.getName());
        playerViewHolder.nationality.setText(player.getNationality());
        playerViewHolder.club.setText(player.getClub());
        playerViewHolder.rating.setText(String.format(Locale.getDefault(), "%d", player.getRating()));
        playerViewHolder.age.setText(String.format(Locale.getDefault(), "Age: %d", player.getAge()));

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
