package com.uis.carmensandiego.carmensandiego;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uis.carmensandiego.carmensandiego.adapter.ConexionesAdapter;
import com.uis.carmensandiego.carmensandiego.model.Caso;
import com.uis.carmensandiego.carmensandiego.model.Pais;
import com.uis.carmensandiego.carmensandiego.model.Viajar;
import com.uis.carmensandiego.carmensandiego.service.CarmenSanDiegoService;
import com.uis.carmensandiego.carmensandiego.service.Connection;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ViajarFragment extends Fragment {

    private List<Pais> conexiones;
    private Caso caso;

    public ViajarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viajar, container, false);

        llenarConexiones(view);

        //Listener para boton viajar
        final ListView lv = (ListView) view.findViewById(R.id.listConexiones);
        //NO ANDA
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String conexionSeleccionada =  lv.getItemAtPosition(position).toString();
                /*Toast toastViajar = Toast.makeText(getContext(), conexionSeleccionada, Toast.LENGTH_SHORT);
                toastViajar.setGravity(Gravity.NO_GRAVITY, 0, 0);
                toastViajar.show();*/
                viajar(conexionSeleccionada);
            }
        });

        return view;
    }

    public void llenarConexiones(View view){
        Caso caso = ((MainActivity) getActivity()).getCaso();
        ListView lvConexiones = (ListView) view.findViewById(R.id.listConexiones);
        List<String> conexionesNombre = this.getNombreConexiones(caso.getPais().getConexiones());
        ConexionesAdapter adapter = new ConexionesAdapter(getActivity(),conexionesNombre);
        lvConexiones.setAdapter(adapter);
    }

    public List<String> getNombreConexiones(List<Pais> pais){
        List<String> nombreConexiones = new ArrayList<>();
        for(Pais p : pais){
            nombreConexiones.add(p.getNombre());
        }
        return nombreConexiones;
    }

    private int getIdPais(List<Pais> conexiones, String paisSeleccionado) {
        int id = 0;
        for(Pais pais : conexiones){
            if (pais.getNombre() == paisSeleccionado){
                id = pais.getId();
            }
        }
        return id;
    }

    public void viajar(final String nombrePaisSeleccionado) {
        Caso caso = ((MainActivity) getActivity()).getCaso();
        int idPaisSeleccionado = getIdPais(caso.getPais().getConexiones(), nombrePaisSeleccionado);

        CarmenSanDiegoService carmenSanDiegoService = new Connection().getService();
        Viajar viajarRequest = new Viajar(idPaisSeleccionado, caso.getId());
        carmenSanDiegoService.viajar(viajarRequest, new Callback<Caso>() {
            @Override
            public void success(Caso caso, Response response) {
                actualizarDatos(caso);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getMessage());
                error.printStackTrace();
            }
        });
    }

    public void actualizarDatos(Caso caso) {
        //Seteo el nuevo caso que me devuelve viajar
        ((MainActivity) getActivity()).setCaso(caso);
        //Actualizo datos
        ((TextView) getActivity().findViewById(R.id.pais_actual)).setText("Estas en " + caso.getPais().getNombre());
        //Muestro un toast
        Toast toastViajar = Toast.makeText(getContext(), "Viajaste a: " + caso.getPais().getNombre(), Toast.LENGTH_SHORT);
        toastViajar.setGravity(Gravity.NO_GRAVITY, 0, 0);
        toastViajar.show();

    }
}

