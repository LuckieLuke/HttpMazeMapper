import Exceptions.BadRequestException;
import Exceptions.NotFoundException;

import java.io.FileNotFoundException;

public class Main {

    private static final int mapNumber = 3;
    private static final String NAJLEPSZEuId = "684219c0";

    public static void main(String[] args) throws FileNotFoundException, NotFoundException, BadRequestException {
        HTTPConnector http = new HTTPConnector(NAJLEPSZEuId, mapNumber);
        Mapper mapper = new Mapper(http);
        Maze m = mapper.mapFromHttp();

        String path = mapNumber + ".txt";
        save(m, path);
        upload(http, path);
    }

    public static void save(Maze maze, String path) throws FileNotFoundException {
        try {
            MazeFile.saveToFile(maze, path);
        } catch(FileNotFoundException e) {
            throw new FileNotFoundException("Cannot save to file!");
        }
    }

    public static void upload(HTTPConnector http, String path) throws FileNotFoundException {
        try {
            http.postUpload(path);
        } catch(FileNotFoundException e) {
            throw new FileNotFoundException("Cannot upload a file - file not found!");
        }
    }
}
