import java.io.FileNotFoundException;

public class Main {

    private static final int mapNumber = 2;
    private static final String NAJLEPSZEuId = "684219c0";

    public static void main(String[] args) throws FileNotFoundException {
        HTTPConnector http = new HTTPConnector(NAJLEPSZEuId, mapNumber);
        Mapper mapper = new Mapper(http);
        Maze m = mapper.mapFromHttp();

        String path = mapNumber + ".txt";
        save(m, path);
        upload(http, path);
    }

    public static void save(Maze maze, String path) throws FileNotFoundException {
        MazeFile.saveToFile(maze, path);
    }

    public static void upload(HTTPConnector http, String path) throws FileNotFoundException {
        http.postUpload(path);
    }
}
