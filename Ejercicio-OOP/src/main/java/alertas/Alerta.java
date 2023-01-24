package alertas;

public class Alerta {

  boolean leido = false;
  TipoDeAlerta tipo;

  public Alerta(TipoDeAlerta tipoDeAlerta) {
    this.tipo = tipoDeAlerta;
  }

  public boolean leido() {
    return this.leido;
  }

  public void marcar_como_leida() {
    this.leido = true;
  }
}
