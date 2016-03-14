# ConvaysGameOfLife

A simple implementation of [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) done in Java with JavaFX.

### Run

- Download the [latest release](https://github.com/rubengees/ConvaysGameOfLife/releases/latest).
- Ensure you hava Java and JavaFX installed (version 8 at least).
- Start the application either by double clicking it or by running:

```sh
java -jar ConvaysGameOfLife.jar
```

### Build yourself

This project uses gradle so you just need to call the appropriate tasks.
What you propably want is the fatJar, as it contains all the needed dependencies.  
Run:

##### Linux
```sh
./gradlew fatJar
```

##### Windows
```sh
gradlew.bat fatJar
```

You'l find the file in the folder `build/libs`. The name is `ConvaysGameOfLife.jar`.

### More

You can use this code however you like (See the [licence](https://github.com/rubengees/ConvaysGameOfLife/blob/master/LICENCE)).  
I do not intend to work further on this project. 
