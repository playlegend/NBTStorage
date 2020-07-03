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

	public NBTTagLongArray(long[] along) {
		this.data = along;
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
		dataoutput.writeInt(this.data.length);

		for (int i = 0; i < this.data.length; ++i) {
			dataoutput.writeLong(this.data[i]);
		}

	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		int j = datainput.readInt();
		if (j >= 1 << 24) throw new NBTLoadException("Error while loading LongArray");

		nbtreadlimiter.allocate((long) (64 * j));
		this.data = new long[j];

		for (int k = 0; k < j; ++k) {
			this.data[k] = datainput.readLong();
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
		long[] along = new long[this.data.length];

		System.arraycopy(this.data, 0, along, 0, this.data.length);
		return new NBTTagLongArray(along);
	}
	
	@Override
	public boolean equals(Object object) {
		return super.equals(object) && Arrays.equals(this.data, ((NBTTagLongArray) object).data);
	}
	
	@Override
	public String toString() {
		String s = "[";
		long[] along = this.data;
		int i = along.length;

		for (int j = 0; j < i; ++j) {
			long k = along[j];

			s = s + k + ",";
		}

		return s + "]";
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(this.data);
	}
}
