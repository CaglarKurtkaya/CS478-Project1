/*
  Name = Caglar Kurtkaya
  UIN = 661223970
 */
package com.example.ckurtk2_project1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import static android.content.Intent.ACTION_INSERT;

public class MainActivity extends AppCompatActivity {
    //Providing an arbitrary result code for the second activity.
    private static final int REQUEST_CODE = 2;
    protected Button button1;
    protected Button button2;
    protected String returnString;
    protected int RETURN_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Locate the button in activity_main.xml*/
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        /*Set click listener for button1 */
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start Main2Activity
                Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
                startActivityForResult(myIntent,REQUEST_CODE);
            }
        });

        /*Set click listener for button2 */
        button2.setOnClickListener(button2Listener);
    }

    // Listener for the button2
    public View.OnClickListener button2Listener  = new View.OnClickListener() {

        // Called when button2 is selected
        @Override
        public void onClick(View v) {
            if(RETURN_CODE == RESULT_OK){
                //starts the pre-installed contacts app to insert the user input(name) that we got it from the second activity
                Intent addContactIntent = new Intent(ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                addContactIntent.putExtra(ContactsContract.Intents.Insert.NAME, returnString);
                startActivity(addContactIntent);
            }
            else{
                // if RESULT_CODE = RESULT_CANCELED toast an error message.
                Toast.makeText(getApplicationContext(), "Please Enter a Legal Full Name", Toast.LENGTH_SHORT).show();
            }
        }
    } ;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*Check if the second activity returns with RESULT_OK*/
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Log.i("Result code is", " " + resultCode);
                /*Get the String from Intent*/
                returnString = data.getStringExtra("Main2Activity");
                Log.i("Getting String from Second Activity ", returnString);
                RETURN_CODE = RESULT_OK;
            }
            else{
                RETURN_CODE = RESULT_CANCELED;
                Log.i("Result code is", " " + resultCode);
            }
        }
    }
}
