public class Main {

    private static final int mapNumber = 1;

    public static void main(String[] args) {

    }

    public static void save(Maze maze, String path) { //jeśli nie będziemy tu nic do końca roboty dopisywać to trza to wrzucić do maina i tyle
        MazeFile.saveToFile(maze, path);
    }

    public static void upload(String path) { //to samo co wyżej
        MazeFile.upload(path, mapNumber);
    }
}
