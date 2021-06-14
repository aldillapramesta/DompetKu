package com.arga.dompetku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PendapatanAdapter extends ArrayAdapter<History> {
    private List<History> pendapatanList;
    private Context mCtx;

    public PendapatanAdapter(List<History> P, Context c){
        super(c, R.layout.list_pendapatan, P);
        this.pendapatanList = P;
        this.mCtx = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_pendapatan,null,true);

        TextView nominal = (TextView) view.findViewById(R.id.textNominal);
        TextView tanggal = (TextView) view.findViewById(R.id.textTanggal);

        History history = pendapatanList.get(position);
        nominal.setText(String.valueOf(history.getNominal()));
        tanggal.setText(history.getTanggal());

        return view;
    }
}
