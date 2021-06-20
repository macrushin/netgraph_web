package client_model.dao;

import client_model.model_ui.Switch;
import org.hibernate.Session;
import org.hibernate.Transaction;
import client_model.utils.HibernateSessionFactoryUtil;

public class SwitchDAO {

    public Switch findById(int id)
    {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Switch.class, id);
    }

    public void save(Switch swtch)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        final Transaction transaction = session.beginTransaction();
        session.save(swtch);
        transaction.commit();
        session.close();
    }

    public void update(Switch swtch)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        final Transaction transaction = session.beginTransaction();
        session.update(swtch);
        transaction.commit();
        session.close();
    }

    public void delete(Switch swtch)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        final Transaction transaction = session.beginTransaction();
        session.delete(swtch);
        transaction.commit();
        session.close();
    }

}
