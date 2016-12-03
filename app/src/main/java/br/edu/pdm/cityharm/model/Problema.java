package br.edu.pdm.cityharm.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by MatheusMahl on 02/12/2016.
 */
@DatabaseTable(tableName = "problema")
public class Problema {

  @DatabaseField(generatedId = true)
  private Integer codigo;
  @DatabaseField(canBeNull = false)
  private String descricaoProblema;

  @DatabaseField(canBeNull = false)
  private double longitude;
  @DatabaseField(canBeNull = false)
  private double latitude;
  @DatabaseField(dataType = DataType.SERIALIZABLE)
  private byte[] foto;


  public Integer getCodigo() {
    return codigo;
  }

  public void setCodigo(Integer codigo) {
    this.codigo = codigo;
  }

  public String getDescricaoProblema() {
    return descricaoProblema;
  }

  public void setDescricaoProblema(String descricaoProblema) {
    this.descricaoProblema = descricaoProblema;
  }

  public byte[] getFoto() {
    return foto;
  }

  public void setFoto(byte[] foto) {
    this.foto = foto;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }



  public Problema() {

  }

}
