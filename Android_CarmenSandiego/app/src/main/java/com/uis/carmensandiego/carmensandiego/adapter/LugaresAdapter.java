package com.uis.carmensandiego.carmensandiego.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.uis.carmensandiego.carmensandiego.R;

import org.w3c.dom.Text;

import java.util.List;

public class LugaresAdapter extends ArrayAdapter<String> {

    private List<String> lugares;

    public LugaresAdapter(Context context, List<String> lugares) {
        super(context, R.layout.fragment_pistas, lugares);
        this.lugares = lugares;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.row_pistas, null);

        TextView lugar = (TextView) item.findViewById(R.id.lugar);
        lugar.setText(lugares.get(position));

        return item;
    }
}
