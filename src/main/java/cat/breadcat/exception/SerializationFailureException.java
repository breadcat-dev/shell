package cat.breadcat.exception;

public class SerializationFailureException extends SerializerException
{
    public SerializationFailureException(Class<?> type, Throwable cause)
    {
        super("Serialization failed for type: " + type, cause);
    }
}
