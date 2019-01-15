package local.hbar.deen.helloworld;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeamScoreFragment extends Fragment {

    int score = 0;

    public TeamScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View teamScoreView = inflater.inflate(R.layout.fragment_team_score, container, false);

        // Set the heading
        ((TextView) teamScoreView.findViewById(R.id.heading)).setText(getArguments().getString("teamName"));

        // Get the <include />'d number_button view
        View numberButton = teamScoreView.findViewById(R.id.number_button);

        // Handle onClick for the ImageButtons and updating textView
        TextView scoreTextView = numberButton.findViewById(R.id.text_score);
        numberButton.findViewById(R.id.inc_btn).setOnClickListener((View v) -> {
                    score += 1;
                    scoreTextView.setText(String.format(Locale.getDefault(), "%d", score));
                }
        );
        numberButton.findViewById(R.id.dec_btn).setOnClickListener((View view) -> {
            if (score > 0) {
                score -= 1;
                scoreTextView.setText(String.format(Locale.getDefault(), "%d", score));
            } else {
                Toast.makeText(view.getContext(), "Already at minimum score", Toast.LENGTH_SHORT).show();
            }
        });

        return teamScoreView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("score", score);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("score");
        }
    }
}
