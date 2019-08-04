package local.hal.st31.android.accountingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import java.util.LinkedList;
import java.util.List;

public class StatisticCategoryIncomeActivity extends AppCompatActivity {

    LinkedList<String > categoryOfThisMonth = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_common);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(StatisticCategoryIncomeActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });
        List<DataEntry> data = new LinkedList<>();
        String thisYear = GlobalUtil.getInstance().databaseHelper.getThisYear();
        String thisMonth = GlobalUtil.getInstance().databaseHelper.getThisMonth();
        categoryOfThisMonth = GlobalUtil.getInstance().databaseHelper.getInputtedCategories(thisYear,thisMonth,"2");

        for(int i = 0 ; i < categoryOfThisMonth.size() ; i++){
            data.add(new ValueDataEntry(categoryOfThisMonth.get(i),GlobalUtil.getInstance().databaseHelper.getTotalIncomeOfCategoryThisMonth(thisYear,thisMonth,categoryOfThisMonth.get(i))));
        }
        if (data.isEmpty()){
            Toast.makeText(getApplicationContext(),R.string.alert_no_data,Toast.LENGTH_SHORT).show();
            finish();
        }

        pie.data(data);

        pie.title("カテゴリごとの収入割合" );

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text(thisYear + "年" + thisMonth + "月")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);


    }
}
