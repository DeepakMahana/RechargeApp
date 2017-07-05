package pooja_pawar.wipro.com.quadplay;

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
    //Adding User to the USERS table

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
            id = db.insert(helper.TABLE_USERS, null, values);
        } catch (Exception e) {
            System.out.println(""+e);
        }
        return id;
    }

    //USER TABLE
    //Getting data from the Users table

    public String[] getData(long mobile) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {helper.MOBILE,helper.EMAIL, helper.NAME, helper.CITY};
        Cursor cursor = db.query(helper.TABLE_USERS, columns, helper.MOBILE+"='"+mobile+"'", null, null, null, null);

        int index1 = cursor.getColumnIndex(helper.MOBILE);
        int index2 = cursor.getColumnIndex(helper.EMAIL);
        int index3 = cursor.getColumnIndex(helper.NAME);
        int index4 = cursor.getColumnIndex(helper.CITY);


        String []values=new String[3];
        while (cursor.moveToNext()) {
            long s1 = cursor.getLong(index1);
            String s2 = cursor.getString(index2);
            String s3 = cursor.getString(index3);
            String s4 = cursor.getString(index4);

            if(s1==mobile){
                values[0]=s2;
                values[1]=s3;
                values[2]=s4;
                return  values;
            }
        }

        return values;
    }


    //USERS TABLE
    //Getting usersName and password for validation during login

    public String checkMobile(long mobile) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String column[] = {helper.MOBILE, helper.PASSWORD};
        Cursor cursor = db.query(helper.TABLE_USERS, column, helper.MOBILE + "='" + mobile + "'", null, null, null, null);

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
        Cursor cursor = db.query(helper.TABLE_USERS, column, helper.MOBILE + "='" + mobile + "'", null, null, null, null);

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

        Cursor cursor=db.query(helper.TABLE_USERS,columns,null,null,null,null,null);
        return cursor;
    }


    //Recharge TABLE
    //Adding Recharges Made By Users

    long addRecharge(long usermobile, long remobile, int amount, String operator, String state) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(helper.USER_MOBILE_ID, usermobile);
        values.put(helper.RECHARGE_MOBILE_NO, remobile);
        values.put(helper.AMOUNT, amount);
        values.put(helper.OPERATOR, operator);
        values.put(helper.STATE, state);

        long id = 0;
        try {
            id = db.insert(helper.TABLE_RECHARGE, null, values);
        } catch (Exception e) {
            System.out.println(""+e);
        }
        return id;
    }

    //RECHARGE TABLE
    //Getting data from the Recharge table

    public String[] getRechargeData(long mobile) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {helper.USER_MOBILE_ID,helper.RECHARGE_MOBILE_NO, helper.AMOUNT, helper.OPERATOR,helper.STATE };
        Cursor cursor = db.query(helper.TABLE_RECHARGE, columns, helper.USER_MOBILE_ID+"='"+mobile+"'", null, null, null, null, null);

        int index1 = cursor.getColumnIndex(helper.USER_MOBILE_ID);
        int index2 = cursor.getColumnIndex(helper.RECHARGE_MOBILE_NO);
        int index3 = cursor.getColumnIndex(helper.AMOUNT);
        int index4 = cursor.getColumnIndex(helper.OPERATOR);
        int index5 = cursor.getColumnIndex(helper.STATE);


        String []values=new String[4];
        while (cursor.moveToNext()) {
            long s1 = cursor.getLong(index1);
            String s2 = cursor.getString(index2);
            String s3 = cursor.getString(index3);
            String s4 = cursor.getString(index4);
            String s5 = cursor.getString(index5);

            if(s1==mobile){
                values[0]=s2;
                values[1]=s3;
                values[2]=s4;
                values[3]=s5;
                return  values;
            }
        }

        return values;
    }


    /* DTH Table - Adding DTH */
    //Adding Recharges Made By Users

    long addDth(long userno, long dthno, int rdth, String dthprovider) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(helper.DTH_MOB_USERID, userno);
        values.put(helper.DTH_NO, dthno);
        values.put(helper.DTH_RECHARGE_AMOUNT, rdth);
        values.put(helper.DTH_PROVIDER, dthprovider);


        long id = 0;
        try {
            id = db.insert(helper.TABLE_DTH, null, values);
        } catch (Exception e) {
            System.out.println(""+e);
        }
        return id;
    }

    //USER TABLE
    //Getting data from the Recharge table

    public String[] getDTHData(long mobile) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {helper.DTH_MOB_USERID,helper.DTH_NO, helper.DTH_RECHARGE_AMOUNT, helper.DTH_PROVIDER };
        Cursor cursor = db.query(helper.TABLE_DTH, columns, helper.DTH_MOB_USERID+"='"+mobile+"'",null ,null, null, null);

        int index1 = cursor.getColumnIndex(helper.DTH_MOB_USERID);
        int index2 = cursor.getColumnIndex(helper.DTH_NO);
        int index3 = cursor.getColumnIndex(helper.DTH_RECHARGE_AMOUNT);
        int index4 = cursor.getColumnIndex(helper.DTH_PROVIDER);



        String []values=new String[3];
        while (cursor.moveToNext()) {
            long s1 = cursor.getLong(index1);
            String s2 = cursor.getString(index2);
            String s3 = cursor.getString(index3);
            String s4 = cursor.getString(index4);

            if(s1==mobile){
                values[0]=s2;
                values[1]=s3;
                values[2]=s4;
                return  values;
            }
        }

        return values;
    }

    /* Landline Table - Adding Landline */
    //Adding Recharges Made By Users

    long addLandline(long userno, long landno, int rland, String landprovider) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(helper.LAND_MOB_USERID, userno);
        values.put(helper.LAND_NO, landno);
        values.put(helper.LAND_RECHARGE_AMOUNT, rland);
        values.put(helper.LAND_PROVIDER, landprovider);


        long id = 0;
        try {
            id = db.insert(helper.TABLE_LANDLINE, null, values);
        } catch (Exception e) {
            System.out.println(""+e);
        }
        return id;
    }

    //Getting data from the Landline table

    public String[] getLandlineData(long mobile) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {helper.LAND_MOB_USERID,helper.LAND_NO, helper.LAND_RECHARGE_AMOUNT, helper.LAND_PROVIDER };
        Cursor cursor = db.query(helper.TABLE_LANDLINE, columns, helper.LAND_MOB_USERID+"='"+mobile+"'",null ,null, null, null);

        int index1 = cursor.getColumnIndex(helper.LAND_MOB_USERID);
        int index2 = cursor.getColumnIndex(helper.LAND_NO);
        int index3 = cursor.getColumnIndex(helper.LAND_RECHARGE_AMOUNT);
        int index4 = cursor.getColumnIndex(helper.LAND_PROVIDER);

        String []values = new String[3];
        while (cursor.moveToNext()) {
            long s1 = cursor.getLong(index1);
            String s2 = cursor.getString(index2);
            String s3 = cursor.getString(index3);
            String s4 = cursor.getString(index4);

            if(s1==mobile){
                values[0]=s2;
                values[1]=s3;
                values[2]=s4;
                return  values;
            }
        }

        return values;
    }

/* BankDetails Table - Adding BankDetails */

    long addBankDetails(long cardno,int expiry, int cvv, long userno, String cardname, String bankname) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(helper.CARD_NO, cardno);
        values.put(helper.CARD_EXP, expiry);
        values.put(helper.CARD_CVV, cvv);
        values.put(helper.BANK_MOB_USERID,userno);
        values.put(helper.CARD_USERNAME,cardname);
        values.put(helper.BANK_NAME,bankname);


        long id = 0;
        try {
            id = db.insert(helper.TABLE_BANKDETAILS, null, values);
        } catch (Exception e) {
            System.out.println(""+e);
        }
        return id;
    }

    //Getting data from the Bank table

    public String[] getBankData(long cardno) {

        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {helper.CARD_NO,helper.CARD_EXP,helper.CARD_CVV, helper.BANK_MOB_USERID, helper.CARD_USERNAME, helper.BANK_NAME };
        Cursor cursor = db.query(helper.TABLE_BANKDETAILS, columns, helper.CARD_NO+"='"+cardno+"'",null ,null, null, null, null);

        int index1 = cursor.getColumnIndex(helper.CARD_NO);
        int index2 = cursor.getColumnIndex(helper.CARD_EXP);
        int index3 = cursor.getColumnIndex(helper.CARD_CVV);
        int index4 = cursor.getColumnIndex(helper.BANK_MOB_USERID);
        int index5 = cursor.getColumnIndex(helper.CARD_USERNAME);
        int index6 = cursor.getColumnIndex(helper.BANK_NAME);

        String []values = new String[5];
        while (cursor.moveToNext()) {
            long s1 = cursor.getLong(index1);
            String s2 = cursor.getString(index2);
            String s3 = cursor.getString(index3);
            String s4 = cursor.getString(index4);
            String s5 = cursor.getString(index5);
            String s6 = cursor.getString(index6);

            if(s1==cardno){
                values[0]=s2;
                values[1]=s3;
                values[2]=s4;
                return  values;
            }
        }

        return values;
    }


    /*

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


    */

    class DatabaseHelper extends SQLiteOpenHelper {

        Context context;

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "Quadplay";
        private static final String TABLE_USERS = "users";
        private static final String TABLE_RECHARGE = "recharge";
        private static final String TABLE_DTH = "dth";
        private static final String TABLE_LANDLINE = "landline";
        private static final String TABLE_BANKDETAILS = "bankdetails";

        /*Details about Users table columns*/
        private static final String MOBILE = "_id";
        private static final String EMAIL = "email";
        private static final String PASSWORD = "password";
        private static final String NAME = "name";
        private static final String CITY = "city";

        /*Details about Recharge Table columns*/
        private static final String RID = "_id";
        private static final String USER_MOBILE_ID = "userno";
        private static final String RECHARGE_MOBILE_NO = "rno";
        private static final String AMOUNT = "ramount";
        private static final String OPERATOR = "operator";
        private static final String STATE = "state";

        /*Details about DTH Details tracking Table*/
        private static final String DTHID = "_id";
        private static final String DTH_MOB_USERID = "userno";
        private static final String DTH_NO = "dthno";
        private static final String DTH_RECHARGE_AMOUNT = "rdth";
        private static final String DTH_PROVIDER = "dthprovider";

        /*Details about Landline Details tracking Table*/
        private static final String LAND_ID = "_id";
        private static final String LAND_MOB_USERID = "userno";
        private static final String LAND_NO = "landno";
        private static final String LAND_RECHARGE_AMOUNT = "rland";
        private static final String LAND_PROVIDER = "landprovider";

        /*Details about Bank Details tracking Table*/
        private static final String CARD_NO ="_id";//the name is kept as id to satisfy the listview cursor adapter
        private static final String CARD_EXP ="expiry";
        private static final String CARD_CVV ="cvv";
        private static final String BANK_MOB_USERID = "userno";
        private static final String CARD_USERNAME = "cardname";
        private static final String BANK_NAME = "bankname";


        /* Create Tables */

        private static final String CREATE_TABLE_USERS = "CREATE TABLE "
                + TABLE_USERS + "("
                + MOBILE + " INTEGER PRIMARY KEY, "
                + EMAIL + " VARCHAR(30), "
                + PASSWORD + " VARCHAR(30), "
                + NAME + " VARCHAR(40), "
                + CITY + " VARCHAR(40)); ";

        private static final String CREATE_TABLE_RECHARGE = "CREATE TABLE "
                + TABLE_RECHARGE + "("
                + RID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_MOBILE_ID + " INTEGER, "
                + RECHARGE_MOBILE_NO + " INTEGER, "
                + AMOUNT + " INTEGER , "
                + OPERATOR + " VARCHAR(20), "
                + STATE + " VARCHAR(20), "
                + " FOREIGN KEY (" +USER_MOBILE_ID+ ") REFERENCES "+TABLE_USERS+"("+MOBILE+"));";

        private static final String CREATE_TABLE_DTH = "CREATE TABLE "
                + TABLE_DTH + "("
                + DTHID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DTH_MOB_USERID + " INTEGER, "
                + DTH_NO + " VARCHAR(30), "
                + DTH_RECHARGE_AMOUNT + " INTEGER, "
                + DTH_PROVIDER + " VARCHAR(30) , "
                + " FOREIGN KEY (" +DTH_MOB_USERID+ ") REFERENCES "+TABLE_USERS+"("+MOBILE+"));";

        private static final String CREATE_TABLE_LANDLINE = "CREATE TABLE "
                + TABLE_LANDLINE + "("
                + LAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LAND_MOB_USERID + " INTEGER, "
                + LAND_NO + " INTEGER, "
                + LAND_RECHARGE_AMOUNT + " INTEGER, "
                + LAND_PROVIDER + " VARCHAR(30) , "
                + " FOREIGN KEY (" +LAND_MOB_USERID+ ") REFERENCES "+TABLE_USERS+"("+MOBILE+"));";

        private static final String CREATE_TABLE_BANKDETAILS = "CREATE TABLE "
                + TABLE_BANKDETAILS + "("
                + CARD_NO + " INTEGER PRIMARY KEY, "
                + CARD_EXP + " INTEGER, "
                + CARD_CVV+ " INTEGER, "
                + BANK_MOB_USERID + " INTEGER, "
                + BANK_NAME + " VARCHAR(50) , "
                + CARD_USERNAME + " VARCHAR(50), "
                + " FOREIGN KEY (" +BANK_MOB_USERID+ ") REFERENCES "+TABLE_USERS+"("+MOBILE+"));";


        /* DATABASE HELPER */

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
                sqLiteDatabase.execSQL(CREATE_TABLE_RECHARGE);
                sqLiteDatabase.execSQL(CREATE_TABLE_DTH);
                sqLiteDatabase.execSQL(CREATE_TABLE_LANDLINE);
                sqLiteDatabase.execSQL(CREATE_TABLE_BANKDETAILS);
            } catch (SQLException e) {
                System.out.println(""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECHARGE);
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DTH);
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LANDLINE);
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BANKDETAILS);
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
            }
        }

    }


}
