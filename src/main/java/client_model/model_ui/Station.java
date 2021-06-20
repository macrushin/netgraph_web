package client_model.model_ui;

import javax.persistence.*;

@Entity
@Table(name = "stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer coordinateFrom;

    Integer coordinateTo;

    public Station() {
    }

    public Station(Integer coordinateFrom, Integer coordinateTo) {
        this.coordinateFrom = coordinateFrom;
        this.coordinateTo = coordinateTo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
