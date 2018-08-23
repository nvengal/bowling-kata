# Bowling
[CodingDojo Bowling Kata](http://codingdojo.org/kata/Bowling/)

The program will calculate the score for a ten frame bowling game. Score calculation takes into account strike and spare bonuses, including bonus rolls after the tenth frame. The program expects well-formed/complete games, but could be easily modified to allow users to input a game frame-by-frame.

In accordance with the kata specification the program:
* Will not check for valid rolls
* Will not check for the correct number of rolls and frames
* Will not provide scores for intermediate frames

### Prerequistes
* Java 1.8 (JDK) <http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html>
* Maven 3 <http://maven.apache.org/download.cgi>

After dowloading and installing Java and Maven set the appropriate environment variables.
```
The JAVA_HOME variable should point to your JDK installation
The PATH variable should include both the Java and Maven executable location ($location/bin)
```

### Program Execution
1. Navigate to the project root
2. Run `mvn clean install`
3. Execute the program by running `java -jar target/bowling-kata-1.0-SNAPSHOT-jar-with-dependencies.jar`

### Sample
```
----------BOWLING GAME SCORE CALCULATOR----------
Enter a 10 frame game in the format '5- 8/ 4- -/ -6 X 43 X -/ 4/7'
Spaces separate frames, '-' represents a miss, '/' a spare and 'X' a strike:
5- 8/ 4- -/ -6 X 43 X -/ 4/7
You scored 114 points
```