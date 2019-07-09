package local.hal.st31.android.accountingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class ListViewAdapter extends BaseAdapter {

    private LinkedList<RecordBean> records = new LinkedList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    public ListViewAdapter(Context context){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(LinkedList<RecordBean> records){
        this.records = records;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.cell_list_view,null);

            RecordBean records = (RecordBean) getItem(position);

            holder = new ViewHolder(convertView,records);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}
class ViewHolder{
    TextView remarkTV;
    TextView timeTV;
    TextView amountTV;
    ImageView categoryIcon;

    public ViewHolder(View itemView,RecordBean records){
        remarkTV = itemView.findViewById(R.id.textView_remark);
        timeTV = itemView.findViewById(R.id.textView_time);
        amountTV = itemView.findViewById(R.id.textView_amount);
        categoryIcon = itemView.findViewById(R.id.imageView_category);

        //画面部品に値を割り当てる
        remarkTV.setText(records.getRemark());

        if(records.getType() == 1){
            amountTV.setText("- " + records.getAmount());
        }else{
            amountTV.setText("+ " + records.getAmount());
        }

        timeTV.setText(DateUtil.getFormattedTime(records.getTimeStamp()));

        categoryIcon.setImageResource(GlobalUtil.getInstance().getResourceIcon(records.getCategory()));
    }
}
