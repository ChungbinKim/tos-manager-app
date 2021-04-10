package com.example.tosmanager.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.tosmanager.R;

public class CreateDialog {
    public static Dialog createPrompt(Context context, CharSequence message, DialogInterface.OnClickListener onClick) {
        return createPromptBuilder(context, onClick).setMessage(message).create();
    }

    public static Dialog createPrompt(Context context, int messageID, DialogInterface.OnClickListener onClick) {
        return createPromptBuilder(context, onClick).setMessage(messageID).create();
    }

    private static AlertDialog.Builder createPromptBuilder(Context context, DialogInterface.OnClickListener onClick) {
        return new AlertDialog.Builder(context)
                .setPositiveButton(R.string.yes, onClick)
                .setNegativeButton(R.string.no, ((dialog, which) -> dialog.cancel()));
    }
}
