package tv.rewinside.nbtstorage.nbt;

import tv.rewinside.nbtstorage.exceptions.NBTLoadException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagLongArray extends NBTBase {

	private long[] data;

	NBTTagLongArray() {
	}

	public NBTTagLongArray(long[] data) {
		this.data = data;
	}

	@Override
	void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(this.data.length);

		for (long value : this.data) {
			dataOutput.writeLong(value);
		}
	}

	@Override
	void load(DataInput dataInput, int i, NBTReadLimiter nbtReadLimiter) throws IOException {
		int count = dataInput.readInt();
		if (count >= 1 << 24) throw new NBTLoadException("Error while loading LongArray");

		nbtReadLimiter.allocate(64 * count);
		this.data = new long[count];

		for (int index = 0; index < count; index++) {
			this.data[index] = dataInput.readLong();
		}
	}

	@Override
	public NBTType getType() {
		return NBTType.LONG_ARRAY;
	}
	
	@Override
	public long[] getData() {
		return this.data;
	}

	@Override
	public NBTBase clone() {
		long[] copyData = new long[this.data.length];

		System.arraycopy(this.data, 0, copyData, 0, this.data.length);
		return new NBTTagLongArray(copyData);
	}
	
	@Override
	public boolean equals(Object object) {
		return super.equals(object) && Arrays.equals(this.data, ((NBTTagLongArray) object).data);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");

		for (long value : this.data) {
			builder.append(value).append(",");
		}
		builder.append("]");
		
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(this.data);
	}
}
