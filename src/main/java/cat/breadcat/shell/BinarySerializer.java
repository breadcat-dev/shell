package cat.breadcat.shell;

import cat.breadcat.breech.BinaryInput;
import cat.breadcat.breech.BinaryOutput;

import java.io.IOException;

public interface BinarySerializer<T>
{
    void write(BinaryOutput out, T value) throws IOException;
    T read(BinaryInput in) throws IOException;
}

// write type id

/*
@Override
    public void write(BinaryOutput out, Player data) throws IOException
    {
        if (data == null)
            throw new NullPointerException("data");

        out.writeString(data.name());
        out.writeInt(data.coins());
    }

    @Override
    public Player read(BinaryInput in) throws IOException
    {
        String name = in.readString();
        int coins = in.readInt();

        return new Player(name, coins);
    }
*/