package com.example.projekt_proz.activities.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.example.projekt_proz.adapters.AdminTestQuestionsViewAdapter;
import com.example.projekt_proz.models.prozTest;

public class TestEditActivity extends AppCompatActivity implements AdminTestQuestionsViewAdapter.OnQuestionClickListener {
    private prozTest currentTest;

    private RecyclerView recyclerView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_test_edit);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            currentTest = (prozTest) getIntent().getSerializableExtra("cur_test");
            if (currentTest == null) {
                // Creating a new test
                currentTest = new prozTest();
                currentTest.initQuestions(0);
            }
        } else {
            currentTest = (prozTest) savedInstanceState.getSerializable("cur_test");
        }

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AdminTestQuestionsViewAdapter(this, this, currentTest.getQuestions()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        editText = findViewById(R.id.text_input_question_name);
        editText.setText(currentTest.getTitle());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentTest.setTitle(editable.toString());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("cur_test", currentTest);
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
            saveTest();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addQuestion(View view) {
        // TODO: Miały być pytania z bazy pytań...
        /*prozQuestion q = ???;
        currentTest.getQuestions().add(q);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scrollToPosition(currentTest.getQuestionsSize() - 1);*/
    }

    public void deleteQuestion(View view) {
        if (currentTest.getQuestions().isEmpty())
            return;
        currentTest.getQuestions().remove(currentTest.getQuestionsSize() - 1);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scrollToPosition(currentTest.getQuestionsSize() - 1);
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

    public void saveTest() {
        String title = currentTest.getTitle();
        if (title.length() == 0) {
            Toast.makeText(TestEditActivity.this, "Nie podano nazwy testu!", Toast.LENGTH_LONG).show();
            return;
        }

        // TODO: data startu/końca

        // TODO: wyślij do serwera

        supportFinishAfterTransition();
    }

    @Override
    public void onQuestionClick(CardView view, int position) {
        Intent i = new Intent(TestEditActivity.this, QuestionEditActivity.class);
        i.putExtra("cur_question", currentTest.getQuestions().get(position));
        startActivity(i);
    }
}
