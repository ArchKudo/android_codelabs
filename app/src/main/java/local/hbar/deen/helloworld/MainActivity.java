package local.hbar.deen.helloworld;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

@Dao
interface WordDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAll();

    @Query("DELETE FROM word_table")
    void deleteAll();
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

    @NonNull
    String getWord() {
        return this.word;
    }
}

@Database(entities = {Word.class}, version = 1, exportSchema = false)
abstract class WordRoomDatabase extends RoomDatabase {
    private static WordRoomDatabase INSTANCE;

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDBAsync(INSTANCE).execute();
        }
    };

    static synchronized WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    WordRoomDatabase.class, "word_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return INSTANCE;
    }

    abstract WordDAO wordDAO();

    private static class PopulateDBAsync extends AsyncTask<Void, Void, Void> {

        private final WordDAO wordDAO;
        private final String[] words = {"Apple", "Banana", "Oranges"};

        PopulateDBAsync(WordRoomDatabase wordRoomDatabase) {
            wordDAO = wordRoomDatabase.wordDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDAO.deleteAll();
            for (String word : words) {
                wordDAO.insert(new Word(word));
            }
            return null;
        }
    }
}

class WordRepository {
    private WordDAO wordDAO;
    private LiveData<List<Word>> words;

    WordRepository(Application application) {
        WordRoomDatabase wordRoomDatabase = WordRoomDatabase.getDatabase(application);
        wordDAO = wordRoomDatabase.wordDAO();
        words = wordDAO.getAll();
    }

    LiveData<List<Word>> getAll() {
        return words;
    }

    void insert(Word word) {
        new insertAsyncTask(wordDAO).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDAO wordDAO;

        insertAsyncTask(WordDAO wordDAO) {
            this.wordDAO = wordDAO;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDAO.insert(words[0]);
            return null;
        }
    }
}

class WordViewModel extends AndroidViewModel {
    private WordRepository wordRepository;
    private LiveData<List<Word>> words;

    WordViewModel(Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        words = wordRepository.getAll();
    }

    LiveData<List<Word>> getAll() {
        return words;
    }

    void insert(Word word) {
        wordRepository.insert(word);
    }
}

class WordsRecyclerViewAdapter extends RecyclerView.Adapter<WordsRecyclerViewAdapter.WordsViewHolder> {
    private final LayoutInflater inflater;
    private List<Word> words;

    WordsRecyclerViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordsRecyclerViewAdapter.WordsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                                       int i) {
        TextView view = (TextView) inflater.inflate(R.layout.text_word, viewGroup, false);
        return new WordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordsRecyclerViewAdapter.WordsViewHolder wordsViewHolder,
                                 int i) {
        if (words != null) {
            wordsViewHolder.wordItemView.setText(words.get(i).getWord());
        } else {
            wordsViewHolder.wordItemView.setText(wordsViewHolder.wordItemView.getContext().getString(R.string.no_words_warn));
            wordsViewHolder.wordItemView.setBackgroundColor(wordsViewHolder.wordItemView.getContext().getColor(R.color.secondaryDarkColor));
        }
    }

    void setWords(List<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (words != null) {
            return words.size();
        } else {
            return 0;
        }
    }

    class WordsViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordsViewHolder(TextView wordItemView) {
            super(wordItemView);
            this.wordItemView = wordItemView;
        }
    }
}

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((View view) ->
                startActivityForResult(new Intent(MainActivity.this, NewWordActivity.class),
                        NEW_WORD_ACTIVITY_REQUEST_CODE));

        WordsRecyclerViewAdapter wordsRecyclerViewAdapter = new WordsRecyclerViewAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(wordsRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        wordViewModel.getAll().observe(this,
                (@Nullable List<Word> words) -> wordsRecyclerViewAdapter.setWords(words));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.getStringExtra(NewWordActivity.EXTRA_REPLY) != null) {
                wordViewModel.insert(new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY)));
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Could not save empty string!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
