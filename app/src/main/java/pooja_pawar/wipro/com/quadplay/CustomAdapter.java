//package pooja_pawar.wipro.com.quadplay;
//
//import android.content.Context;
//import android.support.design.widget.Snackbar;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class CustomAdapter extends ArrayAdapter<CardBean> implements View.OnClickListener{
//
//    private ArrayList<CardBean> dataSet;
//    Context mContext;
//
//    // View lookup cache
//    private static class ViewHolder {
//        TextView txtcno;
//        TextView txtbank;
//        TextView txtbalance;
//    }
//
//    public CustomAdapter(ArrayList<CardBean> data, Context context) {
//        super(context, R.layout.custom_card_item, data);
//        this.dataSet = data;
//        this.mContext=context;
//
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        int position=(Integer) v.getTag();
//        Object object= getItem(position);
//        CardBean dataModel=(CardBean) object;
//
//        switch (v.getId())
//        {
//            case R.id.cardNo:
//                Snackbar.make(v, "Release date " + dataModel.getCardNo(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
//    }
//
//    private int lastPosition = -1;
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        CardBean dataModel = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        ViewHolder viewHolder; // view lookup cache stored in tag
//
//        final View result;
//
//        if (convertView == null) {
//
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.custom_card_item, parent, false);
//            viewHolder.txtcno = (TextView) convertView.findViewById(R.id.cardNo);
//            viewHolder.txtbank = (TextView) convertView.findViewById(R.id.bankName);
//            viewHolder.txtbalance = (TextView) convertView.findViewById(R.id.bankBalance);
//
//            result=convertView;
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//            result=convertView;
//        }
//
//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;
//
//        String cno = String.valueOf(dataModel.getCardNo());
//
//        viewHolder.txtcno.setText(cno);
//        viewHolder.txtbank.setText(dataModel.getBankName());
//        viewHolder.txtbalance.setText(dataModel.getBalance());
//        viewHolder.txtcno.setOnClickListener(this);
//        viewHolder.txtcno.setTag(position);
//        // Return the completed view to render on screen
//        return convertView;
//    }
//}