package es.studium.practica3_AD;

import java.sql.SQLException;

public class TestHotel {
	public static void main(String[] args) throws SQLException { 
		int id = ClientePersistencia.createCliente("María José", "Martínez", "mjmartinez@grupostudium.com", "12345678Z", "Studium2020");
		System.out.println(id);
		System.out.println(ClientePersistencia.readCliente(id, "apellidos")); 
		ClientePersistencia.updateCliente(id, "apellidos", "Martínez Navas"); 
		System.out.println(ClientePersistencia.readCliente(id, "apellidos")); 
		ClientePersistencia.deleteCliente(id); 
		System.out.println(ClientePersistencia.readCliente(id, "apellidos")); 
	}
}
