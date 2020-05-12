package com.example.trabalho;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    // Informaçoes do Banco de Dados
    private static final String DATABASE_NAME = "celularesdb";
    private static final int DATABASE_VERSION = 1;

    // Nomes das Tabelas
    private static final String TABLE_CELULARES = "celulares";
    private static final String TABLE_MARCAS = "marcas";

    // Colunas da tabela Celulares
    private static final String CEL_ID = "idCelular";
    private static final String CEL_MARCA_ID = "marcaId";
    private static final String CEL_MODELO = "modelo";

    // Colunas da tabela Marcas
    private static final String MARCA_ID = "idMarca";
    private static final String MARCA_NOME = "marca";

    // Singleton Pattern
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context);
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CEL = "create table " + TABLE_CELULARES+
                "(" +
                CEL_ID +" integer primary key autoincrement, "+
                CEL_MARCA_ID +" integer not null references "+ TABLE_MARCAS +", "+
                CEL_MODELO +" varchar(50) not null"+
                ")" ;

        String CREATE_TABLE_MARCAS = "create table "+ TABLE_MARCAS +
                "(" +
                MARCA_ID +" integer primary key autoincrement, "+
                MARCA_NOME + " varchar(50) not null"+
                ")";

        db.execSQL(CREATE_TABLE_CEL);
        db.execSQL(CREATE_TABLE_MARCAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARCAS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CELULARES);
            onCreate(db);
        }
    }


    public void addMarca(Marca marca){
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(MARCA_NOME, marca.getMarca());

            db.insertOrThrow(TABLE_MARCAS, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e){
            Log.d("DatabaseHelper", "Erro ao inserir Marca no Banco de Dados");
        } finally {
            db.endTransaction();
        }
    }

    public List<Marca> listarMarcas(){
        SQLiteDatabase db = getReadableDatabase();
        List<Marca> marcas = new ArrayList<>();

        String query = "select "+ MARCA_NOME +", "+ MARCA_ID + " from " + TABLE_MARCAS;

        Cursor cursor = db.rawQuery(query, null);

        try{
            if (cursor.moveToNext()){
                do {
                    Marca novaMarca = new Marca();
                    novaMarca.setMarca(cursor.getString(0));
                    novaMarca.setIdMarca(cursor.getInt(1));
                    marcas.add(novaMarca);
                } while (cursor.moveToNext());
            }

        }catch (Exception e){
            Log.d("DatabaseHelper", "Erro ao consultar Marca no Banco de Dados");
        } finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return marcas;
    }

    public Marca findMarca(int idMarca){
        SQLiteDatabase db = getReadableDatabase();

        String query = "select "+ MARCA_NOME +", "+ MARCA_ID + " from " + TABLE_MARCAS + " where "+ MARCA_ID+ " = "+idMarca;

        Cursor cursor = db.rawQuery(query, null);

        try{
            if (cursor.moveToFirst() ){
                    Marca marca = new Marca();
                    marca.setMarca(cursor.getString(0));
                    marca.setIdMarca(cursor.getInt(1));
                    return marca;
                }
        }catch (Exception e){
            Log.d("DatabaseHelper", "Erro ao consultar Marca no Banco de Dados");
        } finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return null;
    }

    public void atualizaMarca(Marca marca) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(MARCA_NOME, marca.getMarca());

            db.update(TABLE_MARCAS, values, MARCA_ID +" = "+ marca.getIdMarca(), null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d("DatabaseHelper", "Erro ao atualizar Marca no Banco de Dados");
        }finally {
            db.endTransaction();
        }
    }

    public void excluirMarca(int idMarca){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_MARCAS, MARCA_ID + "=" + idMarca, null);
            db.setTransactionSuccessful();
        }catch (SQLiteConstraintException e){
            Log.d("DatabaseHelper", "Erro ao deletar Marca do Banco de Dados pois está sendo usada em outra tabela");

        }catch (Exception e){
            Log.d("DatabaseHelper", "Erro ao deletar Marca do Banco de Dados");
        } finally {
            db.endTransaction();
        }
    }

    public void addCelular(Celular cel){
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(CEL_MARCA_ID, cel.getMarca().getIdMarca());
            values.put(CEL_MODELO, cel.getModelo());

            db.insertOrThrow(TABLE_CELULARES, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e){
            Log.d("DatabaseHelper", "Erro ao inserir Celular no Banco de Dados");
        }
        finally {
            db.endTransaction();
        }

    }

    public List<Celular> listarCelulares(){
        SQLiteDatabase db = getReadableDatabase();
        List<Celular> celList = new ArrayList<>();

        String query = "select "+ CEL_ID + ","+ CEL_MODELO +", "+ CEL_MARCA_ID +" from "+ TABLE_CELULARES;

        Cursor cursor = db.rawQuery(query, null);

        try{
            if (cursor.moveToNext()){
                do {
                    Celular novoCel = new Celular();
                    novoCel.setIdCel(cursor.getInt(0));
                    novoCel.setModelo(cursor.getString(1));
                    novoCel.setMarca(findMarca(cursor.getInt(2)));
                    celList.add(novoCel);
                } while (cursor.moveToNext());
            }

        }catch (Exception e){
            Log.d("DatabaseHelper", "Erro ao Listar Celulares no Banco de Dados");
        } finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return celList;

    }


    public void excluirCelular(int idCelular) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            db.delete(TABLE_CELULARES, CEL_ID+"="+idCelular,null);
            db.setTransactionSuccessful();

        }catch (Exception e){
            Log.d("DatabaseHelper", "Erro ao deletar Celular do Banco de Dados");
        } finally {
            db.endTransaction();
        }
    }
    public void atualizarCelular(Celular celular) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(CEL_MODELO, celular.getModelo());
            values.put(CEL_MARCA_ID, celular.getMarca().getIdMarca());

            db.update(TABLE_CELULARES, values, CEL_ID +" = "+ celular.getIdCel(), null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d("DatabaseHelper", "Erro ao atualizar Celular no Banco de Dados");
        }finally {
            db.endTransaction();
        }
    }

}

