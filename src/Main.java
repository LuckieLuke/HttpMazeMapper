import Exceptions.BadRequestException;
import Exceptions.NotFoundException;

import java.io.FileNotFoundException;

public class Main {

    /*
     ** Please, remember to hide line no. 38 in Mapper class
     ** if there is no reset command available.
     */

    private static final int mapNumber = 4;
    private static final String uId = "684219c0";

    public static void main(String[] args) throws FileNotFoundException, NotFoundException, BadRequestException {
        HTTPConnector http = new HTTPConnector(uId, mapNumber);
        Mapper mapper = new Mapper(http);
        Maze m = mapper.map();

        String path = mapNumber + ".txt";
        save(m, path);
        upload(http, path);
    }

    private static void save(Maze maze, String path) throws FileNotFoundException {
        try {
            MazeFile.saveToFile(maze, path);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Cannot save to file!");
        }
    }

    private static void upload(HTTPConnector http, String path) throws FileNotFoundException {
        try {
            http.postUpload(path);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Cannot upload a file - file not found!");
        }
    }
}
