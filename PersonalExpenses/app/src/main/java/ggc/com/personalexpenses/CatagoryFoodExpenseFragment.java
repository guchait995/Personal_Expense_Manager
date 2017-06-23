package ggc.com.personalexpenses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatagoryFoodExpenseFragment extends Fragment {


    private ListView listfoodexpense;
    ExpenseAdapter FoodExpenseAdapter;
    ArrayList<Expense> expense_list;
    DataBaseHandler dataBaseHandler;

    public static CatagoryFoodExpenseFragment newInstance() {

        Bundle args = new Bundle();

        CatagoryFoodExpenseFragment fragment = new CatagoryFoodExpenseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public CatagoryFoodExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.catagory_food_expense_fragment, container, false);

        dataBaseHandler=new DataBaseHandler(getActivity(),1);
        expense_list=dataBaseHandler.getspecificexpense("where catagory = 'FOOD'");

        //Toast.makeText(getActivity(),e.getItem(), Toast.LENGTH_SHORT).show();
        FoodExpenseAdapter=new ExpenseAdapter(getActivity(),expense_list);
        //FoodExpenseAdapter.getItem(1);
        listfoodexpense= (ListView) root.findViewById(R.id.FoodExpenseList);
        listfoodexpense.setAdapter(FoodExpenseAdapter);

        return root;
    }

}
