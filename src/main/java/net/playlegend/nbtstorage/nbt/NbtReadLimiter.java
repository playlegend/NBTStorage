package net.playlegend.nbtstorage.nbt;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class NbtReadLimiter {

  public static final NbtReadLimiter NO_LIMIT = new NbtReadLimiter(0L) {
    @Override
    public void allocate(final long bits) {
    }
  };

  private final long byteLimit;
  private long allocBytes;

  public void allocate(final long bits) {
    this.allocBytes += bits / 8L;
    if (this.allocBytes > this.byteLimit) {
      throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: "
          + this.allocBytes + "bytes where max allowed: " + this.byteLimit);
    }
  }
}
