/**
 * Name: Xander Bezuidenhout
 * Student Number: 20425997
 */

public class Coordinates {

    public Integer row;
    public Integer col;

    public Coordinates(Integer row, Integer col) {
        this.row=row;
        this.col=col;
    }

    public boolean equals(Coordinates other)
    {
        return (other.row==this.row&&other.col==this.col);
    }

}
