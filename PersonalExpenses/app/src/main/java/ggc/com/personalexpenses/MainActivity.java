package ggc.com.personalexpenses;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.text.InputType;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout frameLayout;
    TextView textViewDate;

    ImageView imagedp;
    ArrayList<Expense> expenseArrayList;
    DataBaseHandler dataBaseHandler;
    ExpenseAdapter expenseAdapter;
ConstraintLayout cl;
    ListView MainTodayexpenselist;
    TextView textViewTotal;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener dates;
     TextView textViewusername;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout= (FrameLayout) findViewById(R.id.frame_container);

        cl= (ConstraintLayout) findViewById(R.id.cl);

        Resources res = getResources();
        Drawable dr=res.getDrawable(R.drawable.background2);
        cl.setBackground(dr);
        textViewDate= (TextView) findViewById(R.id.textViewMainPageDtae);
        textViewTotal= (TextView) findViewById(R.id.textViewTotal);
        textViewDate.setText(getdate());
        MainTodayexpenselist= (ListView) findViewById(R.id.listToday);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayout.removeAllViews();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                        .replace(R.id.frame_container, NewExpenseFragment.newInstance(),null).commit();
                getSupportActionBar().setTitle("Add new Expense");
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                     //   .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        String date=new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        String month=new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
        String year=new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
        dataBaseHandler=new DataBaseHandler(getApplicationContext(),1);
        expenseArrayList=dataBaseHandler.DatewiseTotalExpense(Integer.parseInt(date),Integer.parseInt(month),Integer.parseInt(year));
        if(expenseArrayList!=null) {
            expenseAdapter = new ExpenseAdapter(getApplicationContext(), expenseArrayList);
            MainTodayexpenselist.setAdapter(expenseAdapter);
        }
        else
        {
            MainTodayexpenselist.setAdapter(null);
        }

        calendar=Calendar.getInstance();
       dates=new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };




       Double total= dataBaseHandler.DatewiseExpense(Integer.parseInt(date),Integer.parseInt(month),Integer.parseInt(year));
        textViewTotal.setText("Total : "+String.valueOf(total));

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(),dates,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        View v=navigationView.getHeaderView(0);

        imagedp= (ImageView) v.findViewById(R.id.imageViewdp);
        textViewusername= (TextView) v.findViewById(R.id.tvusername);
        textViewusername.setText(dataBaseHandler.getname());
        textViewusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askname();
            }
        });
        imagedp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                finish();
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
            }
        });
        //frameLayout.removeAllViews();
        //getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
          //      .replace(R.id.frame_container, ExpensesFragment.newInstance(),null).commit();
       // getSupportActionBar().setTitle("Expenses");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void askname()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Name");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                username = input.getText().toString();
                textViewusername.setText(username);
                dataBaseHandler.nameupdate(username);


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    public void updateLabel()
    {

        String myFormat ="dd MMM,yyyy";
        String dateformat="dd";
        String monthformat="MM";
        String yearformat="yyyy";
        SimpleDateFormat sdf=new SimpleDateFormat(myFormat);
        SimpleDateFormat datef=new SimpleDateFormat(dateformat);
        SimpleDateFormat yearf=new SimpleDateFormat(yearformat);
        SimpleDateFormat monthf=new SimpleDateFormat(monthformat);
        String date=datef.format(calendar.getTime());
        String month=monthf.format(calendar.getTime());
        String year=yearf.format(calendar.getTime());
        setrow(date,month,year);
        textViewDate.setText(sdf.format(calendar.getTime()));

    }


    public void setrow(String date,String month,String year)
    {
        expenseArrayList=dataBaseHandler.DatewiseTotalExpense(Integer.parseInt(date),Integer.parseInt(month),Integer.parseInt(year));
        if(expenseArrayList!=null) {
            expenseAdapter = new ExpenseAdapter(getApplicationContext(), expenseArrayList);
            MainTodayexpenselist.setAdapter(expenseAdapter);
        }
        else
        {
            MainTodayexpenselist.setAdapter(null);
        }
        Double total= dataBaseHandler.DatewiseExpense(Integer.parseInt(date),Integer.parseInt(month),Integer.parseInt(year));
        textViewTotal.setText("Total : "+String.valueOf(total));


    }
    public String getdate()
    {

        Calendar c=Calendar.getInstance();
        SimpleDateFormat df=new SimpleDateFormat("dd MMM, yyyy");

        String datew=df.format(c.getTime());
        return datew;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

           Intent intent =new Intent(getApplicationContext(),AboutPage.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_expenses) {

            frameLayout.removeAllViews();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                    .replace(R.id.frame_container, ExpensesFragment.newInstance(),null).commit();
            getSupportActionBar().setTitle("Expenses");

            // Handle the camera action
        } else if (id == R.id.nav_catagory_expenses) {

            Intent intent = new Intent(getApplicationContext(), CatagoryExpense.class);
            startActivity(intent);

        }
            else if (id == R.id.nav_edit_catagory_) {
            frameLayout.removeAllViews();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                    .replace(R.id.frame_container, AddCatagoryFragment.newInstance(),null).commit();
            getSupportActionBar().setTitle("Edit Catagory");


        }
        else if(id==R.id.nav_total_catogory_expense)
        {
            frameLayout.removeAllViews();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                    .replace(R.id.frame_container, MonthlyReportFragment.newInstance(),null).commit();
            getSupportActionBar().setTitle("Monthly Report");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
