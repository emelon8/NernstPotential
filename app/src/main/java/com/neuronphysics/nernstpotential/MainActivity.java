package com.neuronphysics.nernstpotential;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// Additional Imports
import android.text.Html;
import android.text.TextUtils;
import android.view.View; // whenever you need to use widgets
import android.widget.Button; // using buttons
import android.widget.TextView; // using numbers output view
import android.widget.RadioGroup; // using radio button group

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    public Double ConcentrationIn = 15.0;
    public Double ConcentrationOut = 145.0;
    public Double Temperature = 37.0;
    public Double ReversalPotential = 0.0;
    public Double FaradayConstant = 96485.33289; // [C/mol]
    public Double GasConstant = 8.3144598; // [J/(mol*K)]
    public Integer Charge = 1; // z or the charge on the ion species

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)findViewById(R.id.radioButtonNa)).setText(Html.fromHtml(getString(R.string.sodium)));
        ((TextView)findViewById(R.id.radioButtonK)).setText(Html.fromHtml(getString(R.string.potassium)));
        ((TextView)findViewById(R.id.radioButtonCl)).setText(Html.fromHtml(getString(R.string.chloride)));
        ((TextView)findViewById(R.id.radioButtonCa)).setText(Html.fromHtml(getString(R.string.calcium)));

        Button buttonCalc = (Button)findViewById(R.id.buttonCalc);
        RadioGroup IonSpecies = (RadioGroup)findViewById(R.id.radioGroupIon);
        //EditText ConcIn = (EditText)findViewById(R.id.editTextCin);
        //ConcIn.setText("145");

        IonSpecies.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radioButtonNa) {
                    Charge = 1;
                }
                else if (checkedId == R.id.radioButtonK) {
                    Charge = 1;
                }
                else if (checkedId == R.id.radioButtonCl) {
                    Charge = -1;
                }
                else if (checkedId == R.id.radioButtonCa) {
                    Charge = 2;
                }
            }
        });

        buttonCalc.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        TextView inputCin = (TextView)findViewById(R.id.editTextCin);
                        if (!TextUtils.isEmpty(inputCin.getText())) {
                            ConcentrationIn = Double.parseDouble(inputCin.getText().toString());
                        }
                        TextView inputCout = (TextView)findViewById(R.id.editTextCout);
                        if (!TextUtils.isEmpty(inputCout.getText())) {
                            ConcentrationOut = Double.parseDouble(inputCout.getText().toString());
                        }
                        TextView inputTemp = (TextView)findViewById(R.id.editTextTemp);
                        if (!TextUtils.isEmpty(inputTemp.getText())) {
                            Temperature = Double.parseDouble(inputTemp.getText().toString());
                        }
                        TextView output = (TextView)findViewById(R.id.textViewVr);
                        output.setText("");
                        ReversalPotential = ((GasConstant*(Temperature+273.15))/(Charge*FaradayConstant))*java.lang.Math.log(ConcentrationOut/ConcentrationIn)*1e3; // [mV]; add 273.15 to convert degrees C to Kelvin
                        DecimalFormat ReversalPotentialRounded = new DecimalFormat("#.####");
                        output.append(ReversalPotentialRounded.format(ReversalPotential).toString());
                        ReversalPotentialRounded = null;
                    }
                }
        );
    }
}
