package local.hal.st31.android.accountingapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private MainViewPagerAdapter pagerAdapter;
    private TickerView expenseAmountText;
    private TickerView incomeAmountText;
    private TextView dateText;
    private int currentPagerPosition = 0;
    private final static int BUTTON_CLICK = 1;
    private final static int BUTTON_LONG_CLICK = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalUtil.getInstance().setContext(getApplicationContext());
        GlobalUtil.getInstance().mainActivity = this;
        //消除阴影
        getSupportActionBar().setElevation(0);

        expenseAmountText = (TickerView)findViewById(R.id.expense_amount_text);
        expenseAmountText.setCharacterLists(TickerUtils.provideNumberList());
        incomeAmountText = (TickerView)findViewById(R.id.income_amount_text);
        incomeAmountText.setCharacterLists(TickerUtils.provideNumberList());
        dateText=(TextView) findViewById(R.id.date_text);

        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        //本日の画面を最初に表示
        viewPager.setCurrentItem(pagerAdapter.getLastIndex());

        handleFab();
        updateHeader();
    }

    private void handleFab(){
        //+をクリックすると
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddRecordActivity.class);
                startActivityForResult(intent,BUTTON_CLICK);
            }
        });

        //+を長押しすると
        findViewById(R.id.fab).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddCustomRecordActivity.class);
                startActivityForResult(intent,BUTTON_LONG_CLICK);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pagerAdapter.reload();
        updateHeader();
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        currentPagerPosition = i;
        updateHeader();
    }

    public void updateHeader(){
        String expenseAmount = String.valueOf(pagerAdapter.getTotalCost(currentPagerPosition));
        expenseAmountText.setText(expenseAmount);
        String incomeAmount = String.valueOf(pagerAdapter.getTotalIncome(currentPagerPosition));
        incomeAmountText.setText(incomeAmount);
        String date = pagerAdapter.getDateStr(currentPagerPosition);
        dateText.setText(DateUtil.getWeekDay(date));
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
