package im.hoho.soulapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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

    private Context context = null;

    private void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {


        if (lpparam.packageName.contains("cn.soulapp.android")) {
            XposedBridge.log("Loaded App: " + lpparam.packageName);
            XposedBridge.log("Powered by HOHO`` 20181215");
            XposedBridge.log("Updated At 20200207——" + "130");
            XposedBridge.log("For Soulmate 3.20.0 Only");




            try {
                XposedHelpers.findAndHookMethod(
                        "cn.soulapp.android.api.model.superstar.bean.RightInfo",
                        lpparam.classLoader,
                        "isHasCancelVIPSubscription",
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param)
                                    throws Throwable {
                                param.setResult(false);
                            }
                        });

                XposedHelpers.findAndHookMethod(
                        "cn.soulapp.android.api.model.superstar.bean.RightInfo",
                        lpparam.classLoader,
                        "isShowSuperVIP",
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param)
                                    throws Throwable {
                                param.setResult(true);
                            }
                        });

                XposedHelpers.findAndHookMethod(
                        "cn.soulapp.android.api.model.superstar.bean.RightInfo",
                        lpparam.classLoader,
                        "isSuperVIP",
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param)
                                    throws Throwable {
                                param.setResult(true);
                            }
                        });

                XposedHelpers.findAndHookMethod(
                        "cn.soulapp.android.api.model.superstar.bean.RightInfo",
                        lpparam.classLoader,
                        "getLeftDay",
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param)
                                    throws Throwable {
                                param.setResult(365);
                            }
                        });

                XposedHelpers.findAndHookMethod(
                        "cn.soulapp.android.api.model.superstar.bean.RightInfo",
                        lpparam.classLoader,
                        "isHasMyMeet",
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param)
                                    throws Throwable {
                                param.setResult(true);
                            }
                        });
            }catch (Exception ex){
                XposedBridge.log("VIP function test failed!");
            }



//            /*************************FOR VIRTUALXPOSED ONLY**************************/
//            XposedHelpers.findAndHookMethod(
//                    "cn.soulapp.android.utils.ae",
//                    lpparam.classLoader,
//                    "a",
//                    Context.class,
//                    new XC_MethodReplacement() {
//                        @Override
//                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
////                            XposedBridge.log("a record method");
//                            return null;
//                        }
//                    });
//            /*************************FOR VIRTUALXPOSED ONLY**************************/

//            /*************************FOR VIRTUALXPOSED ONLY**************************/
//            XposedHelpers.findAndHookMethod(
//                    "cn.soulapp.android.utils.ae",
//                    lpparam.classLoader,
//                    "a",
//                    Context.class,
//                    new XC_MethodReplacement() {
//                        @Override
//                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
////                            XposedBridge.log("a record method");
//                            return false;
//                        }
//                    });
//            /*************************FOR VIRTUALXPOSED ONLY**************************/


            final Class<?> userClass = lpparam.classLoader.loadClass("com.soul.component.componentlib.service.user.bean.User");
//            final Class<?> userClass = lpparam.classLoader.loadClass("cn.soulapp.android.api.model.user.user.bean.User");


            XposedHelpers.findAndHookMethod("cn.soulapp.android.SoulApp", lpparam.classLoader, "attachBaseContext", Context.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("Loading context...");

                    if (param.args[0] != null) {
                        context = (Context) param.args[0];
                        XposedBridge.log("Context is loaded...");

                    }
                }
            });

            try {
                //set block null
                XposedHelpers.findAndHookMethod(
                        "cn.soulapp.android.ui.user.userhome.UserHomeActivity",
                        lpparam.classLoader,
                        "setUser",
                        userClass,
                        new XC_MethodHook() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param)
                                    throws Throwable {
                                XposedHelpers.setBooleanField(param.args[0], "blockedByTarget", false);
                                Long birthday = (Long) XposedHelpers.getObjectField(param.args[0], "birthday");
//                            String avatarName = (String) XposedHelpers.getObjectField(param.args[0], "avatarName");
                                String birthdayStr = new SimpleDateFormat("生日: yyyy-MM-dd").format(new Date(birthday));
                                XposedBridge.log(" birthday: " + birthdayStr);
                                if (context != null) {
                                    @SuppressLint("ShowToast")
                                    Toast toast = Toast.makeText(context, " birthday: " + birthdayStr, Toast.LENGTH_LONG);
                                    showMyToast(toast, 10 * 1000);
                                }
                            }
                        });
            }catch(Exception ex) {
                XposedBridge.log("Birthday failed!");
            }

            try {
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
            }catch (Exception ex){
                XposedBridge.log("Soulmate failed!");
            }

            try {
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
            }catch (Exception ex){
                XposedBridge.log("Snapchat failed!");
            }

            try {
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
            }catch (Exception ex){
                XposedBridge.log("Soulmate btn failed!");
            }

            try {
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
            }catch (Exception ex){
                XposedBridge.log("Call end public failed!");
            }


            try {
                final Class<?> VoiceRtcEngine = lpparam.classLoader.loadClass("cn.soulapp.android.client.component.middle.platform.utils.mediacall.VoiceRtcEngine");

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
            }catch (Exception ex){
                XposedBridge.log("Call match public failed!");
            }

            try {
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
            }catch (Exception ex){
                XposedBridge.log("Unblock failed!");
            }

            try {
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
            }catch (Exception ex){
                XposedBridge.log("Unlimited calls failed!");
            }

//
//            //Match bag 福袋
//            XposedHelpers.findAndHookMethod(
//                    "cn.soulapp.android.ui.planet.index.PlanetBFragment",
//                    lpparam.classLoader,
//                    "showMatchBag",
//                    boolean.class,
//                    boolean.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            XposedBridge.log("show match bag values:" + param.args[0] + ";" + param.args[1]);
//                            param.args[0] = true;
//                            param.args[1] = true;
////                            XposedBridge.log("chages to all true...");
//                        }
//                    });

        }
    }
}
