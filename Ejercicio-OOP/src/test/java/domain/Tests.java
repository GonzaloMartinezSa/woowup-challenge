package domain;

import static org.junit.jupiter.api.Assertions.*;

import domain.alertas.Alerta;
import domain.alertas.RepoAlertas;
import domain.alertas.TipoDeAlerta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

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
    RepoAlertas.instance().clear();
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
    RepoAlertas.instance().clear();
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
    RepoAlertas.instance().clear();
  }

  // 5.	Se puede enviar una alerta sobre un tema a un usuario específico, solo lo recibe ese único usuario.

  @Test
  public void SePuedeEnviarUnaAlertaAUnUsuarioDirectamente() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    usuario.anadir_tema_de_interes(politica);
    alerta.enviar(usuario);

    assertTrue(usuario.tiene_alerta(alerta));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  @Test
  public void SePuedeEnviarUnaAlertaAUnUsuarioDirectamenteAunqueNoEsteInteresado() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    alerta.enviar(usuario);

    assertTrue(usuario.tiene_alerta(alerta));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  // 6.	Una alerta puede tener una fecha y hora de expiración. Las alertas que tienen expiración,
  // no se muestran al usuario si han expirado.

  @Test
  public void SePuedeCrearUnaAlertaConFechaDeExpiracion() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);
    alerta.set_expiracion(LocalDateTime.of(2024, 5, 3, 12, 30));

    assertFalse(alerta.expirada());
  }

  @Test
  public void UnaAlertaConFechaDeExpiracionPuedeHaberExpirado() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);
    alerta.set_expiracion(LocalDateTime.of(2020, 5, 3, 12, 30));

    assertTrue(alerta.expirada());
  }

  @Test
  public void PuedoEliminarAlertasExpiradas() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);
    Alerta alerta2 = new Alerta("Nuevo presidente", TipoDeAlerta.INFORMATIVA, politica);
    alerta.set_expiracion(LocalDateTime.of(2020, 5, 3, 12, 30));
    alerta2.set_expiracion(LocalDateTime.of(2024, 5, 3, 12, 30));

    //usuario.anadir_tema_de_interes(politica);

    alerta.enviar(usuario);
    alerta2.enviar(usuario);

    usuario.eliminar_alertas_expiradas();

    assertTrue(usuario.tiene_alerta(alerta2));
    assertFalse(usuario.tiene_alerta(alerta));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  // 8.	Un usuario puede marcar una alerta como leída.

  @Test
  public void UnUsuarioPuedeMarcarUnaAlertaComoLeida() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    alerta.enviar(usuario);
    usuario.marcar_como_leida(alerta);

    assertTrue(usuario.leida(alerta));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  @Test
  public void UnaAlertaEmpiezaComoNoLeida() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    alerta.enviar(usuario);

    assertFalse(usuario.leida(alerta));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  // 9.	Se pueden obtener todas las alertas no expiradas de un usuario que aún no ha leído, ordenadas
  // primero las Urgentes y luego las informativas de la más reciente a la más antigua.

  @Test
  public void SePuedenObtenerLasAlertasDeUnUsuario() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);
    Alerta alerta2 = new Alerta("A votar mas", TipoDeAlerta.INFORMATIVA, politica);
    Alerta alerta3 = new Alerta("A votar mas todavia", TipoDeAlerta.URGENTE, politica);
    alerta.setFecha_creacion(LocalDateTime.of(2022, 2, 21, 12, 30));
    alerta2.setFecha_creacion(LocalDateTime.of(2020, 2, 21, 12, 30));

    alerta.enviar(usuario);
    alerta2.enviar(usuario);
    alerta3.enviar(usuario);

    List<Alerta> alertas = usuario.obtener_alertas();

    //alertas.forEach(a -> System.out.println( a.getTipo() + " - " + a.getDescripcion()));

    assertEquals(alerta3, alertas.get(0));
    assertEquals(alerta, alertas.get(1));
    assertEquals(alerta2, alertas.get(2));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }



}
