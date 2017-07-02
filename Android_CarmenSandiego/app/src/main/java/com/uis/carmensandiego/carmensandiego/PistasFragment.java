package com.uis.carmensandiego.carmensandiego;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.uis.carmensandiego.carmensandiego.adapter.LugaresAdapter;
import com.uis.carmensandiego.carmensandiego.model.Caso;
import com.uis.carmensandiego.carmensandiego.model.Pista;
import com.uis.carmensandiego.carmensandiego.service.CarmenSanDiegoService;
import com.uis.carmensandiego.carmensandiego.service.Connection;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PistasFragment extends Fragment {

    private ListView lvLugares;

    public PistasFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pistas, container, false);
        obtenerLugares(view);

        //Listener para boton obtener pista
        final ListView lv = (ListView) view.findViewById(R.id.listLugares);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String lugarSeleccionado =  lv.getItemAtPosition(position).toString();
                obtenerPista(lugarSeleccionado);
            }
        });

        return view;
    }

    public void obtenerLugares(View view) {

        Caso caso = ((MainActivity) getActivity()).getCaso();
        lvLugares = (ListView) view.findViewById(R.id.listLugares);
        LugaresAdapter adapter = new LugaresAdapter(getActivity(),caso.getPais().getLugares());
        lvLugares.setAdapter(adapter);
    }

    public void obtenerPista(String lugarSeleccionado) {
        Caso caso = ((MainActivity) getActivity()).getCaso();

        CarmenSanDiegoService carmenSanDiegoService = new Connection().getService();
        carmenSanDiegoService.getPista(caso.getId(), lugarSeleccionado, new Callback<Pista>(){
            @Override
            public void success(Pista pista, Response response) {
                mostrarPista(pista);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getMessage());
                error.printStackTrace();
            }
        });
    }

    public void mostrarPista(Pista pista){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert);
        if(pista.getResultadoOrden() == null) {
            builder.setTitle("Pista obtenida: ")
                    .setMessage(pista.getPista())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // close alerts
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
        else {
            builder.setTitle("Resultado del juego: ")
                    .setMessage(pista.getResultadoOrden())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // close alerts
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
    }
}

