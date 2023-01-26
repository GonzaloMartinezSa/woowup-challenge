package domain;

import domain.alertas.Alerta;
import domain.alertas.AlertaUsuario;
import domain.alertas.TipoDeAlerta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario {
  private String nombre;
  private List<Tema> temas_de_interes = new ArrayList<>();
  private List<AlertaUsuario> alertas_recibidas = new ArrayList<>();

  public Usuario(String nombre) {
    this.nombre = nombre;
    RepoUsuarios.instance().add(this);
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

  public boolean tiene_alerta(Alerta alerta) {
    return this.alertas_recibidas
        .stream()
        .map(alertaUsuario -> alertaUsuario.getAlerta())
        .collect(Collectors.toList())
        .contains(alerta);
  }

  public void eliminar_alertas_expiradas() {
    List<AlertaUsuario> aux = this.alertas_recibidas
        .stream()
        .filter(alertaUsuario -> alertaUsuario.getAlerta().expirada())
        .collect(Collectors.toList());

    this.alertas_recibidas.removeAll(aux);
  }

  private AlertaUsuario find(Alerta alerta) {
    return this.alertas_recibidas
        .stream()
        .filter(alertaUsuario -> alertaUsuario.getAlerta().equals(alerta))
        .collect(Collectors.toList())
        .get(0);
  }

  public void marcar_como_leida(Alerta alerta) {
    if (tiene_alerta(alerta))
      find(alerta).marcar_como_leida();
  }

  public boolean leida(Alerta alerta) {
    return find(alerta).leida();
  }

  public List<Alerta> obtener_alertas() {
    return this.alertas_recibidas
        .stream()
        .filter(alertaUsuario -> !alertaUsuario.expirada() && !alertaUsuario.leida())
        .map(AlertaUsuario::getAlerta)
        .sorted(Comparador.getComparator())
        .collect(Collectors.toList());
  }

}
