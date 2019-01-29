package Battle;


public class MatrixPC {
    private Box[][] matrixPC;

    MatrixPC(Box defaultBoxPC) {
        matrixPC = new Box[Ranges.getSizeOfFieldPC().x][Ranges.getSizeOfFieldPC().y];
        for(Coord coord: Ranges.getAllCoordsOfFieldsOfPC())
            matrixPC[coord.x][coord.y] = defaultBoxPC;
    }

    Box get(Coord coord) {
        if(Ranges.inRangeFieldPC(coord))
        return matrixPC[coord.x][coord.y];
        return null;
    }

    void set(Coord coord, Box box) {
        matrixPC[coord.x][coord.y] = box;
    }
}
