package com.abdullahdemirer.smartlamp.HelperClasses;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AlertDialogMessage {
    public static void showSuccesAlert(Context context, String title){
        SweetAlertDialog alertDialog = new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE);
        alertDialog.setTitle(title);
        alertDialog.show();
    }
    public static void showWarningAlert(final Context context, String title, String contentText, String confirmText){
        SweetAlertDialog alertDialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        alertDialog.setTitle(title);
        alertDialog.setContentText(contentText);
        alertDialog.setConfirmText(confirmText);
        alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        }).show();
    }
    public static void showErrorAlert(Context context, String title,String contentText){
        SweetAlertDialog alertDialog = new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE);
        alertDialog.setTitle(title);
        alertDialog.setContentText(contentText);
        alertDialog.show();
    }
    public static void showLoadingAlert(Context context,String title){
        SweetAlertDialog alertDialog = new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        alertDialog.setTitleText(title);
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

}
