import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class HospitalManagement {
    private final DBConnection dbConnection = new DBConnection();
    private final Scanner scanner = new Scanner(System.in);

    // Métodos CRUD para Pacientes
    public void createPaciente() {
        System.out.println("Ingrese nombre del paciente:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese apellido del paciente:");
        String apellido = scanner.nextLine();
        System.out.println("Ingrese fecha de nacimiento del paciente (YYYY-MM-DD):");
        String fechaNacimientoStr = scanner.nextLine();
        // Convertir la cadena a java.sql.Date
        java.sql.Date fechaNacimiento = java.sql.Date.valueOf(fechaNacimientoStr);
        System.out.println("Ingrese direccion del paciente:");
        String direccion = scanner.nextLine();
        System.out.println("Ingrese telefono del paciente:");
        String telefono = scanner.nextLine();
        System.out.println("Ingrese correo electronico del paciente:");
        String correo = scanner.nextLine();

        String SQL = "INSERT INTO Pacientes(nombre, apellido, fecha_nacimiento, direccion, telefono, correo_electronico) VALUES(?,?,?,?,?,?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setDate(3, fechaNacimiento);  // Utiliza setDate para el tipo de dato date en PostgreSQL
            pstmt.setString(4, direccion);
            pstmt.setString(5, telefono);
            pstmt.setString(6, correo);
            pstmt.executeUpdate();
            System.out.println("Paciente creado exitosamente.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void getPacientes() {
        String SQL = "SELECT * FROM Pacientes";

        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            while (rs.next()) {
                System.out.println(rs.getInt("id_paciente") + "\t" +
                        rs.getString("nombre") + "\t" +
                        rs.getString("apellido") + "\t" +
                        rs.getString("fecha_nacimiento"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deletePaciente() {
        System.out.println("Ingrese el ID del paciente a eliminar:");
        int idPaciente = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        String SQL = "DELETE FROM Pacientes WHERE id_paciente = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, idPaciente);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Paciente eliminado exitosamente.");
            } else {
                System.out.println("No se encontró un paciente con el ID especificado.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Métodos CRUD para Médicos
    public void createMedico() {
        System.out.println("Ingrese nombre del medico:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese apellido del medico:");
        String apellido = scanner.nextLine();
        System.out.println("Ingrese especialidad del medico:");
        String especialidad = scanner.nextLine();
        System.out.println("Ingrese telefono del medico:");
        String telefono = scanner.nextLine();
        System.out.println("Ingrese correo electronico del medico:");
        String correo = scanner.nextLine();

        String SQL = "INSERT INTO Medicos(nombre, apellido, especialidad, telefono, correo_electronico) VALUES(?,?,?,?,?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, especialidad);
            pstmt.setString(4, telefono);
            pstmt.setString(5, correo);
            pstmt.executeUpdate();
            System.out.println("Medico creado exitosamente.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void getMedicos() {
        String SQL = "SELECT * FROM Medicos";

        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            while (rs.next()) {
                System.out.println(rs.getInt("id_medico") + "\t" +
                        rs.getString("nombre") + "\t" +
                        rs.getString("apellido") + "\t" +
                        rs.getString("especialidad"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteMedico() {
        System.out.println("Ingrese el ID del medico a eliminar:");
        int idMedico = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        String SQL = "DELETE FROM Medicos WHERE id_medico = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, idMedico);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Medico eliminado exitosamente.");
            } else {
                System.out.println("No se encontró un medico con el ID especificado.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Métodos CRUD para Citas
    public void createCita() {
        System.out.println("Ingrese fecha de la cita (YYYY-MM-DD):");
        String fechaStr = scanner.nextLine();

        // Validar el formato de la fecha (opcional, dependiendo de tu validación previa)
        if (!isValidDateFormat(fechaStr)) {
            System.out.println("Formato de fecha incorrecto. Debe ser YYYY-MM-DD.");
            return;
        }

        java.sql.Date fecha = java.sql.Date.valueOf(fechaStr);

        System.out.println("Ingrese hora de la cita (HH:MM):");
        String horaStr = scanner.nextLine();

        // Parsear la cadena de hora (HH:MM)
        String[] parts = horaStr.split(":");
        int horas = Integer.parseInt(parts[0]);
        int minutos = Integer.parseInt(parts[1]);

        // Crear objeto java.sql.Time
        java.sql.Time hora = new java.sql.Time(horas, minutos, 0);

        System.out.println("Ingrese ID del paciente:");
        int idPaciente = scanner.nextInt();
        System.out.println("Ingrese ID del medico:");
        int idMedico = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        System.out.println("Ingrese motivo de la cita:");
        String motivo = scanner.nextLine();

        String SQL = "INSERT INTO Citas(fecha, hora, id_paciente, id_medico, motivo) VALUES(?,?,?,?,?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setDate(1, fecha);  // Utiliza setDate para el tipo de dato date en PostgreSQL
            pstmt.setTime(2, hora);   // Utiliza setTime para el tipo de dato time en PostgreSQL
            pstmt.setInt(3, idPaciente);
            pstmt.setInt(4, idMedico);
            pstmt.setString(5, motivo);
            pstmt.executeUpdate();
            System.out.println("Cita creada exitosamente.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private boolean isValidDateFormat(String dateStr) {
        try {
            java.sql.Date.valueOf(dateStr);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }



    public void getCitas() {
        String SQL = "SELECT * FROM Citas";

        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            while (rs.next()) {
                System.out.println(rs.getInt("id_cita") + "\t" +
                        rs.getDate("fecha") + "\t" +
                        rs.getTime("hora") + "\t" +
                        rs.getInt("id_paciente") + "\t" +
                        rs.getInt("id_medico") + "\t" +
                        rs.getString("motivo"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteCita() {
        System.out.println("Ingrese el ID de la cita a eliminar:");
        int idCita = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        String SQL = "DELETE FROM Citas WHERE id_cita = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, idCita);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Cita eliminada exitosamente.");
            } else {
                System.out.println("No se encontró una cita con el ID especificado.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void mainMenu() {
        while (true) {
            System.out.println("Menú Principal:");
            System.out.println("1. Citas");
            System.out.println("2. Médicos");
            System.out.println("3. Pacientes");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    citasMenu();
                    break;
                case 2:
                    medicosMenu();
                    break;
                case 3:
                    pacientesMenu();
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public void citasMenu() {
        while (true) {
            System.out.println("Menú Citas:");
            System.out.println("1. Crear cita");
            System.out.println("2. Obtener citas");
            System.out.println("3. Eliminar cita");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    createCita();
                    break;
                case 2:
                    getCitas();
                    break;
                case 3:
                    deleteCita();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public void medicosMenu() {
        while (true) {
            System.out.println("Menú Médicos:");
            System.out.println("1. Crear médico");
            System.out.println("2. Obtener médicos");
            System.out.println("3. Eliminar médico");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    createMedico();
                    break;
                case 2:
                    getMedicos();
                    break;
                case 3:
                    deleteMedico();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public void pacientesMenu() {
        while (true) {
            System.out.println("Menú Pacientes:");
            System.out.println("1. Crear paciente");
            System.out.println("2. Obtener pacientes");
            System.out.println("3. Eliminar paciente");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    createPaciente();
                    break;
                case 2:
                    getPacientes();
                    break;
                case 3:
                    deletePaciente();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public static void main(String[] args) {
        HospitalManagement hm = new HospitalManagement();
        hm.mainMenu();
    }
}