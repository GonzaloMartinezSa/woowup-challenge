package domain.alertas;

import domain.RepoUsuarios;
import domain.Tema;
import domain.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

public class Alerta {

  @Getter private String descripcion;
  @Getter private TipoDeAlerta tipo;
  private Tema tema;
  @Getter @Setter private LocalDateTime fecha_creacion;
  private Optional<LocalDateTime> fecha_expiracion = Optional.empty();

  public Alerta(String descripcion, TipoDeAlerta tipoDeAlerta, Tema tema) {
    this.descripcion = descripcion;
    this.tipo = tipoDeAlerta;
    this.tema = tema;
    this.fecha_creacion = LocalDateTime.now();
  }

  public boolean tipo_is(TipoDeAlerta tipo) {
    return this.tipo.equals(tipo);
  }

  public void set_expiracion(LocalDateTime expiracion) {
    this.fecha_expiracion = Optional.of(expiracion);
  }

  public void enviar() {
    RepoUsuarios.instance()
        .interesados_en(this.tema)
        .forEach(usuario -> usuario.recibir_alerta(this));
  }

  public void enviar(Usuario usuario) {
    usuario.recibir_alerta(this);
  }

  public boolean expirada() {
    return LocalDateTime.now().isAfter(this.fecha_expiracion.orElse(LocalDateTime.now()));
  }
}
