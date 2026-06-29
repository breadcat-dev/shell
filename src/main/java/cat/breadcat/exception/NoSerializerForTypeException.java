package cat.breadcat.exception;

public class NoSerializerForTypeException extends SerializerException
{
    public NoSerializerForTypeException(Class<?> clazz)
    {
        super("Unable to find Serializer for type: " + clazz.getSimpleName());
    }
}
