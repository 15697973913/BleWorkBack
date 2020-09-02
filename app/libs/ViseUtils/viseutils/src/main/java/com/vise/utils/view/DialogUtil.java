package com.vise.utils.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.Button;

public class DialogUtil {

    public static AlertDialog.Builder dialogBuilder(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (msg != null) {
            builder.setMessage(msg);
        }
        if (title != null) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static AlertDialog.Builder dialogBuilder(Context context, String title, String msg, int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (msg != null) {
            builder.setMessage(Html.fromHtml(msg));
        }
        if (title != null) {
            builder.setTitle(title);
        }
        return builder;
    }


    public static AlertDialog.Builder dialogBuilder(Context context, int title, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (view != null) {
            builder.setView(view);
        }
        if (title > 0) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static AlertDialog.Builder dialogBuilder(Context context, int titleResId, int msgResId) {
        String title = titleResId > 0 ? context.getResources().getString(titleResId) : null;
        String msg = msgResId > 0 ? context.getResources().getString(msgResId) : null;
        return dialogBuilder(context, title, msg);
    }

    public static Dialog showTips(Context context, String title, String des) {
        Dialog dialog= showTips(context, title, des, null, null);
        setConfirmColor(dialog);

        return dialog;
    }

    public static Dialog showTips(Context context, int title, int des) {
        Dialog dialog=showTips(context, context.getString(title), context.getString(des));
        setConfirmColor(dialog);

        return dialog;
    }

    public static Dialog showTips(Context context, int title, int des, int btn, DialogInterface.OnDismissListener
            dismissListener) {
        Dialog dialog=showTips(context, context.getString(title), context.getString(des), context.getString(btn),
                dismissListener);
        setConfirmColor(dialog);
        return dialog;
    }

    public static Dialog showTips(Context context, String title, String des, String btn, DialogInterface
            .OnDismissListener dismissListener) {
        AlertDialog.Builder builder = dialogBuilder(context, title, des);
        builder.setCancelable(true);
        builder.setPositiveButton(btn, null);

        Dialog dialog = builder.create();
        setConfirmColor(dialog);
        if (!dialog.isShowing()) {
            dialog.show();
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(dismissListener);
        return dialog;
    }

    public static Dialog showTips(Context context, String title, String des, String leftBtn, String rightBtn, DialogInterface
            .OnDismissListener dismissListener) {
        AlertDialog.Builder builder = dialogBuilder(context, title, des);
        builder.setCancelable(true);
        builder.setPositiveButton(rightBtn, null);
        builder.setNegativeButton(leftBtn,null);

        Dialog dialog = builder.create();
        setConfirmColor(dialog);
        if (!dialog.isShowing()) {
            dialog.show();
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(dismissListener);
        return dialog;
    }

    private static void setConfirmColor(Dialog dialog) {
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {                    //
                Button neutralButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_NEUTRAL);
                Button negativeButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_NEGATIVE);
                Button positiveButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.BLUE);
                neutralButton.setTextColor(Color.BLUE);
                negativeButton.setTextColor(Color.BLUE);
            }
        });

    }
}
