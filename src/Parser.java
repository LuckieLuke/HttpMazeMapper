public class Parser {

    public boolean[] parsePossibilities(String body) {
        boolean[] pos = new boolean[4];

        int directions = 0, dist = 2;
        for (int i = 0; i < body.length(); i++) {
            if (body.charAt(i) == ':') {
                boolean pass = (body.charAt(i + dist) == '0');
                switch (directions) {
                    case 0:
                        pos[3] = pass;
                        break;
                    case 1:
                        pos[0] = pass;
                        break;
                    case 2:
                        pos[2] = pass;
                        break;
                    case 3:
                        pos[1] = pass;
                        break;
                }
                directions++;
            }
        }

        return pos;
    }

    public int[] parseSize(String body) {
        String[] sizeString = body.split("x");
        int[] size = new int[sizeString.length];

        for (int i = 0; i < size.length; i++)
            size[i] = Integer.parseInt(sizeString[i]) * 2 + 1;

        return size;
    }

    public int[] parseStartPosition(String body) {
        String[] startString = body.split(",");
        int[] start = new int[startString.length];

        for (int i = 0; i < start.length; i++)
            start[i] = Integer.parseInt(startString[i]) * 2 - 1;

        return start;
    }

    public int parseMoves(String body) {
        int moves = 0;
        try {
            moves = Integer.parseInt(body);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot format to Integer!");
        }

        return moves;
    }
}
