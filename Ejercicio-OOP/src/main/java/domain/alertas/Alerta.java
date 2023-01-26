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
  @Getter private TipoDeReceptor tipoReceptor = TipoDeReceptor.NADIE;
  private Tema tema;
  @Getter @Setter private LocalDateTime fecha_creacion;
  private Optional<LocalDateTime> fecha_expiracion = Optional.empty();

  public Alerta(String descripcion, TipoDeAlerta tipoDeAlerta, Tema tema) {
    this.descripcion = descripcion;
    this.tipo = tipoDeAlerta;
    this.tema = tema;
    this.fecha_creacion = LocalDateTime.now();
  }

  public boolean esDeTipo(TipoDeAlerta tipo) {
    return this.tipo.equals(tipo);
  }

  public boolean esSobreTema(Tema tema) {
    return this.tema.equals(tema);
  }

  public void set_expiracion(LocalDateTime expiracion) {
    this.fecha_expiracion = Optional.of(expiracion);
  }

  public void enviar() {
    this.tipoReceptor = TipoDeReceptor.TODOS;
    RepoAlertas.instance().add(this);
    RepoUsuarios.instance()
        .interesados_en(this.tema)
        .forEach(usuario -> usuario.recibir_alerta(this));
  }

  public void enviar(Usuario usuario) {
    this.tipoReceptor = TipoDeReceptor.USUARIO;
    RepoAlertas.instance().add(this);
    usuario.recibir_alerta(this);
  }

  public boolean expirada() {
    return LocalDateTime.now().isAfter(this.fecha_expiracion.orElse(LocalDateTime.now()));
  }
}
