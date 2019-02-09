package im.hoho.soulapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Switch switchEnableMatchSee;
    private Switch switchEnableSoul;


    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("prefs", Activity.MODE_WORLD_READABLE);
        editor = sp.edit();


        editor.apply();


        switchEnableSoul = (Switch) findViewById(R.id.switchEnableSoul);
        if (sp.getString("switchEnableSoul", "true").equals("true"))
            switchEnableSoul.setChecked(true);
        switchEnableMatchSee = (Switch) findViewById(R.id.switchEnableMatchSee);
        if (sp.getString("switchEnableMatchSee", "true").equals("true"))
            switchEnableMatchSee.setChecked(true);

        switchEnableSoul.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putString("switchEnableMatchSee", Boolean.toString(isChecked).toLowerCase());
                editor.apply();
            }
        });

        switchEnableSoul.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putString("switchEnableSoul", Boolean.toString(isChecked).toLowerCase());
                editor.apply();
            }
        });


    }
}
