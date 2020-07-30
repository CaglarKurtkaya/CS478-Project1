package com.example.ckurtk2_project1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main2Activity extends AppCompatActivity {

    protected EditText myEditText;  // To get user input
    protected String sendString;   // To store user input

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /*Locate the EditText in activity_main.xml*/
        myEditText =  findViewById(R.id.editText);

        /* Set listener for the EditText
          When user clicks on soft done or return this will first validate the user input
          Then, it will send the user input to the main activity.
         */
        myEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL){
                    sendString = myEditText.getText().toString();
                    //Toast an error message if the user enters empty string
                    if(TextUtils.isEmpty(sendString)){
                        Toast.makeText(getApplicationContext(), "Please Enter a Legal Full Name", Toast.LENGTH_SHORT).show();
                    }
                    // user input is not empty now validate
                    else{
                        Intent intent = new Intent();
                        intent.putExtra("Main2Activity", sendString);
                        int validate = validatePersonName(sendString);
                        Log.i("Validate is ", String.valueOf(validate));
                        if(validate == -1) {
                            setResult(RESULT_OK, intent);
                        }
                        else{
                            setResult(RESULT_CANCELED, intent);
                        }
                        finish();
                        return true;
                    }

                }
                return false;
            }
        });
    }


    /**
     * This function will check if User inputs alphabetic characters(A-Z,a-z)/spaces
     * @param userInput
     * @return
     */
    protected int validatePersonName(String userInput){
        String regx =  "^([a-zA-Z]*(\\s)*)+[a-zA-Z]*$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(userInput);
        if(matcher.matches())
        {
            //Return RESULT_OK
            return -1;
        }
        else{
            //if pattern does not matches return RESULT_CANCELED
            return 0;
        }
    }
}
