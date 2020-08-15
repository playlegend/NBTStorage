package net.playlegend.nbtstorage.nbt;

import lombok.NoArgsConstructor;
import lombok.ToString;
import net.playlegend.nbtstorage.nbt.NbtBase.NbtNumber;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor
@ToString
public class NbtTagByte extends NbtNumber {

  private byte data;

  public NbtTagByte(final byte data) {
    this.data = data;
  }

  @Override
  void write(final DataOutput dataOutput) throws IOException {
    dataOutput.writeByte(this.data);
  }

  @Override
  void load(final DataInput dataInput, final int complexity, final NbtReadLimiter nbtReadLimiter) throws IOException {
    nbtReadLimiter.allocate(8L);
    this.data = dataInput.readByte();
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
  public boolean equals(final Object other) {
    if (super.equals(other)) {
      NbtTagByte nbtTagByte = (NbtTagByte) other;

      return this.data == nbtTagByte.data;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ this.data;
  }
}
