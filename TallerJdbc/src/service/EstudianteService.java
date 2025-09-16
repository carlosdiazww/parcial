package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.EstudianteModel;

public class EstudianteService {

    public void insertarEstudiante(Connection conn, EstudianteModel estudiante) {
        String sql = "INSERT INTO Persona (Nombre, Apellido, Correo, Edad, EstadoCivil) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, estudiante.getNombre());
            stm.setString(2, estudiante.getApellido());
            stm.setString(3, estudiante.getCorreo());
            stm.setInt(4, estudiante.getEdad());
            stm.setString(5, estudiante.getEstadoCivil().name());
            int rs = stm.executeUpdate();
            if (rs > 0) {
                System.out.println("Estudiante insertado correctamente.");
            } else {
                System.out.println("Error al insertar estudiante.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar estudiante: " + e.getMessage(), e);
        }
    }

    public void actualizarEstudiante(Connection conn, String correo, String nombre, String apellido, int edad, EstudianteModel.EstadoCivil estadoCivil) {
        String sql = "UPDATE Persona SET Nombre=?, Apellido=?, Edad=?, EstadoCivil=? WHERE Correo=?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, nombre);
            stm.setString(2, apellido);
            stm.setInt(3, edad);
            stm.setString(4, estadoCivil.name());
            stm.setString(5, correo);
            int rs = stm.executeUpdate();
            if (rs > 0) {
                System.out.println("Estudiante actualizado correctamente.");
            } else {
                System.out.println("Estudiante no encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estudiante: " + e.getMessage(), e);
        }
    }

    public void eliminarEstudiante(Connection conn, String correo) {
        String sql = "DELETE FROM Persona WHERE Correo=?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, correo);
            int rs = stm.executeUpdate();
            if (rs > 0) {
                System.out.println("Estudiante eliminado correctamente.");
            } else {
                System.out.println("Estudiante no encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar estudiante: " + e.getMessage(), e);
        }
    }

    public void consultarTodos(Connection conn) {
        String sql = "SELECT * FROM Persona";
        try (PreparedStatement stm = conn.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            boolean hay = false;
            while (rs.next()) {
                hay = true;
                System.out.println("ID: " + rs.getInt("ID") +
                        ", Nombre: " + rs.getString("Nombre") +
                        ", Apellido: " + rs.getString("Apellido") +
                        ", Correo: " + rs.getString("Correo") +
                        ", Edad: " + rs.getInt("Edad") +
                        ", Estado Civil: " + rs.getString("EstadoCivil"));
            }
            if (!hay) {
                System.out.println("No hay estudiantes.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar estudiantes: " + e.getMessage(), e);
        }
    }

    public void consultarPorCorreo(Connection conn, String correo) {
        String sql = "SELECT * FROM Persona WHERE Correo=?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, correo);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    System.out.println("ID: " + rs.getInt("ID") +
                            ", Nombre: " + rs.getString("Nombre") +
                            ", Apellido: " + rs.getString("Apellido") +
                            ", Correo: " + rs.getString("Correo") +
                            ", Edad: " + rs.getInt("Edad") +
                            ", Estado Civil: " + rs.getString("EstadoCivil"));
                } else {
                    System.out.println("Estudiante no encontrado.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar estudiante por correo: " + e.getMessage(), e);
        }
    }
}
