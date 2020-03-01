package ew.finalwork.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class LoadingDialog extends DialogFragment {


    private Activity activity;

    public static LoadingDialog newInstance(Activity activity) {
        LoadingDialog dialog = new LoadingDialog();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progressDialog = ProgressDialog.show(activity, "Loading", "Please, wait...");
        progressDialog.setCancelable(false);
        progressDialog.setOnDismissListener(dialogInterface -> {

        });
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }


}
