package local.hbar.deen.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.parent_layout,
                            createFragmentWithArguments(StringBundle("teamName", "Team 1")))
                    .add(R.id.parent_layout,
                            createFragmentWithArguments(StringBundle("teamName", "Team 2")))
                    .commit();
        }




    }

    Bundle StringBundle(String key, String value) {
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        return bundle;
    }

    TeamScoreFragment createFragmentWithArguments(Bundle bundle) {
        TeamScoreFragment newTeam = new TeamScoreFragment();
        newTeam.setArguments(bundle);
        return newTeam;
    }
}