package domain;

import domain.alertas.Alerta;
import domain.alertas.TipoDeAlerta;

import java.util.Comparator;

public class Comparador {

  // -1: el de la izq (o1) tiene mas prioridad
  //  0: ambos tienen igual prioridad
  //  1: el de la der (o1) tiene menos prioridad
  public static Comparator<Alerta> getComparadorDeAlertas() {
    return (o1, o2) -> {
      if (o1.esDeTipo(TipoDeAlerta.URGENTE) && o2.esDeTipo(TipoDeAlerta.INFORMATIVA))
        return -1;
      else if (o1.esDeTipo(TipoDeAlerta.INFORMATIVA) && o2.esDeTipo(TipoDeAlerta.URGENTE))
        return 1;
      else if (o1.esDeTipo(TipoDeAlerta.INFORMATIVA) && o2.esDeTipo(TipoDeAlerta.INFORMATIVA)) {
        return o1.getFechaCreacion().compareTo(o2.getFechaCreacion()) * -1;
      } else
        return 0;
    };
  }

}
