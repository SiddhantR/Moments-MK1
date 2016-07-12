package com.s4plabs.logbook.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.s4plabs.logbook.MainActivity;
import com.s4plabs.logbook.R;


public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;


    EditText _passwordText;
    Button _loginButton;
    SharedPreferences share;
    TextView t;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        share = this.getSharedPreferences("Joker",Context.MODE_PRIVATE);
         t=(TextView)findViewById(R.id.tv1);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        String s = share.getString("Name",null);
        t.setText(s);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);
        //Progress Dialog


        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        String pass = share.getString("Password",null);
        if(pass.equals(password)){
            onLoginSuccess();
        }
        else{
            onLoginFailed();
            _passwordText.setError("Wrong Password");
            return;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;


        String password = _passwordText.getText().toString();



        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Wrong Password");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}

