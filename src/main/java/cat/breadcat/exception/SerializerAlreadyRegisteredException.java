package cat.breadcat.exception;

public class SerializerAlreadyRegisteredException extends SerializerException
{
    public SerializerAlreadyRegisteredException(Class<?> type)
    {
        super("The Serializer for " + type.getSimpleName() + " is already registered");
    }
}
