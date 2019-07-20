package local.hal.st31.android.accountingapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AddCustomRecordActivity extends AppCompatActivity implements View.OnClickListener{

    private String userInput = "";
    private TextView amountText;
    private MaterialCalendarView calendarView;
    private String selectedDate;
    private TextView dateLayout;
    private NiceSpinner niceSpinner;
    private List<String> dataSet;
    private RecordBean.RecordType type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
    private String selectedCategory = "一般";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_record);

        handleView();
        handleListener();
        handleBackspace();
        handleClear();
        handleCalendar();
        handleSpinner(type);
        handleCategoryChange();

    }

    private void handleView(){
        amountText = findViewById(R.id.custom_add_amount_text);
        calendarView = findViewById(R.id.calendarView);
        dateLayout = findViewById(R.id.selected_date_layout);
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
                if (userInput.equals("")){
                    amountText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
                }
                updateAmountText();
            }
        });
    }

    private void handleClear(){
        findViewById(R.id.c_keyboard_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput="";
                updateAmountText();
                amountText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
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

    private void handleCalendar(){
        calendarView.setSelectedDate(CalendarDay.today());
        selectedDate = DateUtil.getFormattedDate();
        dateLayout.setText(selectedDate);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectedDate = date.getDate().toString();
                dateLayout.setText(selectedDate);
            }
        });
    }

    private void handleCategoryChange(){
        findViewById(R.id.c_keyboard_category_change).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(type == RecordBean.RecordType.RECORD_TYPE_EXPENSE){
                    type = RecordBean.RecordType.RECORD_TYPE_INCOME;
                }else{
                    type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
                }
                handleSpinner(type);
            }
        });
    }

    private void handleSpinner(RecordBean.RecordType type){
        niceSpinner = findViewById(R.id.custom_add_category_spinner);
        if(type == RecordBean.RecordType.RECORD_TYPE_EXPENSE){
            dataSet =  new LinkedList<>(Arrays.asList(GlobalUtil.costTitle));
        }else{
            dataSet =  new LinkedList<>(Arrays.asList(GlobalUtil.earnTitle));
        }
        niceSpinner.attachDataSource(dataSet);
        niceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String input = button.getText().toString();
        userInput += input;
        int integerLength = 0;
        integerLength = userInput.length();
        if (integerLength >= 3 && integerLength <= 5) {
            GlobalUtil.getInstance().handleTextViewStyle(amountText);
        } else if (integerLength > 5 && integerLength <= 9) {
            GlobalUtil.getInstance().handleTextViewStyle(amountText);
        } else if (integerLength > 7){
            Toast.makeText(getApplicationContext(), "多すぎるでしょう", Toast.LENGTH_SHORT).show();
            userInput = amountText.getText().toString();
        }

        updateAmountText();
    }
}
