package domain;

import static org.junit.jupiter.api.Assertions.*;

import domain.alertas.Alerta;
import domain.alertas.RepoAlertas;
import domain.alertas.TipoDeAlerta;
import domain.alertas.TipoDeReceptor;
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
    usuario.anadirInteres(politica);
    assertTrue(usuario.interesadoEn(politica));
  }

  @Test
  public void UnUsuarioNoEstaInteresadoEnUnTemaQueNoEligio() {
    Tema politica = new Tema("politica");
    assertFalse(usuario.interesadoEn(politica));
  }

  @Test
  public void UnUsuarioPuedeEstarInteresadoEnVariosTemas() {
    Tema politica = new Tema("politica");
    Tema deportes = new Tema("deportes");
    Tema juegos = new Tema("juegos");
    usuario.anadirInteres(politica);
    usuario.anadirInteres(deportes);

    assertTrue(usuario.interesadoEn(politica));
    assertTrue(usuario.interesadoEn(deportes));
    assertFalse(usuario.interesadoEn(juegos));
  }

  // 4.	Se puede enviar una alerta sobre un tema y lo reciben todos los usuarios
  // que han optado recibir alertas de ese tema.

  @Test
  public void SePuedeEnviarUnaAlertaPorTema() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    usuario.anadirInteres(politica);
    alerta.enviar();

    assertTrue(usuario.tieneAlerta(alerta));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  @Test
  public void SePuedeEnviarUnaAlertaPorTemaAVariosUsuarios() {
    Usuario usuario2 = new Usuario("Juan");
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    usuario.anadirInteres(politica);
    usuario2.anadirInteres(politica);
    alerta.enviar();

    assertTrue(usuario.tieneAlerta(alerta));
    assertTrue(usuario2.tieneAlerta(alerta));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  @Test
  public void LosUsuariosNoInteresadosNoRecibenLaAlerta() {
    Usuario usuario2 = new Usuario("Juan");
    Usuario usuario3 = new Usuario("Pablo");
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    usuario.anadirInteres(politica);
    usuario2.anadirInteres(politica);
    alerta.enviar();

    assertTrue(usuario.tieneAlerta(alerta));
    assertTrue(usuario2.tieneAlerta(alerta));
    assertFalse(usuario3.tieneAlerta(alerta));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  // 5.	Se puede enviar una alerta sobre un tema a un usuario espec??fico, solo lo recibe ese ??nico usuario.

  @Test
  public void SePuedeEnviarUnaAlertaAUnUsuarioDirectamente() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    usuario.anadirInteres(politica);
    alerta.enviar(usuario);

    assertTrue(usuario.tieneAlerta(alerta));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  @Test
  public void SePuedeEnviarUnaAlertaAUnUsuarioDirectamenteAunqueNoEsteInteresado() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    alerta.enviar(usuario);

    assertTrue(usuario.tieneAlerta(alerta));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  // 6.	Una alerta puede tener una fecha y hora de expiraci??n. Las alertas que tienen expiraci??n,
  // no se muestran al usuario si han expirado.

  @Test
  public void SePuedeCrearUnaAlertaConFechaDeExpiracion() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);
    alerta.setExpiracion(LocalDateTime.of(2024, 5, 3, 12, 30));

    assertFalse(alerta.expirada());
  }

  @Test
  public void UnaAlertaConFechaDeExpiracionPuedeHaberExpirado() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);
    alerta.setExpiracion(LocalDateTime.of(2020, 5, 3, 12, 30));

    assertTrue(alerta.expirada());
  }

  @Test
  public void PuedoEliminarAlertasExpiradas() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);
    Alerta alerta2 = new Alerta("Nuevo presidente", TipoDeAlerta.INFORMATIVA, politica);
    alerta.setExpiracion(LocalDateTime.of(2020, 5, 3, 12, 30));
    alerta2.setExpiracion(LocalDateTime.of(2024, 5, 3, 12, 30));

    //usuario.anadir_tema_de_interes(politica);

    alerta.enviar(usuario);
    alerta2.enviar(usuario);

    usuario.eliminarAlertasExpiradas();

    assertTrue(usuario.tieneAlerta(alerta2));
    assertFalse(usuario.tieneAlerta(alerta));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  // 8.	Un usuario puede marcar una alerta como le??da.

  @Test
  public void UnUsuarioPuedeMarcarUnaAlertaComoLeida() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    alerta.enviar(usuario);
    usuario.marcarComoLeida(alerta);

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

  // 9.	Se pueden obtener todas las alertas no expiradas de un usuario que a??n no ha le??do, ordenadas
  // primero las Urgentes y luego las informativas de la m??s reciente a la m??s antigua.

  @Test
  public void SePuedenObtenerLasAlertasDeUnUsuario() {
    Tema politica = new Tema("politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);
    Alerta alerta2 = new Alerta("A votar mas", TipoDeAlerta.INFORMATIVA, politica);
    Alerta alerta3 = new Alerta("A votar mas todavia", TipoDeAlerta.URGENTE, politica);
    alerta.setFechaCreacion(LocalDateTime.of(2022, 2, 21, 12, 30));
    alerta2.setFechaCreacion(LocalDateTime.of(2020, 2, 21, 12, 30));

    alerta.enviar(usuario);
    alerta2.enviar(usuario);
    alerta3.enviar(usuario);

    List<Alerta> alertas = usuario.obtenerAlertas();

    //alertas.forEach(a -> System.out.println( a.getTipo() + " - " + a.getDescripcion()));

    assertEquals(alerta3, alertas.get(0));
    assertEquals(alerta, alertas.get(1));
    assertEquals(alerta2, alertas.get(2));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  // 10.	Se pueden obtener todas las alertas no expiradas para un tema (primero las Urgentes y luego
  // las Informativas de la m??s reciente a la m??s antigua).
  // Se informa para cada alerta si es para todos los usuarios o para uno espec??fico.

  @Test
  public void SePuedenObtenerAlertasPorTema() {
    Tema politica = new Tema("politica");
    Tema juegos   = new Tema("juegos");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);
    Alerta alerta2 = new Alerta("A votar mas", TipoDeAlerta.INFORMATIVA, politica);
    Alerta alerta3 = new Alerta("A votar mas todavia", TipoDeAlerta.URGENTE, politica);
    Alerta alerta4 = new Alerta("Salio Dead Space Remake", TipoDeAlerta.URGENTE, juegos);
    alerta.setFechaCreacion(LocalDateTime.of(2022, 2, 21, 12, 30));
    alerta2.setFechaCreacion(LocalDateTime.of(2020, 2, 21, 12, 30));

    alerta.enviar(usuario);
    alerta2.enviar(usuario);
    alerta3.enviar(usuario);
    alerta4.enviar(usuario);

    List<Alerta> alertas = politica.obtenerAlertas();

    //alertas.forEach(a -> System.out.println( a.getTipo() + " - " + a.getDescripcion()));

    assertEquals(alerta3, alertas.get(0));
    assertEquals(alerta, alertas.get(1));
    assertEquals(alerta2, alertas.get(2));

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  @Test
  public void SePuedeSaberSiUnaAlertaSeMandoATodos() {
    Tema politica = new Tema("Politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);
    usuario.anadirInteres(politica);
    alerta.enviar();

    assertEquals(TipoDeReceptor.TODOS, alerta.getTipoReceptor());

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  @Test
  public void SePuedeSaberSiUnaAlertaSeMandoAAlguienParticular() {
    Tema politica = new Tema("Politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);
    alerta.enviar(usuario);

    assertEquals(TipoDeReceptor.USUARIO, alerta.getTipoReceptor());

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

  @Test
  public void SePuedeSaberSiUnaAlertaNoSeMandoYNoSeRompe() {
    Tema politica = new Tema("Politica");
    Alerta alerta = new Alerta("A votar gente", TipoDeAlerta.INFORMATIVA, politica);

    assertEquals(TipoDeReceptor.NADIE, alerta.getTipoReceptor());

    RepoUsuarios.instance().clear();
    RepoAlertas.instance().clear();
  }

}
