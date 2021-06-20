package client_model.services;

import client_model.dao.TrackDAO;
import client_model.model_ui.Station;
import client_model.model_ui.Track;

public class Main {
    public static void main(String[] args) {
        Station station = new Station(2,4);
        station.setId(14);
        StationService stationService = new StationService();
        stationService.delete(station);
    }
}
