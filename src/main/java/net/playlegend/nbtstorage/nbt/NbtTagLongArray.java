package net.playlegend.nbtstorage.nbt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.playlegend.nbtstorage.exceptions.NbtLoadException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class NbtTagLongArray extends NbtBase {

  private long[] data;

  public NbtTagLongArray(final long[] data) {
    this.data = data;
  }

  @Override
  void write(final DataOutput dataOutput) throws IOException {
    dataOutput.writeInt(this.data.length);

    for (long value : this.data) {
      dataOutput.writeLong(value);
    }
  }

  @Override
  void load(final DataInput dataInput, final int complexity, final NbtReadLimiter nbtReadLimiter) throws IOException {
    int count = dataInput.readInt();
    if (count >= 1 << 24) {
      throw new NbtLoadException("Error while loading LongArray");
    }

    nbtReadLimiter.allocate(64 * count);
    this.data = new long[count];

    for (int index = 0; index < count; index++) {
      this.data[index] = dataInput.readLong();
    }
  }

  @Override
  public NbtType getType() {
    return NbtType.LONG_ARRAY;
  }

  @Override
  public long[] getData() {
    return this.data;
  }

  @Override
  public NbtBase clone() {
    long[] copyData = new long[this.data.length];

    System.arraycopy(this.data, 0, copyData, 0, this.data.length);
    return new NbtTagLongArray(copyData);
  }

  @Override
  public boolean equals(final Object other) {
    return super.equals(other) && Arrays.equals(this.data, ((NbtTagLongArray) other).data);
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ Arrays.hashCode(this.data);
  }
}
