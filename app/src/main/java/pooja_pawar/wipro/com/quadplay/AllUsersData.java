package pooja_pawar.wipro.com.quadplay;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AllUsersData extends AppCompatActivity {
    ListView lv;
    DbHandlersUsers db;
    Cursor cursor, cursorImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users_data);

        lv = (ListView)findViewById(R.id.listviewUsers);
        db = new DbHandlersUsers(this);
        cursor = db.getAllColumnsUsers();
       // cursorImg = db.getAllUserImages();

        //Comments in AllBankData class,
        String[] from = db.getColumnUsers();
        int[] to={R.id.listNumber,R.id.listName,R.id.listEmail,R.id.listPassword,R.id.listCity};

        SimpleCursorAdapter adapter=new SimpleCursorAdapter(this,R.layout.user_listrow,cursor,from,to);
        lv.setAdapter(adapter);
    }
}
