package client_model.dao;

import client_model.model_ui.Station;
import org.hibernate.Session;
import org.hibernate.Transaction;
import client_model.utils.HibernateSessionFactoryUtil;

public class StationDAO {

    public Station getById(int id)
    {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Station.class, id);
    }
    public void save(Station station)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        final Transaction transaction = session.beginTransaction();
        session.save(station);
        transaction.commit();
        session.close();
    }
    public void update(Station station)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        final Transaction transaction = session.beginTransaction();
        session.update(station);
        transaction.commit();
        session.close();
    }
    public void delete(Station station)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        final Transaction transaction = session.beginTransaction();
        session.delete(station);
        transaction.commit();
        session.close();
    }
}
