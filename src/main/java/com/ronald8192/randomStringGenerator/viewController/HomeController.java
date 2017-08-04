package com.ronald8192.randomStringGenerator.viewController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.text.ParsePosition;
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

    private EventHandler<KeyEvent> positiveNumberFieldEventHandler = new EventHandler<KeyEvent>() {
        public void handle(KeyEvent t) {
            char ar[] = t.getCharacter().toCharArray();
            if (ar.length != 0) {
                char ch = ar[t.getCharacter().toCharArray().length - 1];
                if (!(ch >= '0' && ch <= '9')) {
                    //not a number
                    t.consume();
                }
            }
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUserData();
        setUpNumberField();
        registerListener();
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
        final TextFormatter<Number> formatter7 = new TextFormatter<>(converter, 1, converter.getFilter());
        final TextFormatter<Number> formatter8 = new TextFormatter<>(converter, 1, converter.getFilter());
        final TextFormatter<Number> formatter9 = new TextFormatter<>(converter, 1, converter.getFilter());

        txtMinOccurNumber.setTextFormatter(formatter1);
        txtMinOccurLowerCase.setTextFormatter(formatter2);
        txtMinOccurUpperCase.setTextFormatter(formatter3);
        txtMinOccurSymbol1.setTextFormatter(formatter4);
        txtMinOccurSymbol2.setTextFormatter(formatter5);
        txtMinOccurSymbol3.setTextFormatter(formatter6);

        txtFixLength.setTextFormatter(formatter7);
        txtVariableLengthTo.setTextFormatter(formatter8);
        txtVariableLengthFrom.setTextFormatter(formatter9);


        formatter1.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter2.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter3.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter4.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter5.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter6.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter7.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter8.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());
        formatter9.valueProperty().addListener((observable, oldValue, newValue) -> updatePossibleCharacters());


//        txtMinOccurNumber.addEventFilter(KeyEvent.KEY_TYPED, positiveNumberFieldEventHandler);
//        txtMinOccurLowerCase. addEventFilter(KeyEvent.KEY_TYPED, positiveNumberFieldEventHandler);
//        txtMinOccurUpperCase.  addEventFilter(KeyEvent.KEY_TYPED, positiveNumberFieldEventHandler);
//        txtMinOccurSymbol1.    addEventFilter(KeyEvent.KEY_TYPED, positiveNumberFieldEventHandler);
//        txtMinOccurSymbol2.   addEventFilter(KeyEvent.KEY_TYPED, positiveNumberFieldEventHandler);
//        txtMinOccurSymbol3.   addEventFilter(KeyEvent.KEY_TYPED, positiveNumberFieldEventHandler);
//
//        txtFixLength.         addEventFilter(KeyEvent.KEY_TYPED, positiveNumberFieldEventHandler);
//        txtVariableLengthTo.  addEventFilter(KeyEvent.KEY_TYPED, positiveNumberFieldEventHandler);
//        txtVariableLengthFrom.addEventFilter(KeyEvent.KEY_TYPED, positiveNumberFieldEventHandler);
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

                    txtVariableLengthFrom.setDisable(fixLength);
                    txtVariableLengthTo.setDisable(fixLength);
                }
            }
        });

        grpRandomMethod.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (grpRandomMethod.getSelectedToggle() != null) {
                    boolean randomByGroup = grpRandomMethod.getSelectedToggle().getUserData().toString().equals("radioRandomByGroup");

                }
            }
        });
    }

    private void updatePossibleCharacters(){
        System.out.println("update char");
    }

}
