package ew.finalwork.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import ew.finalwork.R;
import ew.finalwork.utilities.LoginUtility;

public class RegisterActivity extends AppCompatActivity {

    public static final String LOADING = "loading";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ((TextView)findViewById(R.id.login_input_register)).setText(getIntent().getStringExtra(LoginActivity.LOGIN_TAG));
        ((TextView)findViewById(R.id.password_input_register)).setText(getIntent().getStringExtra(LoginActivity.PASSWORD_TAG));
        setButtons();
    }

    private void setButtons(){
        LoadingDialog dialog = LoadingDialog.newInstance(this);
        Button registerUser = findViewById(R.id.register_button);
        registerUser.setOnClickListener(view -> {
            dialog.show(getSupportFragmentManager(), LOADING);
            String login = ((TextView)findViewById(R.id.login_input_register)).getText().toString();
            String password = ((TextView)findViewById(R.id.password_input_register)).getText().toString();
            if (LoginUtility.isLoginValid(login)){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(login, password).addOnCompleteListener(this, task -> {
                    Toast.makeText(RegisterActivity.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                }).addOnFailureListener(this, e -> {
                    Toast.makeText(RegisterActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
            }
            else{
                Toast.makeText(this, "Invalide login!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
