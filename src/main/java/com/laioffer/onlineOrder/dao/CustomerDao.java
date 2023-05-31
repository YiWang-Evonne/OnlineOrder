package com.laioffer.onlineOrder.dao;


import com.laioffer.onlineOrder.entity.Authorities;
import com.laioffer.onlineOrder.entity.Customer;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//Repository是提供数据的
@Repository
public class CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;


    public void signUp(Customer customer) {
        Authorities authorities = new Authorities();
        authorities.setAuthorities("ROLE_USER");
        authorities.setEmail(customer.getEmail());


        Session session = null;
        try {
            session = sessionFactory.openSession(); //开门
            session.beginTransaction();
            session.save(authorities);
            session.save(customer); //这三步是传数据的
            session.getTransaction().commit(); //成功上传
        } catch (Exception ex) { //失败的情况
            ex.printStackTrace();
            if (session != null) session.getTransaction().rollback();
        } finally { //关门
            if (session != null) {
                session.close();
            }
        }
    }
//以上还有更高阶的做法，spring用annotation,那就不需要用session factory了

    public Customer getCustomer(String email) {
        Customer customer = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            customer = session.get(Customer.class, email); //用session实现get
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customer;
    }
}
