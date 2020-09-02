package com.zj.common.http.retrofit.netutils;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class StringResponseBodyConverter implements Converter<ResponseBody, String> {

    @Override
    public String convert(ResponseBody value) {
        try {
            return value.toString();
        } finally {
          value.close();
        }
    }
}
