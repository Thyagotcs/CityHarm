package br.edu.pdm.cityharm.cadastro.usuario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.edu.pdm.cityharm.R;
import br.edu.pdm.cityharm.helper.DatabaseHelper;
import br.edu.pdm.cityharm.model.Usuario;

@EActivity(R.layout.activity_cadastro_usuario)
public class CadastroUsuarioActivity extends AppCompatActivity {

  @ViewById
  protected EditText edtNome;
  @ViewById
  protected EditText edtEmail;
  @ViewById
  protected EditText edtLogin;
  @ViewById
  protected EditText edtSenha;

  @ViewById
  protected Button btnSalvar;
  @ViewById
  protected Button btnFechar;

  /*@LongClick(R.id.imvFoto)
  public void acionarCamera(View v) {
    Intent itCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(itCamera, 110);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 110 && resultCode == RESULT_OK) {
      Bitmap foto = (Bitmap) data.getExtras().get("data");
      Bitmap fotoRedimensionada = Bitmap.createScaledBitmap(foto, imvFoto.getWidth(), imvFoto.getHeight(), false);
      imvFoto.setImageBitmap(fotoRedimensionada);
    }

  }*/

  @Click({R.id.btnSalvar})
  public void onClick(View view) {
    if (edtNome.getText().toString().equalsIgnoreCase("")) {
      Toast.makeText(this, "Informe um nome", Toast.LENGTH_LONG).show();
    } else if (edtEmail.getText().toString().equalsIgnoreCase("")) {
      Toast.makeText(this, "Informe um e-mail", Toast.LENGTH_LONG).show();
    } else if (edtLogin.getText().toString().equalsIgnoreCase("")) {
      Toast.makeText(this, "Informe um login", Toast.LENGTH_LONG).show();
    } else if (edtSenha.getText().toString().equalsIgnoreCase("")) {
      Toast.makeText(this, "Informe uma senha", Toast.LENGTH_LONG).show();
    } else {
      Usuario novoUsuario = new Usuario();
      novoUsuario.setNome(edtNome.getText().toString());
      novoUsuario.setEmail(edtEmail.getText().toString());
      novoUsuario.setLogin(edtLogin.getText().toString());
      novoUsuario.setSenha(edtSenha.getText().toString());
      DatabaseHelper helper = new DatabaseHelper(this);
      helper.saveOrUpdateUsuario(novoUsuario);

      Intent intent = new Intent();
      intent.putExtra("login", novoUsuario.getLogin());
      setResult(RESULT_OK, intent);
      super.finish();
    }

  }
}
