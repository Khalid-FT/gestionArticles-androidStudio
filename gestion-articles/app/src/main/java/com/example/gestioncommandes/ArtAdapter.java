package com.example.gestioncommandes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ArtAdapter extends ArrayAdapter<Article>
{
    Context ctx;
    List<Article> articles;

    public ArtAdapter(@NonNull Context context,
                      @NonNull List<Article> objects) {
        super(context, 0, objects);

        ctx = context;
        articles = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView==null)
            convertView = LayoutInflater.from(ctx)
                    .inflate(R.layout.article_item, parent, false);

        TextView txtCodeArt = convertView.findViewById(R.id.txtCodeArt);
        TextView txtLibelle = convertView.findViewById(R.id.txtLibelle);
        TextView txtPuArt = convertView.findViewById(R.id.txtPuArt);
        TextView txtQte = convertView.findViewById(R.id.txtQte);

        Article article = articles.get(position);
        txtCodeArt.setText(String.valueOf(article.getCode()));
        txtLibelle.setText(article.getLibelle());
        txtPuArt.setText(String.valueOf(article.getPu()));
        txtQte.setText(String.valueOf(article.getQte()));

        return convertView;
    }
}
