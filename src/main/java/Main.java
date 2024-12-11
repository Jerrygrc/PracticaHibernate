import org.hibernate.Session;

public class Main {

    public static void main(String[] args) {

        try (Session session = HibernateUtil.getSession()) {

            UserDAO userDAO = new UserDAO();
            TicketDAO ticketDAO = new TicketDAO();

            // Crear usuarios
             userDAO.createUser("Darío Méndez");

            // Crear tickets
             ticketDAO.createTicket("Noria", 12, 25);
             ticketDAO.createTicket("Tiovivo", 7.5, 26);

            // Borrar usuario
            userDAO.deleteUser(42);

            // Actualizar usuario
            userDAO.updateUser(26, "Jonás Cárdenas");

            // Listar usuarios
            userDAO.readUsers();

            // Borrar tickets
             ticketDAO.deleteTicket(46);
             ticketDAO.deleteTicket(47);

            // Listar tickets
             ticketDAO.listTickets();

            // Obtener tickets de un usuario
             userDAO.getTickets(28);

            // Obtener todos los tickets de una atracción
             ticketDAO.findAttractionTicket("Montaña Rusa");

            // Obtener el total gastado por un usuario
            userDAO.totalSpend(28);

            // Obtener el total gastado en una atracción
            ticketDAO.totalSpendInAttraction("Montaña Rusa");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
