package net.playlegend.nbtstorage.exceptions;

public class NbtSaveException extends Error {

  public NbtSaveException(final String message) {
    super(message);
  }

  public NbtSaveException(final Throwable cause) {
    super(cause);
  }

  public NbtSaveException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
