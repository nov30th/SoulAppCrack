package im.hoho.soulapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by qzj_ on 2016/5/9.
 */
public class PluginMain implements IXposedHookLoadPackage {

    public PluginMain() {
        XposedBridge.log("Now Loading HOHO`` soul app plugin...");
    }


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {


        if (lpparam.packageName.contains("cn.soulapp.android")) {
            XposedBridge.log("Loaded App: " + lpparam.packageName);
            XposedBridge.log("Powered by HOHO`` 20181215");
            XposedBridge.log("Updated At 20190719——" + "79");
            XposedBridge.log("For Soulmate 3.3.2 Only");


            //set soulmate enabled
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.myim.ui.ConversationMenuActivity",
                    lpparam.classLoader,
                    "c",
                    new XC_MethodHook() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            Intent localIntent = (Intent) XposedHelpers.callMethod(param.thisObject, "getIntent");
                            if (localIntent != null) {
//                                XposedBridge.log("Changing Intent prop canInvite...");
                                localIntent.removeExtra("canInvite");
                                localIntent.putExtra("canInvite", true);
                            }
                        }
                    });


            //Snapchat disable
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.imlib.msg.chat.ChatMessage",
                    lpparam.classLoader,
                    "getSnapChat",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param)
                                throws Throwable {
                            param.setResult(0);
                            XposedHelpers.setIntField(param.thisObject, "snapChat", 0);
                        }
                    });

            //set soulmate enabled text
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.myim.ui.ConversationMenuActivity",
                    lpparam.classLoader,
                    "c",
                    new XC_MethodHook() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        protected void afterHookedMethod(MethodHookParam param)
                                throws Throwable {
                            TextView textView = (TextView) XposedHelpers.getObjectField(param.thisObject, "itemSoulmate");
                            textView.setEnabled(true);
                            textView.setText(textView.getText() + "(已破解)");
                        }
                    });


            //cn.soulapp.android.ui.planet.callmatch
            //Set public after call
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.ui.planet.callmatch.CallMatchEndActivity",
                    lpparam.classLoader,
                    "c",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            Intent localIntent = (Intent) XposedHelpers.callMethod(param.thisObject, "getIntent");
                            if (localIntent != null) {
//                                XposedBridge.log("Changing Intent prop isPublic...");
                                localIntent.removeExtra("isPublic");
                                localIntent.putExtra("isPublic", true);
                            }
                        }
                    });

            final Class<?> VoiceRtcEngine = lpparam.classLoader.loadClass("cn.soulapp.android.ui.voicecall.VoiceRtcEngine");

            //returns null
            //show public during voice matching
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.ui.planet.callmatch.CallMatchActivity",
                    lpparam.classLoader,
                    "a",
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            Object VoiceRtcEngineIns = XposedHelpers.callStaticMethod(VoiceRtcEngine, "e");
                            XposedHelpers.setBooleanField(VoiceRtcEngineIns, "h", true);
                        }
                    });

            final Class<?> userClass = lpparam.classLoader.loadClass("cn.soulapp.android.api.model.user.user.bean.User");

            //blockedByTarget
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.ui.user.userhome.UserHomeActivity",
                    lpparam.classLoader,
                    "a",
                    userClass,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            Object obj = param.args[0];
                            boolean isBlocked = XposedHelpers.getBooleanField(obj, "blockedByTarget");
                            if (isBlocked) {
                                XposedBridge.log("Set blocked user to be unblocked.");
                                XposedHelpers.setBooleanField(obj, "blockedByTarget", false);
                            }
                        }
                    });

            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.api.model.user.online.a",
                    lpparam.classLoader,
                    "a",
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
//                            XposedBridge.log("a record method");
                            return null;
                        }
                    });


            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.api.model.user.online.a",
                    lpparam.classLoader,
                    "b",
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
//                            XposedBridge.log("b end method");
                            return null;
                        }
                    });


            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.api.model.user.online.a",
                    lpparam.classLoader,
                    "c",
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
//                            XposedBridge.log("c break method");
                            return null;
                        }
                    });


            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.api.model.user.online.a",
                    lpparam.classLoader,
                    "a",
                    String.class,
                    String.class,
                    boolean.class,
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
//                            XposedBridge.log("a string string boolen enterChannel method");
                            XposedBridge.log("param1:" + param.args[0] + " param2:" + param.args[1] + " param3:" + param.args[2]);

                            return null;
                        }
                    });


            //Match bag 福袋
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.ui.planet.index.PlanetBFragment",
                    lpparam.classLoader,
                    "showMatchBag",
                    boolean.class,
                    boolean.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("show match bag values:" + param.args[0] + ";" + param.args[1]);
                            param.args[0] = true;
                            param.args[1] = true;
//                            XposedBridge.log("chages to all true...");
                        }
                    });

        }
    }
}
