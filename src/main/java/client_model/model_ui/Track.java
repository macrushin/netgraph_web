package client_model.model_ui;

import javax.persistence.*;

@Entity
@Table (name = "tracks")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer number;

    Integer coordinateFrom;

    Integer coordinateTo;

    public Track() {
    }

    public Track(Integer number, Integer coordinateFrom, Integer coordinateTo) {
        this.number = number;
        this.coordinateFrom = coordinateFrom;
        this.coordinateTo = coordinateTo;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCoordinateFrom() {
        return coordinateFrom;
    }

    public void setCoordinateFrom(Integer coordinateFrom) {
        this.coordinateFrom = coordinateFrom;
    }

    public Integer getCoordinateTo() {
        return coordinateTo;
    }

    public void setCoordinateTo(Integer coordinateTo) {
        this.coordinateTo = coordinateTo;
    }
}
