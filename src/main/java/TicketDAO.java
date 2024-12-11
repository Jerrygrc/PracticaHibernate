import entities.Ticket;
import entities.User;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.math.BigDecimal;
import java.util.List;

public class TicketDAO {

    public void createTicket(String name, double price, int userId) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            Ticket ticket = new Ticket();
            ticket.setAttractionName(name);
            ticket.setPrice(BigDecimal.valueOf(price));

            User user = session.get(User.class, userId);
            if (user != null) {
                ticket.setUser(user);
                session.persist(ticket);
                session.getTransaction().commit();
                System.out.println("Ticket creado correctamente.");
            } else {
                System.out.println("No se encontró el usuario con el ID: " + userId);
                session.getTransaction().rollback();
            }
        } catch (HibernateException e) {
            System.err.println("Error al crear el ticket: " + e.getMessage());
        }
    }

    public void listTickets() {
        try (Session session = HibernateUtil.getSession()) {
            List<Ticket> tickets = session.createQuery("from Ticket", Ticket.class).list();

            for (Ticket ticket : tickets) {
                System.out.println("ID: " + ticket.getId() + " - Name: " + ticket.getAttractionName() + " - Price: " + ticket.getPrice());
            }
        } catch (HibernateException e) {
            System.err.println("Error al listar los tickets: " + e.getMessage());
        }
    }

    public void deleteTicket(int idTicket) {
        try (Session session = HibernateUtil.getSession()) {
            Ticket ticket = session.get(Ticket.class, idTicket);

            if (ticket != null) {
                session.beginTransaction();
                session.remove(ticket);
                session.getTransaction().commit();
                System.out.println("Ticket eliminado correctamente.");
            } else {
                System.out.println("No se encontró el ticket con el ID: " + idTicket);
            }
        } catch (HibernateException e) {
            System.err.println("Error al eliminar el ticket: " + e.getMessage());
        }
    }

    public void updateTicketPrice(int idTicket, double price) {
        try (Session session = HibernateUtil.getSession()) {
            Ticket ticket = session.get(Ticket.class, idTicket);

            if (ticket != null) {
                ticket.setPrice(BigDecimal.valueOf(price));

                session.beginTransaction();
                session.merge(ticket);
                session.getTransaction().commit();
                System.out.println("Precio del Ticket actualizado correctamente.");
            } else {
                System.out.println("No se encontró el ticket con el ID: " + idTicket);
            }
        } catch (HibernateException e) {
            System.err.println("Error al actualizar el precio del ticket: " + e.getMessage());
        }
    }

    public void findAttractionTicket(String name) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "from Ticket where attractionName = :name";

            List<Ticket> tickets = session.createQuery(hql, Ticket.class)
                    .setParameter("name", name)
                    .list();

            for (Ticket ticket : tickets) {
                System.out.println("ID: " + ticket.getId() + " - Name: " + ticket.getUser().getName() + " - Price: " + ticket.getPrice());
            }
        } catch (HibernateException e) {
            System.err.println("Error al encontrar el ticket de la atracción: " + e.getMessage());
        }
    }

    public void totalSpendInAttraction(String name) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "select sum(price) from Ticket where attractionName = :name";

            BigDecimal total = session.createQuery(hql, BigDecimal.class)
                    .setParameter("name", name)
                    .uniqueResult();

            System.out.println("Total gastado en " + name + ": " + total);
        } catch (HibernateException e) {
            System.err.println("Error al calcular el total gastado en la atracción: " + e.getMessage());
        }
    }
}

