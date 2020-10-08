package net.playlegend.nbtstorage.nbt;

import lombok.ToString;
import net.playlegend.nbtstorage.exceptions.NbtLoadException;
import net.playlegend.nbtstorage.exceptions.NbtReadException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ToString
public class NbtTagCompound extends NbtBase {

  private final Map<String, NbtBase> map;

  public NbtTagCompound() {
    this.map = new HashMap<>();
  }

  public NbtTagCompound(final Map<String, NbtBase> map) {
    this.map = map;
  }

  @Override
  void write(final DataOutput dataOutput) throws IOException {
    for (String key : this.map.keySet()) {
      NbtBase nbtbase = this.map.get(key);
      writeTag(key, nbtbase, dataOutput);
    }

    dataOutput.writeByte(NbtType.END.toByte());
  }

  @Override
  void load(final DataInput dataInput, final int complexity, final NbtReadLimiter nbtReadLimiter) throws IOException {
    if (complexity > 512) {
      throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
    } else {
      this.map.clear();

      NbtType type;

      while ((type = NbtType.fromByte(readByte(dataInput, nbtReadLimiter))) != NbtType.END) {
        String key = readString(dataInput, nbtReadLimiter);

        nbtReadLimiter.allocate(16 * key.length());
        NbtBase nbtbase = readTag(type, dataInput, complexity + 1, nbtReadLimiter);

        this.map.put(key, nbtbase);
      }

    }
  }

  public Set<String> keySet() {
    return this.map.keySet();
  }

  @Override
  public NbtType getType() {
    return NbtType.COMPOUND;
  }

  @Override
  public NbtTagCompound getData() {
    return this;
  }

  public void set(final String key, final NbtBase value) {
    this.map.put(key, value);
  }

  public void setByte(final String key, final byte value) {
    this.map.put(key, new NbtTagByte(value));
  }

  public void setShort(final String key, final short value) {
    this.map.put(key, new NbtTagShort(value));
  }

  public void setInt(final String key, final int value) {
    this.map.put(key, new NbtTagInt(value));
  }

  public void setLong(final String key, final long value) {
    this.map.put(key, new NbtTagLong(value));
  }

  public void setFloat(final String key, final float value) {
    this.map.put(key, new NbtTagFloat(value));
  }

  public void setDouble(final String key, final double value) {
    this.map.put(key, new NbtTagDouble(value));
  }

  public void setString(final String key, final String value) {
    this.map.put(key, new NbtTagString(value));
  }

  public void setByteArray(final String key, final byte[] value) {
    this.map.put(key, new NbtTagByteArray(value));
  }

  public void setIntArray(final String key, final int[] value) {
    this.map.put(key, new NbtTagIntArray(value));
  }

  public void setLongArray(final String key, final long[] value) {
    this.map.put(key, new NbtTagLongArray(value));
  }

  public void setBoolean(final String key, final boolean value) {
    this.setByte(key, (byte) (value ? 1 : 0));
  }

  public NbtBase get(final String key) {
    return this.map.get(key);
  }

  public NbtType typeOf(final String key) {
    NbtBase nbtbase = this.map.get(key);

    return nbtbase != null ? nbtbase.getType() : NbtType.END;
  }

  public boolean hasKey(final String s) {
    return this.map.containsKey(s);
  }

  public boolean isNumber(final String key) {
    return this.typeOf(key).isNumber();
  }

  public byte getByte(final String key) {
    if (!this.isNumber(key)) {
      return 0;
    }
    NbtNumber number = (NbtNumber) this.map.get(key);

    try {
      return number.getByte();
    } catch (ClassCastException ex) {
      return 0;
    }
  }

  public short getShort(final String key) {
    if (!this.isNumber(key)) {
      return 0;
    }
    NbtNumber number = (NbtNumber) this.map.get(key);

    try {
      return number.getShort();
    } catch (ClassCastException ex) {
      return 0;
    }
  }

  public int getInt(final String key) {
    if (!this.isNumber(key)) {
      return 0;
    }
    NbtNumber number = (NbtNumber) this.map.get(key);

    try {
      return number.getInt();
    } catch (ClassCastException ex) {
      return 0;
    }
  }

  public long getLong(final String key) {
    if (!this.isNumber(key)) {
      return 0;
    }
    NbtNumber number = (NbtNumber) this.map.get(key);

    try {
      return number.getLong();
    } catch (ClassCastException ex) {
      return 0;
    }
  }

  public float getFloat(final String key) {
    if (!this.isNumber(key)) {
      return 0;
    }
    NbtNumber number = (NbtNumber) this.map.get(key);

    try {
      return number.getFloat();
    } catch (ClassCastException ex) {
      return 0;
    }
  }

  public double getDouble(final String key) {
    if (!this.isNumber(key)) {
      return 0;
    }
    NbtNumber number = (NbtNumber) this.map.get(key);

    try {
      return number.getDouble();
    } catch (ClassCastException ex) {
      return 0;
    }
  }

  public String getString(final String key) {
    if (this.typeOf(key) != NbtType.STRING) {
      return "";
    }
    return ((NbtTagString) this.map.get(key)).getData();
  }

  public byte[] getByteArray(final String key) {
    if (this.typeOf(key) != NbtType.BYTE_ARRAY) {
      return new byte[0];
    }
    return ((NbtTagByteArray) this.map.get(key)).getData();
  }

  public int[] getIntArray(final String key) {
    if (this.typeOf(key) != NbtType.INT_ARRAY) {
      return new int[0];
    }
    return ((NbtTagIntArray) this.map.get(key)).getData();
  }

  public long[] getLongArray(final String key) {
    if (this.typeOf(key) != NbtType.LONG_ARRAY) {
      return new long[0];
    }
    return ((NbtTagLongArray) this.map.get(key)).getData();
  }

  public NbtTagCompound getCompound(final String key) {
    if (this.typeOf(key) != NbtType.COMPOUND) {
      return new NbtTagCompound();
    }
    return (NbtTagCompound) this.map.get(key);
  }

  public NbtTagList getList(final String key, final NbtType type) {
    try {
      if (this.typeOf(key) != NbtType.LIST) {
        return new NbtTagList();
      } else {
        NbtTagList nbtTagList = (NbtTagList) this.map.get(key);

        return nbtTagList.size() == 0 || nbtTagList.getListType() != type ? new NbtTagList() : nbtTagList;
      }
    } catch (ClassCastException classcastexception) {
      throw new NbtReadException(classcastexception);
    }
  }

  public boolean getBoolean(final String key) {
    return this.getByte(key) != 0;
  }

  public void remove(final String s) {
    this.map.remove(s);
  }

  @Override
  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  private static NbtBase readTag(final NbtType type, final DataInput dataInput, final int complexity,
                                 final NbtReadLimiter nbtReadLimiter) throws IOException {
    NbtBase nbtBase = type.newInstance();

    try {
      nbtBase.load(dataInput, complexity, nbtReadLimiter);
      return nbtBase;
    } catch (IOException ioexception) {
      throw new NbtLoadException(ioexception);
    }
  }

  private static void writeTag(final String key, final NbtBase nbtBase, final DataOutput dataOutput)
      throws IOException {
    dataOutput.writeByte(nbtBase.getType().toByte());
    if (nbtBase.getType() != NbtType.END) {
      dataOutput.writeUTF(key);
      nbtBase.write(dataOutput);
    }
  }

  private static byte readByte(final DataInput dataInput, final NbtReadLimiter readLimiter) throws IOException {
    return dataInput.readByte();
  }

  private static String readString(final DataInput dataInput, final NbtReadLimiter nbtReadLimiter) throws IOException {
    return dataInput.readUTF();
  }

  public void merge(final NbtTagCompound compound) {
    compound.map.forEach((key, nbtBase) -> {
      if (nbtBase.getType() == NbtType.COMPOUND) {
        // Compound
        if (this.typeOf(key) == NbtType.COMPOUND) {
          // Combine the two compounds
          NbtTagCompound thisCompound = this.getCompound(key);
          thisCompound.merge((NbtTagCompound) nbtBase);
        } else {
          this.set(key, nbtBase.clone());
        }
      } else {
        this.set(key, nbtBase.clone());
      }
    });
  }

  @Override
  public NbtBase clone() {
    NbtTagCompound nbtTagCompound = new NbtTagCompound();

    for (String key : this.map.keySet()) {
      nbtTagCompound.set(key, this.map.get(key).clone());
    }

    return nbtTagCompound;
  }

  @Override
  public boolean equals(final Object other) {
    if (super.equals(other)) {
      NbtTagCompound nbtTagCompound = (NbtTagCompound) other;

      return this.map.entrySet().equals(nbtTagCompound.map.entrySet());
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ this.map.hashCode();
  }
}
