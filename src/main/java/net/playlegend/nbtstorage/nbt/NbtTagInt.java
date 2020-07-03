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

  public NbtTagInt(int data) {
    this.data = data;
  }

  @Override
  void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeInt(this.data);
  }

  @Override
  void load(DataInput datainput, int complexity, NBTReadLimiter nbtReadLimiter) throws IOException {
    nbtReadLimiter.allocate(32L);
    this.data = datainput.readInt();
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
  public boolean equals(Object other) {
    if (super.equals(other)) {
      NbtTagInt nbttagint = (NbtTagInt) other;

      return this.data == nbttagint.data;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ this.data;
  }
}
