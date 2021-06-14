package com.arga.dompetku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<History> {
    private List<History> historyList;
    private Context mCtx;

    public HistoryAdapter(List<History> P, Context c){
        super(c, R.layout.list_history, P);
        this.historyList = P;
        this.mCtx = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_history,null,true);

        TextView jenis = (TextView) view.findViewById(R.id.textJenis);
        TextView nominal = (TextView) view.findViewById(R.id.textNominal2);
        TextView tanggal = (TextView) view.findViewById(R.id.textTanggal1);
        TextView kegiatan = (TextView) view.findViewById(R.id.textKegiatan1);

        History history = historyList.get(position);
        jenis.setText(history.getJenis());
        nominal.setText(String.valueOf(history.getNominal()));
        tanggal.setText(history.getTanggal());
        kegiatan.setText(history.getKegiatan());

        return view;
    }
}
