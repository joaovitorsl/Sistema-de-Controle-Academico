import java.sql.DriverManager;
import java.sql.*; 
import java.sql.Connection;
import java.sql.SQLException;


public class Pessoa {
	protected String Nome;
	protected String CPF;
	protected String Data_Nasc;
	protected int Matricula;
	protected String Telefone;
	protected String Email;
	protected String Senha;
	protected int func;
	
	public Pessoa(String Nome, String CPF, String Data_Nasc, int Matricula, String Telefone, String Email, String Senha, int func){
		this.Nome = Nome;
		this.CPF = CPF;
		this.Data_Nasc = Data_Nasc;
		this.Matricula = Matricula;
		this.Telefone = Telefone;
		this.Email = Email;
		this.Senha = Senha;
		this.func=func;
		
	}
	public void setNome(String Nome){
		this.Nome = Nome;
	}
	public void setCPF(String CPF){
		this.CPF = CPF;	
	}
	public int getMatricula() {
		return Matricula;
	}
	public void setMatricula(int matricula) {
		Matricula = matricula;
	}
	public String getData_Nasc() {
		return Data_Nasc;
	}
	public void setData_Nasc(String data_Nasc) {
		Data_Nasc = data_Nasc;
	}
	public String getTelefone() {
		return Telefone;
	}
	public void setTelefone(String telefone) {
		Telefone = telefone;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getSenha() {
		return Senha;
	}
	public void setSenha(String senha) {
		Senha = senha;
	}
	public String getNome() {
		return Nome;
	}
	public String getCPF() {
		return CPF;
	}
	public void setFunc(int func){
		this.func = func;	
	}
	public int getFunc() {
		return func;
	}
	
	
}
