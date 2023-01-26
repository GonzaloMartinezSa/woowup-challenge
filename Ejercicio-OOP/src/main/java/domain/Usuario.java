package domain;

import domain.alertas.Alerta;
import domain.alertas.AlertaUsuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario {
  private String nombre;
  private List<Tema> temasDeInteres = new ArrayList<>();
  private List<AlertaUsuario> alertasRecibidas = new ArrayList<>();

  public Usuario(String nombre) {
    this.nombre = nombre;
    RepoUsuarios.instance().add(this);
  }

  public void anadirInteres(Tema tema) {
    this.temasDeInteres.add(tema);
  }

  public boolean interesadoEn(Tema tema) {
    return this.temasDeInteres.contains(tema);
  }

  public void recibirAlerta(Alerta alerta) {
    AlertaUsuario alertaUsuario = new AlertaUsuario(alerta);
    this.alertasRecibidas.add(alertaUsuario);
  }

  public boolean tieneAlerta(Alerta alerta) {
    return this.alertasRecibidas
        .stream()
        .map(alertaUsuario -> alertaUsuario.getAlerta())
        .collect(Collectors.toList())
        .contains(alerta);
  }

  public void eliminarAlertasExpiradas() {
    List<AlertaUsuario> aux = this.alertasRecibidas
        .stream()
        .filter(alertaUsuario -> alertaUsuario.getAlerta().expirada())
        .collect(Collectors.toList());

    this.alertasRecibidas.removeAll(aux);
  }

  private AlertaUsuario find(Alerta alerta) {
    return this.alertasRecibidas
        .stream()
        .filter(alertaUsuario -> alertaUsuario.getAlerta().equals(alerta))
        .collect(Collectors.toList())
        .get(0);
  }

  public void marcarComoLeida(Alerta alerta) {
    if (tieneAlerta(alerta))
      find(alerta).marcarComoLeida();
  }

  public boolean leida(Alerta alerta) {
    return find(alerta).leida();
  }

  public List<Alerta> obtenerAlertas() {
    return this.alertasRecibidas
        .stream()
        .filter(alertaUsuario -> !alertaUsuario.expirada() && !alertaUsuario.leida())
        .map(alertaUsuario -> alertaUsuario.getAlerta())
        .sorted(Comparador.getComparadorDeAlertas())
        .collect(Collectors.toList());
  }

}
