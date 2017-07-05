package pranav_wasnik.wipro.com.recharger;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapterCard extends BaseAdapter {
    private Context context;
    private String []number;
    private String []expiry;
    private String []cvv;

    public ListAdapterCard(Context context,String []number, String []expiry,String []cvv) {
        this.context=context;
        this.number=number;
        this.expiry=expiry;
        this.cvv=cvv;
    }

    @Override
    public int getCount() {
        return number.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class Holder{
        TextView number,expiry,cvv;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if(view==null){
            view=View.inflate(context,R.layout.custom_listitem_card,null);

            holder=new Holder();

            holder.number=(TextView)view.findViewById(R.id.cardNumber1);
            holder.expiry=(TextView)view.findViewById(R.id.cardExpiry2);
            holder.cvv=(TextView)view.findViewById(R.id.cardCvv1);

            view.setTag(holder);
        }else{
            holder=(Holder)view.getTag();
        }

        holder.number.setText(number[i]);
        holder.expiry.setText(expiry[i]);
        holder.cvv.setText(cvv[i]);

        return view;
    }
}
