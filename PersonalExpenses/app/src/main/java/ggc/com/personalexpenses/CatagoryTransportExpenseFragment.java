package ggc.com.personalexpenses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatagoryTransportExpenseFragment extends Fragment {

    DataBaseHandler dataBaseHandler;
    ExpenseAdapter TransportexpenseAdapter;
    ListView listTransportExpense;
    public static CatagoryTransportExpenseFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CatagoryTransportExpenseFragment fragment = new CatagoryTransportExpenseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public CatagoryTransportExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.catagory_transport_expense_fragment, container, false);
        dataBaseHandler=new DataBaseHandler(getActivity(),1);
        listTransportExpense= (ListView) root.findViewById(R.id.TransportExpenseList);
        ArrayList<Expense> expense_list = dataBaseHandler.getspecificexpense("where catagory = 'TRANSPORT'");
//        Expense e = expense_list.get(1);
        //Toast.makeText(getActivity(),e.getItem(), Toast.LENGTH_SHORT).show();
        TransportexpenseAdapter=new ExpenseAdapter(getActivity(),expense_list);
        //FoodExpenseAdapter.getItem(1);
        listTransportExpense= (ListView) root.findViewById(R.id.TransportExpenseList);
        listTransportExpense.setAdapter(TransportexpenseAdapter);


        return root;
    }

}
