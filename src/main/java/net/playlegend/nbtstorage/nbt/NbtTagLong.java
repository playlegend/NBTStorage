package net.playlegend.nbtstorage.nbt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.playlegend.nbtstorage.nbt.NbtBase.NbtNumber;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class NbtTagLong extends NbtNumber {

  private long data;

  public NbtTagLong(final long data) {
    this.data = data;
  }

  @Override
  void write(final DataOutput dataOutput) throws IOException {
    dataOutput.writeLong(this.data);
  }

  @Override
  void load(final DataInput dataInput, final int complexity, final NbtReadLimiter nbtReadLimiter) throws IOException {
    nbtReadLimiter.allocate(64L);
    this.data = dataInput.readLong();
  }

  @Override
  public NbtType getType() {
    return NbtType.LONG;
  }

  @Override
  protected Number getNumber() {
    return this.data;
  }

  @Override
  public NbtBase clone() {
    return new NbtTagLong(this.data);
  }

  @Override
  public boolean equals(final Object other) {
    if (super.equals(other)) {
      NbtTagLong nbtTagLong = (NbtTagLong) other;

      return this.data == nbtTagLong.data;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ (int) (this.data ^ this.data >>> 32);
  }
}
