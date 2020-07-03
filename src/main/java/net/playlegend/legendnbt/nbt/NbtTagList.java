package net.playlegend.legendnbt.nbt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.playlegend.legendnbt.exceptions.NbtException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class NbtTagList extends NbtBase {

  private List<NbtBase> list = new ArrayList<>();
  private NbtType type = NbtType.END;

  public NbtTagList(NbtType type, List<NbtBase> values) {
    this.type = type;
    this.list = values;
  }

  @Override
  void write(DataOutput dataOutput) throws IOException {
    if (!this.list.isEmpty()) {
      this.type = this.list.get(0).getType();
    } else {
      this.type = NbtType.END;
    }

    dataOutput.writeByte(this.type.toByte());
    dataOutput.writeInt(this.list.size());

    for (NbtBase value : this.list) {
      value.write(dataOutput);
    }

  }

  @Override
  void load(DataInput datainput, int complexity, NBTReadLimiter nbtReadLimiter) throws IOException {
    if (complexity > 512) {
      throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
    } else {
      nbtReadLimiter.allocate(8L);
      this.type = NbtType.fromByte(datainput.readByte());
      int j = datainput.readInt();
      nbtReadLimiter.allocate(j * 8);

      if ((this.type == NbtType.END) && (j > 0)) {
        throw new RuntimeException("Missing type on ListTag");
      }
      nbtReadLimiter.allocate(32L * j);

      this.list = new ArrayList<>(j);

      for (int k = 0; k < j; k++) {
        NbtBase nbtbase = this.type.newInstance();

        nbtbase.load(datainput, complexity + 1, nbtReadLimiter);
        this.list.add(nbtbase);
      }

    }
  }

  public NbtType getListType() {
    return this.type;
  }

  @Override
  public NbtType getType() {
    return NbtType.LIST;
  }

  @Override
  public List<NbtBase> getData() {
    return this.list;
  }

  public void add(NbtBase nbtBase) {
    if (this.type == NbtType.END) {
      this.type = nbtBase.getType();
    } else if (this.type != nbtBase.getType()) {
      throw new NbtException("Adding mismatching type to NBTTagList");
    }

    this.list.add(nbtBase);
  }

  public void set(int index, NbtBase nbtBase) {
    if (index >= 0 && index < this.list.size()) {
      if (this.type == NbtType.END) {
        this.type = nbtBase.getType();
      } else if (this.type != nbtBase.getType()) {
        throw new NbtException("Adding mismatching type to NBTTagList");
      }

      this.list.set(index, nbtBase);
    } else {
      throw new NbtException("Adding mismatching type to NBTTagList");
    }
  }

  public NbtBase remove(int index) {
    return this.list.remove(index);
  }

  @Override
  public boolean isEmpty() {
    return this.list.isEmpty();
  }

  public NbtTagCompound getCompound(int index) {
    if (index >= 0 && index < this.list.size()) {
      NbtBase nbtbase = this.list.get(index);

      return nbtbase.getType() == NbtType.COMPOUND ? (NbtTagCompound) nbtbase : new NbtTagCompound();
    } else {
      return new NbtTagCompound();
    }
  }

  public int[] getIntArray(int index) {
    if (index >= 0 && index < this.list.size()) {
      NbtBase nbtbase = this.list.get(index);

      return nbtbase.getType() == NbtType.INT_ARRAY ? ((NbtTagIntArray) nbtbase).getData() : new int[0];
    } else {
      return new int[0];
    }
  }

  public double getDouble(int index) {
    if (index >= 0 && index < this.list.size()) {
      NbtBase nbtbase = this.list.get(index);

      return nbtbase.getType() == NbtType.DOUBLE ? ((NbtTagDouble) nbtbase).getDouble() : 0.0D;
    } else {
      return 0.0D;
    }
  }

  public float getFloat(int index) {
    if (index >= 0 && index < this.list.size()) {
      NbtBase nbtbase = this.list.get(index);

      return nbtbase.getType() == NbtType.FLOAT ? ((NbtTagFloat) nbtbase).getFloat() : 0.0F;
    } else {
      return 0.0F;
    }
  }

  public String getString(int index) {
    if (index >= 0 && index < this.list.size()) {
      NbtBase nbtbase = this.list.get(index);

      return nbtbase.getType() == NbtType.STRING ? ((NbtTagString) nbtbase).getData() : nbtbase.toString();
    } else {
      return "";
    }
  }

  public NbtBase get(int index) {
    return index >= 0 && index < this.list.size() ? this.list.get(index) : new NbtTagEnd();
  }

  public int size() {
    return this.list.size();
  }

  @Override
  public NbtBase clone() {
    NbtTagList nbtTagList = new NbtTagList();

    nbtTagList.type = this.type;

    for (NbtBase nbtBase : this.list) {
      NbtBase copy = nbtBase.clone();

      nbtTagList.list.add(copy);
    }

    return nbtTagList;
  }

  @Override
  public boolean equals(Object other) {
    if (super.equals(other)) {
      NbtTagList nbttaglist = (NbtTagList) other;

      if (this.type == nbttaglist.type) {
        return this.list.equals(nbttaglist.list);
      }
    }

    return false;
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ this.list.hashCode();
  }
}
