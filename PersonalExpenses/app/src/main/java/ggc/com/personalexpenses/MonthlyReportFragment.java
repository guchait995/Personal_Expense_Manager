package ggc.com.personalexpenses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonthlyReportFragment extends Fragment {
    DataBaseHandler dataBaseHandler;
    Spinner catagoryChooser_MonthlyReport;
    Spinner monthChosser_MonthlyReport;
    Spinner yearChooser_MonthlyReport;
    TextView textViewReport;
    ArrayList<Expense> expensedatelist;
    private ListView listdatewiseexpense;
    ExpenseAdapter listdateExpenseAdapter;
    Double report=0.0;
    ArrayList<String> catagory_list_monthlyReport;
    ArrayAdapter<String> catagory_adapter;
    public static MonthlyReportFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MonthlyReportFragment fragment = new MonthlyReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MonthlyReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_monthly_report, container, false);
        listdatewiseexpense= (ListView) root.findViewById(R.id.listViewItemsbydate);



        catagoryChooser_MonthlyReport= (Spinner) root.findViewById(R.id.spinnerCatagoryMonthlyReport);
        monthChosser_MonthlyReport= (Spinner) root.findViewById(R.id.spinnerMonthlyReport);
        yearChooser_MonthlyReport= (Spinner) root.findViewById(R.id.spinnerYearMonthlyreport);
        textViewReport= (TextView) root.findViewById(R.id.textViewReport);
        dataBaseHandler=new DataBaseHandler(getActivity(),1);
        catagory_list_monthlyReport=dataBaseHandler.getallcatagory();
        catagory_adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,catagory_list_monthlyReport);
        catagoryChooser_MonthlyReport.setAdapter(catagory_adapter);
        String month=new java.text.SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
        String year=new java.text.SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());

        monthChosser_MonthlyReport.setSelection(Integer.parseInt(month)-1);
        yearChooser_MonthlyReport.setSelection(2017-Integer.parseInt(year));
        int Month = monthChosser_MonthlyReport.getSelectedItemPosition();
        String Year=yearChooser_MonthlyReport.getSelectedItem().toString();
        String catagory = catagoryChooser_MonthlyReport.getSelectedItem().toString();
        Toast.makeText(getActivity(),"Month : "+Month+"\n"+Year+" "+catagory, Toast.LENGTH_SHORT).show();
        catagoryChooser_MonthlyReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                list();

                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textViewReport.setText("0.0");

            }
        });


        monthChosser_MonthlyReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yearChooser_MonthlyReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return  root;
    }


    public void list()
    {
        int Month = monthChosser_MonthlyReport.getSelectedItemPosition();
        String Year=yearChooser_MonthlyReport.getSelectedItem().toString();
        String catagory = catagoryChooser_MonthlyReport.getSelectedItem().toString();

        expensedatelist=dataBaseHandler.MonthWiseCatagoryExpense(Month+1,Integer.parseInt(Year),catagory);
        if(expensedatelist!=null) {
            listdateExpenseAdapter = new ExpenseAdapter(getActivity(), expensedatelist);
            listdatewiseexpense.setAdapter(listdateExpenseAdapter);
        }
        else
        {
            listdatewiseexpense.setAdapter(null);
        }
        report= dataBaseHandler.calculatePriceByCatagoryAndTime(catagory,Month+1,Integer.parseInt(Year));
        textViewReport.setText(String.valueOf(report));
        //Toast.makeText(getActivity(),"Month : "+Month+"\n"+Year+" "+catagory, Toast.LENGTH_SHORT).show();

    }

}
