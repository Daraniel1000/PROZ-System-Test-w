package com.example.projekt_proz.activities.admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projekt_proz.R;
import com.example.projekt_proz.adapters.AdminQuestionAnswersViewAdapter;
import com.example.projekt_proz.models.prozAnswer;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozTest;
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;

import okhttp3.Credentials;


public class QuestionEditActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private EditText editText;
    private prozQuestion currentQuestion;

    private String login;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_question_edit);

        login = getIntent().getStringExtra("login");
        password = getIntent().getStringExtra("password");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            currentQuestion = (prozQuestion) getIntent().getSerializableExtra("question");
            if (currentQuestion == null) {
                // Creating a new question
                currentQuestion = new prozQuestion();
                currentQuestion.initAnswers(0);
            }
        } else {
            currentQuestion = (prozQuestion) savedInstanceState.getSerializable("question");
        }

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AdminQuestionAnswersViewAdapter(this, currentQuestion.getAnswers()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        editText = findViewById(R.id.text_input_question_name);
        editText.setText(currentQuestion.getText());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentQuestion.setText(editable.toString());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("question", currentQuestion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (id == R.id.save_changes) {
            saveQuestion();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addAnswer(View view) {
        prozAnswer pa = new prozAnswer(false, "Nowa odpowiedź " + (currentQuestion.getAnswersSize() + 1));
        currentQuestion.getAnswers().add(pa);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scrollToPosition(currentQuestion.getAnswersSize() - 1);
    }

    public void deleteAnswer(View view) {
        if (currentQuestion.getAnswers().isEmpty())
            return;
        currentQuestion.getAnswers().remove(currentQuestion.getAnswersSize() - 1);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scrollToPosition(currentQuestion.getAnswersSize() - 1);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
            .setTitle("Zapisywanie zmian")
            .setMessage("Czy chcesz wyjść bez zapisywania zmian?")
            .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    supportFinishAfterTransition();
                }

            })
            .setNegativeButton("Nie", null)
            .setCancelable(true)
            .show();
    }

    private void saveQuestion() {
        int correctAns = 0;
        for (int x = 0; x < currentQuestion.getAnswersSize(); x++) {
            if (currentQuestion.getAnswer(x).isCorrect())
                correctAns++;
        }
        if (correctAns == 0) {
            Toast.makeText(QuestionEditActivity.this, "Nie zaznaczono poprawnych odpowiedzi!", Toast.LENGTH_LONG).show();
            return;
        } else if (correctAns == 1) {
            currentQuestion.setType(prozQuestion.TYPE_SINGLE_CHOICE);
        } else {
            currentQuestion.setType(prozQuestion.TYPE_MULTIPLE_CHOICE);
        }
        // TODO: multiple_choice z jedną poprawną też byłby sensowny :P

        if (currentQuestion.getQuestionID() < 0)
        {
            new AddQuestion(login, password, currentQuestion).execute();
            return;
        }

        new AlertDialog.Builder(this)
            .setTitle("Pytanie już zapisano")
            .setMessage("Spowoduje to utworzenie duplikatu. Czy na pewno chcesz to zrobić?")
            .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new AddQuestion(login, password, currentQuestion).execute();
                }

            })
            .setNegativeButton("Nie", null)
            .setCancelable(true)
            .show();
    }

    public class AddQuestion extends AsyncTask<Void, Void, prozQuestion>
    {
        private final String login;
        private final String password;
        private final prozQuestion question;

        private ProgressDialog dialog;

        public AddQuestion(String login, String password, prozQuestion question) {
            super();
            this.login = login;
            this.password = password;
            this.question = question;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(QuestionEditActivity.this, "", "Zapisywanie pytania", true);
        }

        @Override
        protected prozQuestion doInBackground(Void... voids) {
            try {
                return new MyClient().questions().addQuestion(question, Credentials.basic(login, password)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(prozQuestion question) {
            dialog.cancel();
            if (question == null) {
                Toast.makeText(QuestionEditActivity.this, "Nie udało się zapisać pytania!", Toast.LENGTH_LONG).show();
                return;
            }

            supportFinishAfterTransition();
        }
    }
}
