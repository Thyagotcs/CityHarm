package br.edu.pdm.cityharm.cadastro.problema;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;

import br.edu.pdm.cityharm.R;
import br.edu.pdm.cityharm.helper.DatabaseHelper;
import br.edu.pdm.cityharm.model.Problema;

@EActivity(R.layout.activity_cadastro_problema)
public class CadastroProblemaActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

  private GoogleApiClient googleApiClient;

  public Double latitude;
  public Double longitude;

  @ViewById
  public ImageView imvFotoProblema;

  @ViewById
  public Button btnConfirmar;

  @ViewById
  public EditText edtDescricaoProblema;

  @ViewById
  public TextView txtCoordenadas;

  @Click({R.id.btnConfirmar})
  public void onClickConfirmar(View view){
    Bitmap foto = ((BitmapDrawable)imvFotoProblema.getDrawable()).getBitmap();
    ByteArrayOutputStream sFoto = new ByteArrayOutputStream();
    foto.compress(Bitmap.CompressFormat.PNG, 100, sFoto);

    Problema novoProblema = new Problema();
    novoProblema.setFoto(sFoto.toByteArray());
    novoProblema.setLongitude(longitude);
    novoProblema.setLatitude(latitude);
    novoProblema.setDescricaoProblema(edtDescricaoProblema.getText().toString());

    DatabaseHelper helper = new DatabaseHelper(this);
    helper.saveOrUpdateProblema(novoProblema);

    finish();
  }

  @AfterViews
  public void buscarCoordenadas() {
    imvFotoProblema.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.imagempadrao));
    callConnection();
  }

  @LongClick({R.id.imvFotoProblema})
  public void onLongClicCapturar(View view) {
    Intent itCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(itCamera, 110);
  }

  @OnActivityResult(110)
  public void resultCamera(int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
      Bitmap foto = (Bitmap) data.getExtras().get("data");
      imvFotoProblema.setImageBitmap(foto);
    }
  }

  private synchronized void callConnection(){
    googleApiClient = new GoogleApiClient.Builder(this)
            .addOnConnectionFailedListener(this)
            .addConnectionCallbacks(this)
            .addApi(LocationServices.API)
            .build();
    googleApiClient.connect();
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    Log.i("LOG", "onConnected( " + bundle + " )");

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return;
    }
    Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

    if (location != null) {
      latitude = location.getLatitude();
      longitude = location.getLongitude();

      txtCoordenadas.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
    }
  }

  @Override
  public void onConnectionSuspended(int i) {
    Log.i("LOG", "onConnectionSuspended ( " + i + " )");
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Log.i("LOG", "onConnectionFailed( " + connectionResult + " )");
  }
}
