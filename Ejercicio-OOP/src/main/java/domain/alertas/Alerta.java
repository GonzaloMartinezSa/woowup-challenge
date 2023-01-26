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
  @Getter @Setter private LocalDateTime fechaCreacion;
  private Optional<LocalDateTime> fechaExpiracion = Optional.empty();

  public Alerta(String descripcion, TipoDeAlerta tipoDeAlerta, Tema tema) {
    this.descripcion = descripcion;
    this.tipo = tipoDeAlerta;
    this.tema = tema;
    this.fechaCreacion = LocalDateTime.now();
  }

  public boolean esDeTipo(TipoDeAlerta tipo) {
    return this.tipo.equals(tipo);
  }

  public boolean esSobreTema(Tema tema) {
    return this.tema.equals(tema);
  }

  public void setExpiracion(LocalDateTime expiracion) {
    this.fechaExpiracion = Optional.of(expiracion);
  }

  public void enviar() {
    this.tipoReceptor = TipoDeReceptor.TODOS;
    RepoAlertas.instance().add(this);
    RepoUsuarios.instance()
        .interesadosEn(this.tema)
        .forEach(usuario -> usuario.recibirAlerta(this));
  }

  public void enviar(Usuario usuario) {
    this.tipoReceptor = TipoDeReceptor.USUARIO;
    RepoAlertas.instance().add(this);
    usuario.recibirAlerta(this);
  }

  public boolean expirada() {
    return LocalDateTime.now().isAfter(this.fechaExpiracion.orElse(LocalDateTime.now()));
  }
}
