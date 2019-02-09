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
            XposedBridge.log("Updated At 20190130");


            //set soulmate enabled
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.myim.ui.ConversationMenuActivity",
                    lpparam.classLoader,
                    "b",
                    new XC_MethodHook() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            Intent localIntent = (Intent) XposedHelpers.callMethod(param.thisObject,"getIntent");
                            if (localIntent!=null){
                                XposedBridge.log("Changing Intent prop canInvite...");
                                localIntent.removeExtra("canInvite");
                                localIntent.putExtra("canInvite",true);
                            }
                        }
                    });


            //set soulmate enabled text
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.myim.ui.ConversationMenuActivity",
                    lpparam.classLoader,
                    "b",
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




//
//            //set soulmate enabled
//            XposedHelpers.findAndHookMethod(
//                    "cn.soulapp.android.myim.ui.ConversationMenuActivity",
//                    lpparam.classLoader,
//                    "b",
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            TextView textView = (TextView) XposedHelpers.getObjectField(param.thisObject, "itemSoulmate");
//                            textView.setEnabled(true);
//                            XposedHelpers.callMethod(param.thisObject, "d");
//                        }
//                    });

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
                            XposedHelpers.setBooleanField(param.thisObject, "c", true);
                        }
                    });


            //show public during voice matching
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.ui.voicecall.c",
                    lpparam.classLoader,
                    "c",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param)
                                throws Throwable {
                            Object returnedObj = param.getResult();
                            XposedHelpers.setBooleanField(returnedObj, "f", true);
                        }
                    });

            //show public during voice matching
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.ui.voicecall.c",
                    lpparam.classLoader,
                    "a",
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param)
                                throws Throwable {
                            XposedHelpers.setBooleanField(param.thisObject, "f", true);
                        }
                    });


//            //show public during voice matching
//            XposedHelpers.findAndHookMethod(
//                    "cn.soulapp.android.ui.voicecall.c",
//                    lpparam.classLoader,
//                    "onUserJoined",
//                    int.class,
//                    int.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            Object returnedObj = param.getResult();
//                            XposedHelpers.setBooleanField(param.thisObject, "f", true);
//                        }
//                    });


            //show public during voice matching
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.ui.voicecall.c",
                    lpparam.classLoader,
                    "v",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param)
                                throws Throwable {
                            Object returnedObj = param.getResult();
                            XposedHelpers.setBooleanField(param.thisObject, "f", true);
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

            //match count
            /*
            a() record
              public static void a(String paramString1, String paramString2) enterChannel
  public static void b() end
  public static void c() break

             */
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.api.model.user.online.b",
                    lpparam.classLoader,
                    "a",
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("a record method");
                            return null;
                        }
                    });

            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.api.model.user.online.b",
                    lpparam.classLoader,
                    "b",
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("b end method");
                            return null;
                        }
                    });


            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.api.model.user.online.b",
                    lpparam.classLoader,
                    "c",
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("c break method");
                            return null;
                        }
                    });

            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.api.model.user.online.b",
                    lpparam.classLoader,
                    "a",
                    String.class,
                    String.class,
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("a string string enterChannel method");
                            return null;
                        }
                    });



            //region KeyHash
//            //key
//            XposedHelpers.findAndHookMethod(
//                    "cn.soulapp.android.utils.a.a",
//                    lpparam.classLoader,
//                    "b",
//                    String.class,
//                    String.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            XposedBridge.log("pfx b string string returned:" + param.getResult());
//                        }
//                    });
//
//            //key
//            XposedHelpers.findAndHookMethod(
//                    "cn.soulapp.android.utils.a.a",
//                    lpparam.classLoader,
//                    "a",
//                    String.class,
//                    String.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            XposedBridge.log("pfx a string string returned:" + param.getResult());
//                        }
//                    });
//
//
            //endregion

            //region OLD Code
//            final Class<?> emmessage = lpparam.classLoader.loadClass("com.hyphenate.chat.EMMessage");
//
//            //Snapchat
//            XposedHelpers.findAndHookMethod(
//                    "com.hyphenate.chat.EMMessage",
//                    lpparam.classLoader,
//                    "getIntAttribute",
//                    String.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            if (param.args[0] != null && "snapChat".compareTo(param.args[0].toString())==0 )
//                                XposedBridge.log("getIntAttribute snapChat is called.");
//                            param.setResult(0);
//                        }
//                    });
//
//            //Snapchat
//            XposedHelpers.findAndHookMethod(
//                    "com.hyphenate.chat.EMMessage",
//                    lpparam.classLoader,
//                    "getIntAttribute",
//                    String.class,
//                    int.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            if (param.args[0] != null && "snapChat".compareTo(param.args[0].toString())==0 )
//                                XposedBridge.log("getIntAttribute snapChat is called.");
//                            param.setResult(0);
//                        }
//                    });
//
//
//            XposedHelpers.findAndHookMethod(
//                    "com.hyphenate.chat.EMChatManager",
//                    lpparam.classLoader,
//                    "setMessageListened",
//                    emmessage,
//                    new XC_MethodReplacement() {
//                        @Override
//                        protected Object replaceHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            XposedBridge.log("setMessageListened is called.");
//                            return null;
//                        }
//                    });
//
//            XposedHelpers.findAndHookMethod(
//                    "com.hyphenate.chat.EMChatManager",
//                    lpparam.classLoader,
//                    "setVoiceMessageListened",
//                    emmessage,
//                    new XC_MethodReplacement() {
//                        @Override
//                        protected Object replaceHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            XposedBridge.log("setVoiceMessageListened is called.");
//                            return null;
//                        }
//                    });
//
//
//
//            XposedHelpers.findAndHookMethod(
//                    "com.hyphenate.chat.EMChatManager",
//                    lpparam.classLoader,
//                    "ackMessageRead",
//                    String.class,
//                    String.class,
//                    new XC_MethodReplacement() {
//                        @Override
//                        protected Object replaceHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            XposedBridge.log("ackMessageRead is called.");
//                            return null;
//                        }
//                    });
            //endregion

            //region OLD RECALL
//            //RECALL
//            XposedHelpers.findAndHookMethod(
//                    "com.hyphenate.chat.EMMessage",
//                    lpparam.classLoader,
//                    "getStringAttribute",
//                    String.class,
//                    String.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            if (param.args[0] != null && "Revokeflag".equals(param.args[0].toString())) {
//                                XposedBridge.log("getAttribute Revokeflag is called.");
//                                param.setResult(null);
//                            }
//                        }
//                    });
//
//            //RECALL
//            XposedHelpers.findAndHookMethod(
//                    "com.hyphenate.chat.EMMessage",
//                    lpparam.classLoader,
//                    "getStringAttribute",
//                    String.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            if (param.args[0] != null && "Revokeflag".equals(param.args[0].toString())) {
//                                XposedBridge.log("getAttribute Revokeflag is called.");
//                                param.setResult(null);
//                            }
//                        }
//                    });
//
//            //RECALL
//            XposedHelpers.findAndHookMethod(
//                    "com.hyphenate.chat.EMMessage",
//                    lpparam.classLoader,
//                    "getStringAttribute",
//                    String.class,
//                    String.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            if (param.args[0] != null && "messageType".equals(param.args[0].toString())) {
//                                if (param.getResult() != null && param.getResult().toString().equals("RECALL")) {
//                                    XposedBridge.log("getAttribute RECALL is called.");
//                                    param.setResult("TXT");
//                                }
//                            }
//                        }
//                    });
//
//            //RECALL
//            XposedHelpers.findAndHookMethod(
//                    "com.hyphenate.chat.EMMessage",
//                    lpparam.classLoader,
//                    "getStringAttribute",
//                    String.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            if (param.args[0] != null && "messageType".equals(param.args[0].toString())) {
//                                if (param.getResult() != null && param.getResult().toString().equals("RECALL")) {
//                                    XposedBridge.log("getAttribute RECALL is called.");
//                                    param.setResult("TXT");
//                                }
//                            }
//                        }
//                    });
//
//            //Snapchat
//            XposedHelpers.findAndHookMethod(
//                    "com.hyphenate.chat.EMMessage",
//                    lpparam.classLoader,
//                    "getIntAttribute",
//                    String.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            if (param.args[0] != null && "snapChat".equals(param.args[0].toString())) {
//                                XposedBridge.log("getIntAttribute snapChat is called.");
//                                param.setResult(0);
//                            }
//                        }
//                    });
//
//            //Snapchat
//            XposedHelpers.findAndHookMethod(
//                    "com.hyphenate.chat.EMMessage",
//                    lpparam.classLoader,
//                    "getIntAttribute",
//                    String.class,
//                    int.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            if (param.args[0] != null && "snapChat".equals(param.args[0].toString())) {
//                                XposedBridge.log("getIntAttribute snapChat is called.");
//                                param.setResult(0);
//                            }
//                        }
//                    });
            //endregion

            XposedBridge.log("Hook function was executed.");
        }
    }
}
