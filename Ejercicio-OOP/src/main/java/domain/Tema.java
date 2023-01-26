package domain;

import domain.alertas.Alerta;
import domain.alertas.RepoAlertas;

import java.util.List;

public class Tema {
  private String nombre;

  public Tema(String nombre) {
    this.nombre = nombre;
  }

  public List<Alerta> obtener_alertas() {
    return RepoAlertas.instance().obtener_alertas(this);
  }

}
