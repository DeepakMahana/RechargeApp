
package pooja_pawar.wipro.com.quadplay;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AllBankData extends AppCompatActivity {

    ListView lv;
    DbHandlersUsers db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bank_data);

        lv = (ListView)findViewById(R.id.listviewBank);
        db = new DbHandlersUsers(this);
        cursor = db.getAllColumnsBank();

        //Code for displaying the values from sqlite database in the form of a listview,
        //Here SimpleCursorAdapter is used,
        //The order in the to[] array will depend upon the order in which we want to display the information in listview's list item,

        String[] from = db.getColumnBank();
        int[] to = {R.id.listMobUserID,R.id.listCardNo,R.id.listCardUserName,R.id.listBalance,R.id.listExpiry};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.bank_listrow,cursor,from,to);
        lv.setAdapter(adapter);
    }
}

