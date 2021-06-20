package client_model.dao;

import client_model.model_ui.Track;
import org.hibernate.Session;
import org.hibernate.Transaction;
import client_model.utils.HibernateSessionFactoryUtil;

public class TrackDAO {

    public Track getById(int id)
    {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Track.class, id);
    }

    public void save(Track track)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(track);
        transaction.commit();
    }
    public void update(Track track)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(track);
        transaction.commit();
    }
    public void delete(Track track)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(track);
        transaction.commit();
    }
}
