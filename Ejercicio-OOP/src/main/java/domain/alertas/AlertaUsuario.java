package domain.alertas;

public class AlertaUsuario {
  private Alerta alerta;
  private boolean leida = false;

  public AlertaUsuario(Alerta alerta) {
    this.alerta = alerta;
  }

  public boolean leidoa() {
    return this.leida;
  }

  public void marcar_como_leida() {
    this.leida = true;
  }

  public boolean expirada() {
    return alerta.expirada();
  }
}
