package net.playlegend.nbtstorage.exceptions;

public class NbtLoadException extends RuntimeException {

  public NbtLoadException(final String message) {
    super(message);
  }

  public NbtLoadException(final Throwable cause) {
    super(cause);
  }

  public NbtLoadException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
