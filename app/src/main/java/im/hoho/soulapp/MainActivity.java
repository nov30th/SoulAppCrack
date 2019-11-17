package im.hoho.soulapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //    private Switch switchEnableMatchSee;
//    private Switch switchEnableSoul;
//
//
//    private SharedPreferences sp;
//    private SharedPreferences.Editor editor;
    private ListView listview;
    private TextView textView;

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listview);
        textView = (TextView) findViewById(R.id.textView2);

        textView.setText("适用于版本3.4.3，默认已经启用如下功能。");

        listview.setDividerHeight(0);//屏蔽掉listview的横线
        listview.setDivider(null);

        String[] listContent = {"无限语音匹配.",
                "无限语音优先匹配次数.",
                "无线语音匹配优质对手（3.3.2新功能）",
                "接通语音匹配瞬间显示对方资料",
                "与任意人结成Soulmate并激活Soulmate空间",
                "闪照，闪视频以普通图片及视频显示，无时间限制.",
                "修复原生SoulApp无法在VirtualXposed显示朋友列表问题",
                "获得对方生日信息"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                listContent);
        listview.setAdapter(adapter);
//        sp = getSharedPreferences("prefs", Activity.MODE_WORLD_READABLE);
//        editor = sp.edit();
//
//
//        editor.apply();
//
//
//        switchEnableSoul = (Switch) findViewById(R.id.switchEnableSoul);
//        if (sp.getString("switchEnableSoul", "true").equals("true"))
//            switchEnableSoul.setChecked(true);
//        switchEnableMatchSee = (Switch) findViewById(R.id.switchEnableMatchSee);
//        if (sp.getString("switchEnableMatchSee", "true").equals("true"))
//            switchEnableMatchSee.setChecked(true);
//
//        switchEnableSoul.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                editor.putString("switchEnableMatchSee", Boolean.toString(isChecked).toLowerCase());
//                editor.apply();
//            }
//        });
//
//        switchEnableSoul.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                editor.putString("switchEnableSoul", Boolean.toString(isChecked).toLowerCase());
//                editor.apply();
//            }
//        });


    }
}
