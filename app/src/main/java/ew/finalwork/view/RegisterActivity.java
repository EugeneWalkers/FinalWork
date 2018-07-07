package ew.finalwork.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ew.finalwork.R;
import ew.finalwork.model.User;
import ew.finalwork.utilities.DataUtility;
import ew.finalwork.utilities.LoginUtility;
import ew.finalwork.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    public static final int RESULT_CODE = 2;

    public static final String LOADING = "loading";
    public static final String TAG = "RegisterActivity";

    private LoadingDialog dialog;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        dialog = LoadingDialog.newInstance(this);
        ((TextView)findViewById(R.id.login_input_register)).setText(getIntent().getStringExtra(LoginActivity.LOGIN_TAG));
        ((TextView)findViewById(R.id.password_input_register)).setText(getIntent().getStringExtra(LoginActivity.PASSWORD_TAG));
        Observer<User> userObserver = user ->{
            dialog.dismiss();
            if (user != null){
                Intent userData = new Intent();
                userData.putExtra(DataUtility.USER_INFO, user);
                setResult(RESULT_CODE, userData);
                Toast.makeText(this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(this, "Error while register!", Toast.LENGTH_SHORT).show();
            }
        };
        viewModel.getUser().observe(this, userObserver);
        setButtons();
    }

    private void setButtons(){
        Button registerUser = findViewById(R.id.register_button);
        registerUser.setOnClickListener(view -> {
            dialog.show(getSupportFragmentManager(), LOADING);
            String login = ((TextView)findViewById(R.id.login_input_register)).getText().toString();
            String password = ((TextView)findViewById(R.id.password_input_register)).getText().toString();
            if (LoginUtility.isLoginValid(login)){
                viewModel.createUser(login, password);
            }
            else{
                Toast.makeText(this, "Invalid login!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
