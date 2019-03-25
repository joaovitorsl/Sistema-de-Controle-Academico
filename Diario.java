import java.sql.*;

public class Diario {
	
	protected int cod_diario;
	protected String Disciplina;
	protected String professor;
	protected int[] Alunos;
	protected int qalunos;
    protected int carga_h;
    protected float notas[];
    protected int preseca[];
    protected int cod_turma;
    Connection connection;
	  ResultSet rs; 
	  String url = "jdbc:postgresql://localhost:5432/POOproject";
	  String un = "postgres";
	  //String pwd = "ifpb";
	  String pwd = "1111";
	  
	public Diario(int cod, String disc, int[] aluno, String prof, int qalun, int carga_h, int cod_turma){
		
		this.cod_diario = cod;
		this.Disciplina = disc;
		this.Alunos = aluno;
		this.professor = prof;
		this.qalunos = qalun;
    	this.carga_h = carga_h;
    	this.notas = notas;
    	this.cod_turma = cod_turma;
    	
    	
    	
		 try{
			  Class.forName("org.postgresql.Driver");
			  connection = DriverManager.getConnection(url,un,pwd);
			  Statement st = connection.createStatement(); 
			  String query = "create table diario"+cod+" (cod integer, disciplina varchar(15), professor varchar(30), Alunos integer, carga_h integer, nota_1_BI integer, nota_2_BI integer, nota_3_BI integer, nota_4_BI integer, fk_turma integer);";
			  st.executeUpdate(query);
			  query = " ALTER TABLE diario"+ cod +" ADD CONSTRAINT FK_TURMA FOREIGN KEY (fk_turma) REFERENCES TURMA (CODIGO);";
			  st.executeUpdate(query);

			  query = " alter table diario"+cod+" add constraint fk_aluno foreign key(alunos) references ALUNO (MATRICULA)";
			  st.executeUpdate(query);
			  
			  for(int i = 0; i< qalun ; i++){
				  query = "insert into diario"+cod+"(cod, disciplina, professor, Alunos, carga_h, fk_turma) values("+ cod +",'"+ disc +"','"+ prof +"',"+ aluno[i] +","+ carga_h +","+ cod_turma +")";
				  st.executeUpdate(query);
				 
			  }
			  query = "insert into diarios values("+cod+")";
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

	public int getCod_diario() {
		return cod_diario;
	}

	public void setCod_diario(int cod_diario) {
		this.cod_diario = cod_diario;
	}

	public String getDisciplina() {
		return Disciplina;
	}

	public void setDisciplina(String disciplina) {
		Disciplina = disciplina;
	}
	public void setNota1Bi(float nota1, int cod, int mat) {
		this.notas[0] = nota1;
		try{
			  Class.forName("org.postgresql.Driver");
			  connection = DriverManager.getConnection(url,un,pwd);
			  Statement st = connection.createStatement(); 
			  String query = "UPDATE PESSOA SET NOTA_1_BI = "+ nota1 + " WHERE matricula = "+mat+"";
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
	public void setNota2Bi(float nota2, int cod, int mat) {
		this.notas[1] = nota2;
		
	}

	public void setNota3Bi(float nota3, int cod, int mat) {
		this.notas[2] = nota3;
		
	}

	public void setNota4Bi(float nota4, int cod, int mat) {
		this.notas[3] = nota4;
		
	}


	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public int[] getAlunos() {
		return Alunos;
	}

	public void setAlunos(int[] alunos) {
		Alunos = alunos;
	}

	public int getQalunos() {
		return qalunos;
	}

	public void setQalunos(int qalunos) {
		this.qalunos = qalunos;
	}
	public int getCarga_h() {
		return carga_h;
	}

	public void setCarga_h(int carga_h) {
		this.carga_h = carga_h;
	}

	public float[] getNotas() {
		return notas;
	}

	public void setNotas(float[] notas) {
		this.notas = notas;
	}

	public int[] getPreseca() {
		return preseca;
	}

	public void setPreseca(int[] preseca) {
		this.preseca = preseca;
	}
	
}
