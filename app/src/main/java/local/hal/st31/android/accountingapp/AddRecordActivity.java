package local.hal.st31.android.accountingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener,CategoryRecyclerAdapter.onCategoryClickListener{

    private EditText editText;
    private TextView amountText;
    private String userInput="";

    private RecyclerView recyclerView;
    private CategoryRecyclerAdapter recyclerAdapter;

    private String category ="一般支出";
    private RecordBean.RecordType type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
    private String remark = category;

    private boolean editTag = false;
    RecordBean record = new RecordBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        //去阴影
//        getSupportActionBar().setElevation(0);

        handleListener();
        handleView();
        handleTypeChange();
        handleBackspace();
        handleDone();
        handleClear();

//      編集するかどうかを判定
        RecordBean record = (RecordBean) getIntent().getSerializableExtra("record");
        if(record != null){
//            Log.d("PXLL", "onCreate: getIntent: " + record.getRemark());
            editTag = true;
            this.record = record;
        }
    }

    private void handleListener(){
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

    }

    private void handleView(){
        amountText = findViewById(R.id.textView_amount);
        editText = findViewById(R.id.editText);
        editText.setText(remark);

        // 设置recycler view
        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new CategoryRecyclerAdapter(getApplicationContext());
        recyclerView.setAdapter(recyclerAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerAdapter.notifyDataSetChanged();
        recyclerAdapter.setOnCategoryClickListener(this);
    }

    private void handleClear(){
        findViewById(R.id.keyboard_clear).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                userInput="";
                updateAmountText();
            }
        });
    }

    private void handleTypeChange(){
        findViewById(R.id.keyboard_type).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(type == RecordBean.RecordType.RECORD_TYPE_EXPENSE){
                    type = RecordBean.RecordType.RECORD_TYPE_INCOME;
                }else{
                    type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
                }
                recyclerAdapter.changeType(type);
                category = recyclerAdapter.getSelected();
            }
        });
    }

    private void handleBackspace(){
        findViewById(R.id.keyboard_backspace).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(userInput.length() > 0){
                    userInput= userInput.substring(0,userInput.length()-1);
                }
                updateAmountText();
            }
        });
    }

    private void handleDone(){
        findViewById(R.id.keyboard_done).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!userInput.equals("")){
                    double amount = Double.valueOf(userInput);

                    record.setCategory(category);
                    record.setRemark(editText.getText().toString());
                    record.setAmount(amount);
                    if(type == RecordBean.RecordType.RECORD_TYPE_EXPENSE){
                        record.setType(1);
                    }else{
                        record.setType(2);
                    }

                    if(editTag == true){
                        GlobalUtil.getInstance().databaseHelper.editRecord(record.getUuid(), record);
                    }
                    else{
                        GlobalUtil.getInstance().databaseHelper.addRecord(record);
                    }

                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"金額を入力してください",Toast.LENGTH_SHORT).show();
                }


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
        if (integerLength > 7){
            Toast.makeText(getApplicationContext(), "多すぎるでしょう", Toast.LENGTH_SHORT).show();
            userInput = amountText.getText().toString();
        }

        updateAmountText();
    }
    private void updateAmountText() {
        if (userInput.equals("")) {
            amountText.setText("0");
        } else {
            amountText.setText(userInput);
        }
    }

    @Override
    public void onClick(String category) {
        this.category = category;
        editText.setText(category);
    }
}
