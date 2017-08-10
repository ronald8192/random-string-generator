package com.ronald8192.randomStringGenerator.viewController;

import com.ronald8192.advancedRandom.Range;
import com.ronald8192.advancedRandom.SequenceGenerator;
import com.ronald8192.randomStringGenerator.PreDefinedGroup;
import com.ronald8192.randomStringGenerator.SequenceGeneratorBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

/**
 * Created by ronald8192 on 04/08/2017.
 */
public class HomeController implements Initializable {

    @FXML
    ToggleGroup grpPossibleChar;
    @FXML
    RadioButton radioDefinedGroup;
    @FXML
    RadioButton radioCustom;

    @FXML
    CheckBox chkNumber;
    @FXML
    CheckBox chkLowerCase;
    @FXML
    CheckBox chkUpperCase;
    @FXML
    CheckBox chkSymbol1;
    @FXML
    CheckBox chkSymbol2;
    @FXML
    CheckBox chkSymbol3;

    @FXML
    TextField txtMinOccurNumber;
    @FXML
    TextField txtMinOccurLowerCase;
    @FXML
    TextField txtMinOccurUpperCase;
    @FXML
    TextField txtMinOccurSymbol1;
    @FXML
    TextField txtMinOccurSymbol2;
    @FXML
    TextField txtMinOccurSymbol3;

    @FXML
    TextArea txtAllPossibleCharacter;

    @FXML
    ToggleGroup grpLengthOption;
    @FXML
    RadioButton radioFixLength;
    @FXML
    RadioButton radioVariableLength;
    @FXML
    TextField txtFixLength;
    @FXML
    TextField txtVariableLengthFrom;
    @FXML
    TextField txtVariableLengthTo;

    @FXML
    ToggleGroup grpRandomMethod;
    @FXML
    RadioButton radioRandomByGroup;
    @FXML
    RadioButton radioRandomByCharacter;
    @FXML
    TextArea txtOutput;

    @FXML
    Button btnReset;
    @FXML
    Button btnGenerate;
    @FXML
    Button btnClear;
    @FXML
    Button btnCopy;

    EventHandler chkNumberOnAction = (EventHandler<ActionEvent>) event -> {
        System.out.println("chkNumberOnAction");
    };

    EventHandler btnResetOnAction = (EventHandler<ActionEvent>) event -> {
        radioDefinedGroup.setSelected(true);
        chkNumber.setSelected(true);
        chkLowerCase.setSelected(true);
        chkUpperCase.setSelected(true);
        chkSymbol1.setSelected(false);
        chkSymbol2.setSelected(false);
        chkSymbol3.setSelected(false);

        txtMinOccurNumber.setText("2");
        txtMinOccurLowerCase.setText("2");
        txtMinOccurUpperCase.setText("2");
        txtMinOccurSymbol1.setText("2");
        txtMinOccurSymbol2.setText("1");
        txtMinOccurSymbol3.setText("1");

        txtAllPossibleCharacter.setText(""); //TODO: all possible char method
        radioFixLength.setSelected(true);
        txtFixLength.setText("6"); //TODO:min length method
        txtVariableLengthFrom.setText("6"); //TODO:min length method

        txtVariableLengthTo.setText("12");

        radioRandomByGroup.setSelected(true);
    };

    EventHandler btnClearOnAction = (EventHandler<ActionEvent>) event -> {
        txtOutput.clear();
    };

    EventHandler btnCopyOnAction = (EventHandler<ActionEvent>) event -> {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(txtOutput.getText());
        clipboard.setContent(content);
    };

    SequenceGeneratorBuilder builder = new SequenceGeneratorBuilder();
    SequenceGenerator generator;
    EventHandler btnGenerateOnAction = (EventHandler<ActionEvent>) event -> {
        boolean preDefinedGroup = grpPossibleChar.getSelectedToggle().getUserData().toString().equals("radioDefinedGroup");
        char[] customChar = {' '};

        builder = new SequenceGeneratorBuilder();
        if(preDefinedGroup){
            if (chkNumber.isSelected()) builder.addPreDefinedGroup(PreDefinedGroup.NUMBER, Integer.parseInt(txtMinOccurNumber.getText()));
            if (chkLowerCase.isSelected()) builder.addPreDefinedGroup(PreDefinedGroup.LOWERCASE, Integer.parseInt(txtMinOccurLowerCase.getText()));
            if (chkUpperCase.isSelected()) builder.addPreDefinedGroup(PreDefinedGroup.UPPERCASE, Integer.parseInt(txtMinOccurUpperCase.getText()));
            if (chkSymbol1.isSelected()) builder.addPreDefinedGroup(PreDefinedGroup.SYMBOL1, Integer.parseInt(txtMinOccurSymbol1.getText()));
            if (chkSymbol2.isSelected()) builder.addPreDefinedGroup(PreDefinedGroup.SYMBOL2, Integer.parseInt(txtMinOccurSymbol2.getText()));
            if (chkSymbol3.isSelected()) builder.addPreDefinedGroup(PreDefinedGroup.SYMBOL3, Integer.parseInt(txtMinOccurSymbol3.getText()));
        }else{
            customChar = txtAllPossibleCharacter.getText().toCharArray();
            builder.setCustomRange(new Range(0, customChar.length - 1, 0));
        }

        boolean randomByGroup = grpRandomMethod.getSelectedToggle().getUserData().toString().equals("radioRandomByGroup");
        if (randomByGroup) {
            builder.setRandomMethod(SequenceGenerator.Mode.RANDOM_BY_RANGE_THEN_NUMBER);
        } else {
            builder.setRandomMethod(SequenceGenerator.Mode.RANDOM_BY_POSSIBLE_NUMBER);
        }

        generator = builder.build();
        boolean fixLength = grpLengthOption.getSelectedToggle().getUserData().toString().equals("radioFixLength");
        int len = generator.minLength();
        if(generator.minLength() > Integer.parseInt(txtVariableLengthFrom.getText())){
            txtVariableLengthFrom.setText(""+generator.minLength());
        }
        if (fixLength) {
            if (generator.minLength() > Integer.parseInt(txtFixLength.getText())) {
                txtFixLength.setText(generator.minLength() + "");
            }else {
                len = Integer.parseInt(txtFixLength.getText());
            }
        } else {
            if (generator.minLength() > Integer.parseInt(txtVariableLengthTo.getText())) {
                txtVariableLengthTo.setText(generator.minLength() + "");
            } else {
                len = Integer.parseInt(txtVariableLengthTo.getText());
            }
        }
        try {
            List<Integer> generated = generator.generateList(len, fixLength);
            List<String> randomStringList = new ArrayList<>();
            if (preDefinedGroup) {
                generated.forEach(num -> randomStringList.add("" + PreDefinedGroup.getCharById(num)));
            } else {
                final char[] finalCustomChar = customChar;
                generated.forEach(num -> randomStringList.add("" + finalCustomChar[num]));
            }
            txtOutput.setText(randomStringList.stream().reduce(((a, b) -> a + b)).get());
        } catch (Exception e) {
            e.printStackTrace();
        }



    };


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUserData();
        setUpNumberField();
        registerListener();
        btnReset.getOnAction().handle(new ActionEvent());
    }

    private void initializeUserData() {
        radioDefinedGroup.setUserData("radioDefinedGroup");
        radioCustom.setUserData("radioCustom");

        radioFixLength.setUserData("radioFixLength");
        radioVariableLength.setUserData("radioVariableLength");

        radioRandomByGroup.setUserData("radioRandomByGroup");
        radioRandomByCharacter.setUserData("radioRandomByCharacter");
    }

    private void setUpNumberField() {

        NumberStringFilteredConverter converter = new NumberStringFilteredConverter();
        final TextFormatter<Number> formatter1 = new TextFormatter<>(converter, 1, converter.getFilter());
        final TextFormatter<Number> formatter2 = new TextFormatter<>(converter, 1, converter.getFilter());
        final TextFormatter<Number> formatter3 = new TextFormatter<>(converter, 1, converter.getFilter());
        final TextFormatter<Number> formatter4 = new TextFormatter<>(converter, 1, converter.getFilter());
        final TextFormatter<Number> formatter5 = new TextFormatter<>(converter, 1, converter.getFilter());
        final TextFormatter<Number> formatter6 = new TextFormatter<>(converter, 1, converter.getFilter());
        final TextFormatter<Number> formatter7 = new TextFormatter<>(converter, 3, converter.getFilter());
        final TextFormatter<Number> formatter8 = new TextFormatter<>(converter, 3, converter.getFilter());
        final TextFormatter<Number> formatter9 = new TextFormatter<>(converter, 10, converter.getFilter());

        txtMinOccurNumber.setTextFormatter(formatter1);
        txtMinOccurLowerCase.setTextFormatter(formatter2);
        txtMinOccurUpperCase.setTextFormatter(formatter3);
        txtMinOccurSymbol1.setTextFormatter(formatter4);
        txtMinOccurSymbol2.setTextFormatter(formatter5);
        txtMinOccurSymbol3.setTextFormatter(formatter6);

        txtFixLength.setTextFormatter(formatter7);
        txtVariableLengthFrom.setTextFormatter(formatter8);
        txtVariableLengthTo.setTextFormatter(formatter9);


        formatter1.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter2.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter3.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter4.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter5.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter6.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter7.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter8.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter9.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
    }



    class NumberStringFilteredConverter extends NumberStringConverter {
        // Note, if needed you can add in appropriate constructors
        // here to set locale, pattern matching or an explicit
        // type of NumberFormat.
        //
        // For more information on format control, see
        //    the NumberStringConverter constructors
        //    DecimalFormat class
        //    NumberFormat static methods for examples.
        // This solution can instead extend other NumberStringConverters if needed
        //    e.g. CurrencyStringConverter or PercentageStringConverter.

        public UnaryOperator<TextFormatter.Change> getFilter() {
            return change -> {
                String newText = change.getControlNewText();
                if (newText.isEmpty()) {
                    return change;
                }

                ParsePosition parsePosition = new ParsePosition(0);
                Object object = getNumberFormat().parse(newText, parsePosition);
                if (object == null || parsePosition.getIndex() < newText.length()) {
                    return null;
                } else {
                    return change;
                }
            };
        }
    }

    private void registerListener() {
        grpPossibleChar.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (grpPossibleChar.getSelectedToggle() != null) {
                    boolean preDefinedGroup = grpPossibleChar.getSelectedToggle().getUserData().toString().equals("radioDefinedGroup");

                    chkNumber.setDisable(!preDefinedGroup);
                    chkLowerCase.setDisable(!preDefinedGroup);
                    chkUpperCase.setDisable(!preDefinedGroup);
                    chkSymbol1.setDisable(!preDefinedGroup);
                    chkSymbol2.setDisable(!preDefinedGroup);
                    chkSymbol3.setDisable(!preDefinedGroup);

                    txtMinOccurNumber.setDisable(!preDefinedGroup);
                    txtMinOccurLowerCase.setDisable(!preDefinedGroup);
                    txtMinOccurUpperCase.setDisable(!preDefinedGroup);
                    txtMinOccurSymbol1.setDisable(!preDefinedGroup);
                    txtMinOccurSymbol2.setDisable(!preDefinedGroup);
                    txtMinOccurSymbol3.setDisable(!preDefinedGroup);

                    txtAllPossibleCharacter.setDisable(preDefinedGroup);

                }

            }
        });

        grpLengthOption.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (grpLengthOption.getSelectedToggle() != null) {
                    boolean fixLength = grpLengthOption.getSelectedToggle().getUserData().toString().equals("radioFixLength");

                    txtFixLength.setDisable(!fixLength);

//                    txtVariableLengthFrom.setDisable(fixLength);
                    txtVariableLengthTo.setDisable(fixLength);
                }
            }
        });

//        grpRandomMethod.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
//            @Override
//            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
//                if (grpRandomMethod.getSelectedToggle() != null) {
//                    boolean randomByGroup = grpRandomMethod.getSelectedToggle().getUserData().toString().equals("radioRandomByGroup");
//
//                }
//            }
//        });

        chkNumber.setOnAction(chkNumberOnAction);
        btnReset.setOnAction(btnResetOnAction);
        btnGenerate.setOnAction(btnGenerateOnAction);
        btnClear.setOnAction(btnClearOnAction);
        btnCopy.setOnAction(btnCopyOnAction);
    }

    private void updatePossibleCharacters(){
        //TODO
        System.out.println("update char");
    }


}
