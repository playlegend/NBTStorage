package net.playlegend.legendnbt.nbt;

import lombok.ToString;
import net.playlegend.legendnbt.exceptions.NbtLoadException;
import net.playlegend.legendnbt.exceptions.NbtReadException;

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

  public NbtTagCompound(Map<String, NbtBase> map) {
    this.map = map;
  }

  @Override
  void write(DataOutput dataOutput) throws IOException {
    for (String key : this.map.keySet()) {
      NbtBase nbtbase = this.map.get(key);
      writeTag(key, nbtbase, dataOutput);
    }

    dataOutput.writeByte(NbtType.END.toByte());
  }

  @Override
  void load(DataInput datainput, int complexity, NBTReadLimiter nbtReadLimiter) throws IOException {
    if (complexity > 512) {
      throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
    } else {
      this.map.clear();

      NbtType type;

      while ((type = NbtType.fromByte(readByte(datainput, nbtReadLimiter))) != NbtType.END) {
        String key = readString(datainput, nbtReadLimiter);

        nbtReadLimiter.allocate(16 * key.length());
        NbtBase nbtbase = readTag(type, key, datainput, complexity + 1, nbtReadLimiter);

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

  public void set(String key, NbtBase value) {
    this.map.put(key, value);
  }

  public void setByte(String key, byte value) {
    this.map.put(key, new NbtTagByte(value));
  }

  public void setShort(String key, short value) {
    this.map.put(key, new NbtTagShort(value));
  }

  public void setInt(String key, int value) {
    this.map.put(key, new NbtTagInt(value));
  }

  public void setLong(String key, long value) {
    this.map.put(key, new NbtTagLong(value));
  }

  public void setFloat(String key, float value) {
    this.map.put(key, new NbtTagFloat(value));
  }

  public void setDouble(String key, double value) {
    this.map.put(key, new NbtTagDouble(value));
  }

  public void setString(String key, String value) {
    this.map.put(key, new NbtTagString(value));
  }

  public void setByteArray(String key, byte[] value) {
    this.map.put(key, new NbtTagByteArray(value));
  }

  public void setIntArray(String key, int[] value) {
    this.map.put(key, new NbtTagIntArray(value));
  }

  public void setBoolean(String key, boolean value) {
    this.setByte(key, (byte) (value ? 1 : 0));
  }

  public NbtBase get(String key) {
    return (NbtBase) this.map.get(key);
  }

  public NbtType typeOf(String key) {
    NbtBase nbtbase = (NbtBase) this.map.get(key);

    return nbtbase != null ? nbtbase.getType() : NbtType.END;
  }

  public boolean hasKey(String s) {
    return this.map.containsKey(s);
  }

  public boolean isNumber(String key) {
    return this.typeOf(key).isNumber();
  }

  public byte getByte(String key) {
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

  public short getShort(String key) {
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

  public int getInt(String key) {
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

  public long getLong(String key) {
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

  public float getFloat(String key) {
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

  public double getDouble(String key) {
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

  public String getString(String key) {
    if (this.typeOf(key) != NbtType.STRING) {
      return "";
    }
    return ((NbtTagString) this.map.get(key)).getData();
  }

  public byte[] getByteArray(String key) {
    if (this.typeOf(key) != NbtType.BYTE_ARRAY) {
      return new byte[0];
    }
    return ((NbtTagByteArray) this.map.get(key)).getData();
  }

  public int[] getIntArray(String key) {
    if (this.typeOf(key) != NbtType.INT_ARRAY) {
      return new int[0];
    }
    return ((NbtTagIntArray) this.map.get(key)).getData();
  }

  public NbtTagCompound getCompound(String key) {
    if (this.typeOf(key) != NbtType.COMPOUND) {
      return new NbtTagCompound();
    }
    return (NbtTagCompound) this.map.get(key);
  }

  public NbtTagList getList(String key, NbtType type) {
    try {
      if (this.typeOf(key) != NbtType.LIST) {
        return new NbtTagList();
      } else {
        NbtTagList nbttaglist = (NbtTagList) this.map.get(key);

        return nbttaglist.size() == 0 || nbttaglist.getListType() != type ? new NbtTagList() : nbttaglist;
      }
    } catch (ClassCastException classcastexception) {
      throw new NbtReadException(classcastexception);
    }
  }

  public boolean getBoolean(String key) {
    return this.getByte(key) != 0;
  }

  public void remove(String s) {
    this.map.remove(s);
  }

  @Override
  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  static NbtBase readTag(NbtType type, String s, DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
    NbtBase nbtbase = type.newInstance();

    try {
      nbtbase.load(datainput, i, nbtreadlimiter);
      return nbtbase;
    } catch (IOException ioexception) {
      throw new NbtLoadException(ioexception);
    }
  }

  private static void writeTag(String s, NbtBase nbtbase, DataOutput dataoutput) throws IOException {
    dataoutput.writeByte(nbtbase.getType().toByte());
    if (nbtbase.getType() != NbtType.END) {
      dataoutput.writeUTF(s);
      nbtbase.write(dataoutput);
    }
  }

  private static byte readByte(DataInput datainput, NBTReadLimiter nbtreadlimiter) throws IOException {
    return datainput.readByte();
  }

  private static String readString(DataInput datainput, NBTReadLimiter nbtreadlimiter) throws IOException {
    return datainput.readUTF();
  }

  public void merge(NbtTagCompound compound) {
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
    NbtTagCompound nbttagcompound = new NbtTagCompound();

    for (String s : this.map.keySet()) {
      nbttagcompound.set(s, this.map.get(s).clone());
    }

    return nbttagcompound;
  }

  @Override
  public boolean equals(Object other) {
    if (super.equals(other)) {
      NbtTagCompound nbttagcompound = (NbtTagCompound) other;

      return this.map.entrySet().equals(nbttagcompound.map.entrySet());
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ this.map.hashCode();
  }
}
