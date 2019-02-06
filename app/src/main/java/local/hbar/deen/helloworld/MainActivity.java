package local.hbar.deen.helloworld;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Entity(tableName = "word_table")
    class Word {

        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "word")
        private String word;

        Word(@NonNull String word) {
            this.word = word;
        }

        String getWord() {
            return this.word;
        }
    }

    @Dao
    interface WordDAO {

        @Insert
        void insert(Word word);

        @Query("SELECT * from word_table ORDER BY word ASC")
        LiveData<List<Word>> getAll();

        @Query("DELETE FROM word_table")
        void deleteAll();
    }
}
