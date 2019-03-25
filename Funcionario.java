import java.sql.DriverManager;
import java.sql.*; 
import java.sql.Connection;
import java.sql.SQLException;


public class Funcionario extends Pessoa{

	protected float Salario;
	protected int Carga_Hor;
	
	public Funcionario(String Nome, String CPF, String Data_Nasc, int Matricula, String Telefone, String Email, String Senha, float salario, int carga_hor, int func) {
		super(Nome, CPF, Data_Nasc, Matricula, Telefone, Email, Senha, func);
		this.Salario = salario;
		this.Carga_Hor = carga_hor;
	}

	public float getSalario() {
		return Salario;
	}

	public void setSalario(float salario) {
		Salario = salario;
	}

	public int getCarga_Hor() {
		return Carga_Hor;
	}

	public void setCarga_Hor(int carga_Hor) {
		Carga_Hor = carga_Hor;
	}
}
