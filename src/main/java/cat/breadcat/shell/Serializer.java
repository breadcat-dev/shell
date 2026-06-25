package cat.breadcat.shell;

import cat.breadcat.breech.BinaryInput;
import cat.breadcat.breech.BinaryOutput;
import com.sun.nio.sctp.IllegalReceiveException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Serializer
{
    private static final Map<Class<?>, BinarySerializer<?>> serializers = new HashMap<>();

    private Serializer() {}


    @SuppressWarnings("unchecked")
    private static <T> BinarySerializer<T> getSerializer(Class<T> type)
    {
        BinarySerializer<T> serializer = (BinarySerializer<T>)serializers.get(type);
        if(serializer == null)
            throw new IllegalStateException("Unable to find Serializer for type: " + type.getSimpleName());

        return serializer;
    }


    public static <T> void register(Class<T> type, BinarySerializer<T> serializer)
    {
        if(serializers.containsKey(type))
            throw new IllegalStateException("Already registered Serializer for: " + type.getSimpleName());

        serializers.put(type, serializer);
    }

    @SuppressWarnings("unchecked")
    public static <T> void serialize(BinaryOutput out, T data)
    {
        if(data == null)
            throw new NullPointerException("data");

        Class<T> type = (Class<T>)data.getClass();
        BinarySerializer<T> serializer = getSerializer(type);

        try
        {
            serializer.write(out, data);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Failed to Serialize: " + type, e);
        }
    }

    public static <T> T deserialize(BinaryInput in, Class<T> type)
    {
        BinarySerializer<T> serializer = getSerializer(type);

        try
        {
           return serializer.read(in);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to Serialize: " + type, e);
        }
    }


    public static boolean isRegistered(Class<?> type)
    {
        return serializers.containsKey(type);
    }
}
