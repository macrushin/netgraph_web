package client_model.services;

import client_model.dao.SwitchDAO;
import client_model.dao.TrackDAO;
import client_model.model_ui.Switch;
import client_model.model_ui.Track;

public class TrackService {
    private final TrackDAO trackDAO = new TrackDAO();

    public Track findTrack(int id) {
        return trackDAO.getById(id);
    }

    public void save(Track track) {
        trackDAO.save(track);
    }

    public void update(Track track) {
        trackDAO.update(track);
    }

    public void delete(Track track) {
        trackDAO.delete(track);
    }
}
