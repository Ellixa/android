package org.elis.jp4application;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

TextView welcomeTW;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_welcome);

       welcomeTW= findViewById(R.id.welcome_tv);

        String mail= getIntent().getStringExtra(MainActivity.WELCOME);

        welcomeTW.setText(getString(R.string.welcome)+" "+mail);

        welcomeTW.setOnClickListener(this);
    }
public void clicca(View view){
    Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto", getIntent().getStringExtra(MainActivity.WELCOME), null));
    startActivity(Intent.createChooser(i, "Choose an Email client : "));

}
    public void onClick(View view){
        if (view.getId() == R.id.welcome_tv) {
            clicca(this.welcomeTW);

        }
    }


}
