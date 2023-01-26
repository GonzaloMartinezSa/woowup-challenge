package domain.alertas;

import lombok.Getter;

public class AlertaUsuario {
  @Getter private Alerta alerta;
  private boolean leida = false;

  public AlertaUsuario(Alerta alerta) {
    this.alerta = alerta;
  }

  public boolean leida() {
    return this.leida;
  }

  public void marcarComoLeida() {
    this.leida = true;
  }

  public boolean expirada() {
    return alerta.expirada();
  }
}
