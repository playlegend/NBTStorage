package net.playlegend.legendnbt.nbt;

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

  public NbtTagString(String s) {
    this.data = s;
    if (s == null) {
      throw new IllegalArgumentException("Empty string not allowed");
    }
  }

  @Override
  void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeUTF(this.data);
  }

  @Override
  void load(DataInput datainput, int complexity, NBTReadLimiter nbtReadLimiter) throws IOException {
    this.data = datainput.readUTF();
    nbtReadLimiter.allocate((long) (16 * this.data.length()));
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
  public boolean equals(Object other) {
    if (!super.equals(other)) {
      return false;
    } else {
      NbtTagString nbttagstring = (NbtTagString) other;

      return this.data == null && nbttagstring.data == null || this.data != null && this.data.equals(nbttagstring.data);
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ this.data.hashCode();
  }
}
