import entities.Ticket;
import entities.User;
import org.hibernate.Session;
import org.hibernate.HibernateException;

import java.util.List;

public class UserDAO {

    public void createUser(String name) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            User user = new User();
            user.setName(name);

            session.persist(user);
            session.getTransaction().commit();
            System.out.println("User creado correctamente.");
        } catch (HibernateException e) {
            System.err.println("Error al crear el user: " + e.getMessage());
        }
    }

    public void readUsers() {
        try (Session session = HibernateUtil.getSession()) {
            List<User> users = session.createQuery("from User", User.class).list();

            for (User user : users) {
                System.out.println("ID: " + user.getId() + " - Name: " + user.getName());
            }
        } catch (HibernateException e) {
            System.err.println("Error al leer los users: " + e.getMessage());
        }
    }

    public void deleteUser(int idUser) {
        try (Session session = HibernateUtil.getSession()) {
            User user = session.get(User.class, idUser);

            if (user != null) {
                session.beginTransaction();
                session.remove(user);
                session.getTransaction().commit();

                System.out.println("User eliminado correctamente.");
            } else {
                System.out.println("No se encontró el user con el ID: " + idUser);
            }
        } catch (HibernateException e) {
            System.err.println("Error al eliminar el user: " + e.getMessage());
        }
    }

    public void updateUser(int idUser, String name) {
        try (Session session = HibernateUtil.getSession()) {
            User user = session.get(User.class, idUser);

            if (user != null) {
                user.setName(name);

                session.beginTransaction();
                session.merge(user);
                session.getTransaction().commit();

                System.out.println("User actualizado correctamente.");
            } else {
                System.out.println("No se encontró el user con el ID: " + idUser);
            }
        } catch (HibernateException e) {
            System.err.println("Error al actualizar el user: " + e.getMessage());
        }
    }

    public void getTickets(int idUser) {
        try (Session session = HibernateUtil.getSession()) {
            User user = session.get(User.class, idUser);

            if (user != null) {
                System.out.println("Tickets de " + user.getName() + ":");
                for (Ticket ticket : user.getTickets()) {
                    System.out.println("ID: " + ticket.getId() + " - Name: " + ticket.getAttractionName() + " - Price: " + ticket.getPrice());
                }
            } else {
                System.out.println("No se encontró el user con el ID: " + idUser);
            }
        } catch (HibernateException e) {
            System.err.println("Error al obtener los tickets: " + e.getMessage());
        }
    }

    public void totalSpend(int idUser) {
        try (Session session = HibernateUtil.getSession()) {
            User user = session.get(User.class, idUser);
            double total = 0;
            if (user != null) {
                for (Ticket ticket : user.getTickets()) {
                    total += ticket.getPrice().doubleValue();
                }
                System.out.println("El usuario " + user.getName() + " ha gastado un total de " + total + "€");
            } else {
                System.out.println("No se encontró el user con el ID: " + idUser);
            }
        } catch (HibernateException e) {
            System.err.println("Error al calcular el gasto total: " + e.getMessage());
        }
    }
}
