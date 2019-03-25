import java.sql.*;


public class Aluno extends Pessoa {

	protected int Cod_Turma;
	
	public Aluno(String Nome, String CPF, String Data_Nasc, int Matricula, String Telefone, String Email, String Senha, int Cod_Turma, int func) {
		super(Nome, CPF, Data_Nasc, Matricula, Telefone, Email, Senha, func);
		this.Cod_Turma = Cod_Turma;

		
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
			  String query = "INSERT INTO ALUNO values ('"+ Nome +"', '"+ CPF +"', '"+ Data_Nasc +"', "+ Matricula +", '"+ Telefone +"', '"+ Email +"', '"+ Senha +"', "+ Cod_Turma+", "+ func +");";
			  st.executeUpdate(query);
			  st.close();
			  connection.close();
			 }
		catch(ClassNotFoundException cnfe){
			 System.out.println("Nao foi possivel encontrar o Driver apropriado");
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			System.out.println("Nao foi possivel conectar ao SGBD");
		}
		
	}

	public int getCod_Turma() {
		return Cod_Turma;
	}

	public void setCod_Turma(int cod_Turma) {
		Cod_Turma = cod_Turma;
	}
}
