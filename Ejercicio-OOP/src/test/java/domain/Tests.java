package domain;

import static org.junit.jupiter.api.Assertions.*;

import domain.alertas.Alerta;
import domain.alertas.TipoDeAlerta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Tests {

  Usuario usuario;

  @BeforeEach
  public void init() {
    usuario = new Usuario("Gonzalo");
  }

  // 3.	Los usuarios pueden optar sobre cuales temas quieren recibir alertas.

  @Test
  public void UnUsuarioPuedeEstarInteresadoEnUnTema() {
    Tema politica = new Tema("politica");
    usuario.anadir_tema_de_interes(politica);
    assertTrue(usuario.interesado_en(politica));
  }

  @Test
  public void UnUsuarioNoEstaInteresadoEnUnTemaQueNoEligio() {
    Tema politica = new Tema("politica");
    assertFalse(usuario.interesado_en(politica));
  }

  @Test
  public void UnUsuarioPuedeEstarInteresadoEnVariosTemas() {
    Tema politica = new Tema("politica");
    Tema deportes = new Tema("deportes");
    Tema juegos = new Tema("juegos");
    usuario.anadir_tema_de_interes(politica);
    usuario.anadir_tema_de_interes(deportes);

    assertTrue(usuario.interesado_en(politica));
    assertTrue(usuario.interesado_en(deportes));
    assertFalse(usuario.interesado_en(juegos));
  }

  // 4.	Se puede enviar una alerta sobre un tema y lo reciben todos los usuarios
  // que han optado recibir alertas de ese tema.

  @Test
  public void SePuedeEnviarUnaAlertaPorTema() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    usuario.anadir_tema_de_interes(politica);
    alerta.enviar();

    assertTrue(usuario.tiene_alerta(alerta));
    RepoUsuarios.instance().clear();
  }

  @Test
  public void SePuedeEnviarUnaAlertaPorTemaAVariosUsuarios() {
    Usuario usuario2 = new Usuario("Juan");
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    usuario.anadir_tema_de_interes(politica);
    usuario2.anadir_tema_de_interes(politica);
    alerta.enviar();

    assertTrue(usuario.tiene_alerta(alerta));
    assertTrue(usuario2.tiene_alerta(alerta));
    RepoUsuarios.instance().clear();
  }

  @Test
  public void LosUsuariosNoInteresadosNoRecibenLaAlerta() {
    Usuario usuario2 = new Usuario("Juan");
    Usuario usuario3 = new Usuario("Pablo");
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    usuario.anadir_tema_de_interes(politica);
    usuario2.anadir_tema_de_interes(politica);
    alerta.enviar();

    assertTrue(usuario.tiene_alerta(alerta));
    assertTrue(usuario2.tiene_alerta(alerta));
    assertFalse(usuario3.tiene_alerta(alerta));
    RepoUsuarios.instance().clear();
  }

}
