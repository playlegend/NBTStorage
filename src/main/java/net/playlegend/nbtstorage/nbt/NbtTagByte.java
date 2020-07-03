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
public class NbtTagByte extends NbtNumber {

  private byte data;

  public NbtTagByte(byte data) {
    this.data = data;
  }

  @Override
  void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeByte(this.data);
  }

  @Override
  void load(DataInput datainput, int complexity, NBTReadLimiter nbtReadLimiter) throws IOException {
    nbtReadLimiter.allocate(8L);
    this.data = datainput.readByte();
  }

  @Override
  public NbtType getType() {
    return NbtType.BYTE;
  }

  @Override
  protected Number getNumber() {
    return this.data;
  }

  @Override
  public NbtBase clone() {
    return new NbtTagByte(this.data);
  }

  @Override
  public boolean equals(Object other) {
    if (super.equals(other)) {
      NbtTagByte nbttagbyte = (NbtTagByte) other;

      return this.data == nbttagbyte.data;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ this.data;
  }
}
