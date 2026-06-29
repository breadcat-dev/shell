package cat.breadcat.shell;

import cat.breadcat.breech.bytes.ByteInputStream;
import cat.breadcat.breech.bytes.ByteOutputStream;
import cat.breadcat.exception.SerializationFailureException;
import cat.breadcat.exception.SerializerAlreadyRegisteredException;
import cat.breadcat.exception.NoSerializerForTypeException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SerializerRegistry
{
    private final Map<Class<?>, BinarySerializer<?>> registry;

    public SerializerRegistry()
    {
        this.registry = new HashMap<>();
    }


    @SuppressWarnings("unchecked")
    private <T> BinarySerializer<T> serializer(Class<T> type)
    {
        BinarySerializer<T> serializer = (BinarySerializer<T>)registry.get(type);
        if(serializer == null)
            throw new NoSerializerForTypeException(type);

        return serializer;
    }


    public <T> void register(Class<T> type, BinarySerializer<T> serializer)
    {
        if(isRegistered(type))
            throw new SerializerAlreadyRegisteredException(type);

        registry.put(type, serializer);
    }

    @SuppressWarnings("unchecked")
    public <T> void serialize(ByteOutputStream out, T data)
    {
        if(data == null)
            throw new NullPointerException("data");

        Class<T> type = (Class<T>)data.getClass();
        BinarySerializer<T> serializer = serializer(type);

        try
        {
            serializer.write(out, data);
        }
        catch(IOException e)
        {
            throw new SerializationFailureException(type, e);
        }
    }

    public <T> T deserialize(ByteInputStream in, Class<T> type)
    {
        BinarySerializer<T> serializer = serializer(type);

        try
        {
           return serializer.read(in);
        }
        catch (IOException e)
        {
            throw new SerializationFailureException(type, e);
        }
    }


    public boolean isRegistered(Class<?> type)
    {
        return registry.containsKey(type);
    }
}
