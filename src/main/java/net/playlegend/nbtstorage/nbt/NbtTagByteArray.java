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
public class NbtTagByteArray extends NbtBase {

  private byte[] data;

  public NbtTagByteArray(final byte[] data) {
    this.data = data;
  }

  @Override
  void write(final DataOutput dataOutput) throws IOException {
    dataOutput.writeInt(this.data.length);
    dataOutput.write(this.data);
  }

  @Override
  void load(final DataInput dataInput, final int complexity, final NbtReadLimiter nbtReadLimiter) throws IOException {
    int count = dataInput.readInt();
    if (count >= 1 << 24) {
      throw new NbtLoadException("Error while loading ByteArray");
    }

    nbtReadLimiter.allocate(8 * count);
    this.data = new byte[count];
    dataInput.readFully(this.data);
  }

  @Override
  public NbtType getType() {
    return NbtType.BYTE_ARRAY;
  }

  @Override
  public byte[] getData() {
    return this.data;
  }

  @Override
  public NbtBase clone() {
    byte[] dataCopy = new byte[this.data.length];

    System.arraycopy(this.data, 0, dataCopy, 0, this.data.length);
    return new NbtTagByteArray(dataCopy);
  }

  @Override
  public boolean equals(final Object other) {
    return super.equals(other) && Arrays.equals(this.data, ((NbtTagByteArray) other).data);
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ Arrays.hashCode(this.data);
  }

}
