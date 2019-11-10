package com.webnode.maxsoncm.joysticklinvor;

/**
 * Created by Maxson on 08/02/2016.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class ControleBD {
    private SQLiteDatabase bd;

    public ControleBD(Context context){
        BancoDados auxBd = new BancoDados(context);
        bd = auxBd.getWritableDatabase();
    }


    public void inserir(Obj_Layout ObjLayout){
        ContentValues valores = new ContentValues();

        //valores.put("codigo", ObjLayout.getCodigo());//auto numerico
        valores.put("nome", ObjLayout.getNome());
        valores.put("descricao", ObjLayout.getDescricao());
        valores.put("altura", ObjLayout.getAltura());
        valores.put("largura", ObjLayout.getLargura());
        valores.put("imagem", ObjLayout.getImagem());
        valores.put("descricao", ObjLayout.getBanner());

        bd.insert("layouts", null, valores);
    }


    public void atualizar(Obj_Layout ObjLayout){
        ContentValues valores = new ContentValues();
        valores.put("codigo", ObjLayout.getCodigo());
        valores.put("nome", ObjLayout.getNome());
        valores.put("descricao", ObjLayout.getDescricao());
        valores.put("altura", ObjLayout.getAltura());
        valores.put("largura", ObjLayout.getLargura());
        valores.put("imagem", ObjLayout.getImagem());
        valores.put("descricao", ObjLayout.getBanner());


        bd.update("layouts", valores, "_id = ?", new String[]{"" + ObjLayout.getCodigo()});
    }


    public void deletar(Obj_Layout ObjLayout){
        bd.delete("layouts", "_id = "+ObjLayout.getCodigo(), null);
    }


    public List<Obj_Layout> buscar(){
        List<Obj_Layout> list = new ArrayList<Obj_Layout>();
        String[] colunas = new String[]{"_id", "lay_nome", "lay_descricao","lay_screen","lay_banner"};

        Cursor cursor = bd.query("layouts", colunas, null, null, null, null, "lay_nome ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{

                Obj_Layout l = new Obj_Layout();
                l.setCodigo(cursor.getLong(0));
                l.setNome(cursor.getString(1));
                l.setDescricao(cursor.getString(2));
                l.setImagem(cursor.getBlob(3));
                l.setBanner(cursor.getBlob(4));
                list.add(l);

            }while(cursor.moveToNext());
        }

        return(list);
    }

}
