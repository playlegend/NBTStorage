package net.playlegend.legendnbt.exceptions;


public class NbtLoadException extends Error {

  public NbtLoadException(Throwable t) {
    super(t);
  }

  public NbtLoadException(String s) {
    super(s);
  }

  public NbtLoadException(String s, Throwable t) {
    super(s, t);
  }

}
