package com.MB.hmt;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.*;

public class HTMTestController {
    @FXML
    private Label wordLabel;
    @FXML
    private Label answerLabel1;
    @FXML
    private Label answerLabel2;
    @FXML
    private Label answerLabel3;
    @FXML
    private Label answerLabel4;

    private int correct = 0, incorrect = 0, total, QAIndex = 0;

    //hashmap where key is the answer and value is the question
    private final HashMap<String, String> HAQ = new HashMap<>();

    private final HashMap<String, String> HQA = new HashMap<>();

    private List<String> QA = HTMViewController.getQA();


    private final ArrayList<String> mutableQA = new ArrayList<>(QA);

    private ArrayList<String> incorrectlyAnsweredQuestions = new ArrayList<>();
    private List<Label> labels = null;


    @FXML
    public void initialize() {
        labels = Arrays.asList(answerLabel1, answerLabel2, answerLabel3, answerLabel4);
        setEventHandler();
        initializeHashmap();
        setWords();
    }

    private void initializeHashmap() {
        System.out.println(QA);
        for (String line : QA) {
            String[] split = line.split("-");
            System.out.println(Arrays.toString(split));
            HAQ.put(split[1], split[0]);
            HQA.put(split[0], split[1]);
        }
    }

    private void setWords() {
        try {
            String[] split = QA.get(QAIndex).split("-");
            String question = split[0];
            String answer = split[1];
            ArrayList<String> words = get3Random();
            words.add(answer);
            Collections.shuffle(words);
            setLabels(words, question);
        } catch (IndexOutOfBoundsException ignored) {
            QA = new ArrayList<>(reconstructLines());
            System.out.println(QA);
            QAIndex = 0;
            if (QA.size() == 0) return;
            setWords();
        }
    }

    private void setLabels(ArrayList<String> words, String question) {
        ListIterator<String> iterator = words.listIterator();
        wordLabel.setText(question);
        for (Label label : labels) {
            label.setText(iterator.next());
        }
    }

    private ArrayList<String> reconstructLines() {
        ArrayList<String> reconstructedLines = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : incorrectlyAnsweredQuestions) {
            stringBuilder.append(str).append("-").append(HQA.get(str));
            reconstructedLines.add(stringBuilder.toString());
            stringBuilder = new StringBuilder();
        }
        incorrectlyAnsweredQuestions = new ArrayList<>();
        return reconstructedLines;
    }


    private ArrayList<String> get3Random() {
        int counter = 0;
        ArrayList<String> words = new ArrayList<>();
        Collections.shuffle(mutableQA);
        for (int i = 0; i < mutableQA.size() && counter < 3; i++) {
            if (!mutableQA.get(i).equalsIgnoreCase(QA.get(QAIndex))) {
                words.add(mutableQA.get(i).split("-")[1]);
                counter++;
            }
        }
        QAIndex++;
        return words;

    }


    private void setEventHandler() {
        EventHandler<MouseEvent> eventHandler = mouseEvent -> {
            Text text = (Text) mouseEvent.getPickResult().getIntersectedNode();
            System.out.printf("User picked %s%n", text.getText());
            if (HAQ.get(text.getText()).equalsIgnoreCase(wordLabel.getText())) {
                System.out.println("Correct!");
                correct++;
            } else {
                System.out.println("Incorrect!");
                incorrectlyAnsweredQuestions.add(wordLabel.getText());
                incorrect++;
            }
            setWords();
        };
        answerLabel1.setOnMouseClicked(eventHandler);
        answerLabel2.setOnMouseClicked(eventHandler);
        answerLabel3.setOnMouseClicked(eventHandler);
        answerLabel4.setOnMouseClicked(eventHandler);
    }


}
