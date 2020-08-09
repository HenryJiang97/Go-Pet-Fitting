package com.example.gopetfitting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.gopetfitting.models.Pet;

import androidx.appcompat.app.AppCompatActivity;

public class PetStoreActivity extends AppCompatActivity {

    private static final String TAG = "PetStoreActivity";

    ImageButton petBowlButton;
    ImageButton petCanButton;
    ImageButton waterButton;
    ImageButton toyButton;
    ImageButton cleanButton;
    ImageButton vaccineButton;
    EditText coinEditText;
    EditText haveFoodEditText;
    EditText haveWaterEditText;
    EditText haveToysEditText;
    EditText haveVaccineEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_store);

        final Pet mypet = new Pet();

        petBowlButton = (ImageButton) findViewById(R.id.pet_bowl_image_button);
        petBowlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPetBowl(mypet);
            }
        });

        petCanButton = (ImageButton) findViewById(R.id.pet_can_image_button);
        petCanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add3PetBowl(mypet);
            }
        });

        waterButton = (ImageButton) findViewById(R.id.water_image_button);
        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWater(mypet);
            }
        });

        toyButton = (ImageButton) findViewById(R.id.toy_image_button);
        toyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToy(mypet);
            }
        });

        cleanButton = (ImageButton) findViewById(R.id.clean_image_button);
        cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVaccine(mypet);
            }
        });

        vaccineButton = (ImageButton) findViewById(R.id.vaccine_image_button);
        vaccineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add3Vaccine(mypet);
            }
        });

        Log.e(TAG, "mypet coins = " + mypet.getCoins());

        coinEditText = (EditText) findViewById(R.id.coin_editText);
        coinEditText.setText(String.valueOf(mypet.getCoins()));

        haveFoodEditText = (EditText) findViewById(R.id.have_food_editText);
        haveFoodEditText.setText(String.valueOf(mypet.getHave_food()));

        haveWaterEditText = (EditText) findViewById(R.id.have_water_editText);
        haveWaterEditText.setText(String.valueOf(mypet.getHave_food()));

        haveToysEditText = (EditText) findViewById(R.id.have_toys_editText);
        haveToysEditText.setText(String.valueOf(mypet.getHave_food()));

        haveVaccineEditText = (EditText) findViewById(R.id.have_vaccine_editText);
        haveVaccineEditText.setText(String.valueOf(mypet.getHave_food()));

    }

    private void addPetBowl(Pet mypet) {
        if(mypet.getCoins() - 2 >= 0) {
            mypet.setHave_food(mypet.getHave_food() + 1);
            mypet.setCoins(mypet.getCoins() - 2);
            coinEditText.setText(String.valueOf(mypet.getCoins()));
            haveFoodEditText.setText(String.valueOf(mypet.getHave_food()));
        } else {
            Toast.makeText(PetStoreActivity.this, "You don't have enough coins",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void add3PetBowl(Pet mypet) {
        if(mypet.getCoins() - 5 >= 0) {
            mypet.setHave_food(mypet.getHave_food() + 3);
            mypet.setCoins(mypet.getCoins() - 5);
            coinEditText.setText(String.valueOf(mypet.getCoins()));
            haveFoodEditText.setText(String.valueOf(mypet.getHave_food()));
        } else {
            Toast.makeText(PetStoreActivity.this, "You don't have enough coins",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void addWater(Pet mypet) {
        if(mypet.getCoins() - 1 >= 0) {
            mypet.setHave_water(mypet.getHave_water() + 1);
            mypet.setCoins(mypet.getCoins() - 1);
            coinEditText.setText(String.valueOf(mypet.getCoins()));
            haveWaterEditText.setText(String.valueOf(mypet.getHave_water()));
        } else {
            Toast.makeText(PetStoreActivity.this, "You don't have enough coins",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void addToy(Pet mypet) {
        if(mypet.getCoins() - 5 >= 0) {
            mypet.setHave_toys(mypet.getHave_toys() + 1);
            mypet.setCoins(mypet.getCoins() - 5);
            coinEditText.setText(String.valueOf(mypet.getCoins()));
            haveToysEditText.setText(String.valueOf(mypet.getHave_toys()));
        } else {
            Toast.makeText(PetStoreActivity.this, "You don't have enough coins",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void addVaccine(Pet mypet) {
        if(mypet.getCoins() - 20 >= 0) {
            mypet.setHave_vaccination(mypet.getHave_vaccination() + 1);
            mypet.setCoins(mypet.getCoins() - 20);
            coinEditText.setText(String.valueOf(mypet.getCoins()));
            haveVaccineEditText.setText(String.valueOf(mypet.getHave_vaccination()));
        } else {
            Toast.makeText(PetStoreActivity.this, "You don't have enough coins",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void add3Vaccine(Pet mypet) {
        if(mypet.getCoins() - 50 >= 0) {
            mypet.setHave_vaccination(mypet.getHave_vaccination() + 3);
            mypet.setCoins(mypet.getCoins() - 50);
            coinEditText.setText(String.valueOf(mypet.getCoins()));
            haveVaccineEditText.setText(String.valueOf(mypet.getHave_vaccination()));
        } else {
            Toast.makeText(PetStoreActivity.this, "You don't have enough coins",
                    Toast.LENGTH_SHORT).show();
        }
    }


}
