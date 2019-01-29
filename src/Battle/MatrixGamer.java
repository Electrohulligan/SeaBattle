package Battle;

class MatrixGamer {
    private Box[][] matrixGamer;

    MatrixGamer(Box defaultBoxGamer) {
        matrixGamer = new Box[Ranges.getSizeOfFieldGamer().x][Ranges.getSizeOfFieldGamer().y];
        for(Coord coord: Ranges.getAllCoordsOfFieldsOfGamer())
            matrixGamer[coord.x][coord.y] = defaultBoxGamer;
    }

    Box get(Coord coord) {
        if(Ranges.inRangeFieldGamer(coord))
        return matrixGamer[coord.x][coord.y];
        return null;
    }

    void set(Coord coord, Box box) {
        matrixGamer[coord.x][coord.y] = box;
    }
}
