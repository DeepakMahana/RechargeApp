package pranav_wasnik.wipro.com.recharger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class LandlinePaymentS1 extends AppCompatActivity {

    ListView lv;

    public static int[] images={R.drawable.actp,R.drawable.airtelp,R.drawable.mtnlp,R.drawable.bsnlp,R.drawable.mtnlp,R.drawable.reliancep,R.drawable.docomop};
    public static String[] providers={"ACPT","Airtel Landline","MTNL Delhi","BSNL Landline","MTNL MUM","Reliance Communications","DOCOMOLL"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_pay1);

        //Initializing the list view and passing images[] and provider array to adapter for implementing listview,
        lv=(ListView)findViewById(R.id.listviewProvider);
        lv.setAdapter(new ListAdapter(this,providers,images));
    }
}
