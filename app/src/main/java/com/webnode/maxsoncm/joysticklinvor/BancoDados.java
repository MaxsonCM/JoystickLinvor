package com.webnode.maxsoncm.joysticklinvor;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.util.Log;


/**
 * Created by Maxson on 06/07/2016.
 */
public class BancoDados extends SQLiteOpenHelper {
    private static final String BANCO = "dados.db";
    private static final String CAMINHO = "/data/data/com.webnode.maxsoncm.joysticklinvor/"+BANCO;
    private static final String LAYOUTS = "CREATE TABLE IF NOT EXISTS LAYOUTS("+
            "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "lay_altura integer," +
            "lay_largura integer," +
            "lay_nome varchar(20)," +
            "lay_descricao varchar(50)," +
            "lay_stylo varchar(50)," +
            "lay_screen blob," +
            "lay_banner blob);";
    private static final String COMPONENTES = "CREATE TABLE IF NOT EXISTS LAYOUTS("+
            "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "com_layout integer," +
            "com_ordem integer," +
            "com_tipo_componente text(10)," +
            "com_rotulo text(10)," +
            "com_stylo varchar(50)," +
            "com_comando_down varchar(10)," +
            "com_comando_up varchar(10)," +
            "com_fim_comando varchar(5)," +
            "com_altura integer," +
            "com_largura integer," +
            "com_rotacao integer," +
            "com_posicaoX float," +
            "com_posicaoY float," +
            "com_analogico_min float," +
            "com_analogico_max float);";

    private static final int VERSAO = 1;
    private SQLiteDatabase db;
    private Context context;
    static SQLiteDatabase mDB;
    private static BancoDados mInstance = null;
    
    public BancoDados (Context context) {
        super(context, CAMINHO, null, VERSAO);
        this.context = context;
        if (!isCreateDB(db)){
            getWritableDatabase();
        }
        mDB=getWritableDatabase();
    }

    private boolean isCreateDB(SQLiteDatabase db) {
        try{
            db = SQLiteDatabase.openDatabase(CAMINHO, null, SQLiteDatabase.OPEN_READONLY);
            db.close();
            return true;
        }catch(SQLiteException ex){
            return false;
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LAYOUTS);
        db.execSQL(COMPONENTES);

        boolean geraReg=false;
        String query;
        try {
            Cursor c = db.rawQuery("SELECT _id FROM stylos WHERE _id > ? limit 1,1", new String[] {"0"});
            geraReg = c.moveToFirst();
            c.close();
        } catch (SQLException e) {

        }

        if (!geraReg){

            query="insert into stylos (" +
                    " sty_descricao" +
                    " )VALUES(" +
                    " 'Dark'";

            try {db.execSQL(query);} catch (SQLException e) { }

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;
        if (newVersion > oldVersion) {
            /*
            query="alter table componentes add column com_novo_campo text(1);";
            try {db.execSQL(query);} catch (SQLException e) { Log.i("falha", "FALHA na alteração de");}
            */
        }
    }
}
