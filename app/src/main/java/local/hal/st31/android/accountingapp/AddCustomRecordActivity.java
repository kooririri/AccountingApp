package local.hal.st31.android.accountingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddCustomRecordActivity extends AppCompatActivity implements View.OnClickListener{

    private String userInput = "";
    private TextView amountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_record);

        handleView();
        handleListener();
        handleBackspace();

    }

    private void handleView(){
        amountText = findViewById(R.id.custom_add_amount_text);
    }

    private void handleListener(){
        findViewById(R.id.c_keyboard_one).setOnClickListener(this);
        findViewById(R.id.c_keyboard_two).setOnClickListener(this);
        findViewById(R.id.c_keyboard_three).setOnClickListener(this);
        findViewById(R.id.c_keyboard_four).setOnClickListener(this);
        findViewById(R.id.c_keyboard_five).setOnClickListener(this);
        findViewById(R.id.c_keyboard_six).setOnClickListener(this);
        findViewById(R.id.c_keyboard_seven).setOnClickListener(this);
        findViewById(R.id.c_keyboard_eight).setOnClickListener(this);
        findViewById(R.id.c_keyboard_nine).setOnClickListener(this);
        findViewById(R.id.c_keyboard_zero).setOnClickListener(this);
    }

    private void handleBackspace(){
        findViewById(R.id.c_keyboard_backspace).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(userInput.length() > 0){
                    userInput= userInput.substring(0,userInput.length()-1);
                }
                updateAmountText();
            }
        });
    }

    private void updateAmountText(){
        if (userInput.equals("")) {
            amountText.setText("0");
        } else {
            amountText.setText(userInput);
        }
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String input = button.getText().toString();
        userInput += input;
        updateAmountText();
    }
}
