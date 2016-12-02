package br.edu.pdm.cityharm;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.edu.pdm.cityharm.cadastro.usuario.CadastroUsuarioActivity_;
import br.edu.pdm.cityharm.helper.DatabaseHelper;
import br.edu.pdm.cityharm.model.Usuario;
import br.edu.pdm.cityharm.principal.PrincipalActivity_;


@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

  @ViewById
  protected EditText edtLogin;
  @ViewById
  protected EditText edtSenha;
  @ViewById
  protected ImageButton btnLogin;
  @ViewById
  protected ImageButton btnSobre;

  @ViewById
  protected TextView txtRegistrar;

  @AfterViews
  public void inicializar() {
    //Deixar o texto sublinhado
    txtRegistrar.setPaintFlags(txtRegistrar.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
  }

  public void abrirRegistro(View view) {
    Intent itRegistro = new Intent(this, CadastroUsuarioActivity_.class);
    // espera resultado da tela de cadastro de usuario
    startActivityForResult(itRegistro, 100);
  }

  @Click({R.id.btnSobre, R.id.btnLogin})
  public void onClick(View view) {
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    switch (view.getId()) {
      case R.id.btnLogin:
        // recuperar valores da tela
        String strLogin = edtLogin.getText().toString();
        String strSenha = edtSenha.getText().toString();
        Usuario usuario = databaseHelper.getUsuarioByLoginSenha(strLogin, strSenha);
        if (usuario != null) {
       // if (strLogin != "") {
          Intent it = new Intent(this, PrincipalActivity_.class);
          it.putExtra("usuario", usuario);
          startActivity(it);
          finish();
        } else {
          edtLogin.setText("");
          edtSenha.setText("");
          Toast.makeText(this, R.string.msgLoginErro, Toast.LENGTH_LONG).show();
          edtLogin.requestFocus();
        }
        break;

      case R.id.btnSobre:
        finish();
        break;
    }
  }
}
