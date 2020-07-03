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

  public NbtTagDouble(double data) {
    this.data = data;
  }

  @Override
  void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeDouble(this.data);
  }

  @Override
  void load(DataInput datainput, int complexity, NBTReadLimiter nbtReadLimiter) throws IOException {
    nbtReadLimiter.allocate(64L);
    this.data = datainput.readDouble();
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
  public boolean equals(Object other) {
    if (super.equals(other)) {
      NbtTagDouble nbttagdouble = (NbtTagDouble) other;

      return this.data == nbttagdouble.data;
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
