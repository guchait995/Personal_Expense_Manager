package ggc.com.personalexpenses;


import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewExpenseFragment extends Fragment {
DataBaseHandler dataBaseHandler;
    private Button buttonSave;
    private DatePicker datePicker;
    private EditText editTextAmount;
    private EditText editTextItem;

    private Spinner spinnercatagory;


    ArrayList<String> catagory_list;
Spinner catagoryChooser;
    ArrayAdapter<String> catagory_adapter;
    SpinnerAdapter sa;
    public NewExpenseFragment() {
        // Required empty public constructor
    }

    public static NewExpenseFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NewExpenseFragment fragment = new NewExpenseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.new_expenses_fragment, container, false);
        dataBaseHandler=new DataBaseHandler(getActivity(),1);
        editTextAmount= (EditText) root.findViewById(R.id.editTextExpenseAmount);
        editTextItem= (EditText) root.findViewById(R.id.editTextItem);

        buttonSave= (Button) root.findViewById(R.id.buttonSave);
        datePicker= (DatePicker) root.findViewById(R.id.datePicker);

        catagoryChooser= (Spinner) root.findViewById(R.id.spinnerCatagoryChooser);
        catagory_list=dataBaseHandler.getallcatagory();
        catagory_adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,catagory_list);
        catagoryChooser.setAdapter(catagory_adapter);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int date= datePicker.getDayOfMonth();
                int month =datePicker.getMonth();
                int year=datePicker.getYear();

                String amount = editTextAmount.getText().toString().trim();
                Double amountd=Double.parseDouble(amount);
                String catagory=catagoryChooser.getSelectedItem().toString();
                String item =editTextItem.getText().toString().trim();

                if((!catagory.equalsIgnoreCase("FOOD"))&&(!catagory.equalsIgnoreCase("TRANSPORT")))
                {
                    if(!catagory.equalsIgnoreCase("OTHERS"))
                    {
                        item=item+"( "+catagory+" )";
                    }

                }
                Expense e=new Expense(date,month+1,year,item,catagory,amountd);
                dataBaseHandler.createNewExpense(e);

                Toast.makeText(getActivity(), "New Expense Added:\n"+amount+" "+catagory+" "+item+","+date+","+month+","+year, Toast.LENGTH_SHORT).show();


                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

}
