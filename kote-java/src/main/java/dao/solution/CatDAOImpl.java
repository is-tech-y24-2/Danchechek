package dao.solution;

import dao.CatDAO;
import hibernate.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import models.Cat;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;

public class CatDAOImpl implements CatDAO {

    @Override
    public Cat findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Cat.class, id);
    }

    @Override
    public void save(Cat cat) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.save(cat);
        session.close();
    }

    @Override
    public void update(Cat cat) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.update(cat);
        session.close();
    }

    @Override
    public void delete(Cat cat) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < getAll().size(); i++)
        {
            if(getAll(i).getPassportOwner() == cat.getPassportOwner()
            && Objects.equals(getAll(i).getDate(), cat.getDate())
            && Objects.equals(getAll(i).getBreed(), cat.getBreed())
            && Objects.equals(getAll(i).getColor(), cat.getColor()))
            {
                session.remove(session.get(Cat.class, getAll(i).getId()));
                tx.commit();
                session.close();
            }
        }
    }

    @Override
    public List<Cat> getAll() {
        return (List<Cat>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Cat").list();
    }

    @Override
    public Cat getAll(int i) {
        return getAll().get(i);
    }
}
