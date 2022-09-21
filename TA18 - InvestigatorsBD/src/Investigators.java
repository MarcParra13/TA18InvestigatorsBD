import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Investigators {
	static String bd = "investigators3";
	static String login = "root";
	static String password = "Introducir contraseña";
	static String url = "jdbc:mysql://localhost:3306/" + bd;
	
	
	public static void main(String[] args) throws Exception {
// crear conexión
		
		Connection conn = null; 
		try { 
			//Class.forName("com.mysql.jdbc.Driver"); 
			//conn = DriverManager.getConnection(url, login, password); 
			String sURL = url; conn = DriverManager.getConnection(sURL,login,password); 
			if (conn != null) { 
				System.out.println("-Abierta base de datos " + url + " - Ok"); 
			// Crear tabla contacto 
				Statement st = conn.createStatement();
			// Permite comandos SQL 
				st.executeUpdate("CREATE TABLE IF NOT EXISTS FACULTAD (" + 
								  "Codigo INT NOT NULL, " + 
								  "Nombre NVARCHAR(100), " + 
								  "PRIMARY KEY (Codigo))"); 
				System.out.println("\n-Creada tabla (facultad) - Ok");
				
				st.executeUpdate("CREATE TABLE IF NOT EXISTS EQUIPOS (" + 
						   		"NumSerie CHAR(4) NOT NULL, " + 
						   		"Nombre NVARCHAR(100), " +
						   		"Facultad INT, " +
						   		"PRIMARY KEY (NumSerie), " +
						   		"FOREIGN KEY (Facultad) REFERENCES FACULTAD (Codigo))"); 
				System.out.println("\n-Creada tabla (equipos) - Ok");
				
				st.executeUpdate("CREATE TABLE IF NOT EXISTS INVESTIGADORES (" + 
				   				"DNI VARCHAR(8) NOT NULL, " +
				   				"NoMaPELS NVARCHAR(100), " +
						   		"Facultad INT, " +
						   		"PRIMARY KEY (DNI), " +
						   		"FOREIGN KEY (Facultad) REFERENCES FACULTAD (Codigo))");  
				System.out.println("\n-Creada tabla (investigadores) - Ok");	
				
				st.executeUpdate("CREATE TABLE IF NOT EXISTS RESERVA (" + 
		   						"DNI VARCHAR(8) NOT NULL, " +
		   						"NumSerie CHAR(4) NOT NULL, " + 
				   				"Comienzo DATETIME, " + 
				   				"Fin DATETIME, " + 
				   				"PRIMARY KEY (DNI, NumSerie), " + 
				   				"FOREIGN KEY (DNI) REFERENCES INVESTIGADORES (DNI), " + 
				   				"FOREIGN KEY (NumSerie) REFERENCES EQUIPOS (NumSerie))"); 
				System.out.println("\n-Creada tabla (reserva) - Ok");
								
// Insertar datos a la tabla 
				int codigo[] = {6234, 3892, 2231, 7853, 1235}; 
				String nombreFacultad[] = { "A", "B", "C", "D", "E" }; 
				for (int i = 0; i < codigo.length; i++) { 
					st.executeUpdate( 
							"INSERT INTO FACULTAD (" + 
							"Codigo, " + 
							"Nombre) " + 
							"VALUES ("+ "'" + 
							codigo[i] + "','" + 
							nombreFacultad[i] + "' )"); 
					} 
				System.out.println("\n-Añadir registros a la tabla FACULTAD - Ok"); 
				
				int numSerie[] = {1234, 6352, 8923, 6278, 4568}; 
				String nombreEquipo[] = { "A", "B", "C", "D", "E" };
				int facultadEquipo[] = {6234, 3892, 2231, 7853, 1235};
				for (int i = 0; i < codigo.length; i++) { 
					st.executeUpdate( 
							"INSERT INTO EQUIPOS (" + 
							"NumSerie, " + 
							"Nombre, " + 
							"Facultad) " + 
							"VALUES ("+ "'" + 
							numSerie[i] + "','" +
							nombreEquipo[i] + "','" +
							facultadEquipo[i] + "' )"); 
					} 
				System.out.println("\n-Añadir registros a la tabla EQUIPOS - Ok"); 
				
				int dni[] = {27863426, 23847288, 18902373, 12781327, 89127371}; 
				String nomApels[] = { "A", "B", "C", "D", "E" };
				int facultadInvestigador[] = {6234, 3892, 2231, 7853, 1235};
				for (int i = 0; i < codigo.length; i++) { 
					st.executeUpdate( 
							"INSERT INTO INVESTIGADORES (" + 
							"DNI, " + 
							"nomApels, " + 
							"Facultad) " + 
							"VALUES ("+ "'" + 
							dni[i] + "','" + 
							nomApels[i] + "','" + 
							facultadInvestigador[i] + "' )"); 
					} 
				System.out.println("\n-Añadir registros a la tabla INVESTIGADORES - Ok");
				
				int dniReserva[] = {27863426, 23847288, 18902373, 12781327, 89127371}; 
				int numSerieReserva[] = {1234, 6352, 8923, 6278, 4568}; 
				String comienzo[] = {"2022-01-01", "2022-01-01", "2022-01-01", "2022-01-01", "2022-01-01"};
				String fin[] = {"2022-12-31", "2022-12-31", "2022-12-31","2022-12-31", "2022-12-31"};
				for (int i = 0; i < codigo.length; i++) { 
					st.executeUpdate( 
							"INSERT INTO RESERVA (" + 
							"DNI, " + 
							"NumSerie, " +
							"Comienzo, " + 
							"Fin) " + 
							"VALUES ("+ "'" + 
							dniReserva[i] + "','" + 
							numSerieReserva[i] + "','" + 
							comienzo[i] + "','" + 
							fin[i] + "' )"); 
					} 
				System.out.println("\n-Añadir registros a la tabla RESERVA - Ok"); 
				
				
// Consulta de datos 
				System.out.println("\n-Consultar registros tabla FACULTAD:"); 
				ResultSet rs1 = st.executeQuery("select * from facultad"); 
				while (rs1.next()) {
					System.out.println(
							rs1.getString(1) + " " + 
							rs1.getString(2)); 
				}
				
				System.out.println("\n-Consultar registros tabla EQUIPOS:"); 
				ResultSet rs2 = st.executeQuery("select * from equipos"); 
				while (rs2.next()) {
					System.out.println(
							rs2.getString(1) + " " + 
							rs2.getString(2) + " " + 
							rs2.getString(3)); 
				}
				
				System.out.println("\n-Consultar registros tabla INVESTIGADORES:"); 
				ResultSet rs3 = st.executeQuery("select * from investigadores"); 
				while (rs3.next()) {
					System.out.println(
							rs3.getString(1) + " " + 
							rs3.getString(2) + " " + 
							rs3.getString(3)); 
				}
				
				System.out.println("\n-Consultar registros tabla RESERVA:"); 
				ResultSet rs4 = st.executeQuery("select * from reserva"); 
				while (rs4.next()) {
					System.out.println(
							rs4.getString(1) + " " + 
							rs4.getString(2) + " " + 
							rs4.getString(3) + " " + 
							rs4.getString(4)); 
				}
				// Borrar tabla st.executeUpdate("DROP TABLE contacto"); 
				//System.out.println("-Borrar tabla contacto - Ok"); 
				conn.close();

				
// Cerrar base de datos 
				
				System.out.println("\n-Cerrar base de datos " + url + " - Ok"); 
				} 
			} catch (SQLException ex) { 
				System.out.println(ex); 
			} 
	  }	 

}


