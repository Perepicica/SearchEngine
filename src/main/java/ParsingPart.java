public class ParsingPart {
    public static void parser(){
        new Thread(
                new ParsersManager(3,2)
        ).start();
    }
}
