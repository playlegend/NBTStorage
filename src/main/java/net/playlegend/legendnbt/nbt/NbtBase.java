package net.playlegend.legendnbt.nbt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class NbtBase {

  abstract void write(DataOutput dataOutput) throws IOException;

  abstract void load(DataInput datainput, int complexity, NBTReadLimiter nbtReadLimiter) throws IOException;

  public abstract NbtType getType();

  public abstract Object getData();

  public boolean isEmpty() {
    return false;
  }

  @Override
  public abstract NbtBase clone();

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof NbtBase)) {
      return false;
    } else {
      NbtBase nbtbase = (NbtBase) other;

      return this.getType() == nbtbase.getType();
    }
  }

  @Override
  public abstract String toString();

  @Override
  public int hashCode() {
    return this.getType().toByte();
  }

  public abstract static class NbtNumber extends NbtBase {

    protected abstract Number getNumber();

    @Override
    public Number getData() {
      return this.getNumber();
    }

    public byte getByte() {
      return (byte) this.getNumber();
    }

    public short getShort() {
      return (short) this.getNumber();
    }

    public int getInt() {
      return (int) this.getNumber();
    }

    public long getLong() {
      return (long) this.getNumber();
    }

    public float getFloat() {
      return (float) this.getNumber();
    }

    public double getDouble() {
      return (double) this.getNumber();
    }
  }
}
