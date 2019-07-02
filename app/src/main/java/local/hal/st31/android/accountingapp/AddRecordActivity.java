package local.hal.st31.android.accountingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        findViewById(R.id.keyboard_one).setOnClickListener(this);
        findViewById(R.id.keyboard_two).setOnClickListener(this);
        findViewById(R.id.keyboard_three).setOnClickListener(this);
        findViewById(R.id.keyboard_four).setOnClickListener(this);
        findViewById(R.id.keyboard_five).setOnClickListener(this);
        findViewById(R.id.keyboard_six).setOnClickListener(this);
        findViewById(R.id.keyboard_seven).setOnClickListener(this);
        findViewById(R.id.keyboard_eight).setOnClickListener(this);
        findViewById(R.id.keyboard_nine).setOnClickListener(this);
        findViewById(R.id.keyboard_zero).setOnClickListener(this);
        handleDot();
        handleTypeChange();
        handleBackspace();
        handleDone();
    }

    private void handleDot(){
        findViewById(R.id.keyboard_dot).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("pxl","dot");
            }
        });
    }

    private void handleTypeChange(){
        findViewById(R.id.keyboard_type).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("pxl","type");
            }
        });
    }

    private void handleBackspace(){
        findViewById(R.id.keyboard_backspace).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("pxl","back");
            }
        });
    }

    private void handleDone(){
        findViewById(R.id.keyboard_done).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("pxl","done");
            }
        });
    }
    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String input = button.getText().toString();
        Log.d("pxl",input);
    }
}
