package domain;

import static org.junit.jupiter.api.Assertions.*;
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

}
