package net.playlegend.nbtstorage.nbt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class NbtTagEnd extends NbtBase {

  @Override
  void load(final DataInput dataInput, final int complexity, final NbtReadLimiter nbtReadLimiter) throws IOException {
  }

  @Override
  void write(final DataOutput dataOutput) {
  }

  @Override
  public NbtType getType() {
    return NbtType.END;
  }

  @Override
  public Object getData() {
    return null;
  }

  @Override
  public NbtBase clone() {
    return new NbtTagEnd();
  }
}
