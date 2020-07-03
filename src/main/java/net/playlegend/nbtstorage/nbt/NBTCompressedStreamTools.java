package net.playlegend.nbtstorage.nbt;

import net.playlegend.nbtstorage.exceptions.NbtReadException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTCompressedStreamTools {

  public static NbtTagCompound read(InputStream inputstream) throws IOException {
    DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputstream)));

    NbtTagCompound nbttagcompound;

    try {
      nbttagcompound = readLimited(datainputstream, NBTReadLimiter.NO_LIMIT);
    } finally {
      datainputstream.close();
    }

    return nbttagcompound;
  }

  public static void write(NbtTagCompound nbttagcompound, OutputStream outputstream) throws IOException {
    try (DataOutputStream dataoutputstream
             = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputstream)))) {
      writeCompound(nbttagcompound, dataoutputstream);
    }

  }

  public static NbtTagCompound read(DataInputStream datainputstream) throws IOException {
    return readLimited(datainputstream, NBTReadLimiter.NO_LIMIT);
  }

  public static NbtTagCompound readLimited(DataInput datainput, NBTReadLimiter nbtreadlimiter) throws IOException {
    NbtBase nbtbase = readTag(datainput, 0, nbtreadlimiter);

    if (nbtbase instanceof NbtTagCompound) {
      return (NbtTagCompound) nbtbase;
    } else {
      throw new IOException("Root tag must be a named compound tag");
    }
  }

  public static void writeCompound(NbtTagCompound nbttagcompound, DataOutput dataoutput) throws IOException {
    writeTag(nbttagcompound, dataoutput);
  }

  private static void writeTag(NbtBase nbtbase, DataOutput dataoutput) throws IOException {
    dataoutput.writeByte(nbtbase.getType().toByte());
    if (nbtbase.getType() != NbtType.END) {
      dataoutput.writeUTF("");
      nbtbase.write(dataoutput);
    }
  }

  private static NbtBase readTag(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
    byte b0 = datainput.readByte();

    if (b0 == 0) {
      return new NbtTagEnd();
    } else {
      datainput.readUTF();
      NbtBase nbtbase = NbtType.fromByte(b0).newInstance();

      try {
        nbtbase.load(datainput, i, nbtreadlimiter);
        return nbtbase;
      } catch (IOException ioexception) {
        throw new NbtReadException(ioexception);
      }
    }
  }
}
