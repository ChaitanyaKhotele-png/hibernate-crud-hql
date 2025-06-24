package com.hospital;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HospitalMain {
    public static void main(String[] args) {
        Session session = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Hospital.class)
                .buildSessionFactory()
                .openSession();

        Transaction tx = session.beginTransaction();

        // Create
        Hospital h1 = new Hospital("City Hospital", "New York", 200);
        Hospital h2 = new Hospital("General Hospital", "Chicago", 150);
        session.save(h1);
        session.save(h2);

        // Read
        Query<Hospital> query = session.createQuery("FROM Hospital", Hospital.class);
        List<Hospital> hospitals = query.list();
        hospitals.forEach(h -> System.out.println(h.getName() + " - " + h.getLocation()));

        // Update
        Query updateQuery = session.createQuery("UPDATE Hospital SET capacity = :cap WHERE name = :name");
        updateQuery.setParameter("cap", 250);
        updateQuery.setParameter("name", "City Hospital");
        updateQuery.executeUpdate();

        // Delete
        Query deleteQuery = session.createQuery("DELETE Hospital WHERE name = :name");
        deleteQuery.setParameter("name", "General Hospital");
        deleteQuery.executeUpdate();

        tx.commit();
        session.close();
    }
}
