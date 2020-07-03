package net.playlegend.nbtstorage.exceptions;


public class NbtException extends Error {

  public NbtException(String s) {
    super(s);
  }

  public NbtException(Throwable t) {
    super(t);
  }

  public NbtException(String s, Throwable t) {
    super(s, t);
  }

}
