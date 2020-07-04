package net.playlegend.nbtstorage.nbt;

import lombok.NoArgsConstructor;
import lombok.ToString;
import net.playlegend.nbtstorage.exceptions.NbtException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@ToString
public class NbtTagList extends NbtBase {

  private List<NbtBase> list = new ArrayList<>();
  private NbtType type = NbtType.END;

  public NbtTagList(final NbtType type, final List<NbtBase> values) {
    this.type = type;
    this.list = values;
  }

  @Override
  void write(final DataOutput dataOutput) throws IOException {
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
  void load(final DataInput dataInput, final int complexity, final NbtReadLimiter nbtReadLimiter) throws IOException {
    if (complexity > 512) {
      throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
    } else {
      nbtReadLimiter.allocate(8L);
      this.type = NbtType.fromByte(dataInput.readByte());
      int j = dataInput.readInt();
      nbtReadLimiter.allocate(j * 8);

      if ((this.type == NbtType.END) && (j > 0)) {
        throw new RuntimeException("Missing type on ListTag");
      }
      nbtReadLimiter.allocate(32L * j);

      this.list = new ArrayList<>(j);

      for (int k = 0; k < j; k++) {
        NbtBase nbtbase = this.type.newInstance();

        nbtbase.load(dataInput, complexity + 1, nbtReadLimiter);
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

  public void add(final NbtBase nbtBase) {
    if (this.type == NbtType.END) {
      this.type = nbtBase.getType();
    } else if (this.type != nbtBase.getType()) {
      throw new NbtException("Adding mismatching type to NBTTagList");
    }

    this.list.add(nbtBase);
  }

  public void set(final int index, final NbtBase nbtBase) {
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

  public NbtBase remove(final int index) {
    return this.list.remove(index);
  }

  @Override
  public boolean isEmpty() {
    return this.list.isEmpty();
  }

  public NbtTagCompound getCompound(final int index) {
    if (index >= 0 && index < this.list.size()) {
      NbtBase nbtBase = this.list.get(index);

      return nbtBase.getType() == NbtType.COMPOUND ? (NbtTagCompound) nbtBase : new NbtTagCompound();
    } else {
      return new NbtTagCompound();
    }
  }

  public int[] getIntArray(final int index) {
    if (index >= 0 && index < this.list.size()) {
      NbtBase nbtBase = this.list.get(index);

      return nbtBase.getType() == NbtType.INT_ARRAY ? ((NbtTagIntArray) nbtBase).getData() : new int[0];
    } else {
      return new int[0];
    }
  }

  public double getDouble(final int index) {
    if (index >= 0 && index < this.list.size()) {
      NbtBase nbtBase = this.list.get(index);

      return nbtBase.getType() == NbtType.DOUBLE ? ((NbtTagDouble) nbtBase).getDouble() : 0.0D;
    } else {
      return 0.0D;
    }
  }

  public float getFloat(final int index) {
    if (index >= 0 && index < this.list.size()) {
      NbtBase nbtBase = this.list.get(index);

      return nbtBase.getType() == NbtType.FLOAT ? ((NbtTagFloat) nbtBase).getFloat() : 0.0F;
    } else {
      return 0.0F;
    }
  }

  public String getString(final int index) {
    if (index >= 0 && index < this.list.size()) {
      NbtBase nbtBase = this.list.get(index);

      return nbtBase.getType() == NbtType.STRING ? ((NbtTagString) nbtBase).getData() : nbtBase.toString();
    } else {
      return "";
    }
  }

  public NbtBase get(final int index) {
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
  public boolean equals(final Object other) {
    if (super.equals(other)) {
      NbtTagList nbtTagList = (NbtTagList) other;

      if (this.type == nbtTagList.type) {
        return this.list.equals(nbtTagList.list);
      }
    }

    return false;
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ this.list.hashCode();
  }
}
