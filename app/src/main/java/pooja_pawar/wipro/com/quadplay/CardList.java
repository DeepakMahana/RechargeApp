//
//package pooja_pawar.wipro.com.quadplay;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.ListView;
//
//import java.util.ArrayList;
//
//public class CardList extends AppCompatActivity {
//    private static CustomAdapter adapter;
//    ListView lv;
//    CustomAdapter card;
//    ArrayList<CardBean> myList;
//    DbHandlersUsers db;
//    long mobile;
//    SessionManager sm;
//
////    public static int[] images={R.drawable.actp,R.drawable.airtelp,R.drawable.mtnlp,R.drawable.bsnlp,R.drawable.mtnlp,R.drawable.reliancep,R.drawable.docomop};
////    public static String[] providers={"ACPT","Airtel Landline","MTNL Delhi","BSNL Landline","MTNL MUM","Reliance Communications","DOCOMOLL"};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.existing_card_list);
//        //Initializing the list view and passing images[] and provider array to adapter for implementing listview,
//        sm = new SessionManager(this);
//        mobile = sm.getMobile();
//        myList = new ArrayList<CardBean>();
//        db = new DbHandlersUsers(CardList.this);
//        myList = db.getUserCards(mobile);
//
//        lv = (ListView)findViewById(R.id.list);
//        adapter = new CustomAdapter(myList,getApplicationContext());
//        lv.setAdapter(adapter);
//    }
//}
//
