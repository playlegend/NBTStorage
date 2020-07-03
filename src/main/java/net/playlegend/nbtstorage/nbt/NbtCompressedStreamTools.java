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

public class NbtCompressedStreamTools {

  public static NbtTagCompound read(final DataInputStream dataInputStream) throws IOException {
    return readLimited(dataInputStream, NbtReadLimiter.NO_LIMIT);
  }

  public static NbtTagCompound read(final InputStream inputStream) throws IOException {
    try (DataInputStream datainputstream
             = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream)))) {
      return readLimited(datainputstream, NbtReadLimiter.NO_LIMIT);
    }
  }

  public static void write(final NbtTagCompound nbtTagCompound, final OutputStream outputStream) throws IOException {
    try (DataOutputStream dataoutputstream
             = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)))) {
      writeCompound(nbtTagCompound, dataoutputstream);
    }
  }

  public static NbtTagCompound readLimited(final DataInput dataInput, final NbtReadLimiter nbtReadLimiter)
      throws IOException {
    NbtBase nbtbase = readTag(dataInput, 0, nbtReadLimiter);

    if (nbtbase instanceof NbtTagCompound) {
      return (NbtTagCompound) nbtbase;
    } else {
      throw new IOException("Root tag must be a named compound tag");
    }
  }

  public static void writeCompound(final NbtTagCompound nbtTagCompound, final DataOutput dataOutput)
      throws IOException {
    writeTag(nbtTagCompound, dataOutput);
  }

  private static void writeTag(final NbtBase nbtBase, final DataOutput dataOutput) throws IOException {
    dataOutput.writeByte(nbtBase.getType().toByte());
    if (nbtBase.getType() != NbtType.END) {
      dataOutput.writeUTF("");
      nbtBase.write(dataOutput);
    }
  }

  private static NbtBase readTag(final DataInput dataInput, final int complexity, final NbtReadLimiter nbtReadLimiter)
      throws IOException {
    byte type = dataInput.readByte();

    if (type == 0) {
      return new NbtTagEnd();
    } else {
      dataInput.readUTF();
      NbtBase nbtbase = NbtType.fromByte(type).newInstance();

      try {
        nbtbase.load(dataInput, complexity, nbtReadLimiter);
        return nbtbase;
      } catch (IOException ioexception) {
        throw new NbtReadException(ioexception);
      }
    }
  }
}
