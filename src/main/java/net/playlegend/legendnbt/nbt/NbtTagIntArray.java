package net.playlegend.legendnbt.nbt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.playlegend.legendnbt.exceptions.NbtLoadException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class NbtTagIntArray extends NbtBase {

  private int[] data;

  public NbtTagIntArray(int[] data) {
    this.data = data;
  }

  @Override
  void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeInt(this.data.length);

    for (int value : this.data) {
      dataOutput.writeInt(value);
    }

  }

  @Override
  void load(DataInput datainput, int complexity, NBTReadLimiter nbtReadLimiter) throws IOException {
    int count = datainput.readInt();
    if (count >= 1 << 24) {
      throw new NbtLoadException("Error while loading IntArray");
    }

    nbtReadLimiter.allocate(32 * count);
    this.data = new int[count];

    for (int index = 0; index < count; index++) {
      this.data[index] = datainput.readInt();
    }

  }

  @Override
  public NbtType getType() {
    return NbtType.INT_ARRAY;
  }

  @Override
  public int[] getData() {
    return this.data;
  }

  @Override
  public NbtBase clone() {
    int[] dataCopy = new int[this.data.length];

    System.arraycopy(this.data, 0, dataCopy, 0, this.data.length);
    return new NbtTagIntArray(dataCopy);
  }

  @Override
  public boolean equals(Object other) {
    return super.equals(other) && Arrays.equals(this.data, ((NbtTagIntArray) other).data);
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ Arrays.hashCode(this.data);
  }
}
