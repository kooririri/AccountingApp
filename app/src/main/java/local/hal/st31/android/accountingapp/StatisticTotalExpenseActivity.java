package local.hal.st31.android.accountingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class StatisticTotalExpenseActivity extends AppCompatActivity {

    private LinkedList<String> availableMonths = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_common);


        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();
        List<DataEntry> data = new ArrayList<>();

        availableMonths = GlobalUtil.getInstance().databaseHelper.getAvailableMonth();
        for(int i = 0 ; i < availableMonths.size() ; i++){
            data.add(new ValueDataEntry(DateUtil.convertSqlMonthToString(availableMonths.get(i)),GlobalUtil.getInstance().databaseHelper.getTotalExpendThisMonth(availableMonths.get(i))));
        }

        if(data.isEmpty()){
            Toast.makeText(getApplicationContext(),R.string.alert_no_data,Toast.LENGTH_SHORT).show();
        }


        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("今年支出割合図");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("ヶ月");
        cartesian.yAxis(0).title("支出金額");

        anyChartView.setChart(cartesian);

    }
}
