package pooja_pawar.wipro.com.quadplay;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TransactionHistory extends AppCompatActivity {

    ListView lv;
    DbHandlersUsers db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_hist);

        lv = (ListView) findViewById(R.id.listviewTransactions);
        db = new DbHandlersUsers(this);
        cursor = db.getHistory();

        //Comment for the same is mentioned in AllBankData class,
        String[] from = db.getColumns();
        int[] to = {R.id.mobileNumber, R.id.mobileTag, R.id.providerName, R.id.rechargeAmount, R.id.dateOfPayment, R.id.timeOfPayment};

        if(cursor.getCount()==0){
            Toast.makeText(this,"No transactions Available",Toast.LENGTH_SHORT).show();
        }else{
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.custom_list_item_history, cursor, from, to);
            lv.setAdapter(adapter);
        }

    }
}
