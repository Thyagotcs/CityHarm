package br.edu.pdm.cityharm.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import br.edu.pdm.cityharm.R;
import br.edu.pdm.cityharm.model.Problema;
import br.edu.pdm.cityharm.model.Usuario;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

  // name of the database file for your application -- change to something appropriate for your app
  private static final String DATABASE_NAME = "cityharm.db";
  // any time you make changes to your database objects, you may have to increase the database version
  private static final int DATABASE_VERSION = 1;

  private RuntimeExceptionDao<Usuario, Integer> usuarioRuntimeDao = null;

  private RuntimeExceptionDao<Problema, Integer> problemaRuntimeDao = null;

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
  }

  /**
   * This is called when the database is first created. Usually you should call createTable statements here to create
   * the tables that will store your data.
   */
  @Override
  public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
    try {
      Log.i(DatabaseHelper.class.getName(), "onCreate");
      TableUtils.createTable(connectionSource, Usuario.class);

    } catch (SQLException e) {
      Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
      throw new RuntimeException(e);
    }

    // here we try inserting data in the on-create as a test
    RuntimeExceptionDao<Usuario, Integer> uDao = getUsuarioDao();
    // create some entries in the onCreate
    Usuario usuario = new Usuario();
    usuario.setNome("admin");
    usuario.setEmail("admin@teste.com");
    usuario.setLogin("admin");
    usuario.setSenha("admin");
    uDao.create(usuario);
    Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate!");

  }

  /**
   * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
   * the various data to match the new version number.
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    try {
      Log.i(DatabaseHelper.class.getName(), "onUpgrade");
      TableUtils.dropTable(connectionSource, Usuario.class, true);
      // after we drop the old databases, we create the new ones
      onCreate(db, connectionSource);
    } catch (SQLException e) {
      Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
   * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
   */
  public RuntimeExceptionDao<Usuario, Integer> getUsuarioDao() {
    if (usuarioRuntimeDao == null) {
      usuarioRuntimeDao = getRuntimeExceptionDao(Usuario.class);
    }
    return usuarioRuntimeDao;
  }

  public RuntimeExceptionDao<Problema, Integer> getProblemaDao() {
    if (problemaRuntimeDao == null) {
      problemaRuntimeDao = getRuntimeExceptionDao(Problema.class);
    }
    return problemaRuntimeDao;
  }

  public Usuario getUsuarioByLoginSenha(String login, String senha) {
    Usuario u = null;
    try {
      QueryBuilder<Usuario, Integer> qry = getUsuarioDao().queryBuilder();
      qry.where().eq("login", login).and().eq("senha", senha);
      List<Usuario> lstUsuarios = qry.query();
      if (lstUsuarios != null && lstUsuarios.size() > 0) {
        u = lstUsuarios.get(0);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return u;
  }

  public void saveOrUpdateUsuario(Usuario usuario) {
    getUsuarioDao().createOrUpdate(usuario);
  }

  public void saveOrUpdateProblema(Problema problema) {
    getProblemaDao().createOrUpdate(problema);
  }

  /**
   * Close the database connections and clear any cached DAOs.
   */
  @Override
  public void close() {
    super.close();
    usuarioRuntimeDao = null;
    problemaRuntimeDao = null;
  }
}