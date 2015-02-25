package edu.gatech.calculator;

import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    //has started doing calculations
    private boolean state = false;
    //0 add ; 1 minus; 2 multiply
    private Integer operator = null;
    //when operator has been executed, save initial value
    private Integer value = null;
    private Integer result = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //router

    public void handleClick(View view) {

        //figure out which button was pressed
        switch(view.getId()) {
            case R.id.numView0:
                handleNumClick(0);
                break;
            case R.id.numView1:
                handleNumClick(1);
                break;
            case R.id.numView2:
                handleNumClick(2);
                break;
            case R.id.numView3:
                handleNumClick(3);
                break;
            case R.id.numView4:
                handleNumClick(4);
                break;
            case R.id.numView5:
                handleNumClick(5);
                break;
            case R.id.numView6:
                handleNumClick(6);
                break;
            case R.id.numView7:
                handleNumClick(7);
                break;
            case R.id.numView8:
                handleNumClick(8);
                break;
            case R.id.numView9:
                handleNumClick(9);
                break;
            case R.id.opViewClear:
                handleOpClearClick();
                break;
            case R.id.opViewEquals:
                handleOpEqualsClick();
                break;
            case R.id.opViewMinus:
                handleOpMinusClick();
                break;
            case R.id.opViewPlus:
                handleOpPlusClick();
                break;
            case R.id.opViewMultiply:
                handleOpMultiplyClick();
                break;
        }
    }

    //state related methods
    public void reset(String message) {
        state = false;
        operator = null;
        value = null;
        result = null;
        if (message == null) {
            setText("0");
        } else {
            setText(message);
        }
    }

    public boolean checkIfOperatorOnScreen() {
        if (getCurrentValue().contains("+") || getCurrentValue().contains("-") || getCurrentValue().contains("*")){
            return true;
        }
        return false;
    }

    public boolean checkIfErrorOnScreen() {
        if (getCurrentValue().equals("Error")) {
            return true;
        } else {
            return false;
        }
    }

    //routed methods
    public void handleNumClick(Integer number) {
        if (result != null){
            reset(null);
        }

        if (checkIfOperatorOnScreen()) {
            setText(number.toString());
            return;
        }

        if (!state) {
            setText(number.toString());
            state = true;
        } else {
            setText(getCurrentValue() + number.toString());
        }
    }

    public void handleOpEqualsClick() {
        calculate();
        return;
    }

    public void handleOpPlusClick() {
        //check for error state
        if (checkIfOperatorOnScreen() || state == false || checkIfErrorOnScreen()) {
            executeError();
            return;
        }
        if (operator != null) {
            calculate();
            operator = 0;
            setText(result.toString());
            value = result;
            result = null;
        }

        operator = 0;
        value = Integer.valueOf(getCurrentValue());
        setText(getCurrentValue() + " +");
    }

    public void handleOpMinusClick() {
        //check for error state
        if (checkIfOperatorOnScreen() || state == false || checkIfErrorOnScreen()) {
            executeError();
            return;
        }
        if (operator != null) {
            calculate();
            operator = 1;
            setText(result.toString());
            value = result;
            result = null;
        }

        operator = 1;
        value = Integer.valueOf(getCurrentValue());
        setText(getCurrentValue() + " -");
    }

    public void handleOpMultiplyClick() {
        //check for error state
        if (checkIfOperatorOnScreen() || state == false || checkIfErrorOnScreen()) {
            executeError();
            return;
        }
        if (operator != null) {
            calculate();
            operator = 2;
            setText(result.toString());
            value = result;
            result = null;
        }

        operator = 2;
        value = Integer.valueOf(getCurrentValue());
        setText(getCurrentValue() + " *");
    }

    public void handleOpClearClick() {
        reset(null);
    }

    public void calculate() {
        if (state == false || operator == null || value == null || result != null) {
            executeError();
            return;
        }
        if (checkIfOperatorOnScreen()) {
            executeError();
            return;
        }

        Integer getValue = Integer.valueOf(getCurrentValue());

        switch(operator) {
            case 0:
                result = value + getValue;
                break;
            case 1:
                result = value - getValue;
                break;
            case 2:
                result = value * getValue;
                break;
        }
        setText(result.toString());
    }

    //helper methods
    public void executeError() {
        reset("Error");
    }

    public String getCurrentValue() {
        TextView textView = (TextView) findViewById(R.id.textView);
        return textView.getText().toString();
    }

    public void setText(String text) {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(text);
    }

    public void logState(String location) {
        Log.d("State", String.valueOf(state));
        Log.d("Operator", String.valueOf(operator));
        Log.d("Value", String.valueOf(value));
        Log.d("Result", String.valueOf(result));
        Log.d("current", getCurrentValue());
        Log.d("location", location);
    }
}
