package ggc.com.personalexpenses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by SOURAV on 6/17/2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {
    public DataBaseHandler(Context context, int version) {
        super(context, "personalexpensedatabase", null, version);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table expense (id INTEGER PRIMARY KEY,date int,month int,year int,catagory text,item text,price double)");
        db.execSQL("insert into expense (date,month,year,catagory,item,price) values (01,01,2017,'FOOD','MEAL','400')");
        db.execSQL("insert into expense (date,month,year,catagory,item,price) values (01,01,2017,'TRANSPORT','TAXI','200')");
        db.execSQL("insert into expense (date,month,year,catagory,item,price) values (01,01,2017,'OTHERS','MOVIE','500')");

        db.execSQL("create table catagoryexpense (catagory text)");
        db.execSQL("insert into catagoryexpense (catagory) values ('TRANSPORT')");
        db.execSQL("insert into catagoryexpense (catagory) values ('FOOD')");
        db.execSQL("insert into catagoryexpense (catagory) values ('OTHERS')");

        db.execSQL("create table monthlybudget (budget double)");
        db.execSQL("insert into monthlybudget (budget) values (20000)");

        db.execSQL("create table usernametable (username text)");
        db.execSQL("insert into usernametable (username) values ('Sourav Guchait')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void budgetupdate(double newbudget,double oldbudget)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("budget",newbudget);
        db.insert("monthlybudget",null,cv);

        db.close();

    }
    public void nameupdate(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("username",name);
        db.insert("usernametable",null,cv);

        db.close();

    }

    public String getname()
    {
        SQLiteDatabase db=getReadableDatabase();
        String name=null;
        Cursor c=db.rawQuery("select username from usernametable" ,null);
        if(c.moveToFirst()) {
            do {
                name = c.getString(0);
            }while(c.moveToNext());

        }
        return name;
    }

    public double getbudget()
    {
        SQLiteDatabase db=getReadableDatabase();
        double priceind=0.0;
        Cursor c=db.rawQuery("select budget from monthlybudget" ,null);
       if(c.moveToFirst()) {
           do {
               priceind = c.getInt(0);
           }while(c.moveToNext());

       }
           return priceind;
    }


    public void createNewExpense(Expense expense) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("date", expense.getDate());
        cv.put("month", expense.getMonth());
        cv.put("year", expense.getYear());
        cv.put("catagory", expense.getCatagory());
        cv.put("item", expense.getItem());
        cv.put("price", expense.getPrice());
        db.insert("expense", null, cv);
        db.close();
        Log.i("DATABASE VALUE:", "createNewExpense: "+","+expense.getDate()+","+expense.getMonth()+","+expense.getYear());
    }

    public void createnewcatagory(String newcatagory)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("catagory",newcatagory);
        db.insert("catagoryexpense",null,cv);
        db.close();
    }
    public int deletecatagory(String deletecatagory)
    {
        SQLiteDatabase db=getWritableDatabase();
        //String[] a=new String[]{newcatagory};
        int i =db.delete("catagoryexpense","catagory='"+deletecatagory+"'",null);

        db.close();
        return i;
    }


    public double calculatePriceByCatagoryAndTime(String catagory,int month,int year)
    {
        double price=0.0;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("select price from expense where catagory = '"+catagory+"' and month='"+month+"' and year='"+year+"'",null);
        if(cursor.moveToFirst())
        {

            do
            {

                double priceind=cursor.getInt(0);
                price=price+priceind;

            }while (cursor.moveToNext());

            return price;
        }

        return price;
    }

    public ArrayList<String> getallcatagory()
    {
        ArrayList<String> catagory_list=null;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("select catagory from catagoryexpense",null);
        if(cursor.moveToFirst())
        {
            catagory_list=new ArrayList<>();
            do
            {
                String catagory=cursor.getString(0);

               catagory_list.add(catagory);

            }while (cursor.moveToNext());
        }

        db.close();
        return catagory_list;
    }



    public ArrayList<Expense> getallexpense()
    {
        ArrayList<Expense> expense_list=null;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("select id,date,month,year,catagory,item,price from expense",null);
        if(cursor.moveToFirst())
        {
            expense_list=new ArrayList<>();
            do
            {

                int id=cursor.getInt(0);
                int date=cursor.getInt(1);
                int month=cursor.getInt(2);
                int year=cursor.getInt(3);
                String catagory=cursor.getString(4);
                String item=cursor.getString(5);
                int price=cursor.getInt(6);
                Log.i("insidewhile", "getallexpense: "+id+","+item+","+price);
                Expense expense=new Expense(id,date,month,year,item,catagory,price);

                expense_list.add(expense);

            }while (cursor.moveToNext());
        }

        Log.i("outside", "getallexpense: ");
        db.close();
        return expense_list;
    }


    public ArrayList<Expense> getspecificexpense(String where_clause)
    {
        ArrayList<Expense> expense_list=null;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("select date,month,year,catagory,item,price from expense "+where_clause,null);
        if(cursor.moveToFirst())
        {
            expense_list=new ArrayList<>();
            do
            {
               // int id=cursor.getInt(0);
                int date=cursor.getInt(0);
                int month=cursor.getInt(1);
                int year=cursor.getInt(2);
                String catagory=cursor.getString(3);
                String item=cursor.getString(4);
                int price=cursor.getInt(5);
                Expense expense=new Expense(date,month,year,item,catagory,price);

                expense_list.add(expense);

            }while (cursor.moveToNext());
        }

        db.close();
        return expense_list;
    }
    public ArrayList<Expense> MonthWiseCatagoryExpense(int Month,int year,String catagory)
    {
        ArrayList<Expense> expense_list=null;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("select date,month,year,item,price from expense where month='"+Month+"' and year='"+year+"' and catagory='"+catagory+"'",null);
        if(cursor.moveToFirst())
        {
            expense_list=new ArrayList<>();
            do
            {
                // int id=cursor.getInt(0);
                 int date=cursor.getInt(0);
                 int month=cursor.getInt(1);
                 int yeari=cursor.getInt(2);
                String item=cursor.getString(3);
                double price=cursor.getInt(4);
                Expense expense=new Expense(date,month,year,price,item);

                expense_list.add(expense);

            }while (cursor.moveToNext());
        }

        db.close();
        return expense_list;
    }

    public double MonthlyTotalExpense(int Month,int year)
    {
        SQLiteDatabase db=getReadableDatabase();
        double total=0;
        Cursor cursor= db.rawQuery("select date,month,year,item,price from expense where month='"+Month+"' and year='"+year+"'",null);
        if(cursor.moveToFirst())
        {
            do
            {
                // int id=cursor.getInt(0);
                int date=cursor.getInt(0);
                int month=cursor.getInt(1);
                int yeari=cursor.getInt(2);
                String item=cursor.getString(3);
                double price=cursor.getInt(4);
               total=total+price;
                Expense expense=new Expense(date,month,year,price,item);


            }while (cursor.moveToNext());
        }

        db.close();
        return total;
    }

    public ArrayList<Expense> DatewiseTotalExpense(int date, int Month, int year)
    {
        ArrayList<Expense> expense_list=null;
        SQLiteDatabase db=getReadableDatabase();
        double total=0;
        Cursor cursor= db.rawQuery("select date,month,year,item,price,catagory from expense where date='"+date+"' and month='"+Month+"' and year='"+year+"'",null);
        if(cursor.moveToFirst())
        {
            expense_list=new ArrayList<>();
            do
            {
                // int id=cursor.getInt(0);
                int datei=cursor.getInt(0);
                int month=cursor.getInt(1);
                int yeari=cursor.getInt(2);
                String item=cursor.getString(3);
                double price=cursor.getInt(4);
                String catagory=cursor.getString(5);
                total=total+price;
                Expense expense=new Expense(datei,month,year,item,catagory,price);

                expense_list.add(expense);

            }while (cursor.moveToNext());
        }

        db.close();
        return expense_list;
    }
    public double DatewiseExpense(int date, int Month, int year)
    {
        ArrayList<Expense> expense_list=null;
        SQLiteDatabase db=getReadableDatabase();
        double total=0;
        Cursor cursor= db.rawQuery("select date,month,year,item,price,catagory from expense where date='"+date+"' and month='"+Month+"' and year='"+year+"'",null);
        if(cursor.moveToFirst())
        {
            expense_list=new ArrayList<>();
            do
            {
                // int id=cursor.getInt(0);
                int datei=cursor.getInt(0);
                int month=cursor.getInt(1);
                int yeari=cursor.getInt(2);
                String item=cursor.getString(3);
                double price=cursor.getInt(4);
                String catagory=cursor.getString(5);
                total=total+price;
                Expense expense=new Expense(datei,month,year,item,catagory,price);

                expense_list.add(expense);

            }while (cursor.moveToNext());
        }

        db.close();
        return total;
    }


    public void readfromsms(String item,double price)
    {
        String date=new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        String month=new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
        String year=new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());

        int datei=Integer.parseInt(date);
        int monthi=Integer.parseInt(month);
        int yeari=Integer.parseInt(year);
        Expense expense=new Expense(datei,monthi,yeari,item,"OTHERS",price);
        createNewExpense(expense);


    }


}




