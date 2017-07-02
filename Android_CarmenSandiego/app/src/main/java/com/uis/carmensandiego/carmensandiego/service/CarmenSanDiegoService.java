package com.uis.carmensandiego.carmensandiego.service;

import com.uis.carmensandiego.carmensandiego.bodies.EmitirOrdenBody;
import com.uis.carmensandiego.carmensandiego.bodies.ViajarBody;
import com.uis.carmensandiego.carmensandiego.model.Caso;
import com.uis.carmensandiego.carmensandiego.model.OrdenEmitida;
import com.uis.carmensandiego.carmensandiego.model.Pista;
import com.uis.carmensandiego.carmensandiego.model.Viajar;
import com.uis.carmensandiego.carmensandiego.model.Villano;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface CarmenSanDiegoService {
    @POST("/iniciarJuego")
    void iniciarJuego(Callback<Caso> callback);

    @GET("/villanos")
    void getVillanos(Callback<List<Villano>> callback);

    @GET("/pistaDelLugar/{casoId}/{nombreLugar}")
    void getPista(@Path("casoId") int idCaso, @Path("nombreLugar") String lugar, Callback<Pista> callback);

    @POST("/emitirOrden")
    void emitirOrdenPara(@Body OrdenEmitida ordenEmitida, Callback<String> callback);

    @POST("/viajar")
    void viajar(@Body Viajar viajar, Callback<Caso> callback);
}
