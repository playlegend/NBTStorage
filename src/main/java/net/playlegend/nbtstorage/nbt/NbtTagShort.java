package net.playlegend.nbtstorage.nbt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class NbtTagShort extends NbtBase.NbtNumber {

  private short data;

  public NbtTagShort(final short data) {
    this.data = data;
  }

  @Override
  void write(final DataOutput dataOutput) throws IOException {
    dataOutput.writeShort(this.data);
  }

  @Override
  void load(final DataInput dataInput, final int complexity, final NbtReadLimiter nbtReadLimiter) throws IOException {
    nbtReadLimiter.allocate(16L);
    this.data = dataInput.readShort();
  }

  @Override
  public NbtType getType() {
    return NbtType.SHORT;
  }

  @Override
  protected Number getNumber() {
    return this.data;
  }

  @Override
  public NbtBase clone() {
    return new NbtTagShort(this.data);
  }

  @Override
  public boolean equals(final Object other) {
    if (super.equals(other)) {
      NbtTagShort nbtTagShort = (NbtTagShort) other;

      return this.data == nbtTagShort.data;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ this.data;
  }
}
