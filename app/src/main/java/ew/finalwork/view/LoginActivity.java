package ew.finalwork.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ew.finalwork.R;
import ew.finalwork.model.User;
import ew.finalwork.utilities.LoginUtility;
import ew.finalwork.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    LoginViewModel viewModel;
    private FirebaseAuth mAuth;
    LoadingDialog dialog;
    public static final String LOADING_DIALOG_TAG = "loading_dialog_tag";
    private static final String LOGIN_TAG = "loading_dialog_tag";
    private static final String PASSWORD_TAG = "loading_dialog_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        dialog = LoadingDialog.newInstance(this);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            viewModel.readUserFromBase(currentUser);
        }
        setContentView(R.layout.activity_login);
        setButtonLogin();
        Observer<User> observerUser = user -> {
            if (user != null) {
                Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
                goToMain.putExtra("user", user);
                startActivity(goToMain);
            }
            else{
                Toast.makeText(this, "Error sign in!", Toast.LENGTH_SHORT).show();
            }
            Log.i("lololo", "smth changed");
        };
        Observer<Boolean> isDialogShowObserver = aBoolean -> {
            if (aBoolean){
                dialog.show(getSupportFragmentManager(), LOADING_DIALOG_TAG);
            }
            else{
                dialog.dismiss();
            }
        };
        LiveData<User> user = viewModel.getUser();
        user.observe(this, observerUser);
        LiveData<Boolean> dialogLiveData = viewModel.getIsDialogShow();
        dialogLiveData.observe(this, isDialogShowObserver);
        Log.i("End", "End");
    }

    private void setButtonLogin() {
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(view -> {
            String login = ((EditText) findViewById(R.id.login_input)).getText().toString();
            String password = ((EditText) findViewById(R.id.password_input)).getText().toString();
            if (LoginUtility.isValid(login)) {
                dialog.show(getSupportFragmentManager(), LOADING_DIALOG_TAG);
                viewModel.onLoginClicked(login, password);
            } else {
                Toast.makeText(LoginActivity.this, "Invalid login!", Toast.LENGTH_SHORT).show();
            }

        });
        TextView registerButton = findViewById(R.id.register_button_login);
        registerButton.setOnClickListener(view -> {
            Intent goToRegister = new Intent(LoginActivity.this, RegisterActivity.class);
            String login = ((EditText) findViewById(R.id.login_input)).getText().toString();
            String password = ((EditText) findViewById(R.id.password_input)).getText().toString();
            goToRegister.putExtra(LOGIN_TAG, login);
            goToRegister.putExtra(PASSWORD_TAG, password);
            startActivity(goToRegister);
        });

    }

}
