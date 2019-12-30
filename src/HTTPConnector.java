import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
    //private String moveUriString;     na razie chyba niepotrzebne

    public HTTPConnector(String uId, int mapId) {
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
        startPositionUriString = getBase(uId, mapId).append("/startposition").toString();
        possibilitiesUriString = getBase(uId, mapId).append("/possibilities").toString();
    }

    private StringBuilder getBase(String uid, int mapId) {
        return new StringBuilder("http://tesla.iem.pw.edu.pl:4444/").append(uid).append("/").append(mapId);
    }

    public void move(int intDirection){ //nie wiem jak przekazujemy kierunek na razie gotowy string np. "left"
        String direction = getDirection(intDirection);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getBase(uId, mapId).append("/move/").append(direction).toString()))
                .POST(HttpRequest.BodyPublishers.ofString("")) .build();
        HttpResponse<String> response = getResponse(request);

        //nie pamiętam czy move ma coś zwracać ale nawet jeśli to chyba nie tu xd
    }

    private String getDirection(int direction) {
        String result = "";
        switch(direction) {
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

    public void reset() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(resetUriString)).POST(HttpRequest.BodyPublishers.ofString("")) .build();
        HttpResponse<String> response = getResponse(request);

        //response.body() <-- można sprawdzić czy wyszło resetowanie (ale można też nie)
    }

    public int[] getSize() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(sizeUriString)).build();
        HttpResponse<String> response = getResponse(request);
        return parsik.parseSize(response.body());
    }

    public int[] getStartPosition(){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(startPositionUriString)).build();
        HttpResponse<String> response = getResponse(request);
        return parsik.parseStartPosition(response.body());
    }

    public int getMoves(){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(movesUriString)).build();
        HttpResponse<String> response = getResponse(request);
        return parsik.parseMoves(response.body());
    }

    public boolean[] getPossibilities() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(possibilitiesUriString)).build();
        HttpResponse<String> response = getResponse(request);

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
