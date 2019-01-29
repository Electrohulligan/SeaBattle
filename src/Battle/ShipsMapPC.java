package Battle;

import java.util.Random;

class ShipsMapPC {
    MatrixPC shipsMapPC;
    private int totalShipsPC;

    ShipsMapPC(int totalShips) {
        this.totalShipsPC = totalShips;
        shipsMapPC = new MatrixPC(Box.zero);

            placeFourDeckShipPC();

        for (int i = 0; i < 2; i++)
            placeThreeDeckShipPC();

        for (int i = 0; i < 3; i++)
            placeTwoDeckShipPC();

        for (int i = 0; i < 4; i++)
            placeOneDeckShipPC();
        removeLabelPC();

    }

    private void placeOneDeckShipPC() {
        while (true) {
            Coord coord = Ranges.getRandomCoords();
            if (Box.num1 == shipsMapPC.get(coord) || Box.flaged == shipsMapPC.get(coord))
                continue;
            shipsMapPC.set(coord, Box.num1);
            for (Coord around : Ranges.getCoordsAround(coord))
                shipsMapPC.set(around, Box.flaged);
            break;
        }
    }
    private void placeTwoDeckShipPC() {
        Random rand = new Random();
        int direction = rand.nextInt(2);

        if (direction == 0) {
            while (true) {
                Coord coord = Ranges.getRandomCoordsOfTwoDecksShipHorizontal();
                if (Box.num1 == shipsMapPC.get(coord) || Box.flaged == shipsMapPC.get(coord)
                        || Box.num1 == shipsMapPC.get(new Coord(coord.x + 1, coord.y))
                        || Box.flaged == shipsMapPC.get(new Coord(coord.x + 1, coord.y)))
                    continue;

                shipsMapPC.set(coord, Box.num1);
                for(Coord around : Ranges.getCoordsAround(coord))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);

                shipsMapPC.set(new Coord(coord.x + 1, coord.y), Box.num1);
                for(Coord around : Ranges.getCoordsAround(new Coord(coord.x + 1, coord.y)))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);
                break;
            }
        }
        if (direction == 1) {
            while (true) {
                Coord coord = Ranges.getRandomCoordsOfTwoDecksShipVertical();
                if (Box.num1 == shipsMapPC.get(coord) || Box.flaged == shipsMapPC.get(coord)
                        || Box.num1 == shipsMapPC.get(new Coord(coord.x, coord.y + 1))
                        || Box.flaged == shipsMapPC.get(new Coord(coord.x, coord.y + 1)))
                    continue;

                shipsMapPC.set(coord, Box.num1);
                for(Coord around : Ranges.getCoordsAround(coord))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);

                shipsMapPC.set(new Coord(coord.x , coord.y + 1), Box.num1);
                for(Coord around : Ranges.getCoordsAround(new Coord(coord.x, coord.y + 1)))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);
                break;
            }
        }
    }
    private void placeThreeDeckShipPC() {
        Random rand = new Random();
        int direction = rand.nextInt(2);

        if (direction == 0) {
            while (true) {
                Coord coord = Ranges.getRandomCoordsOfThreeDecksShipHorizontal();
                if (Box.num1 == shipsMapPC.get(coord) || Box.flaged == shipsMapPC.get(coord)
                        || Box.num1 == shipsMapPC.get(new Coord(coord.x + 1, coord.y))
                        || Box.flaged == shipsMapPC.get(new Coord(coord.x + 1, coord.y))
                        || Box.num1 == shipsMapPC.get(new Coord(coord.x + 2, coord.y))
                        || Box.flaged == shipsMapPC.get(new Coord(coord.x + 2, coord.y)))
                    continue;

                shipsMapPC.set(coord, Box.num1);
                for(Coord around : Ranges.getCoordsAround(coord))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);

                shipsMapPC.set(new Coord(coord.x + 1, coord.y), Box.num1);
                for(Coord around : Ranges.getCoordsAround(new Coord(coord.x + 1, coord.y)))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);

                shipsMapPC.set(new Coord(coord.x + 2, coord.y), Box.num1);
                for(Coord around : Ranges.getCoordsAround(new Coord(coord.x + 2, coord.y)))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);
                break;
            }
        }
        if (direction == 1) {
            while (true) {
                Coord coord = Ranges.getRandomCoordsOfThreeDecksShipVertical();
                if (Box.num1 == shipsMapPC.get(coord) || Box.flaged == shipsMapPC.get(coord)
                        || Box.num1 == shipsMapPC.get(new Coord(coord.x, coord.y + 1))
                        || Box.flaged == shipsMapPC.get(new Coord(coord.x, coord.y + 1))
                        || Box.num1 == shipsMapPC.get(new Coord(coord.x, coord.y + 2))
                        || Box.flaged == shipsMapPC.get(new Coord(coord.x, coord.y + 2)))
                    continue;

                shipsMapPC.set(coord, Box.num1);
                for(Coord around : Ranges.getCoordsAround(coord))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);

                shipsMapPC.set(new Coord(coord.x , coord.y + 1), Box.num1);
                for(Coord around : Ranges.getCoordsAround(new Coord(coord.x, coord.y + 1)))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);

                shipsMapPC.set(new Coord(coord.x, coord.y + 2), Box.num1);
                for(Coord around : Ranges.getCoordsAround(new Coord(coord.x, coord.y + 2)))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);
                break;
            }
        }
    }
    private void placeFourDeckShipPC() {
        Random rand = new Random();
        int direction = rand.nextInt(2);

        if (direction == 0) {
            while (true) {
                Coord coord = Ranges.getRandomCoordsOfFourDecksShipHorizontal();
                if (Box.num1 == shipsMapPC.get(coord) || Box.flaged == shipsMapPC.get(coord)
                        || Box.num1 == shipsMapPC.get(new Coord(coord.x + 1, coord.y))
                        || Box.flaged == shipsMapPC.get(new Coord(coord.x + 1, coord.y))
                        || Box.num1 == shipsMapPC.get(new Coord(coord.x + 2, coord.y))
                        || Box.flaged == shipsMapPC.get(new Coord(coord.x + 2, coord.y))
                        || Box.num1 == shipsMapPC.get(new Coord(coord.x + 3, coord.y))
                        || Box.flaged == shipsMapPC.get(new Coord(coord.x + 3, coord.y)))
                    continue;

                shipsMapPC.set(coord, Box.num1);
                for(Coord around : Ranges.getCoordsAround(coord))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);

                shipsMapPC.set(new Coord(coord.x + 1, coord.y), Box.num1);
                for(Coord around : Ranges.getCoordsAround(new Coord(coord.x + 1, coord.y)))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);

                shipsMapPC.set(new Coord(coord.x + 2, coord.y), Box.num1);
                for(Coord around : Ranges.getCoordsAround(new Coord(coord.x + 2, coord.y)))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);

                shipsMapPC.set(new Coord(coord.x + 3, coord.y), Box.num1);
                for(Coord around : Ranges.getCoordsAround(new Coord(coord.x + 3, coord.y)))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);
                break;
            }
        }
        if (direction == 1) {
            while (true) {
                Coord coord = Ranges.getRandomCoordsOfFourDecksShipVertical();
                if (Box.num1 == shipsMapPC.get(coord) || Box.flaged == shipsMapPC.get(coord)
                        || Box.num1 == shipsMapPC.get(new Coord(coord.x, coord.y + 1))
                        || Box.flaged == shipsMapPC.get(new Coord(coord.x, coord.y + 1))
                        || Box.num1 == shipsMapPC.get(new Coord(coord.x, coord.y + 2))
                        || Box.flaged == shipsMapPC.get(new Coord(coord.x, coord.y + 2))
                        || Box.num1 == shipsMapPC.get(new Coord(coord.x, coord.y + 3))
                        || Box.flaged == shipsMapPC.get(new Coord(coord.x, coord.y + 3)))
                    continue;

                shipsMapPC.set(coord, Box.num1);
                for(Coord around : Ranges.getCoordsAround(coord))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);

                shipsMapPC.set(new Coord(coord.x , coord.y + 1), Box.num1);
                for(Coord around : Ranges.getCoordsAround(new Coord(coord.x, coord.y + 1)))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);

                shipsMapPC.set(new Coord(coord.x, coord.y + 2), Box.num1);
                for(Coord around : Ranges.getCoordsAround(new Coord(coord.x, coord.y + 2)))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);

                shipsMapPC.set(new Coord(coord.x, coord.y + 3), Box.num1);
                for(Coord around : Ranges.getCoordsAround(new Coord(coord.x, coord.y + 3)))
                    if(Box.zero == shipsMapPC.get(around))
                        shipsMapPC.set(around, Box.flaged);
                break;
            }
        }
    }
    private void removeLabelPC() {
        for (Coord coord : Ranges.getAllCoordsOfFieldsOfPC())
            if (Box.flaged == shipsMapPC.get(coord))
                shipsMapPC.set(coord, Box.zero);
    }


    int getTotalShipsPC() {
        return totalShipsPC;
    }
    Box getBoxMapPC(Coord coord) {
        return shipsMapPC.get(coord);
    }
    void setFlagBox(Coord coord) {
        shipsMapPC.set(coord, Box.flaged);
    }
}