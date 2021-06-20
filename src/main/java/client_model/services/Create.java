package client_model.services;

import client_model.model_ui.Station;
import client_model.model_ui.Track;

public class Create {
    public static void main(String[] args) {
        Track track1 = new Track(1,0,100);
        Track track2 = new Track(2,0,100);
        Station station1= new Station(0,1);
        Station station2 = new Station( 2,4);
        Station station3 = new Station( 99,100);
        TrackService trackService = new TrackService();
        trackService.save(track1);
        trackService.save(track2);
        StationService stationService = new StationService();
        stationService.save(station1);
        stationService.save(station2);
        stationService.save(station3);
    }
}
