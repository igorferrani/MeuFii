package com.br.meufii.interfaceApp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FiiService {
    @GET("{ativo}/?aba=tabela")
    Call<ResponseBody> htmlPage(@Path("ativo") String ativo);
}
