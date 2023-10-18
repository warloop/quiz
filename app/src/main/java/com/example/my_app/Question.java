package com.example.my_app;

public class Question {
    private int questionText;
    private boolean correctAnswer;

    public Question(int questionText, boolean correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public int getQuestionText() {
        return questionText;
    }

    public boolean isCorrect() {
        return correctAnswer;
    }
}