package ew.finalwork.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import ew.finalwork.R;
import ew.finalwork.utilities.LoginUtility;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    private void setButtons(){
        Button registerUser = findViewById(R.id.register_button);
        registerUser.setOnClickListener(view -> {
            String name = ((TextView)findViewById(R.id.name_input_register)).getText().toString();
            String login = ((TextView)findViewById(R.id.login_input_register)).getText().toString();
            String password = ((TextView)findViewById(R.id.password_input_register)).getText().toString();
            if (LoginUtility.isLoginValid(login)){
                createNewUser(name, login, password);
            }

        });
    }

    private void createNewUser(String name, String login, String password){

    }
}
