package im.hoho.soulapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.TextView;

import java.util.TreeMap;

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
            XposedBridge.log("Updated At 20190719");
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
                                XposedBridge.log("Changing Intent prop canInvite...");
                                localIntent.removeExtra("canInvite");
                                localIntent.putExtra("canInvite", true);
                            }
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


            //no time to calc, just disable as I don't need the snap func
//
//            //Snapchat disable
//            XposedHelpers.findAndHookMethod(
//                    "cn.soulapp.imlib.msg.chat.ChatMessage",
//                    lpparam.classLoader,
//                    "getSnapChat",
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            param.setResult(0);
//                            XposedHelpers.setIntField(param.thisObject, "snapChat", 0);
//                        }
//                    });
//
//            //cn.soulapp.android.ui.planet.callmatch
//            //Set public after call
//            XposedHelpers.findAndHookMethod(
//                    "cn.soulapp.android.ui.planet.callmatch.CallMatchEndActivity",
//                    lpparam.classLoader,
//                    "c",
//                    new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            XposedHelpers.setBooleanField(param.thisObject, "c", true);
//                        }
//                    });


            /*   void a(int paramInt)
  {
    this.a = paramInt;
    if ((cn.soulapp.android.ui.voicecall.c.c().e) && (cn.soulapp.android.ui.voicecall.c.c().f))
    {
      this.statusTv.setText("");
      cn.soulapp.android.ui.voicecall.c.c().l().stopAudioMixing();
      this.statusTv.setBackgroundResource(2131233998);
      if (!this.e) {
        this.B.a(2131298323, false);
        */
            /*
            void a(int paramInt)
  {
    this.a = paramInt;
    if ((VoiceRtcEngine.d().g) && (VoiceRtcEngine.d().h))
    {
      this.statusTv.setText("");
      VoiceRtcEngine.d().n().stopAudioMixing();
      this.statusTv.setBackgroundResource(2131232034);
      if (!this.j) {
        if (!ay.a(this.i))
        {
          this.D.setVisible(2131298622, true);
          this.D.setText(2131298622, this.i);
        }
        else
        {
          this.D.setVisible(2131298622, false);
        }
      }
      this.emojiLayout.setVisibility(0);
      this.addTimeLayout.setVisibility(4);
      this.statusTv.setTextColor(Color.parseColor("#FFFFFF"));
    }
             */

            //show public during voice matching
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.ui.voicecall.VoiceRtcEngine",
                    lpparam.classLoader,
                    "d",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param)
                                throws Throwable {
                            Object returnedObj = param.getResult();
                            XposedHelpers.setBooleanField(returnedObj, "h", true);
                        }
                    });


            //returns null
            //show public during voice matching
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.ui.voicecall.VoiceRtcEngine",
                    lpparam.classLoader,
                    "b",
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            XposedHelpers.setBooleanField(param.thisObject, "h", true);
//                            Intent localIntent = (Intent) XposedHelpers.callMethod(param.thisObject, "getIntent");
//                            if (localIntent != null) {
//                                XposedBridge.log("Changing Intent prop isPublic...");
//                                localIntent.removeExtra("isPublic");
//                                localIntent.putExtra("isPublic", true);
//                            }
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


//            //show public during voice matching
//            XposedHelpers.findAndHookMethod(
//                    "cn.soulapp.android.ui.voicecall.VoiceRtcEngine",
//                    lpparam.classLoader,
//                    "d",
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            Object returnedObj = param.getResult();
//                            if (returnedObj != null)
//                                XposedHelpers.setBooleanField(param.thisObject, "h", true);
//                        }
//                    });


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


            /*
            public static void a()
  {
    cn.soulapp.android.api.model.user.a.a(((a)cn.soulapp.android.api.model.user.a.a(a.class)).c(), new SimpleHttpCallback()
    {
      public void a(RemainTimes paramAnonymousRemainTimes)
      {
        c.c().j = paramAnonymousRemainTimes;
        j.b("������������", new Object[0]);
      }
    }, false);
  }
             */
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.api.model.user.online.a",
                    lpparam.classLoader,
                    "a",
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("a record method");
                            return null;
                        }
                    });


            /*
              public static void b()
  {
    cn.soulapp.android.api.model.user.a.a(((a)cn.soulapp.android.api.model.user.a.a(a.class)).d(), new SimpleHttpCallback()
    {
      public void a(User paramAnonymousUser)
      {
        j.b("������������", new Object[0]);
      }
    }, false);
  }
             */
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.api.model.user.online.a",
                    lpparam.classLoader,
                    "b",
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("b end method");
                            return null;
                        }
                    });

/*
  public static void c()
  {
    cn.soulapp.android.api.model.user.a.a(((a)cn.soulapp.android.api.model.user.a.a(a.class)).e(), new SimpleHttpCallback()
    {
      public void a(User paramAnonymousUser)
      {
        j.b("������������", new Object[0]);
      }
    }, false);
  }
 */
            XposedHelpers.findAndHookMethod(
                    "cn.soulapp.android.api.model.user.online.a",
                    lpparam.classLoader,
                    "c",
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("c break method");
                            return null;
                        }
                    });


            /*
              public static void a(String paramString1, String paramString2)
  {
    cn.soulapp.android.api.model.user.a.a(((a)cn.soulapp.android.api.model.user.a.a(a.class)).a(paramString1, paramString2, c.c().o, c.c().t), new SimpleHttpCallback()
    {
      public void a(Balance paramAnonymousBalance)
      {
        if (paramAnonymousBalance != null)
        {
          c.c().p = paramAnonymousBalance.balance;
          c localc = c.c();
          StringBuilder localStringBuilder;
          if (c.c().p > 10)
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(c.c().p);
            localStringBuilder.append("Soul���������������");
            localStringBuilder.append(c.c().p / 10);
          }
          for (paramAnonymousBalance = "���";; paramAnonymousBalance = "Soul���������������0���")
          {
            localStringBuilder.append(paramAnonymousBalance);
            paramAnonymousBalance = localStringBuilder.toString();
            break;
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(c.c().p);
          }
          localc.q = paramAnonymousBalance;
        }
        j.b("-----������������-----", new Object[0]);
      }
    }, false);
  }
             */

/*
package cn.soulapp.android.api.model.pay.bean;

import java.io.Serializable;

public class PayResult
 */

            /*
            public static void a(String paramString1, String paramString2, boolean paramBoolean) {
    HashMap hashMap = new HashMap();
    hashMap.put("targetUserIdEcpt", paramString1);
    hashMap.put("channelName", paramString2);
    hashMap.put("uuid", (VoiceRtcEngine.e()).r);
    hashMap.put("speedUp", Boolean.valueOf((VoiceRtcEngine.e()).x));
    if ((VoiceRtcEngine.e()).z != null)
      hashMap.put("cardType", Integer.valueOf((VoiceRtcEngine.e()).z.cardType));
    hashMap.put("cardResult", Boolean.valueOf(paramBoolean));
    a.a(((IRobotApi)a.a(IRobotApi.class)).enterChannel(hashMap), new SimpleHttpCallback<Balance>() {
          public void a(Balance param1Balance) {
            if (param1Balance != null) {
              String str;
              (VoiceRtcEngine.e()).s = param1Balance.balance;
              VoiceRtcEngine voiceRtcEngine;
              if (((voiceRtcEngine = VoiceRtcEngine.e()).e()).s > 10) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("������10soul���������������");
                stringBuilder.append((VoiceRtcEngine.e()).s / 10);
                stringBuilder.append("���");
                str = stringBuilder.toString();
              } else {
                str = "������10soul���������������0���";
              }
              voiceRtcEngine.t = str;
            }
            g.b("-----������������-----", new Object[0]);
          }
        }false);
  }
  */


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
                            XposedBridge.log("a string string boolen enterChannel method");
                            XposedBridge.log("param1:" + param.args[0] + " param2:" + param.args[1] + " param3:" + param.args[2]);

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
                            XposedBridge.log("chages to all true...");

                        }
                    });


            XposedBridge.log("Hook function was executed.");
        }
    }
}
