package naveen.quizzers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Naveen on 10/8/2015.
 */

public class MyDialogFragment extends DialogFragment {
    Context mContext;
    String title, message;

    public MyDialogFragment() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle a = getArguments();
        String title = a.getString("title");
        String message = a.getString("message");
        String context = a.getString("context");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        switch (context) {
            case "register":
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getActivity(), Login.class);
                        startActivity(i);
                        getActivity().finish();
                    }

                });
                break;
            case "register_error":
            case "login_empty":
            case "register_empty":
                alertDialogBuilder.setPositiveButton("OK", null);
                break;
            case "login":
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getActivity(), Questions.class);
                        startActivity(i);
                        getActivity().finish();
                    }

                });
                break;
            case "login_error":
                alertDialogBuilder.setPositiveButton("OK", null);
                break;
        }

        alertDialogBuilder.setCancelable(false);
        return alertDialogBuilder.create();
    }
}
