package net.playlegend.nbtstorage.nbt;

import lombok.ToString;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@ToString
public class NbtTagString extends NbtBase {

  private String data;

  public NbtTagString() {
    this.data = "";
  }

  public NbtTagString(final String data) {
    if (data == null) {
      throw new IllegalArgumentException("Empty string not allowed");
    }

    this.data = data;
  }

  @Override
  void write(final DataOutput dataOutput) throws IOException {
    dataOutput.writeUTF(this.data);
  }

  @Override
  void load(final DataInput dataInput, final int complexity, final NbtReadLimiter nbtReadLimiter) throws IOException {
    this.data = dataInput.readUTF();
    nbtReadLimiter.allocate(16 * this.data.length());
  }

  @Override
  public NbtType getType() {
    return NbtType.STRING;
  }

  @Override
  public String getData() {
    return this.data;
  }

  @Override
  public boolean isEmpty() {
    return this.data.isEmpty();
  }

  @Override
  public NbtBase clone() {
    return new NbtTagString(this.data);
  }

  @Override
  public boolean equals(final Object other) {
    if (!super.equals(other)) {
      return false;
    } else {
      NbtTagString nbtTagString = (NbtTagString) other;

      return this.data == null && nbtTagString.data == null || this.data != null && this.data.equals(nbtTagString.data);
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ this.data.hashCode();
  }
}
