package client_model.services;

import client_model.dao.StationDAO;
import client_model.dao.TrackDAO;
import client_model.model_ui.Station;
import client_model.model_ui.Track;
import client_model.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StationService {

    private StationDAO stationDAO = new StationDAO();

    private TrackDAO trackDAO = new TrackDAO();

    public void save(Station station) {
        stationDAO.save(station);
        int maxCoordinate = (int) HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("select max(coordinateTo) from Track").list().get(0);
        final List<Track> tracks = getTracksForSplit(station);
        for (Track track : tracks) {
            if (station.getCoordinateFrom() != 0) {
                Track leftTrack = new Track(track.getNumber(), track.getCoordinateFrom(), station.getCoordinateFrom());
                trackDAO.save(leftTrack);
            }
            if (station.getCoordinateTo() != maxCoordinate) {
                Track rightTrack = new Track(track.getNumber(), station.getCoordinateTo(), track.getCoordinateTo());
                trackDAO.save(rightTrack);
            }
            track.setCoordinateFrom(station.getCoordinateFrom());
            track.setCoordinateTo(station.getCoordinateTo());
            trackDAO.update(track);
        }
    }

    public Station findStation(int id) {
        return stationDAO.getById(id);
    }

    public void update(Station station) {
        final Station oldStation = stationDAO.getById(station.getId());
        final List<Track> leftTracks = getLeftTracksForUpdateDelete(oldStation);
        final List<Track> rightTracks = getRightTracksForUpdateOrDelete(oldStation);
        final List<Track> innerTracks = getInnerTracksForUpdate(oldStation);
        for (Track leftTrack : leftTracks) {
            leftTrack.setCoordinateTo(station.getCoordinateFrom());
            trackDAO.update(leftTrack);
        }
        for (Track rightTrack : rightTracks) {
            rightTrack.setCoordinateFrom(station.getCoordinateTo());
            trackDAO.update(rightTrack);
        }
        for (Track innerTrack : innerTracks) {
            innerTrack.setCoordinateFrom(station.getCoordinateFrom());
            innerTrack.setCoordinateTo(station.getCoordinateTo());
            trackDAO.update(innerTrack);
        }
        stationDAO.update(station);
    }

    public void delete(Station station) {
        deleteSwitches(station);
        deleteStationTracks(station);
        final int rightTrackSize = getRightTracksForUpdateOrDelete(station).size();
        final int leftTrackSize = getLeftTracksForUpdateDelete(station).size();
        final int minSizeTracks = Math.min(leftTrackSize, rightTrackSize);
        if (minSizeTracks != 0) {
            final Integer coordinateFrom = getLeftTracksForUpdateDelete(station).get(0).getCoordinateFrom();
            final Integer coordinateTo = getRightTracksForUpdateOrDelete(station).get(0).getCoordinateTo();
            for (int trackNumber = 1; trackNumber <= minSizeTracks; trackNumber++) {
                trackDAO.save(new Track(trackNumber, coordinateFrom, coordinateTo));
            }
        } else if (leftTrackSize == 0) {
            final Integer coordinateFrom = station.getCoordinateFrom();
            final Integer coordinateTo = getRightTracksForUpdateOrDelete(station).get(0).getCoordinateTo();
            for (int trackNumber = 1; trackNumber <= rightTrackSize; trackNumber++) {
                trackDAO.save(new Track(trackNumber, coordinateFrom, coordinateTo));
            }
        } else {
            final Integer coordinateFrom = getLeftTracksForUpdateDelete(station).get(0).getCoordinateFrom();
            final Integer coordinateTo = station.getCoordinateTo();
            for (int trackNumber = 1; trackNumber <= leftTrackSize; trackNumber++) {
                trackDAO.save(new Track(trackNumber, coordinateFrom, coordinateTo));
            }
        }
        for (Track track : getRightTracksForUpdateOrDelete(station)) {
            trackDAO.delete(track);
        }
        for (Track track : getLeftTracksForUpdateDelete(station)) {
            trackDAO.delete(track);
        }
        stationDAO.delete(station);
    }

    private void deleteStationTracks(Station station) {
        final Integer coordinateFrom = station.getCoordinateFrom();
        final Integer coordinateTo = station.getCoordinateTo();
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        final Transaction transaction = session.beginTransaction();
        session.createQuery(" delete from Track where coordinateFrom =" + coordinateFrom + " and coordinateTo = " + coordinateTo).executeUpdate();
        transaction.commit();
        session.close();
    }

    private void deleteSwitches(Station station) {
        final Integer coordinateFrom = station.getCoordinateFrom();
        final Integer coordinateTo = station.getCoordinateTo();
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        final Transaction transaction = session.beginTransaction();
        session.createQuery(" delete from Switch where coordinateFrom >" + coordinateFrom + " and coordinateFrom < " + coordinateTo).executeUpdate();
        transaction.commit();
        session.close();
    }

    /**
     * При добавлении станции, линии (пути), добавленные станции нужно разделить
     * на пути перегона слева и справа и на станционные для этого нужно найти все пути,
     * лежащие на заданном промежутке.
     *
     * @param station
     * @return
     */
    private List<Track> getTracksForSplit(Station station) {
        final Integer coordinateFrom = station.getCoordinateFrom();
        final Integer coordinateTo = station.getCoordinateTo();
        final List<Track> tracks = (List<Track>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From Track where coordinateFrom <= " + coordinateFrom + " and coordinateTo >= " + coordinateTo).list();
        return tracks;
    }

    /**
     * При обновлении расположения или длины  станции все треки,
     * начинающие с конца (справа) станции нужно также обновить
     * В данном методе происходит поиск всех таких треков
     *
     * @param station
     * @return
     */
    private List<Track> getRightTracksForUpdateOrDelete(Station station) {
        final Integer coordinateTo = station.getCoordinateTo();
        final List<Track> tracks = (List<Track>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From Track where coordinateFrom = " + coordinateTo).list();
        return tracks;
    }

    /**
     * При обновлении расположения или длины  станции все треки,
     * заканчивающие к началу (слева) станции нужно также обновить
     * В данном методе происходит поиск всех таких треков
     *
     * @param station
     * @return
     */
    private List<Track> getLeftTracksForUpdateDelete(Station station) {
        final Integer coordinateFrom = station.getCoordinateFrom();
        final List<Track> tracks = (List<Track>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From Track where coordinateTo = " + coordinateFrom).list();
        return tracks;
    }

    /**
     * При обновлении расположения или длины  станции все треки,
     * внутри станции нужно также обновить
     * В данном методе происходит поиск всех таких треков
     *
     * @param station
     * @return
     */
    private List<Track> getInnerTracksForUpdate(Station station) {
        final Integer coordinateFrom = station.getCoordinateFrom();
        final Integer coordinateTo = station.getCoordinateTo();
        final List<Track> tracks = (List<Track>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From Track where coordinateFrom == " + coordinateFrom + " and coordinateTo == " + coordinateTo).list();
        return tracks;
    }
}
