package br.edu.pdm.cityharm.about;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.edu.pdm.cityharm.R;

@EActivity(R.layout.activity_sobre)
public class SobreActivity extends AppCompatActivity {

@ViewById
public Button btnVoltar;

  @Click({R.id.btnVoltar})
  public void onClick(View view) {
    finish();
  }
}
