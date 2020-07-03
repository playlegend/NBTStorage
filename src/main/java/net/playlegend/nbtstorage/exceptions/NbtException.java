package net.playlegend.nbtstorage.exceptions;

public class NbtException extends Error {

  public NbtException(final String message) {
    super(message);
  }

  public NbtException(final Throwable cause) {
    super(cause);
  }

  public NbtException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
