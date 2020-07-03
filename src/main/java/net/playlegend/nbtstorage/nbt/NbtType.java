package net.playlegend.nbtstorage.nbt;

public enum NbtType {

  END(0, NbtTagEnd::new),
  BYTE(1, NbtTagByte::new),
  SHORT(2, NbtTagShort::new),
  INT(3, NbtTagInt::new),
  LONG(4, NbtTagLong::new),
  FLOAT(5, NbtTagFloat::new),
  DOUBLE(6, NbtTagDouble::new),
  BYTE_ARRAY(7, NbtTagByteArray::new),
  STRING(8, NbtTagString::new),
  LIST(9, NbtTagList::new),
  COMPOUND(10, NbtTagCompound::new),
  INT_ARRAY(11, NbtTagIntArray::new),
  LONG_ARRAY(12, NbtTagLongArray::new);

  private static final NbtType[] types;
  private final byte byteType;
  private final InstanceCreator instanceCreator;

  static {
    byte length = 0;
    for (NbtType type : values()) {
      if (length <= type.byteType) {
        length = (byte) (type.byteType + 1);
      }
    }

    types = new NbtType[length];

    for (NbtType type : values()) {
      types[type.byteType] = type;
    }
  }

  NbtType(final int byteType, final InstanceCreator instanceCreator) {
    if (byteType < -128 || byteType > 127) {
      throw new IllegalArgumentException("byteType cannot be bigger than 127 or smaller than -128");
    }
    this.byteType = (byte) byteType;
    this.instanceCreator = instanceCreator;
  }

  public byte toByte() {
    return this.byteType;
  }

  public NbtBase newInstance() {
    return this.instanceCreator.newInstance();
  }

  public boolean isNumber() {
    switch (this) {
      case BYTE:
      case SHORT:
      case INT:
      case LONG:
      case FLOAT:
      case DOUBLE:
        return true;
      default:
        return false;
    }
  }

  public static NbtType fromByte(final byte b) {
    return types[b];
  }

  private interface InstanceCreator {

    NbtBase newInstance();

  }
}
