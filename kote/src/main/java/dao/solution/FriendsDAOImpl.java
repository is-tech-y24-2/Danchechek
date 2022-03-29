package dao.solution;

import dao.FriendsDAO;
import hibernate.HibernateSessionFactoryUtil;
import models.Cat;
import models.Friends;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;

public class FriendsDAOImpl implements FriendsDAO {
    @Override
    public Friends findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Friends.class, id);
    }

    @Override
    public void save(Friends friend) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.save(friend);
        session.close();
    }

    @Override
    public void update(Friends friend) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.update(friend);
        session.close();
    }

    @Override
    public void delete(Friends friend) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < getAll().size(); i++)
        {
            if(getAll().get(i).getFirst() == friend.getFirst()
                    && Objects.equals(getAll().get(i).getSecond(), friend.getSecond()))
            {
                session.remove(session.get(Friends.class, getAll().get(i).getId()));
                tx.commit();
                session.close();
            }
        }
    }

    @Override
    public List<Friends> getAll() {
        return (List<Friends>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Friends").list();
    }
}
