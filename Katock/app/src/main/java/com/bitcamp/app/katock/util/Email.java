package com.bitcamp.app.katock.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by 1027 on 2018-02-13.
 */

public class Email {
    private Context context;
    private Activity activity;

    public Email(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }
    public void sendEmail(String mail){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto : "+mail));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL,mail);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Hello");
        intent.putExtra(Intent.EXTRA_TEXT,"잘지내냐");
        context.startActivity(intent.createChooser(intent, "이메일"));
    }
}
