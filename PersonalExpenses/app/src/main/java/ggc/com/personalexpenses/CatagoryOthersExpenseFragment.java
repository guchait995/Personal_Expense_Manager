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
public class CatagoryOthersExpenseFragment extends Fragment {
    DataBaseHandler dataBaseHandler;
    ExpenseAdapter OtherexpenseAdapter;
    ListView listOtherExpense;

    public static CatagoryOthersExpenseFragment newInstance() {

        Bundle args = new Bundle();

        CatagoryOthersExpenseFragment fragment = new CatagoryOthersExpenseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public CatagoryOthersExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.catagory_others_expense_fragment, container, false);

        dataBaseHandler=new DataBaseHandler(getActivity(),1);
        ArrayList<Expense> expense_list = dataBaseHandler.getspecificexpense("where catagory <> 'TRANSPORT' AND catagory <> 'FOOD'");
        OtherexpenseAdapter=new ExpenseAdapter(getActivity(),expense_list);
        listOtherExpense= (ListView) root.findViewById(R.id.OtherExpenseList);
        listOtherExpense.setAdapter(OtherexpenseAdapter);

        return root;
    }

}
