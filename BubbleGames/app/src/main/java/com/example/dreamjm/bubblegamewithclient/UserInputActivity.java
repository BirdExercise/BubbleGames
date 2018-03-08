package com.example.dreamjm.bubblegamewithclient;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.graphics.drawable.BitmapDrawable;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserInputActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView textView;
    private Button button;
    private Button uploadPictureButton;
    private Spinner modeSpinner;
    private Spinner backgroundSpinner;
    private ImageView imgView;
    private int miniLevel = 1;
    private int selectedLevel = 0;
    private int selectedMode = 0;
    private int selectedBackground = 0;

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
        initializeVariables();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserInputActivity.this, GameManager.class);
                intent.putExtra("selectedLevel", seekBar.getProgress()+miniLevel);
                if (modeSpinner.getSelectedItemPosition() == 0)
                    intent.putExtra("selectedMode", 1);
                else
                    intent.putExtra("selectedMode", modeSpinner.getSelectedItemPosition());
                if (backgroundSpinner.getSelectedItemPosition() == 0)
                    intent.putExtra("selectedBackground", 1);
                else if (backgroundSpinner.getSelectedItemPosition() == 3)
                    intent.putExtra("selectedBackground", imgView.getTag().toString());
                else
                    intent.putExtra("selectedBackground", backgroundSpinner.getSelectedItemPosition());
                startActivity(intent);
            }
        });

        // start a upload picture activity
        uploadPictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });


    }

    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar1);
        textView = (TextView) findViewById(R.id.textView2);
        button = (Button) findViewById(R.id.button1);
        uploadPictureButton = (Button) findViewById(R.id.buttonLoadPicture);
        modeSpinner = (Spinner) findViewById(R.id.spinner1);
        backgroundSpinner = (Spinner) findViewById(R.id.spinner2);
        imgView = (ImageView) findViewById(R.id.imgView);

        initializeSeekBar();
        initializeModeSpinner();
        initializeBackgroundSpinner();
    }

    private void initializeSeekBar() {

        seekBar.setProgress(seekBar.getMax()/2);
        // Log.d("seekBar.getProgress()", Integer.toString(seekBar.getProgress()));
        textView.setText("You are choosing level: " + (seekBar.getProgress() + miniLevel) + "/" + (seekBar.getMax() + miniLevel));

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                selectedLevel = progressValue + miniLevel;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText("You are choosing level: " + (seekBar.getProgress() + miniLevel) + "/" + (seekBar.getMax() + miniLevel));
            }
        });
    }

    private void initializeModeSpinner() {
        String[] modeStringArray = new String[]{
                "Select a mode...",
                "Dropping Mode",
                "Bouncing Mode"
        };
        final List<String> modeList = new ArrayList<>(Arrays.asList(modeStringArray));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,modeList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0) {
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        modeSpinner.setAdapter(spinnerArrayAdapter);

        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // selectedItemText = (String) parent.getItemAtPosition(position);

                if(position > 0){
                    selectedMode = position;
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + Integer.toString(selectedMode), Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initializeBackgroundSpinner() {
        String[] modeStringArray = new String[]{
                "Select a background...",
                "Green Background",
                "Yellow Background",
                "Upload my own picture"
        };
        final List<String> modeList = new ArrayList<>(Arrays.asList(modeStringArray));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,modeList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0) {
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        backgroundSpinner.setAdapter(spinnerArrayAdapter);

        backgroundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position > 0){
                    selectedBackground = position;
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + Integer.toString(selectedBackground), Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            /*
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                Toast.makeText(this, "Succeed", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }*/
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imgView.setImageBitmap(selectedImage);
                    imgView.setTag(imageUri.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }


}
