package br.edu.pdm.cityharm.principal;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.edu.pdm.cityharm.R;
import br.edu.pdm.cityharm.cadastro.problema.CadastroProblemaActivity;
import br.edu.pdm.cityharm.cadastro.problema.CadastroProblemaActivity_;
import br.edu.pdm.cityharm.mapa.MapaProblemasActivity;

@EActivity(R.layout.activity_principal)
public class PrincipalActivity extends AppCompatActivity {

  @ViewById
  protected Button btnRegistrarProblema;

  @ViewById
  protected Button btnAbrirMapaProblemas;


  @Click({R.id.btnRegistrarProblema})
  public void onClickRegistrarProblema(View view){
    Intent itRegistroProblema = new Intent(this, CadastroProblemaActivity_.class);
    startActivityForResult(itRegistroProblema, 120);
  }

  @Click({R.id.btnAbrirMapaProblemas})
  public void onClickAbrirMapaProblemas(View view){
    Intent itMapaProblemas = new Intent(this, MapaProblemasActivity.class);
    startActivityForResult(itMapaProblemas, 130);
  }

}
