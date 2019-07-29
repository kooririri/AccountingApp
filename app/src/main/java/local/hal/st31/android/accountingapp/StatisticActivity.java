package local.hal.st31.android.accountingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

//        findViewById(R.id.general_statistic).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),StatisticGeneralActivity.class);
//                startActivity(intent);
//            }
//        });

        findViewById(R.id.statistic_total_expense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatisticTotalExpenseActivity.class);
                startActivity(intent);
            }
        });
//
//        findViewById(R.id.statistic_total_income).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),StatisticTotalIncomeActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        findViewById(R.id.statistic_category_expense).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),StatisticCategoryExpenseActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        findViewById(R.id.statistic_category_income).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),StatisticCategoryIncomeActivity.class);
//                startActivity(intent);
//            }
//        });

        findViewById(R.id.general_statistic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"more",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
