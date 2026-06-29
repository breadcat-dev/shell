package cat.breadcat.shell;

import cat.breadcat.breech.bytes.ByteInputStream;
import cat.breadcat.breech.bytes.ByteOutputStream;

import java.io.IOException;

public interface BinarySerializer<T>
{
    void write(ByteOutputStream out, T value) throws IOException;
    T read(ByteInputStream in) throws IOException;
}