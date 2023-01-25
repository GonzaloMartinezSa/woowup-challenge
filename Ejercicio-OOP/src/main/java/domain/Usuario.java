package domain;

import domain.Tema;
import domain.alertas.Alerta;
import domain.alertas.AlertaUsuario;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
  private String nombre;
  private List<Tema> temas_de_interes = new ArrayList<>();
  private List<AlertaUsuario> alertas_recibidas = new ArrayList<>();

  public Usuario(String nombre) {
    this.nombre = nombre;
  }

  public void anadir_tema_de_interes(Tema tema) {
    this.temas_de_interes.add(tema);
  }

  public boolean interesado_en(Tema tema) {
    return this.temas_de_interes.contains(tema);
  }

  public void recibir_alerta(Alerta alerta) {
    AlertaUsuario alertaUsuario = new AlertaUsuario(alerta);
    this.alertas_recibidas.add(alertaUsuario);
  }

}
