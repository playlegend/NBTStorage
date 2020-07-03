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
public class NbtTagFloat extends NbtNumber {

  private float data;

  public NbtTagFloat(float data) {
    this.data = data;
  }

  @Override
  void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeFloat(this.data);
  }

  @Override
  void load(DataInput datainput, int complexity, NBTReadLimiter nbtReadLimiter) throws IOException {
    nbtReadLimiter.allocate(32L);
    this.data = datainput.readFloat();
  }

  @Override
  public NbtType getType() {
    return NbtType.FLOAT;
  }

  @Override
  protected Number getNumber() {
    return this.data;
  }

  @Override
  public NbtBase clone() {
    return new NbtTagFloat(this.data);
  }

  @Override
  public boolean equals(Object other) {
    if (super.equals(other)) {
      NbtTagFloat nbttagfloat = (NbtTagFloat) other;

      return this.data == nbttagfloat.data;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ Float.floatToIntBits(this.data);
  }
}
