package com.example.financialmanager;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class ExpenseFragment extends Fragment {

    RecyclerView recycler_view;
    PaymentAdapter adapter;
    List<PaymentModel> payment_list = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new PaymentAdapter(getContext(), getList());
        recycler_view.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recycler_view = getView().findViewById(R.id.recycler_view);
    }

    public List<PaymentModel> getList() {
        if (!mainScreen.transaction.equals("") && !mainScreen.category.equals("") && mainScreen.cost!=0) {
            payment_list.add(new PaymentModel(mainScreen.transaction, mainScreen.category, Double.valueOf(mainScreen.cost).toString()));
            mainScreen.transaction = ("");
            mainScreen.category = "";
            mainScreen.cost=0;
        }

        return payment_list;
    }

}