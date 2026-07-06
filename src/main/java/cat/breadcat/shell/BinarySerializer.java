package cat.breadcat.shell;

import cat.breadcat.breech.bytes.BinaryReader;
import cat.breadcat.breech.bytes.BinaryWriter;

import java.io.IOException;

public interface BinarySerializer<T>
{
    void write(BinaryWriter out, T value) throws IOException;
    T read(BinaryReader in) throws IOException;
}