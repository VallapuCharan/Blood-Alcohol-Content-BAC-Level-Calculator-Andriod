//Assignment : Homework 1
//File name  : group15_hw1.zip
//Full names : Manideep Reddy Nukala, Sai Charan Reddy Vallapureddy
package com.mad.group15_hw1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int increment_percentage = 5;
    int step = 5;
    int max = 25;
    int min = 5;
    double bacResult;
    String weight_value;
    public double gender_value = (float) 0.68;
    float weight_lbs =0;
    double alcoholSeekValue = 5.0;
    ArrayList<Drink> drinkList = new ArrayList<Drink>();
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static double round1(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Switch gender=findViewById(R.id.genderToggle);

        setTitle("BAC Calculator");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        final SeekBar alcohol_seek=findViewById(R.id.alcoholSeekbar);
        final Button saveButton=findViewById(R.id.saveWeight);
        Button resetButton=findViewById(R.id.reset);
        final ProgressBar progressBar= findViewById(R.id.progress);
        final Button addDrink=findViewById(R.id.addDrink);
        final RadioGroup radioGroup=findViewById(R.id.alcoholSize);
        final TextView seek_per=findViewById(R.id.seek_per);
        final RadioButton oneOunce= findViewById(R.id.oneOunce);
        int t = ((max - min) / step);
        alcohol_seek.setMax(t);
        alcohol_seek.setProgress(0);
        seek_per.setText(alcoholSeekValue + "%");
        //alcohol_seek.incrementProgressBy(increment_percentage);
        final TextView bacText = findViewById(R.id.bac);
        final  TextView statusText = findViewById(R.id.yourStatusValue);
        progressBar.setMax(100);



        alcohol_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alcoholSeekValue = min + (progress * step);
                seek_per.setText( alcoholSeekValue + "%");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo","hfhfh");
                EditText weight = findViewById(R.id.weightValue);
                weight_value = weight.getText().toString();
                if (weight.getText().toString().length() <= 0) {
                    weight.setError("Enter the weight in lb.");
                }
                else
                {
                    if(drinkList.size() != 0)
                    {
                        bacResult = 0;
                        for(Drink d : drinkList)
                        {
                            double A = d.size * d.alcoholSeekValue;
                            bacResult = bacResult + ((A * 6.24) / (gender_value * Integer.parseInt(weight_value))/100);
                        }
                        DecimalFormat df = new DecimalFormat("##.##");
                        String result = "BAC Status:" + String.valueOf(df.format(bacResult));
                        bacText.setText(result);
                        int resultBar = (int) (bacResult * 400);
                        progressBar.setProgress(resultBar);
                        statusText.setTextColor(Color.parseColor("#FFFFFF"));
                        Log.d("demo",bacText.getText().toString());
                        if(bacResult<=0.25){
                            addDrink.setEnabled(true);
                        }
                        if(bacResult>=0.25){
                            saveButton.setEnabled(false);
                            addDrink.setEnabled(false);
                            Toast.makeText(getApplicationContext(),"No more drinks for you",Toast.LENGTH_SHORT).show();
                        }
                        if(bacResult<=0.08){
                            statusText.setText(String.valueOf("You're safe"));
                            statusText.setBackgroundResource(R.color.colorGreen);
                        }
                        else if(bacResult>0.08 && bacResult<0.20){
                            statusText.setText(String.valueOf("Be careful..."));
                            statusText.setBackgroundResource(R.color.colorOrange);
                        }
                        else{
                            statusText.setText(String.valueOf("Over the limit!"));
                            statusText.setBackgroundResource(R.color.colorRed);
                        }
                    }
                weight_lbs = Float.parseFloat(weight_value);
                if (weight_value.length() <= 0) {
                    weight.setError("Please enter weight");
                    weight.requestFocus();
                    return;
                }
                gender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            gender_value = (float) 0.55;
                        } else {
                            gender_value = (float) .68;
                        }

                    }
                });

                Log.d("save", "gender is " + gender_value);
                Log.d("demo", weight_value);
                Log.d("demo", String.valueOf(gender_value));


                //Log.d("demo","Weight : "+weight_value+" gender is " + s1_value);
            }
            }

        });
        addDrink.setOnClickListener(new View.OnClickListener() {
            //ProgressBar progressBar= (ProgressBar) findViewById(R.id.progress);
            @Override
            public void onClick(View v) {
                EditText weight = findViewById(R.id.weightValue);
                if (weight.getText().toString().length() <= 0) {
                    weight.setError("Enter the weight in lb.");
                }
                else if(weight_lbs == 0){
                    Toast.makeText(getApplicationContext(),"Press save button",Toast.LENGTH_SHORT).show();
                }
                else{
                int ounce = 1;
                TextView bacStatus=findViewById(R.id.bac);
                if(radioGroup.getCheckedRadioButtonId()==R.id.oneOunce)
                    {
                        ounce=1;
                }else if (radioGroup.getCheckedRadioButtonId()==R.id.fiveOunce)
                {
                    ounce=5;
                }else if (radioGroup.getCheckedRadioButtonId()==R.id.twelveOunce)
                {
                    ounce=12;
                }
                Drink drink = new Drink(ounce,alcoholSeekValue);
                drinkList.add(drink);
                bacResult = 0;
                for(Drink d : drinkList)
                {
                    double A = d.size * d.alcoholSeekValue;
                    bacResult = bacResult + ((A * 6.24) / (gender_value * Integer.parseInt(weight_value))/100);
                }
                //double A = ounce * alcoholSeekValue;
                // bacResult = bacResult + ((A * 6.24) / (gender_value * Integer.parseInt(weight_value))/100);
                 DecimalFormat df = new DecimalFormat("##.##");
                 String result = "BAC Status:" + String.valueOf(df.format(bacResult));
                 bacText.setText(result);
                 int resultBar = (int) (bacResult * 400);
                 progressBar.setProgress(resultBar);
                 statusText.setTextColor(Color.parseColor("#FFFFFF"));
                Log.d("demo",bacText.getText().toString());
                if(bacResult>=0.25){
                    //saveButton.setEnabled(false);
                    addDrink.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"No more drinks for you",Toast.LENGTH_SHORT).show();
                }
                if(bacResult<=0.08){
                    statusText.setText(String.valueOf("You're safe"));
                    statusText.setBackgroundResource(R.color.colorGreen);
                }
                else if(bacResult>0.08 && bacResult<0.20){
                    statusText.setText(String.valueOf("Be careful..."));
                    statusText.setBackgroundResource(R.color.colorOrange);
                }
                else{
                    statusText.setText(String.valueOf("Over the limit!"));
                    statusText.setBackgroundResource(R.color.colorRed);
                }
            }
            }


        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });




    }
}
