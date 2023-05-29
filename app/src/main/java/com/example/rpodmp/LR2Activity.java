package com.example.rpodmp;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rpodmp.entities.MiningDevice;

public class LR2Activity extends AppCompatActivity {

    private MiningDevice[] miningDevices = new MiningDevice[]{
            new MiningDevice("GeForce RTX 4090", 1787, 450, 2.7),
            new MiningDevice("GeForce RTX 4080", 1509, 320, 2.1),
            new MiningDevice("Radeon RX 7900 XTX", 1420, 355, 2.2),
            new MiningDevice("GeForce RTX 4070 Ti", 1399, 285, 2.0),
            new MiningDevice("GeForce RTX 3090 Ti", 1110, 450, 2.3),
            new MiningDevice("Radeon RX 7900 XT", 1110, 315, 1.9),
            new MiningDevice("GeForce RTX 3080 Ti", 1050, 350, 2.1),
            new MiningDevice("Radeon RX 6950 XT", 999, 335, 1.85),
            new MiningDevice("Radeon RX 6900 XT", 950, 300, 1.7),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lr2);

        getSupportActionBar().hide();

        TextView devicePriceTextView = findViewById(R.id.devicePriceTextView);
        TextView deviceProfitTextView = findViewById(R.id.deviceProfitTextView);
        TextView deviceEnergyTextView = findViewById(R.id.deviceEnergyTextView);
        TextView resultTextView = findViewById(R.id.resultTextView);
        Spinner spinner = (Spinner)findViewById(R.id.deviceSpinner);
        Button calculateButton = findViewById(R.id.calculateButton);
        EditText energyPriceEditText = findViewById(R.id.priceEditText);

        Resources resources = this.getResources();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, miningDevices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MiningDevice item = (MiningDevice)parent.getItemAtPosition(position);
                devicePriceTextView.setText("Цена: " + item.get_price() + "$");
                deviceEnergyTextView.setText("Потребление энергии: " + item.get_energyConsumption() + "W/h");
                deviceProfitTextView.setText("Выгода: " + item.get_profit() + "$/h");
                resultTextView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                devicePriceTextView.setText("Цена:");
                deviceEnergyTextView.setText("Потребление энергии:");
                deviceProfitTextView.setText("Выгода:");
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiningDevice device = (MiningDevice)spinner.getSelectedItem();
                double energyPrice;
                try {
                    energyPrice = parseDouble(energyPriceEditText.getText().toString()) * 0.001;
                    double profitInHour = device.get_profit() - (device.get_energyConsumption() * energyPrice);
                    int days = (int)Math.round(device.get_price() / (profitInHour * 24));
                    // quantityText not working on API 30 (minimum 33)
                    // String resultString = resources.getQuantityText(R.plurals.payback, days).toString();
                    String resultString = String.format("Окупаемость за %d дней", days);
                    resultTextView.setText(resultString);
                    resultTextView.setVisibility(View.VISIBLE);
                }
                catch(NumberFormatException e){
                    energyPriceEditText.setError("Неправильный формат числа");
                }
            }
        };

        calculateButton.setOnClickListener(clickListener);
    }
}