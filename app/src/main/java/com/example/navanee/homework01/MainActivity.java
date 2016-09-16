// Navaneeth Reddy Matta and Bala Aditya Devineni
package com.example.navanee.homework01;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup rg = null;
    RadioButton rb = null;
    SeekBar sBar = null;
    Switch genderSwitch = null;
    ProgressBar mProgress = null;
    Button addButtonCell = null;
    TextView finalLevel = null;
    TextView myStatusVal = null;
    int alcoholQuantity = 1;
    Double genderValue = 0.68;
    int alcoholPercentage = 5;
    Double finalBACLevel = 0.0;
    TextView alcoholValueLabel = null;
    Double weight = 0.0;
    ArrayList<Drink> drinks = new ArrayList<Drink>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        alcoholValueLabel = (TextView)findViewById(R.id.alcoholValue);
        rg = (RadioGroup) findViewById(R.id.radioGrp);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = (RadioButton)findViewById(checkedId);
                if(rb.getText() == getString(R.string.drink_label1)) {
                    alcoholQuantity = 1;
                } else if(rb.getText() == getString(R.string.drink_label2)) {
                    alcoholQuantity = 5;
                } else {
                    alcoholQuantity = 12;
                }
            }
        });

        sBar = (SeekBar) findViewById(R.id.alcoholPercent);
        sBar.setMax(25);
        sBar.setProgress(5);
        alcoholValueLabel.setText(alcoholPercentage + " %");
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alcoholPercentage = progress;
                alcoholValueLabel.setText(alcoholPercentage + " %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        genderSwitch = (Switch) findViewById(R.id.genderToggle);
        genderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    genderValue = 0.55;
                } else {
                    genderValue = 0.68;
                }
            }
        });

        mProgress = (ProgressBar) findViewById(R.id.bacProgressBar);

        addButtonCell = (Button) findViewById(R.id.addBtn);
        finalLevel = (TextView) findViewById(R.id.finalBACLevel);
        myStatusVal = (TextView) findViewById(R.id.myStatus);

        findViewById(R.id.addBtn).setOnClickListener(this);
        findViewById(R.id.saveBtn).setOnClickListener(this);
        findViewById(R.id.resetBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveBtn) {
            try {
                weight = Double.parseDouble(((TextView) findViewById(R.id.weightEditor)).getText().toString());
                updateBACRecords();
                showToastMessage("Your Details Have Been Updated");
            } catch (NumberFormatException e) {
                showToastMessage("Enter Valid Details");
            }
        } else if (v.getId() == R.id.addBtn) {
            if(weight > 0) {
                Drink myDrink = new Drink(alcoholQuantity, alcoholPercentage);
                drinks.add(myDrink);
                updateBACValue(myDrink);
            } else {
                //showToastMessage("Enter Your Basic Details");
                TextView tv = (TextView) findViewById(R.id.weightEditor);
                tv.setError("Enter Your Basic Details");
            }
        } else if (v.getId() == R.id.resetBtn) {
            drinks.clear();
            showToastMessage("Your Basic Details Were Reset");
            myStatusVal.setText("You are safe.");
            myStatusVal.setBackgroundColor(Color.parseColor(getString(R.string.greenColor)));
            finalLevel.setText("0.0");
            finalBACLevel = 0.0;
            mProgress.setProgress(0);
            enableButtons();
        }
    }

    public void updateBACRecords() {
        for(int i = 0; i < drinks.size() - 1; i++) {
            updateBACValue(drinks.get(i));
        }
    }

    public void updateBACValue(Drink newDrink) {
        Double BACVal = (newDrink.getAlcoholConsumed() * 6.24) / (weight * genderValue);
        finalBACLevel += BACVal;
        finalLevel.setText(String.format( "%.2f", finalBACLevel));
        mProgress.setProgress((int)(finalBACLevel % 1));
        if(finalBACLevel < 0.08){
            mProgress.setProgress((int)(finalBACLevel * 400));
            myStatusVal.setText("You are safe.");
            myStatusVal.setBackgroundColor(Color.parseColor(getString(R.string.greenColor)));
            enableButtons();
        } else if (finalBACLevel >= 0.08 && finalBACLevel < 0.20) {
            mProgress.setProgress((int)(finalBACLevel * 400));
            myStatusVal.setText("Be careful...");
            myStatusVal.setBackgroundColor(Color.parseColor(getString(R.string.yellowColor)));
            enableButtons();
        } else if (finalBACLevel >= 0.20 && finalBACLevel < 0.25){
            mProgress.setProgress((int)(finalBACLevel * 400));
            myStatusVal.setText("Over the limit!");
            myStatusVal.setBackgroundColor(Color.parseColor(getString(R.string.redColor)));
            enableButtons();
        } else {
            mProgress.setProgress(100);
            myStatusVal.setText("Over the limit!");
            myStatusVal.setBackgroundColor(Color.parseColor(getString(R.string.redColor)));
            showToastMessage("No more drinks for you.");
            disableButtons();
        }
    }

    public void disableButtons() {
        addButtonCell.setEnabled(false);
    }

    public void enableButtons() {
        addButtonCell.setEnabled(true);
    }

    public void showToastMessage(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
}
