package com.example.tcc.dados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BancoDados extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "type_me.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CARACTERISTICAS = "caracteristicas";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_ID = "user_id";

    public static final String COLUMN_SESSION_ID = "session_id";
    public static final String COLUMN_KEY = "character";
    public static final String COLUMN_DOWN_TIME = "down_time";
    public static final String COLUMN_UP_TIME = "up_time";


    public static final String COLUMN_REPETITION = "repetition";

    public static final String TABLE_USERS = "users";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_YEAR = "year";

    public static final String COLUMN_COLLECTED_DATA = "collected_data";

    public static final String TABLE_STATISTICS = "statistics";

    public static final String COLUMN_TOTAL = "total";


    // Operacoes sql
    private static final String CREATE_TABLE_CARACTERISTICAS = "create table "
            + TABLE_CARACTERISTICAS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_USER_ID + " integer, "
            + COLUMN_SESSION_ID + " integer, " + COLUMN_KEY + " text, " + COLUMN_DOWN_TIME + " integer, "
            + COLUMN_UP_TIME + " integer, " + COLUMN_REPETITION + " integer" + ");";

    private static final String CREATE_TABLE_USERS = "create table "
            + TABLE_USERS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME + " text, " + COLUMN_USER_ID + " integer, "
            + COLUMN_SESSION_ID + " integer, " + COLUMN_GENDER + " text, "
            + " text, " + COLUMN_YEAR + " text," + COLUMN_COLLECTED_DATA + " integer" + ");";

    private static final String CREATE_TABLE_STATISTICS = "create table "
            + TABLE_STATISTICS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_USER_ID + " integer, " + COLUMN_SESSION_ID + " integer, " + COLUMN_TOTAL + " integer"
            + ");";

    private Context context;
    private SQLiteDatabase database;

    public BancoDados(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        database = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        this.database = database;

        database.execSQL(CREATE_TABLE_CARACTERISTICAS);
        database.execSQL(CREATE_TABLE_USERS);
        database.execSQL(CREATE_TABLE_STATISTICS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARACTERISTICAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTICS);
        onCreate(db);
    }

    // apagar todas as tabelas da base de dados
    public void deleted() {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CARACTERISTICAS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTICS);
        onCreate(database);

    }


    // metodo auxiliar para inserir dados na base de dados
    public void insertKeyData(Caracter cd, int rep) {

        ContentValues values = new ContentValues();
        values.put(BancoDados.COLUMN_USER_ID, cd.getIdUsuario());
        values.put(BancoDados.COLUMN_SESSION_ID, cd.getIdSessao());
        values.put(BancoDados.COLUMN_KEY, cd.getCaracter());
        values.put(BancoDados.COLUMN_DOWN_TIME, cd.getDownTime());
        values.put(BancoDados.COLUMN_UP_TIME, cd.getUpTime());
        values.put(BancoDados.COLUMN_REPETITION, rep);

        database.insert(BancoDados.TABLE_CARACTERISTICAS, null,
                values);
    }


    //inserir dados na base de dados
    public synchronized void savePasswordKeyData(ArrayList<Caracter> vector, int rep) {

        for (Caracter cd : vector) {
            insertKeyData(cd, rep);
        }

    }

    // metodo para inserir um usuario na base de dados
    public void addUser(Usuario user) {
        ContentValues values = new ContentValues();
        values.put(BancoDados.COLUMN_NAME, user.getNome());
        values.put(BancoDados.COLUMN_USER_ID, user.getIdUsuario());
        values.put(BancoDados.COLUMN_SESSION_ID, user.getIdSessao());
        values.put(BancoDados.COLUMN_GENDER, user.getGenero());
        values.put(BancoDados.COLUMN_YEAR, user.getFetaria());
        values.put(BancoDados.COLUMN_COLLECTED_DATA, user.getDadosColectados());
        database.insert(BancoDados.TABLE_USERS, null,
                values);
    }


    // buscar dados de um usuario apartir do nome
    public Usuario getUser(String name) {
        Cursor c = database.rawQuery("select * from " + TABLE_USERS + " where " + COLUMN_NAME + "='" + name + "';", null);
        Usuario user = new Usuario();

        if (c.moveToFirst()) {

            user.setNome(c.getString(1));
            user.setIdUsuario(c.getInt(2));
            user.setIdSessao(c.getInt(3));
            user.setGenero(c.getString(4));
            user.setFetaria(c.getString(5));

            user.setDadosColectados(c.getInt(6));
            c.close();
            return user;
        }
        return null;
    }


    // Buscar usuario pelo id
    public String getNameUser(long userId) {
        List<Usuario> listaUse = getAllUsers();
        for (Usuario user : listaUse) {

            if (user.getIdUsuario() == userId) {
                String nome = user.getNome();
                return nome;
            }

        }
        return "vazio";
    }


    // buscar quantidade de dados do usuario
    public int getCollectedData(long userId) {
        Cursor c = database.rawQuery("select " + COLUMN_COLLECTED_DATA + " from " + TABLE_USERS + " where " + COLUMN_USER_ID + "=" + userId + ";", null);
        if (c.moveToFirst()) {
            int total = c.getInt(0);
            c.close();
            return total;
        }
        return 0;
    }


    // busca todos usuarios
    public ArrayList<Usuario> getAllUsers() {
        ArrayList<Usuario> UserArray = new ArrayList<Usuario>();
        Cursor c = database.rawQuery("select * from " + TABLE_USERS + ";", null);
        if (c.moveToFirst()) {
            do {
                Usuario user= new Usuario();

                user.setNome(c.getString(1));
                user.setIdUsuario(c.getInt(2));
                user.setIdSessao(c.getInt(3));
                user.setGenero(c.getString(4));
                user.setFetaria(c.getString(6));
                user.setDadosColectados(c.getInt(7));
                UserArray.add(user);
            } while (c.moveToNext());
        }
        c.close();
        return UserArray;
    }

    // actualiza quantidade de sessoes de um usuario
    public void updateUserSessionId(long userId, int sessionId) {
        ContentValues values = new ContentValues();
        values.put(BancoDados.COLUMN_SESSION_ID, sessionId);
        database.update(TABLE_USERS, values, COLUMN_USER_ID + "=" + userId, null);
    }

    // buscar caracteristicas no tabela caracteristicas
    public ArrayList<Caracter> getAllData() {
        ArrayList<Caracter> CaracterArray = new ArrayList<Caracter>();
        String[] s = new String[6];

        Cursor c = database.rawQuery("select * from " + TABLE_CARACTERISTICAS + ";", null);
        s = c.getColumnNames();
        ;

        if (c.moveToFirst()) {
            do {
                Caracter caracter = new Caracter();
                caracter.setIdUsuario(c.getInt(1));
                caracter.setIdSessao(c.getInt(2));
                caracter.setCaracter(c.getString(3));
                caracter.setDownTime(c.getLong(4));
                caracter.setUpTime(c.getLong(5));
                caracter.setRepeticao(c.getInt(6));
                CaracterArray.add(caracter);
            } while (c.moveToNext());
        }
        c.close();

        return CaracterArray;
    }

}
