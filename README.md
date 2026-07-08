# Shell

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)
![Java](https://img.shields.io/badge/Java-21-blue)
![Status](https://img.shields.io/badge/status-experimental-orange)
![Release](https://img.shields.io/github/v/release/breadcat-dev/shell)

> A tiny binary serialization library for Java projects

Part of the TANK Series.

---

## Features

- Register serializers for any Java type
- Type-safe serialization/deserialization
- Manual, explicit serializers
- Uses Breech's binary I/O classes

## Design Goals

- Tiny API
- Explicit serialization logic
- Extendable

---

## Installation

Currently, Shell is not on Maven Central.
To use it, clone the repository and publish it to your local Maven Repository.


```sh
git clone https://github.com/breadcat-dev/shell.git
cd shell
```

### Linux / MacOS
```sh
./gradlew publishToMavenLocal
```
### Windows
```sh
./gradlew.bat publishToMavenLocal
```

Once installed, add the dependency:

### Groovy
```gradle
implementation "cat.breadcat:shell:<version>"
```

### Kotlin DSL
```gradle
implementation("cat.breadcat:shell:<version>")
```

---

## Quick Example

```java
SerializerRegistry registry = new SerializerRegistry();

registry.register(Person.class, new BinarySerializer<Person>()
{
    @Override
    public void write(BinaryWriter out, Person person) throws IOException
    {
        out.writeString(person.name());
        out.writeInt(person.age());
    }

    @Override
    public Person read(BinaryReader in) throws IOException
    {
        String name = in.readString();
        int age = in.readInt();
        
        return new Person(name, age);
    }
});
```

## Full Example

### Main
```java
SerializerRegistry registry = new SerializerRegistry();
registry.register(Player.class, new PlayerSerializer());

/*
Can also chain register methods:

registry
    .register(X.class, new XSerializer())
    .register(Y.class, new YSerializer())
    .register(Z.class, new ZSerializer());
*/
```

### Writing

```java
try(BinaryWriter out = new BinaryWriter(new FileOutputStream("./save.bin"), ByteOrder.LITTLE_ENDIAN))
{
    registry.serialize(out, new Player(UUID.randomUUID(), "breadcat", 2000, new Item[0]));
}
catch(IOException e)
{
    throw new RuntimeException(e);
}
```

### Reading

```java
try(BinaryReader in = new BinaryReader(new FileInputStream("./save.bin"), ByteOrder.LITTLE_ENDIAN))
{
    Player player = registry.deserialize(in, Player.class);
    
    System.out.println("Loaded player '" + player.username() + "'");
}
catch(IOException e)
{
    throw new RuntimeException(e);
}
```

### PlayerSerializer

```java
public final class PlayerSerializer implements BinarySerializer<Player>
{
    @Override
    public void write(BinaryWriter out, Player player) throws IOException
    {
        UUID id = player.id();
        String username = player.username();
        int cash = player.cash();
        Item[] inventory = player.inventory();

        // Write id (UUID)
        out.writeLong(id.getMostSignificantBits());
        out.writeLong(id.getLeastSignificantBits());
        
        out.writeString(username);
        out.writeInt(cash);

        // Write inventory (Item[])
        out.writeInt(inventory.length); // Store the amount of items in the array
        for(Item item : inventory)
        {
            out.writeString(item.name());
            out.writeInt(item.value());
        }
    }

    @Override
    public Player read(BinaryReader in) throws IOException
    {
        // Read id (UUID)
        long mostSignificant = in.readLong();
        long leastSignificant = in.readLong();
        UUID id = new UUID(mostSignificant, leastSignificant);
        
        String username = in.readString();
        int cash = in.readInt();

        // Read inventory (Item[])
        int itemCount = in.readInt();
        Item[] inventory = new Item[itemCount];

        for(int i = 0; i < itemCount; i++)
        {
            String itemName = in.readString();
            int itemValue = in.readInt();

            inventory[i] = new Item(itemName, itemValue);
        }

        return new Player(id, username, cash, inventory);
    }
}
```

### Player

```java
public record Player(
        UUID id,
        String username,
        int cash,
        Item[] inventory
)
{}
```

### Item

```java
public record Item(
        String name,
        int value
)
{}
```

---

## Dependencies:
- Breech - [Github](https://github.com/breadcat-dev/breech)