package domain.alertas;

import domain.RepoUsuarios;
import domain.Tema;
import domain.Usuario;

import java.time.LocalDateTime;

public class Alerta {

  private String descripcion;
  private TipoDeAlerta tipo;
  private Tema tema;
  private LocalDateTime fecha_y_hora_de_expiacion;

  public Alerta(String descripcion, TipoDeAlerta tipoDeAlerta, Tema tema) {
    this.descripcion = descripcion;
    this.tipo = tipoDeAlerta;
    this.tema = tema;
  }

  public void enviar() {
    RepoUsuarios.instance()
        .interesados_en(this.tema)
        .forEach(usuario -> usuario.recibir_alerta(this));
  }

  public void enviar(Usuario usuario) {
    usuario.recibir_alerta(this);
  }

}
