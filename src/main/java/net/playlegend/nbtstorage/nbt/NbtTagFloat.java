package net.playlegend.nbtstorage.nbt;

import lombok.NoArgsConstructor;
import lombok.ToString;
import net.playlegend.nbtstorage.nbt.NbtBase.NbtNumber;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor
@ToString
public class NbtTagFloat extends NbtNumber {

  private float data;

  public NbtTagFloat(final float data) {
    this.data = data;
  }

  @Override
  void write(final DataOutput dataOutput) throws IOException {
    dataOutput.writeFloat(this.data);
  }

  @Override
  void load(final DataInput dataInput, final int complexity, final NbtReadLimiter nbtReadLimiter) throws IOException {
    nbtReadLimiter.allocate(32L);
    this.data = dataInput.readFloat();
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
  public boolean equals(final Object other) {
    if (super.equals(other)) {
      NbtTagFloat nbtTagFloat = (NbtTagFloat) other;

      return this.data == nbtTagFloat.data;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ Float.floatToIntBits(this.data);
  }
}
