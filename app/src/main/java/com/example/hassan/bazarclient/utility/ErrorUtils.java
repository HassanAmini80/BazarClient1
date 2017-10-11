package com.example.hassan.bazarclient.utility;

import com.example.hassan.bazarclient.models.ErrorModel;
import com.example.hassan.bazarclient.Network.FakeGoodProvider;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by Hassan on 7/4/2017.
 */

public class ErrorUtils {
    public  static ErrorModel parseError(Response<?> response){
        FakeGoodProvider fakeGoodProvider = new FakeGoodProvider();

        Converter<ResponseBody, ErrorModel> converter = fakeGoodProvider.getRetrofitClient().responseBodyConverter(ErrorModel.class, new Annotation[0]);

        ErrorModel errorModel;

        try {
            errorModel = converter.convert(response.errorBody());
        }catch (IOException e){
            return new ErrorModel();
        }

        return errorModel;
    }
}
