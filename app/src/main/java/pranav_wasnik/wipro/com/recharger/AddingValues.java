package pranav_wasnik.wipro.com.recharger;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

public class AddingValues {
    Context context;
    DbHandlersUsers db;

    public AddingValues(Context context,DbHandlersUsers db) {
        this.context=context;
        this.db=db;
    }

    //Adding the values of the user from the values file where we have stored the predetermined values for Users,
    public String addAllUsers(){
        Resources r=context.getResources();
        TypedArray ta=r.obtainTypedArray(R.array.array);

        //ids were taking in order to verify correct row insertion. Since it was all working properly hence removed the debuggin code,
        //which was to check if the id is greater than 0, if yes then the row inserted properly,

        String []s1=ta.getResources().getStringArray(R.array.User1);
        long id1=db.addUser(Long.parseLong(s1[0]),s1[1],s1[2],s1[3],s1[4]);

        s1=ta.getResources().getStringArray(R.array.User2);
        long id2=db.addUser(Long.parseLong(s1[0]),s1[1],s1[2],s1[3],s1[4]);

        s1=ta.getResources().getStringArray(R.array.User3);
        long id3=db.addUser(Long.parseLong(s1[0]),s1[1],s1[2],s1[3],s1[4]);

        s1=ta.getResources().getStringArray(R.array.User4);
        long id4=db.addUser(Long.parseLong(s1[0]),s1[1],s1[2],s1[3],s1[4]);

        s1=ta.getResources().getStringArray(R.array.User5);
        long id5=db.addUser(Long.parseLong(s1[0]),s1[1],s1[2],s1[3],s1[4]);

        ta.recycle();
        return "User Added";
    }

    //Adding the values of the Bank from the values file where we have stored the predetermined values for Bank Details,
    public String addBankDetails(){
        Resources r=context.getResources();
        TypedArray ta=r.obtainTypedArray(R.array.cardArray1);

        String []a=ta.getResources().getStringArray(R.array.card11);
        long id1=db.addBankData(Long.parseLong(a[2]),Double.parseDouble(a[1]),Long.parseLong(a[0]),a[3],Integer.parseInt(a[4]));

        a=ta.getResources().getStringArray(R.array.card22);
        long id2=db.addBankData(Long.parseLong(a[2]),Double.parseDouble(a[1]),Long.parseLong(a[0]),a[3],Integer.parseInt(a[4]));

        a=ta.getResources().getStringArray(R.array.card33);
        long id3=db.addBankData(Long.parseLong(a[2]),Double.parseDouble(a[1]),Long.parseLong(a[0]),a[3],Integer.parseInt(a[4]));

        a=ta.getResources().getStringArray(R.array.card44);
        long id4=db.addBankData(Long.parseLong(a[2]),Double.parseDouble(a[1]),Long.parseLong(a[0]),a[3],Integer.parseInt(a[4]));

        a=ta.getResources().getStringArray(R.array.card55);
        long id5=db.addBankData(Long.parseLong(a[2]),Double.parseDouble(a[1]),Long.parseLong(a[0]),a[3],Integer.parseInt(a[4]));

        ta.recycle();
        return "User Added";
    }

}
