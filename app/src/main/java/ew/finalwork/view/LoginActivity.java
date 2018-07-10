package ew.finalwork.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ew.finalwork.R;
import ew.finalwork.receivers.NetworkChangeReceiver;
import ew.finalwork.utilities.DataUtility;
import ew.finalwork.utilities.LoginUtility;
import ew.finalwork.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    LoginViewModel viewModel;
    LoadingDialog dialog;
    public static final String TAG = "login_activity";
    public static final String LOADING_DIALOG_TAG = "loading_dialog_tag";
    public static final String LOGIN_TAG = "login_tag";
    public static final String PASSWORD_TAG = "password_tag";
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        setButtonLogin();
        dialog = LoadingDialog.newInstance(this);
        Observer<String> observerUserState = userState -> {
            if (userState != null) {
                Log.i(TAG, userState);
                switch (userState){
                    case LoginViewModel.NOT_SIGNED:
                        break;
                    case LoginViewModel.SIGNING:
                        dialog.show(getSupportFragmentManager(), LOADING_DIALOG_TAG);
                        break;
                    case LoginViewModel.ERROR_SIGNIN:
                        dialog.dismiss();
                        Toast.makeText(this, "Error sign in!", Toast.LENGTH_SHORT).show();
                        break;
                    case LoginViewModel.SUCCESS_SIGNIN:

                        Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
                        goToMain.putExtra(DataUtility.USER_INFO, viewModel.getUser());
                        startActivityForResult(goToMain, REQUEST_CODE);
                        dialog.dismiss();
                        registerReceiver(new NetworkChangeReceiver(), new IntentFilter(NetworkChangeReceiver.SYNC_COMMAND));
                        finish();
                        break;
                }
            }
        };
        LiveData<String> user = viewModel.getUserState();
        user.observe(this, observerUserState);
        if (viewModel.getFireUser() != null){
            viewModel.readUserFromBase(viewModel.getFireUser());
        }
    }

    private void setButtonLogin() {
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(view -> {
            String login = ((EditText) findViewById(R.id.login_input)).getText().toString();
            String password = ((EditText) findViewById(R.id.password_input)).getText().toString();
            if (LoginUtility.isLoginValid(login) && LoginUtility.isPasswordValid(password)) {
                viewModel.onLoginClicked(login, password, this);
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
            startActivityForResult(goToRegister, REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RegisterActivity.RESULT_CODE){
            switch (requestCode){
                case REQUEST_CODE:
                    data.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(data);
                    break;
                default:
                    Log.i(TAG, "Error with starting activity after register");
                    break;
            }
        }
    }
}
