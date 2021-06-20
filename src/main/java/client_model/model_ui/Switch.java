package client_model.model_ui;

import javax.persistence.*;

@Entity
@Table(name = "switches")
public class Switch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer idTrackFrom;

    Integer idTrackTo;

    Integer coordinateFrom;

    Integer coordinateTo;

    public Switch() {
    }

    public Switch(Integer idTrackFrom, Integer idTrackTo, Integer coordinateFrom, Integer coordinateTo) {
        this.idTrackFrom = idTrackFrom;
        this.idTrackTo = idTrackTo;
        this.coordinateFrom = coordinateFrom;
        this.coordinateTo = coordinateTo;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdTrackFrom() {
        return idTrackFrom;
    }

    public void setIdTrackFrom(Integer idTrackFrom) {
        this.idTrackFrom = idTrackFrom;
    }

    public Integer getIdTrackTo() {
        return idTrackTo;
    }

    public void setIdTrackTo(Integer idTrackTo) {
        this.idTrackTo = idTrackTo;
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
