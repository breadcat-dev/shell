package cat.breadcat.shell;

import cat.breadcat.breech.BinaryInput;
import cat.breadcat.breech.BinaryOutput;

import java.io.IOException;

public interface BinarySerializer<T>
{
    void write(BinaryOutput out, T value) throws IOException;
    T read(BinaryInput in) throws IOException;
}