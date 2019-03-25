import java.sql.DriverManager;
import java.sql.*; 
import java.sql.Connection;
import java.sql.SQLException;


public class Professor extends Funcionario{
	protected String Area;
	protected String Nivel;
	
	public Professor(String Nome, String CPF, String Data_Nasc, int Matricula, String Telefone, String Email, String Senha, float salario, int carga_hor, String area, String nivel, int func) {
		super(Nome, CPF, Data_Nasc, Matricula, Telefone, Email, Senha, salario, carga_hor, func);
		this.Area = area;
		this.Nivel = nivel;
		
		Connection connection;
		  ResultSet rs;
		  String url = "jdbc:postgresql://localhost:5432/POOproject";
		  String un = "postgres";
		  //String pwd = "ifpb";
		  String pwd = "1111";
		 try{
			  Class.forName("org.postgresql.Driver");
			  connection = DriverManager.getConnection(url,un,pwd);
			  Statement st = connection.createStatement(); 
			  String query = "INSERT INTO PROFESSOR values ('"+ Nome +"', '"+ CPF +"', '"+ Data_Nasc +"', "+ Matricula +", '"+ Telefone +"', '"+ Email +"', '"+ Senha +"', '"+ salario +"', '"+ carga_hor +"', '"+ area +"', '"+ nivel +"', "+ func +");";
			  st.executeUpdate(query);
			  st.close();
			  connection.close();
			 }
			 catch(ClassNotFoundException cnfe){
			 System.out.println("Nao foi possivel encontrar o Driver apropriado");
			 }
			 catch(SQLException sqle){
			 System.out.println("Nao foi possivel conectar ao SGBD");
			 }
		
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public String getNivel() {
		return Nivel;
	}

	public void setNivel(String nivel) {
		Nivel = nivel;
	}

}

