# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#-obfuscationdictionary key.txt
#-classobfuscationdictionary key.txt
#-packageobfuscationdictionary key.txt

-ignorewarnings

#=====================okhttputils框架=====================
#====okhttputils====
#android Studio环境中不需要，eclipse环境中需要
#-libraryjars libs/okhttputils.jar
-dontwarn com.zhy.http.**
-keep class  com.zhy.http.**{*;}
-keep interface com.zhy.http.**{*;}

#====okhttp====
#android Studio环境中不需要，eclipse环境中需要
#-libraryjars libs/okhttp-2.7.0.jar
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-keep interface okhttp3.**{*;}

-keepattributes Signature
-keepattributes *Annotation*
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.**{*;}
-keep interface com.squareup.okhttp.**{*;}

#====okio====
#android Studio环境中不需要，eclipse环境中需要
#-libraryjars libs/okio-1.6.0.jar
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**
-keep class okio.**{*;}
-keep interface okio.**{*;}

#====gson====
#android Studio环境中不需要，eclipse环境中需要
#-libraryjars libs/gson-2.2.4.jar
#-keep class sun.misc.Unsafe{*;}
#-dontwarn com.google.gson.**
#-keep class com.google.gson.**{*;}
#-keep class com.google.gson.stream.**{*;}
#这一段包名应该是你所有的java bean　定义的目录
#-keep class com.google.gson.examples.android.com.good.smarteye.glasses.model.**{*;}

-dontwarn org.apache.http.**
-keep class org.apache.http.** { *;}

##---------------Begin: proguard configuration for Gson ----------
-keep public class com.google.gson.**
-keepclassmembers class com.google.gson.** {public private protected *;}

-keepattributes Signature
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

#-keep public class com.project.Bean { private *; }

# 或者将所有被gson使用的变量都加下面声明，这样gson就可以识别对应的变量
#@SerializedName("name")

##---------------End: proguard configuration for Gson ----------


#1.Serializable 的配置
# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * implements java.io.Serializable {*;}

#GreenDao 的混淆配置 BegainJavaNative
-keep class org.greenrobot.greendao.**{*;}
-keep public interface org.greenrobot.greendao.**
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
-keep class net.sqlcipher.database.**{*;}
-keep public interface net.sqlcipher.database.**
-dontwarn net.sqlcipher.database.**
-dontwarn org.greenrobot.greendao.**

-keep class de.greenrobot.dao.** {*;}
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static Java.lang.String TABLENAME;
}

#增加如下：
-keep class data.db.dao.*$Properties {
    public static <fields>;
}
-keepclassmembers class data.db.dao.** {
    public static final <fields>;
  }
#GreenDao 的混淆配置 End


#butterknife 的混淆配置 Begain
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
#butterknife 的混淆配置 End


#eventbus 的混淆配置 Begain
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#eventbus 的混淆配置 End


-keep public class pl.droidsonroids.gif.GifIOException{<init>(int, java.lang.String);}


# retrofit 和 rxjava 的混淆配置 Begain

-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
#-keep class org.xz_sale.entity.**{*;}//这是你定义的实体类

# retrofit 和 rxjava 的混淆配置 End


# Understand the @Keep support annotation. Start
-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

#support annotation End


# greenDAO开始
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use RxJava:
-dontwarn rx.**
# greenDAO结束


# 如果按照上面介绍的加入了数据库加密功能，则需添加一下配置
#sqlcipher数据库加密开始
-keep  class net.sqlcipher.** {*;}
-keep  class net.sqlcipher.database.** {*;}

#sqlcipher数据库加密结束

#### utdid 混淆配置 start
-keep class com.ut.** {*;}
-dontwarn com.ut.**
-keep class com.ta.** {*;}
-dontwarn com.ta.**
#### utdid 混淆配置 end

-assumenosideeffects class android.common.baselibrary.log.MLog{
      <fields> ;
      public static void d(...);
      public static void w(...);
      public static void e(...);
      public static void wtf(...);
      private static StackTraceElement getCallerStackTraceElement();
      public static void point(...);
      public static void createDipPath(...);
      private static String generateTag(...);
      private static class ReusableFormatter;
      public interface CustomLogger;
      private static final ThreadLocal<*> *;
      public static String format(...);
      public static boolean isSDAva();
}

-keepclasseswithmembers class com.android.common.baselibrary.util.comutil.security.RSAUtils{
     #public static java.lang.String sign(byte[], java.lang.String);
     public static java.lang.String sign(byte[], java.security.PrivateKey);
     #public static java.lang.String encDataByRSA(java.lang.String, java.lang.String);
     public static java.lang.String encDataByRSA(java.lang.String, java.security.PrivateKey);
     public static java.lang.String decDataByRSA(java.lang.String, java.security.PublicKey);
}

#-keepclasseswithmembers class com.android.common.baselibrary.util.comutil.security.Base64Utils{
#      public static byte[] decode(java.lang.String);
#      public static java.lang.String encode(byte[]);
# }

#-keepclassmembers class com.android.common.baselibrary.jnative.JavaNative{*;}
