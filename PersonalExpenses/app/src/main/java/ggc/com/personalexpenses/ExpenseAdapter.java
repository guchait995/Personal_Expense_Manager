package ggc.com.personalexpenses;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SOURAV on 6/17/2017.
 */

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    Context context;
    ArrayList<Expense> expense_list;
    ArrayList<String> catagorylist;
    TextView textViewRowItem;
    TextView textViewRowPrice;

    public ExpenseAdapter(@NonNull Context context, ArrayList<Expense> expense_list) {
        super(context, R.layout.catagory_expense_row,expense_list);
        this.context=context;
        this.expense_list=expense_list;

        Log.i("EXPENSE ADAPTER", "Construtor getView: ");
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // return super.getView(position, convertView, parent);
        View root=null;
        Log.i("EXPENSE ADAPTER", " in start getView: ");
        if(convertView==null)
        {
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            root=layoutInflater.inflate(R.layout.catagory_expense_row,parent,false);
        }
        else {

            root = convertView;
        }

        Log.i("", "getView: expenselistsize"+(expense_list.size()-position)+"position="+position);

            Expense expense=expense_list.get(position);
            Log.i("EXPENSE ADAPTER", "getView: "+expense.getItem()+","+expense.getPrice());
            textViewRowItem= (TextView) root.findViewById(R.id.textViewRowItem);
            textViewRowPrice= (TextView) root.findViewById(R.id.textViewRowPrice);
            textViewRowItem.setText("("+expense.getDate()+"/"+expense.getMonth()+"/"+expense.getYear()+") "+expense.getItem());
            textViewRowPrice.setText(String.valueOf(expense.getPrice()));
            



        return root;
    }


    public void TotalPrice()
    {



    }


}
