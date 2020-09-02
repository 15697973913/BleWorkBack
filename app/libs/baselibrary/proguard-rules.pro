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
}

#-keepclasseswithmembers class com.android.common.baselibrary.util.comutil.security.Base64Utils{
#      public static byte[] decode(java.lang.String);
#      public static java.lang.String encode(byte[]);
# }

#-keepclassmembers class com.android.common.baselibrary.jnative.JavaNative{*;}