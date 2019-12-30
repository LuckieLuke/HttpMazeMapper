import java.io.FileNotFoundException;

public class Main {

    private static final int mapNumber = 1;
    private static final String NAJLEPSZEuId = "6a1bcbed";

    public static void main(String[] args) {
/*        Mapper mapper = new Mapper();
        mapper.mapFromHttp(NAJLEPSZEuId, mapNumber);*/


    }

    public static void save(Maze maze, String path) throws FileNotFoundException { //jeśli nie będziemy tu nic do końca roboty dopisywać to trza to wrzucić do maina i wsio
        MazeFile.saveToFile(maze, path);
    }

    public static void upload(String path) { //to samo co wyżej
        MazeFile.upload(path, mapNumber);
    }
}
