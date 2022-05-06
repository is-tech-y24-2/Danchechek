package ru.itmo.kotiki.dao.solution;

import dao.OwnerDAO;
import hibernate.HibernateSessionFactoryUtil;
import models.Cat;
import models.Owner;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;

public class OwnerDAOImpl implements OwnerDAO {

    @Override
    public Owner findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Owner.class, id);
    }

    @Override
    public void save(Owner owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.save(owner);
        session.close();
    }

    @Override
    public void update(Owner owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.update(owner);
        session.close();
    }

    @Override
    public void delete(Owner owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        for (int i = 0; i < getAll().size(); i++) {
            if (Objects.equals(getAll().get(i).getName(), owner.getName())
                    && Objects.equals(getAll().get(i).getDate(), owner.getDate())) {
                session.remove(session.get(Owner.class, getAll().get(i).getId()));
                tx.commit();
                session.close();
            }
        }
    }

    @Override
    public Owner findByPassportOwner(int passport) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From Owner where passportCode = :name");
        query.setParameter("name", passport);
        return (Owner) query.getSingleResult();
    }

    public List<Owner> getAll() {
        return (List<Owner>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Owner").list();
    }

    @Override
    public Owner getAll(int i) {
        return getAll().get(i);
    }
}
