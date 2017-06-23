package ggc.com.personalexpenses;


import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesFragment extends Fragment {

private TextView textViewExpense,textViewBudget,textViewSavings;

private DataBaseHandler dataBaseHandler;
    Spinner spinnerMonth,spinneryear;
    String budget;
    public ExpensesFragment() {
        // Required empty public constructor
    }

    public static ExpensesFragment newInstance() {

        Bundle args = new Bundle();

        ExpensesFragment fragment = new ExpensesFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_expenses, container, false);
        dataBaseHandler=new DataBaseHandler(getActivity(),1);
        textViewExpense= (TextView) root.findViewById(R.id.textViewExpenseValue);
        textViewBudget= (TextView) root.findViewById(R.id.textViewTotalIncomeValue);
        textViewBudget.setText(String.valueOf(dataBaseHandler.getbudget()));
        Toast.makeText(getActivity(),String.valueOf(dataBaseHandler.getbudget()) , Toast.LENGTH_SHORT).show();
        textViewSavings= (TextView) root.findViewById(R.id.textViewSavingValue);
        String month=new java.text.SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
        String year=new java.text.SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
        Log.i("", "onCreateView: "+month+" "+year);

        spinnerMonth= (Spinner) root.findViewById(R.id.spinnerMonth);

        spinneryear= (Spinner) root.findViewById(R.id.spinnerYear);
        spinnerMonth.setSelection(Integer.parseInt(month)-1);
        spinneryear.setSelection(2017-Integer.parseInt(year));

        double expense=dataBaseHandler.MonthlyTotalExpense((spinnerMonth.getSelectedItemPosition()+1),Integer.parseInt(spinneryear.getSelectedItem().toString()));
        textViewExpense.setText(String.valueOf(expense));

        textViewBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askbudget();

            }
        });

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                refreshbudget();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinneryear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshbudget();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return root;
    }




    public void refreshbudget()
    {
        double expense=dataBaseHandler
                .MonthlyTotalExpense((spinnerMonth.getSelectedItemPosition()+1),Integer.parseInt(spinneryear.getSelectedItem().toString()));
        textViewExpense.setText(String.valueOf(expense));
        textViewSavings.setText(String.valueOf((Double.parseDouble(textViewBudget.getText().toString()))-(Double.parseDouble(textViewExpense.getText().toString()))));
        double budgetvalue= Double.parseDouble(textViewBudget.getText().toString());
        double expensevalue=Double.parseDouble(textViewExpense.getText().toString());
        double percentagecal;
        percentagecal=budgetvalue*75/100;
        if(expensevalue>percentagecal)
        {
            textViewSavings.setTextColor(Color.RED);
            textViewExpense.setTextColor(Color.RED);

        }

        else
        {
            textViewSavings.setTextColor(Color.GREEN);
        textViewExpense.setTextColor(Color.GREEN);

        }
    }



    public void askbudget()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter Budget");
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                budget = input.getText().toString();

                String oldbudget =textViewBudget.getText().toString();
                textViewBudget.setText(budget);
                dataBaseHandler.budgetupdate(Double.parseDouble(budget),Double.parseDouble(oldbudget));
                refreshbudget();
                Toast.makeText(getActivity(), "Budget Saved", Toast.LENGTH_SHORT).show();


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
}
