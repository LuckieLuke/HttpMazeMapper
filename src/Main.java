import java.io.FileNotFoundException;

public class Main {

    private static final int mapNumber = 4;
    private static final String NAJLEPSZEuId = "684219c0";

    public static void main(String[] args) throws FileNotFoundException {
        Mapper mapper = new Mapper();
        Maze m = mapper.mapFromHttp(NAJLEPSZEuId, mapNumber);

        String path = mapNumber + ".txt";
        save(m, path);
    }

    public static void save(Maze maze, String path) throws FileNotFoundException { //jeśli nie będziemy tu nic do końca roboty dopisywać to trza to wrzucić do maina i wsio
        MazeFile.saveToFile(maze, path);
    }

    public static void upload(Maze m, String path) {

    }
}
