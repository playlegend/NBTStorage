package tv.rewinside.nbtstorage.nbt;

import tv.rewinside.nbtstorage.exceptions.NBTException;


public enum NBTType {

	END(0, NBTTagEnd.class),
	BYTE(1, NBTTagByte.class),
	SHORT(2, NBTTagShort.class),
	INT(3, NBTTagInt.class),
	LONG(4, NBTTagLong.class),
	FLOAT(5, NBTTagFloat.class),
	DOUBLE(6, NBTTagDouble.class),
	BYTE_ARRAY(7, NBTTagByteArray.class),
	STRING(8, NBTTagString.class),
	LIST(9, NBTTagList.class),
	COMPOUND(10, NBTTagCompound.class),
	INT_ARRAY(11, NBTTagIntArray.class),
	LONG_ARRAY(12, NBTTagLongArray.class);
	
	private byte byteType;
	private Class<? extends NBTBase> clazz;
	
	private NBTType(int byteType, Class<? extends NBTBase> clazz) {
		if (byteType < -128 || byteType > 127) throw new IllegalArgumentException("byteType cannot be bigger than 127 or smaller than -128");
		this.byteType = (byte) byteType;
		this.clazz = clazz;
	}
	
	public byte toByte() {
		return this.byteType;
	}
	
	public NBTBase newInstance() {
		try {
			return (NBTBase) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException ex) {
			throw new NBTException(String.format("Error while creating new Instance of type %s", this.name()), ex);
		}
	}
	
	public boolean isNumber() {
		switch(this) {
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
	
	public static NBTType fromByte(byte b) {
		for (NBTType type : values()) {
			if (type.toByte() == b) {
				return type;
			}
		}
		
		throw new IllegalArgumentException(String.format("Unknown NBTType %d", b));
	}
	
}
