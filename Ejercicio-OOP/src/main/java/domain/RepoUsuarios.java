package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepoUsuarios {
  private static RepoUsuarios instance = new RepoUsuarios();
  private List<Usuario> usuarios = new ArrayList<>();

  public static RepoUsuarios instance() {
    return instance;
  }

  public void add(Usuario usuario) {
    this.usuarios.add(usuario);
  }

  public void delete(Usuario usuario) {
    this.usuarios.remove(usuario);
  }

  public void clear() {
    this.usuarios = new ArrayList<>();
  }

  public List<Usuario> all() {
    return this.usuarios;
  }

  public List<Usuario> interesados_en(Tema tema) {
    return this.usuarios
        .stream()
        .filter(usuario -> usuario.interesado_en(tema))
        .collect(Collectors.toList());
  }

}
