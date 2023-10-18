package com.example.my_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Deklaracje zmiennych
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private int currentIndex = 0;
    private int correctAnswersCount = 0;
    private boolean answered = false;

    // Tablica pytań
    private Question[] questions = new Question[] {
            new Question(R.string.q_activity, true),
            new Question(R.string.q_find_resources, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_version, false)
    };

    // Metoda sprawdzająca poprawność odpowiedzi
    private void checkAnswerCorrectness(boolean userAnswer) {
        if (answered) {
            return; // Jeśli odpowiedź została już udzielona, nie można jej zmienić
        }

        boolean correctAnswer = questions[currentIndex].isCorrect();
        int resultMessageId;
        if (userAnswer == correctAnswer) {
            resultMessageId = R.string.correct_answer;
            correctAnswersCount++; // Zwiększ licznik poprawnych odpowiedzi
        } else {
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        answered = true; // Ustaw flagę, że odpowiedź została udzielona

        if (currentIndex == questions.length - 1) {
            showResult();
        }
    }

    // Metoda ustawiająca kolejne pytanie
    private void setNextQuestion() {
        currentIndex = (currentIndex + 1) % questions.length;
        if (currentIndex == 0) {
            // Jeśli wracamy do pierwszego pytania, zresetuj licznik poprawnych odpowiedzi
            correctAnswersCount = 0;
        }
        questionTextView.setText(questions[currentIndex].getQuestionText());
        answered = false; // Zresetuj flagę odpowiedzi na nowe pytanie
    }

    // Metoda wyświetlająca wynik końcowy
    private void showResult() {
        String resultMessage = getString(R.string.result_message, correctAnswersCount, questions.length);
        Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();
        if (correctAnswersCount == questions.length) {
            nextButton.setEnabled(false); // Wyłącz przycisk "Następne" po zakończeniu quizu
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicjalizacja przycisków i widoków
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

        // Obsługa kliknięć przycisków "Prawda" i "Fałsz"
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });

        // Obsługa kliknięcia przycisku "Następne"
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNextQuestion();
            }
        });

        // Wyświetl pierwsze pytanie przy uruchomieniu
        questionTextView.setText(questions[currentIndex].getQuestionText());
    }
}
