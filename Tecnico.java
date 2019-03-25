import java.sql.*;


public class Tecnico extends Funcionario {
	
	protected String setor;
	
	public Tecnico(String Nome, String CPF, String Data_Nasc, int Matricula, String Telefone, String Email, String Senha, float salario, int carga_hor, String setor, int func) {
		super(Nome, CPF, Data_Nasc, Matricula, Telefone, Email, Senha, salario, carga_hor, func);
		this.setor = setor;
		
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
			  String query = "INSERT INTO TECNICO values ('"+ Nome +"', '"+ CPF +"', '"+ Data_Nasc +"', "+ Matricula +", '"+ Telefone +"', '"+ Email +"', '"+ Senha +"', '"+ salario +"', '"+ carga_hor +"', '"+ setor +"', "+ func +");";
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

	public String getSetor() {
		return setor;
	}

	public void setLocal(String novoSetor) {
		setor = novoSetor;
	}
	

}
