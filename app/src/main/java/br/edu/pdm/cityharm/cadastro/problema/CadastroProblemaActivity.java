package br.edu.pdm.cityharm.cadastro.problema;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import br.edu.pdm.cityharm.R;

@EActivity(R.layout.activity_cadastro_problema)
public class CadastroProblemaActivity extends AppCompatActivity {

  GoogleApiClient client;

  @ViewById
  public ImageView imvFotoProblema;

  @ViewById
  public Button btnConfirmar;

  @ViewById
  public TextView txtCoordenadas;

  @AfterViews
  public void buscarCoordenadas(){

    if (mGoogleApiClient == null) {
      mGoogleApiClient = new GoogleApiClient.Builder(this)
              .addConnectionCallbacks(this)
              .addOnConnectionFailedListener(this)
              .addApi(LocationServices.API)
              .build();
    }



  }

  @LongClick({R.id.imvFotoProblema})
  public void onLongClicCapturar(View view){
    Intent itCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(itCamera, 110);
  }

  @OnActivityResult(110)
  public void resultCamera(int resultCode, Intent data){
    if (resultCode == RESULT_OK) {
      Bitmap foto = (Bitmap) data.getExtras().get("data");
      Bitmap fotoRedimensionada = Bitmap.createScaledBitmap(foto, imvFotoProblema.getWidth(), imvFotoProblema.getHeight(), false);
      imvFotoProblema.setImageBitmap(fotoRedimensionada);
    }
  }

}
