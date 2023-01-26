package domain;

import domain.alertas.Alerta;
import domain.alertas.RepoAlertas;

import java.util.List;

public class Tema {
  private String nombre;

  public Tema(String nombre) {
    this.nombre = nombre;
  }

  public List<Alerta> obtenerAlertas() {
    return RepoAlertas.instance().obtenerAlertas(this);
  }

}
