package domain.alertas;

import domain.RepoUsuarios;
import domain.Tema;
import domain.Usuario;

import java.time.LocalDateTime;

public class Alerta {

  private String descripcion;
  private boolean leido = false;
  private TipoDeAlerta tipo;
  private LocalDateTime fecha_y_hora_de_expiacion;

  public Alerta(String descripcion, TipoDeAlerta tipoDeAlerta) {
    this.descripcion = descripcion;
    this.tipo = tipoDeAlerta;
  }

  public boolean leido() {
    return this.leido;
  }

  public void marcar_como_leida() {
    this.leido = true;
  }

  public void enviar(Tema tema) {
    RepoUsuarios.instance()
        .interesados_en(tema)
        .forEach(usuario -> usuario.recibir_alerta(this));
  }

  public void enviar(Tema tema, Usuario usuario) {
    usuario.recibir_alerta(this);
  }

}
