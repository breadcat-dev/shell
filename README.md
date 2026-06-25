# Shell - Lightweight ~~& Reliable~~  binary serialization library for Java
### Part of my TANK Series

## Status

EXPERIMENTAL - API might change in the future

## What it does
- Registers binary serializers for custom Java types
- Serializes and deserializes objects through a unified API
- Uses [Breech](https://github.com/breadcat-dev/breech) for low-level binary reading and writing
- Keeps serialization logic isolated in dedicated serializer classes

## Examples

Example Record
```java
public record Player(
     String name,
     int score
) {}
```

Custom Serializer
```java
public final class PlayerSerializer implements BinarySerializer<Player>
{
    @Override
    public void write(BinaryOutput out, Player player) throws IOException
    {
        if(player == null)
            throw new NullPointerException("player");

        out.writeString(player.name());
        out.writeInt(player.score());
    }

    @Override
    public Player read(BinaryInput in) throws IOException
    {
        String name = in.readString();
        int score = in.readInt();

        return new Player(name, score);
    }
}
```

Registry
```java
Serializer.register(Player.class, new PlayerSerializer());
```

Writing and Reading
```java
try(BinaryOutput out = new BinaryOutput(BinaryEndianness.LittleEndian, Files.newOutputStream(Path.of("./data.bin"))))
{
    Player player = new Player("breadcatz", 3689);
            
    Serializer.serialize(out, player);
}
catch (IOException e)
{
    throw new RuntimeException(e);
}


try(BinaryInput in = new BinaryInput(BinaryEndianness.LittleEndian, Files.newInputStream(Path.of("data.bin"))))
{
    Player player = Serializer.deserialize(in, Player.class);
            
    System.out.println("name - " + player.name() + "\nscore - " + player.score());
}
catch (IOException e)
{
    throw new RuntimeException(e);
}
```

(Console)
```
name - breadcatz
score - 3689
```


## Dependencies:
- Breech: `cat.breadcat:breech:[VERSION]` [Github](https://github.com/breadcat-dev/breech)