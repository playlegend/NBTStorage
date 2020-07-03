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
public class NbtTagDouble extends NbtNumber {

  private double data;

  public NbtTagDouble(final double data) {
    this.data = data;
  }

  @Override
  void write(final DataOutput dataOutput) throws IOException {
    dataOutput.writeDouble(this.data);
  }

  @Override
  void load(final DataInput dataInput, final int complexity, final NbtReadLimiter nbtReadLimiter) throws IOException {
    nbtReadLimiter.allocate(64L);
    this.data = dataInput.readDouble();
  }

  @Override
  public NbtType getType() {
    return NbtType.DOUBLE;
  }

  @Override
  protected Number getNumber() {
    return this.data;
  }

  @Override
  public NbtBase clone() {
    return new NbtTagDouble(this.data);
  }

  @Override
  public boolean equals(final Object other) {
    if (super.equals(other)) {
      NbtTagDouble nbtTagDouble = (NbtTagDouble) other;

      return this.data == nbtTagDouble.data;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    long i = Double.doubleToLongBits(this.data);

    return super.hashCode() ^ (int) (i ^ i >>> 32);
  }

}
