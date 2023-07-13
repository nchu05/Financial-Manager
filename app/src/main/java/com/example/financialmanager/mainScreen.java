package com.example.financialmanager;

import static android.view.View.INVISIBLE;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.financialmanager.databinding.ActivityMainScreenBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class mainScreen extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    ExpenseFragment expenseFragment = new ExpenseFragment();

    PieChart pieChart;
    List<PieEntry> pieEntryList;
    PieEntry temp = new PieEntry(1, "No expenses");

    String[] categories = {"Shopping", "Travel", "Groceries", "Entertainment", "Gas", "Home"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    Button addButton;
    TextView addNewEntryTV;
    EditText transactionET;
    EditText costET;
    TextInputLayout categoriesDropdown;
    public static String transaction = "";
    public static double cost =0;
    public static String category = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        //Categories Drop-down setup
        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, categories);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(mainScreen.this, "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        //Initial visibility setup
        findViewById(R.id.container).bringToFront();
        addButton = findViewById(R.id.addButton);
        categoriesDropdown = findViewById(R.id.category_dropdown);
        addNewEntryTV = findViewById(R.id.addNewEntryTV);
        transactionET = findViewById(R.id.transactionET);
        costET = findViewById(R.id.costET);
        addButton.setVisibility(INVISIBLE);
        addNewEntryTV.setVisibility(INVISIBLE);
        transactionET.setVisibility(INVISIBLE);
        costET.setVisibility(INVISIBLE);
        autoCompleteTextView.setVisibility(INVISIBLE);
        categoriesDropdown.setVisibility(INVISIBLE);


        //Pie Chart setup
        pieEntryList = new ArrayList<>();
        pieChart = findViewById(R.id.pieChart);
        setUpChart();
        pieChart.getDescription().setText("");
        pieEntryList.add(temp);


        //Navigation View setup
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    pieChart.setVisibility(View.VISIBLE);
                    addButton.setVisibility(INVISIBLE);
                    addNewEntryTV.setVisibility(INVISIBLE);
                    transactionET.setVisibility(INVISIBLE);
                    costET.setVisibility(INVISIBLE);
                    autoCompleteTextView.setVisibility(INVISIBLE);
                    categoriesDropdown.setVisibility(INVISIBLE);
                    return true;
                }

                else if (item.getItemId() == R.id.expense) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, expenseFragment).commit();
                    pieChart.setVisibility(View.INVISIBLE);
                    addButton.setVisibility(View.VISIBLE);
                    addNewEntryTV.setVisibility(View.VISIBLE);
                    transactionET.setVisibility(View.VISIBLE);
                    costET.setVisibility(View.VISIBLE);
                    autoCompleteTextView.setVisibility(View.VISIBLE);
                    categoriesDropdown.setVisibility(View.VISIBLE);
                    return true;
                }

                return false;
            }
        });

        //User input setup
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!transactionET.getText().equals("") && !costET.getText().equals("") && !autoCompleteTextView.getText().equals("")) {
                    transaction = String.valueOf(transactionET.getText());
                    cost = Double.parseDouble(costET.getText().toString());
                    category = autoCompleteTextView.getText().toString();
                    transactionET.setText("Transaction");
                    costET.setText("Cost");
                    autoCompleteTextView.setText("");
                    getSupportFragmentManager().beginTransaction().detach(expenseFragment).attach(expenseFragment).commit();
                    pieEntryList.add(new PieEntry((float) cost, category));
                    if (pieEntryList.get(0)==temp) {
                        pieEntryList.remove(0);
                    }
                    setUpChart();

                }
            }
        });

    }

    private void setUpChart() {
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "Pie Chart");
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
        pieData.setValueTextSize(12f);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

}