package ggc.com.personalexpenses;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class AddCatagoryFragment extends Fragment {

    private Spinner spinnerEditChooser;
    private LinearLayout linearLayoutAdd;
    private LinearLayout linearLayoutDelete;

    private EditText editTextnewCatagory;
    private Button buttonAddCatagory;

    private Spinner spinnerDeleteChooser;

    private Button buttonDeleteCatagory;

    private DataBaseHandler dataBaseHandler;
    ArrayList<String> catagory_list;

    ArrayAdapter<String> catagory_adapter;

    public AddCatagoryFragment() {
    }
    public static AddCatagoryFragment newInstance() {
        AddCatagoryFragment fragment = new AddCatagoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root= inflater.inflate(R.layout.fragment_add_catagory, container, false);
        dataBaseHandler=new DataBaseHandler(getActivity(),1);
        buttonAddCatagory= (Button) root.findViewById(R.id.buttonAddCatagory);
        buttonDeleteCatagory= (Button) root.findViewById(R.id.buttonDeleteCatagory);
        spinnerDeleteChooser= (Spinner) root.findViewById(R.id.spinnerDeleteOptionChooser);
        catagory_list=dataBaseHandler.getallcatagory();
        catagory_adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,catagory_list);
        spinnerDeleteChooser.setAdapter(catagory_adapter);


        editTextnewCatagory= (EditText) root.findViewById(R.id.editText_newCatagory);
        spinnerEditChooser= (Spinner) root.findViewById(R.id.spinnerEditChooser);
        linearLayoutAdd= (LinearLayout) root.findViewById(R.id.addlayout);
        linearLayoutDelete= (LinearLayout) root.findViewById(R.id.deletelayout);
        linearLayoutAdd.setVisibility(View.INVISIBLE);
        linearLayoutDelete.setVisibility(View.INVISIBLE);

        spinnerEditChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1)
                {
                     linearLayoutDelete.setVisibility(View.VISIBLE);
                    linearLayoutAdd.setVisibility(View.GONE);
                    Snackbar.make(view,"Default Catagories Can't Be Deleted",Snackbar.LENGTH_LONG);

                }
                else
                {
                     linearLayoutAdd.setVisibility(View.VISIBLE);
                    linearLayoutDelete.setVisibility(View.GONE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        buttonAddCatagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newcatagory =editTextnewCatagory.getText().toString().toUpperCase().trim();
                dataBaseHandler.createnewcatagory(newcatagory);
                Toast.makeText(getActivity(), newcatagory+" CATAGORY ADDED", Toast.LENGTH_SHORT).show();

            }
        });
        Snackbar.make(root,"Default Catagories can't deleted",Snackbar.LENGTH_LONG).show();

        buttonDeleteCatagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String deletecatagory=spinnerDeleteChooser.getSelectedItem().toString();
                if(deletecatagory.equalsIgnoreCase("TRANSPORT")||
                        deletecatagory.equalsIgnoreCase("FOOD")||
                        deletecatagory.equalsIgnoreCase("OTHERS"))
                {
                    Toast.makeText(getActivity(), "DEFAULT CATAGORIES CAN'T BE DELETED", Toast.LENGTH_SHORT).show();
                }
                else {
                   try {
                       int i = dataBaseHandler.deletecatagory(deletecatagory);
                       Toast.makeText(getActivity(), deletecatagory + " CATAGORY DELETED", Toast.LENGTH_SHORT).show();
                   }
                   catch (Exception e)
                   {
                       Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
                }
            }
        });

        return root;
    }

}
