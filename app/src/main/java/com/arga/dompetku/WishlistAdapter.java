package com.arga.dompetku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WishlistAdapter extends ArrayAdapter<Wishlist> {
    private List<Wishlist> wishList;
    private Context mCtx;

    public WishlistAdapter(List<Wishlist> P, Context c){
        super(c, R.layout.list_wishlist, P);
        this.wishList = P;
        this.mCtx = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_wishlist,null,true);

        TextView kegiatan = (TextView) view.findViewById(R.id.textKegiatan);
        TextView tahun = (TextView) view.findViewById(R.id.textTahun);
        TextView nominal = (TextView) view.findViewById(R.id.textNominal1);

        Wishlist wishlist = wishList.get(position);
        kegiatan.setText(wishlist.getWish());
        tahun.setText(String.valueOf(wishlist.getTahun()));
        nominal.setText(String.valueOf(wishlist.getHarga()));

        return view;
    }
}
