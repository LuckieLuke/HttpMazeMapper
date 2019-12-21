public class Parser {

    public boolean[] parsePossibilities(String body) { //bool[4] ← przygotowuje tablicę z body
        boolean[] pos = new boolean[4];

        for(boolean b: pos)
            b = false;

        int directions = 0;
        for(int i = 0; i < body.length(); i++) {
            if(body.charAt(i) == ':') {
                boolean pass = (body.charAt(i+3) == '0');
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

        for(int i = 0; i < size.length; i++)
            size[i] = Integer.parseInt(sizeString[i]);

        return size;
    }

    public int parseMoves(String body) {
        int moves = 0;
        try {
            moves = Integer.parseInt(body);
        } catch (NumberFormatException e) {
            System.out.println("Cannot format to Integer!");
        }

        return moves;
    }
}
