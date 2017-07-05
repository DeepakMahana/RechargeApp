package pranav_wasnik.wipro.com.recharger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

//Most important class of all!!!
public class DbHandlersUsers {

    DatabaseHelper helper;
    Context context;

    public DbHandlersUsers(Context context) {
        helper = new DatabaseHelper(context);
        this.context = context;
    }

    //USER TABLE
    //Adding User to the USERS table,
    long addUser(long mobile, String email, String password, String name, String city) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(helper.MOBILE, mobile);
        values.put(helper.EMAIL, email);
        values.put(helper.PASSWORD, password);
        values.put(helper.NAME, name);
        values.put(helper.CITY, city);

        long id = 0;
        try {
            id = db.insert(helper.TABLE_NAME, null, values);
        } catch (Exception e) {
            System.out.println(""+e);
        }
        return id;
    }

    //USER TABLE
    //Getting data from the Users table
    public String[] getData(long mobile) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {helper.MOBILE,helper.EMAIL, helper.NAME};
        Cursor cursor = db.query(helper.TABLE_NAME, columns, helper.MOBILE+"='"+mobile+"'", null, null, null, null);

        int index1=cursor.getColumnIndex(helper.MOBILE);
        int index2 = cursor.getColumnIndex(helper.EMAIL);
        int index3 = cursor.getColumnIndex(helper.NAME);


        String []values=new String[2];
        while (cursor.moveToNext()) {
            long s1 = cursor.getLong(index1);
            String s2 = cursor.getString(index2);
            String s3=cursor.getString(index3);

            if(s1==mobile){
                values[0]=s2;
                values[1]=s3;
                return  values;
            }
        }

        return values;
    }


    //                      USERS TABLE
    //Getting usersName and password for validation during login
    public String checkMobile(long mobile) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String column[] = {helper.MOBILE, helper.PASSWORD};
        Cursor cursor = db.query(helper.TABLE_NAME, column, helper.MOBILE + "='" + mobile + "'", null, null, null, null);

        int index1 = cursor.getColumnIndex(helper.PASSWORD);
        int index2 = cursor.getColumnIndex(helper.MOBILE);

        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            String s1 = cursor.getString(index1);
            long s2 = cursor.getLong(index2);
            if (s2 == mobile) {
                builder.append(s1);
                return builder.toString();
            }
        }

        return builder.toString();
    }

    //Checking whether mobile already exist
    //USER TABLE
    public boolean checkMobileExist(long mobile) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] column = {helper.MOBILE};
        Cursor cursor = db.query(helper.TABLE_NAME, column, helper.MOBILE + "='" + mobile + "'", null, null, null, null);

        int index = cursor.getColumnIndex(helper.MOBILE);

        while (cursor.moveToNext()) {
            long s = cursor.getLong(index);
            if (mobile == s) {
                return FALSE;
            }
        }
        return TRUE;
    }

    //USERS TABLE
    //getting the columns from user table
    public String[] getColumnUsers(){
        //Order will depend on the order of how you are displaying values in your list item
        // i.e. the values in String []to array in the listview activity
        String []a={helper.MOBILE,helper.EMAIL,helper.PASSWORD,helper.NAME,helper.CITY};
        return a;
    }

    //USERS TABLE
    //getting the cursor and sending all the columns
    public Cursor getAllColumnsUsers(){
        SQLiteDatabase db=helper.getWritableDatabase();

        String[] columns={helper.MOBILE,helper.EMAIL,helper.PASSWORD,helper.NAME,helper.CITY};

        Cursor cursor=db.query(helper.TABLE_NAME,columns,null,null,null,null,null);
        return cursor;
    }


    //Adding bank data of the Users. Since we dont have access to the bank's original data, we are creating our own data
    //BANK TABLE
    public long addBankData(long cardNumber, double balance, long mobile,String expiry, int cvv) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(helper.CARD_NUMBER, cardNumber);
        values.put(helper.BALANCE, balance);
        values.put(helper.MOBILE1, mobile);
        values.put(helper.EXPIRY, expiry);
        values.put(helper.CVV, cvv);

        long id = 0;
        try {
            id = db.insert(helper.TABLE_CUSTOMERS, null, values);
        } catch (Exception e) {
            System.out.print(""+e);
        }
        return id;
    }


    //Getting a single element from Bank Users Table
    //BANK TABLE
    public String getSingleCustomer() {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {helper.CARD_NUMBER,helper.BALANCE,helper.MOBILE1,helper.EXPIRY,helper.CVV};
        Cursor cursor = db.query(helper.TABLE_CUSTOMERS, columns, null, null, null, null, null, null);

        StringBuilder sb = new StringBuilder();

        int index1 = cursor.getColumnIndex(helper.CARD_NUMBER);
        int index7 = cursor.getColumnIndex(helper.BALANCE);
        int index8 = cursor.getColumnIndex(helper.EXPIRY);
        int index9 = cursor.getColumnIndex(helper.CVV);
        int index10=cursor.getColumnIndex(helper.CARD_NUMBER);

        cursor.moveToNext();
        long a = cursor.getLong(index1);
        double g = cursor.getDouble(index7);
        String h = cursor.getString(index8);
        int i = cursor.getInt(index9);
        long j=cursor.getLong(index10);

        sb.append(a + " " + g + " " + h + " " + i +" "+j+" \n");

        return sb.toString();
    }

    //Checking for correct Card Number, Expiry and CVV
    //BANK TABLE
    public double checkCredential(long cardNumber, String cardExp, int cardCvv){
        SQLiteDatabase db=helper.getWritableDatabase();

        String []columns={helper.CARD_NUMBER,helper.BALANCE,helper.EXPIRY,helper.CVV};
        Cursor cursor=db.query(helper.TABLE_CUSTOMERS,columns,helper.CARD_NUMBER+"='"+cardNumber+"'",null,null,null,null);

        int index1=cursor.getColumnIndex(helper.BALANCE);
        int index2=cursor.getColumnIndex(helper.CARD_NUMBER);
        int index3=cursor.getColumnIndex(helper.EXPIRY);
        int index4=cursor.getColumnIndex(helper.CVV);

        double balance=0.0;

        while(cursor.moveToNext()){
            double d=cursor.getDouble(index1);
            long a=cursor.getLong(index2);
            String b=cursor.getString(index3);
            int c=cursor.getInt(index4);

            if(cardNumber==a && cardExp.equals(b) && cardCvv==c){
                return d;
            }
        }
        return balance;
    }

    //BANK TABLE
    //Check if the newly added card already exist
    public boolean checkDuplicateCard(long cardNumber){
        SQLiteDatabase db=helper.getWritableDatabase();

        String []columns={helper.CARD_NUMBER};
        Cursor cursor=db.query(helper.TABLE_CUSTOMERS,columns,helper.CARD_NUMBER+"='"+cardNumber+"'",null,null,null,null);

        int index1=cursor.getColumnIndex(helper.CARD_NUMBER);

        while(cursor.moveToNext()){
            long num=cursor.getLong(index1);

            if(num==cardNumber){
                return FALSE;
            }
        }
        return TRUE;
    }

    //BANK TABLE
    //Update the balance after the recharge is done
    public boolean updateBalance(long cardNumber,double amount){
        SQLiteDatabase db=helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(helper.BALANCE,amount);

        String []updated={""+cardNumber};

        long id=db.update(helper.TABLE_CUSTOMERS,cv,helper.CARD_NUMBER+" =? ",updated);
        if(id<0){
            return FALSE;
        }else{
            return TRUE;
        }
    }

    //BANK TABLE
    //getting the required column names from the BANK Customer table
    public String[] getColumnBank(){
        //Order will depend on the order of how you are displaying values in your list item
        // i.e. the values in String []to array in the listview activity
        String []a={helper.MOBILE1,helper.BALANCE,helper.CARD_NUMBER,helper.EXPIRY,helper.CVV};
        return a;
    }

    //BANK TABLE
    //Getting the columns for the Accounts Activity
    public String[] getColumnBank1(){
        String []a={helper.CARD_NUMBER,helper.EXPIRY,helper.CVV};
        return a;
    }

    //BANK TABLE
    //getting all the columns
    public Cursor getAllColumnsBank(){
        SQLiteDatabase db=helper.getWritableDatabase();

        String []columns={helper.CARD_NUMBER,helper.BALANCE,helper.MOBILE1,helper.EXPIRY,helper.CVV};

        Cursor cursor=db.query(helper.TABLE_CUSTOMERS,columns,null,null,null,null,null);
        return cursor;
    }


    //BANK TABLE
    //Getting the cursor for only the match mobile number
    public Cursor getAllColumnsBank1(long mobile){
        SQLiteDatabase db=helper.getWritableDatabase();

        String []columns={helper.CARD_NUMBER,helper.BALANCE,helper.MOBILE1,helper.EXPIRY,helper.CVV};

        Cursor cursor=db.query(helper.TABLE_CUSTOMERS,columns,helper.MOBILE1+"='"+mobile+"'",null,null,null,null);
        return cursor;
    }

    //Adding data for the transaction history MOBILE
    //MOBILE TABLE
    public long addRechargeInfo(long mobile,double amount,String date,String time,String provider,String tag){
        SQLiteDatabase db=helper.getWritableDatabase();

        String []columns={helper.MOBILE1,helper.PROVIDER,helper.RECHARGE_AMOUNT};

        ContentValues cv=new ContentValues();
        cv.put(helper.MOBILE1,mobile);
        cv.put(helper.RECHARGE_AMOUNT,amount);
        cv.put(helper.DATE,date);
        cv.put(helper.TIME,time);
        cv.put(helper.PROVIDER,provider);
        cv.put(helper.TAG,tag);


        long id = 0;
        try {
            id = db.insert(helper.TABLE_MOBILE, null, cv);
        } catch (Exception e) {
            System.out.print(""+e);
        }
        return id;
    }

    //getting data from
    //MOBILE Table
    public String getAllMobile(){
        SQLiteDatabase db=helper.getWritableDatabase();

        String []columns={helper.MOBILE1,helper.PROVIDER,helper.RECHARGE_AMOUNT};
        Cursor cursor=db.query(helper.TABLE_MOBILE,columns,null,null,null,null,null);

        StringBuilder sb=new StringBuilder();

        int index1=cursor.getColumnIndex(helper.MOBILE1);
        int index2=cursor.getColumnIndex(helper.PROVIDER);
        int index3=cursor.getColumnIndex(helper.RECHARGE_AMOUNT);
        int index4=cursor.getColumnIndex(helper.DATE);
        int index5=cursor.getColumnIndex(helper.TIME);

        while(cursor.moveToNext()){
            long a=cursor.getLong(index1);
            String b=cursor.getString(index2);
            double c=cursor.getDouble(index3);
            String d=cursor.getString(index4);
            String e=cursor.getString(index5);

            sb.append(a+" "+b+" "+c+" "+d+" "+e+"\n");
        }
        return sb.toString();
    }

    //deleting all history after the user logouts
    //MOBILE TABLE
    public void deleteAllData(){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL("delete from "+helper.TABLE_MOBILE);
    }

    //Getting all the columns Names for mobile history
    //MOBILE TABLE
    public String[] getColumns(){
        //Order will depend on the order of how you are displaying values in your list item
        // i.e. the values in String []to array in the listview activity
        String []a={helper.MOBILE1,helper.TAG,helper.PROVIDER,helper.RECHARGE_AMOUNT,helper.DATE,helper.TIME};
        return a;
    }

    //Getting details from mobile table
    //MOBILE TABLE
    public Cursor getHistory(){
        SQLiteDatabase db=helper.getWritableDatabase();
        String []columns={helper.MOBILE,helper.MOBILE1,helper.RECHARGE_AMOUNT,helper.DATE,helper.TIME,helper.PROVIDER,helper.TAG};

        Cursor c=db.query(helper.TABLE_MOBILE,columns,null,null,null,null,null,null);

        return c;
    }

    //CARD TABLE
    //Inserting the value into the table from AddNewCard class
    public long addCardDetails(long number,String expiry,String cvv){
        SQLiteDatabase db=helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(helper.CARD_NUMBER_CARD,number);
        cv.put(helper.CARD_EXPIRY_CARD,expiry);
        cv.put(helper.CARD_CVV_CARD,cvv);

        long id = 0;
        try {
            id = db.insert(helper.TABLE_CARD, null, cv);
        } catch (Exception e) {
            System.out.print(""+e);
        }
        return id;
    }

    //CARD TABLE
    //getting the required row for the list view adapter
    public String[] getColumnsCard(){
        String []a={helper.CARD_NUMBER_CARD,helper.CARD_EXPIRY_CARD,helper.CARD_CVV_CARD};
        return a;
    }

    //CARD TABLE
    //getting the Cursor with all the columns for the list adapter
    public Cursor getColumnCardCursor(){
        SQLiteDatabase db=helper.getWritableDatabase();
        String []columns={helper.CARD_NUMBER_CARD,helper.EXPIRY,helper.CVV};

        Cursor cursor=db.query(helper.TABLE_CARD,columns,null,null,null,null,null);
        return cursor;
    }


    class DatabaseHelper extends SQLiteOpenHelper {
        Context context;
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "Users";
        private static final String TABLE_NAME = "Details";
        private static final String TABLE_CUSTOMERS = "Customers";
        private static final String TABLE_MOBILE = "Mobile";
        private static final String TABLE_CARD = "Card";

        /*Details about table columns*/
        private static final String MOBILE = "_id";
        private static final String EMAIL = "email";
        private static final String PASSWORD = "password";
        private static final String NAME = "name";
        private static final String CITY = "city";

        /*Details about Customer Table columns*/
        private static final String MOBILE1 = "mobile";
        private static final String BALANCE = "balance";
        private static final String CARD_NUMBER = "_id";
        private static final String EXPIRY = "expiry";
        private static final String CVV = "cvv";

        /*Details about Mobile Company Details tracking Table*/
        private static final String RECHARGE_AMOUNT = "recharge";
        private static final String DATE = "date";
        private static final String TIME = "time";
        private static final String PROVIDER = "provider";
        private static final String TAG="tag";
        private static final String ID="_id";

        /*Details for Card Table*/
        private static final String CARD_NUMBER_CARD="_id";//the name is kept as id to satisfy the listview cursor adapter
        private static final String CARD_EXPIRY_CARD="expiry";
        private static final String CARD_CVV_CARD="cvv";


        private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE " + TABLE_NAME + "(" + MOBILE + " INTEGER PRIMARY KEY, " +
                EMAIL + " VARCHAR(30), " + PASSWORD + " VARCHAR(30), " + NAME + " VARCHAR(40), " + CITY + " VARCHAR(40)); ";

        private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_CUSTOMERS + "(" + CARD_NUMBER + " INTEGER PRIMARY KEY, " +
                BALANCE + " REAL, " + MOBILE1 + " INTEGER , " + EXPIRY + " VARCHAR(10), " + CVV + " INTEGER);";


        private static final String CREATE_TABLE_MOBILE = "CREATE TABLE " + TABLE_MOBILE + " ( " +
                ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+MOBILE1 + " INTEGER , " + RECHARGE_AMOUNT + " REAL, " + DATE + " VARCHAR(20), " +
                TIME + " VARCHAR(20)," + PROVIDER + " VARCHAR(30),"+ TAG +" VARCHAR(20));";


        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_CUSTOMERS);
                sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT);
                sqLiteDatabase.execSQL(CREATE_TABLE_MOBILE);
            } catch (SQLException e) {
                System.out.println(""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOBILE);
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
            }
        }

    }


}

/*
        private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_CUSTOMERS + "(" + ACCOUNT + " INTEGER PRIMARY KEY, " +
                NAME + " VARCHAR(30), " + ADDRESS + " VARCHAR(100), " + DOB + " VARCHAR(20), " + MOBILE1 + " INTEGER ," + BRANCH_CODE + " VARCHAR(10), " +
                BALANCE + " REAL, " + CARD_NUMBER + " INTEGER NOT NULL, " + EXPIRY + " varchar(10), " + CVV + " INTEGER);";
*/

/*
        private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_CUSTOMERS + "(" + ACCOUNT + " INTEGER PRIMARY KEY, " +
                MOBILE1 + " INTEGER ," + BALANCE + " REAL, " + CARD_NUMBER + " INTEGER NOT NULL, " + EXPIRY + " varchar(10), " + CVV + " INTEGER);";
*/

/*        private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_CUSTOMERS + "(" + ACCOUNT + " INTEGER PRIMARY KEY, " +
                BALANCE + " REAL, " + CARD_NUMBER + " INTEGER , " + EXPIRY + " VARCHAR(10), " + CVV + " INTEGER);";*/

/*
        private static final String CREATE_TABLE_CARD="CREATE TABLE "+TABLE_CARD+"("+
                CARD_NUMBER_CARD+" INTEGER PRIMARY KEY, "+CARD_EXPIRY_CARD+" VARCHAR(10), "+CARD_CVV_CARD+" VARCHAR(3));";
*/
