package com.MB.hmt;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.*;

public class HTMTestController {

    @FXML
    private Label correctLabel;
    @FXML
    private Label incorrectLabel;
    @FXML
    private Label totalCountLabel;
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

    private int correct = 0, incorrect = 0, total;

    //hashmap where key is the answer and value is the question
    private final HashMap<String, String> HAQ = new HashMap<>();

    ////hashmap where key is the question and value is the answer
    private final HashMap<String, String> HQA = new HashMap<>();
    private final List<String> QA = HTMViewController.getQA();


    private final ArrayList<String> mutableQA = new ArrayList<>(QA);
    private List<Label> labels = null;


    @FXML
    public void initialize() {
        correctLabel.setText("Correct 0");
        incorrectLabel.setText("Incorrect 0");
        totalCountLabel.setText("Questions left %d".formatted(mutableQA.size()));
        labels = Arrays.asList(answerLabel1, answerLabel2, answerLabel3, answerLabel4);
        setup();
        updateFrame();
        setEventHandler();

    }

    private void updateFrame() {
        Collections.shuffle(mutableQA);
        try {
            wordLabel.setText(mutableQA.get(0).split("-")[0]);
            setChoices(mutableQA.get(0).split("-")[1]);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("NICE");
        }
    }

    //works
    private ArrayList<String> generateThreeUnique(String mainAnswer) {
        Collections.shuffle(QA);
        ArrayList<String> words = new ArrayList<>();
        int added = 0;
        words.add(mainAnswer);
        for (int i = 0; i < QA.size() || added <= 3; i++) {
            if (!QA.get(i).split("-")[1].equalsIgnoreCase(mainAnswer)) {
                words.add(QA.get(i).split("-")[1]);
                added++;
            }
        }

        return words;
    }

    private void setChoices(String mainAnswer) {
        ArrayList<String> words = generateThreeUnique(mainAnswer);
        //Collections.shuffle(words);
        int index = 0;
        for (Label label : labels) {
            label.setText(words.get(index++));
        }

    }

    private void setup() {
        for (String str : QA) {
            String[] split = str.split("-");
            HAQ.put(split[1], split[0]);
            HQA.put(split[0], split[1]);
        }
        total = mutableQA.size();
        Collections.shuffle(mutableQA);
    }

    private void setEventHandler() {
        EventHandler<MouseEvent> eventHandler = mouseEvent -> {
            Text text = (Text) mouseEvent.getPickResult().getIntersectedNode();
            if (wordLabel.getText().equalsIgnoreCase(HAQ.get(text.getText()))) {
                updateCorrect(text);
            }
            if (!wordLabel.getText().equalsIgnoreCase(HAQ.get(text.getText()))){
                removeWordFromMutable(text.getText());
                updateIncorrect();
            }
            if (incorrect + correct == total) {
                resetCounter();
            }
            updateFrame();

        };
        answerLabel1.setOnMouseClicked(eventHandler);
        answerLabel2.setOnMouseClicked(eventHandler);
        answerLabel3.setOnMouseClicked(eventHandler);
        answerLabel4.setOnMouseClicked(eventHandler);
    }

    private void removeWordFromMutable(String answer) {
        for (int i = 0; i < mutableQA.size(); i++) {
            try {
                if (mutableQA.get(i).split("-")[1].equalsIgnoreCase(answer)) {
                    mutableQA.remove(i);
                    break;
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                if (mutableQA.get(i).equalsIgnoreCase(answer)) {
                    mutableQA.remove(i);
                    break;
                }
            }
        }
    }

    private void updateCorrect(Text text) {
        correctLabel.setText("Correct %d".formatted(++correct));
        totalCountLabel.setText("Questions left %d".formatted(mutableQA.size()));
        removeWordFromMutable(text.getText());

    }

    private void updateIncorrect() {
        incorrectLabel.setText("Incorrect %d".formatted(++incorrect));
        totalCountLabel.setText("Questions left %d".formatted(mutableQA.size()));

    }

    private void resetCounter() {
        correctLabel.setText("Correct 0");
        incorrectLabel.setText("Incorrect 0");
        totalCountLabel.setText("Questions left %d".formatted(mutableQA.size()));
        correct = 0;
        incorrect = 0;
        total = mutableQA.size();
    }


}
