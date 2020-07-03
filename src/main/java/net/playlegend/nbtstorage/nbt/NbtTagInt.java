package net.playlegend.nbtstorage.nbt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class NbtTagInt extends NbtBase.NbtNumber {

  private int data;

  public NbtTagInt(final int data) {
    this.data = data;
  }

  @Override
  void write(final DataOutput dataOutput) throws IOException {
    dataOutput.writeInt(this.data);
  }

  @Override
  void load(final DataInput dataInput, final int complexity, final NbtReadLimiter nbtReadLimiter) throws IOException {
    nbtReadLimiter.allocate(32L);
    this.data = dataInput.readInt();
  }

  @Override
  public NbtType getType() {
    return NbtType.INT;
  }

  @Override
  protected Number getNumber() {
    return this.data;
  }

  @Override
  public NbtBase clone() {
    return new NbtTagInt(this.data);
  }

  @Override
  public boolean equals(final Object other) {
    if (super.equals(other)) {
      NbtTagInt nbtTagInt = (NbtTagInt) other;

      return this.data == nbtTagInt.data;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ this.data;
  }
}
