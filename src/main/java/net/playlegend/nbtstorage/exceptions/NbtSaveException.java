package net.playlegend.nbtstorage.exceptions;


public class NbtSaveException extends Error {

  public NbtSaveException(Throwable t) {
    super(t);
  }

  public NbtSaveException(String s) {
    super(s);
  }

  public NbtSaveException(String s, Throwable t) {
    super(s, t);
  }

}
