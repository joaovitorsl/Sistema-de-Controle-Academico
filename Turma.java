import java.sql.*;

public class Turma{
	protected int cod_turma;
    protected String nome;
	
    public Turma(int cod_turma, String nome) {
    	this.cod_turma=cod_turma;
    	this.nome=nome;
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
			  String query = "INSERT INTO TURMA values ("+ cod_turma +", '"+ nome +"');";
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

	public int getCod_turma() {
		return cod_turma;
	}

	public void setCod_turma(int cod_turma) {
		this.cod_turma = cod_turma;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
     
}
