package local.hbar.deen.helloworld;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Locale;


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
        playerViewHolder.age.setText(String.format(Locale.getDefault(), "Age: %s", player.get(3)));
        GlideApp.with(playerViewHolder.name.getContext())
                .load(player.get(4))
                .error(GlideApp.with(playerViewHolder.name.getContext())
                                .load("https://cdn.sofifa.org/players/4/notfound_0.png"))
                .apply(RequestOptions.circleCropTransform())
                .apply(new RequestOptions().override(200, 200))
                
                .fallback(R.mipmap.ic_launcher_foreground)
                .into(playerViewHolder.player_img);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView name, club, nationality, age, rating;
        ImageView player_img;

        PlayerViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.text_name);
            club = view.findViewById(R.id.text_club);
            nationality = view.findViewById(R.id.text_nationality);
            age = view.findViewById(R.id.num_age);
            rating = view.findViewById(R.id.num_rating);
            player_img = view.findViewById(R.id.img_player);
        }
    }
}


