package domain.alertas;

import domain.Comparador;
import domain.Tema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepoAlertas {

  private static RepoAlertas instance = new RepoAlertas();
  private List<Alerta> alertas = new ArrayList<>();

  public static RepoAlertas instance() {
    return instance;
  }

  public void add(Alerta alerta) {
    this.alertas.add(alerta);
  }

  public void delete(Alerta alerta) {
    this.alertas.remove(alerta);
  }

  public void clear() {
    this.alertas = new ArrayList<>();
  }

  public List<Alerta> all() {
    return this.alertas;
  }

  public List<Alerta> obtenerAlertas(Tema tema) {
    return this.alertas
        .stream()
        .filter(alerta -> !alerta.expirada() && alerta.esSobreTema(tema))
        .sorted(Comparador.getComparadorDeAlertas())
        .collect(Collectors.toList());
  }

}
