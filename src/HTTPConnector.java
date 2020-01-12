import Exceptions.BadRequestException;
import Exceptions.ForbiddenMoveException;
import Exceptions.NotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

public class HTTPConnector {

    private String uId;
    private int mapId;
    private HttpClient client;
    private Parser parsik;

    private String sizeUriString;
    private String possibilitiesUriString;
    private String movesUriString;
    private String startPositionUriString;
    private String resetUriString;
    private String uploadUriString;

    public HTTPConnector(String uId, int mapId) {
        try {
            URL url = new URL("http://tesla.iem.pw.edu.pl:4444/");
            URLConnection connection = url.openConnection();
            connection.connect();
            System.out.println("Connection working!");
        } catch (MalformedURLException e) {
            System.out.println("VPN or Internet not connected!");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Internet or VPN not connected!");
            System.exit(2);
        }

        this.uId = uId;
        this.mapId = mapId;
        client = HttpClient.newHttpClient();
        parsik = new Parser();
        prepareUriStrings();
    }

    private void prepareUriStrings() {
        resetUriString = getBase(uId, mapId).append("/reset").toString();
        sizeUriString = getBase(uId, mapId).append("/size").toString();
        movesUriString = getBase(uId, mapId).append("/moves").toString();
        uploadUriString = getBase(uId, mapId).append("/upload").toString();
        startPositionUriString = getBase(uId, mapId).append("/startposition").toString();
        possibilitiesUriString = getBase(uId, mapId).append("/possibilities").toString();
    }

    private StringBuilder getBase(String uid, int mapId) {
        return new StringBuilder("http://tesla.iem.pw.edu.pl:4444/").append(uid).append("/").append(mapId);
    }

    private String getDirection(int direction) {
        String result = "";
        switch (direction) {
            case 1:
                result = "left";
                break;
            case 2:
                result = "up";
                break;
            case -1:
                result = "right";
                break;
            case -2:
                result = "down";
                break;
        }
        return result;
    }

    public void postMove(int intDirection) throws ForbiddenMoveException {
        String direction = getDirection(intDirection);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getBase(uId, mapId).append("/move/").append(direction).toString()))
                .POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = getResponse(request);

        if (response.statusCode() == 403)
            throw new ForbiddenMoveException(response.body());
    }

    public void postReset() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(resetUriString)).POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = getResponse(request);
    }

    public void postUpload(String path) throws FileNotFoundException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uploadUriString)).POST(HttpRequest.BodyPublishers.ofFile(Paths.get(path)))
                .build();
        HttpResponse<String> response = getResponse(request);
        System.out.println(response.body());
    }

    public int[] getSize() throws BadRequestException, NotFoundException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(sizeUriString)).build();
        HttpResponse<String> response = getResponse(request);

        if (response.statusCode() == 400)
            throw new BadRequestException(response.body());
        else if (response.statusCode() == 404)
            throw new NotFoundException(response.body());

        return parsik.parseSize(response.body());
    }

    public int[] getStartPosition() throws BadRequestException, NotFoundException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(startPositionUriString)).build();
        HttpResponse<String> response = getResponse(request);

        if (response.statusCode() == 400)
            throw new BadRequestException(response.body());
        else if (response.statusCode() == 404)
            throw new NotFoundException(response.body());

        return parsik.parseStartPosition(response.body());
    }

    public int getMoves() throws NotFoundException, BadRequestException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(movesUriString)).build();
        HttpResponse<String> response = getResponse(request);

        if (response.statusCode() == 400)
            throw new BadRequestException(response.body());
        else if (response.statusCode() == 404)
            throw new NotFoundException(response.body());

        return parsik.parseMoves(response.body());
    }

    public boolean[] getPossibilities() throws BadRequestException, NotFoundException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(possibilitiesUriString)).build();
        HttpResponse<String> response = getResponse(request);

        if (response.statusCode() == 400)
            throw new BadRequestException(response.body());
        else if (response.statusCode() == 404)
            throw new NotFoundException(response.body());

        return parsik.parsePossibilities(response.body());
    }

    private HttpResponse<String> getResponse(HttpRequest request) {
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
