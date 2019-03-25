import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import org.omg.CORBA.SystemException;
import java.lang.String;

import java.sql.*;

//FALTA FUNÇÕES 6, 7 E 8 ALUNO, PROFESSOR E TECNICO
//FALTA FUNÇÃO 9 PROFESSOR

public class Main {

	protected static String cod_turma[] = null;
	static int i = 0;
	static ArrayList<Pessoa> pessoas = new ArrayList();
	static ArrayList<Turma> turmas = new ArrayList();
	static Diario[] diarios = new Diario[1000];
	static int d = 0;

	static String url = "jdbc:postgresql://localhost:5432/POOproject";
	static String un = "postgres";
	// static String pwd = "ifpb";
	static String pwd = "1111";

	// MAIN
	public static void main(String[] args) {
		Connection connection;
		ResultSet rs;
		while (true) {
			String res = login();
			System.out.println();

			if (res.equals("adm"))
				while (menuADM() != 9)
					;
			else if (res.equals("aluno") || res.equals("tecnico"))
				while (menuAT() != 9)
					;
			else if (res.equals("professor"))
				while (menuP() != 10)
					;
			else
				System.out.println("Matrícula ou Senha errada (ou inexistente)\n");
		}
	}

	// LOGIN
	public static String login() {
		Scanner input = new Scanner(System.in);
		System.out.println("==============================================");
		System.out.println("==================  LOGIN  ===================");
		System.out.println("==============================================\n");

		System.out.print("== Digite sua matrícula: ");
		String user = input.next();

		System.out.print("== Digite sua senha: ");
		String password = input.next();

		if (user.equals("admin") && password.equals("admin") || user.equals("ADMIN") && user.equals("ADMIN"))
			return "adm";

		else {
			int u;
			try {
				u = Integer.parseInt(user);
			} catch (Exception E) {
				return "0";
			}
			Iterator it = pessoas.iterator();
			for (int a = 0; a < i; a++)
				if (u == pessoas.get(a).getMatricula() && password.equals(pessoas.get(a).getSenha()))
					return "outro";

			Connection connection;
			ResultSet rs;
			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, un, pwd);
				Statement st = connection.createStatement();
				String query = "select matricula, senha, id_func from pessoa";
				rs = st.executeQuery(query);
				while (rs.next())
					if (u == rs.getInt("MATRICULA") && password.equals(rs.getString("senha")))
						if (1 == rs.getInt("id_func"))
							return "aluno";
						else if (2 == rs.getInt("id_func"))
							return "professor";
						else
							return "tecnico";

				rs.close();
				st.close();
				connection.close();
			} catch (ClassNotFoundException cnfe) {
				System.out.println("Nao foi possivel encontrar o Driver apropriado");

			} catch (SQLException sqle) {
				System.out.println("Nao foi possivel conectar ao SGBD");
			}
			return "0";
		}
	}

	// MENU DO ADMINISTRADOR
	public static int menuADM() {
		Scanner input = new Scanner(System.in);
		System.out.println("==============  ADMINISTRADOR  ===============");
		System.out.println("===== Digite a opção que deseja acessar ======\n");
		System.out.println("(1) Incluir uma nova pessoa");
		System.out.println("(2) Modificar o cadastro de uma pessoa");
		System.out.println("(3) Remover uma pessoa");
		System.out.println("(4) Adicionar diario");
		System.out.println("(5) Deslogar");

		int op = erroOP(1, 5);

		if (op == 5)
			return 9;

		ADM(op);
		return 0;
	}

	// FUNÇÕES DO ADM
	public static void ADM(int op) {
		Scanner input = new Scanner(System.in);
		Scanner name = new Scanner(System.in);
		boolean erro = false;

		if (op == 1) {
			System.out.println("\n==============================================");
			System.out.println("===========  CADASTRO DE PESSOAS  ============");
			System.out.println("==============================================\n");
			String nome;
			while (true) {
				System.out.print("== NOME: ");
				nome = name.nextLine();
				nome = nome.toUpperCase();
				if (!verificaNome(nome)) {
					System.out.println("\n==================================");
					System.out.println("  Invalido, digite apenas Letras  ");
					System.out.println("==================================\n");
				} else
					break;
			}

			String CPF;
			while (true) {
				System.out.print("== CPF (só números): ");
				CPF = name.nextLine();
				String res = verificaCPF(CPF);
				if (res.equals("c")) {
					System.out.println("\n===================================");
					System.out.println("  Invalido, digite apenas números  ");
					System.out.println("===================================\n");
				} else if (res.equals("b")) {
					System.out.println("\n======================================");
					System.out.println("  Invalido, CPF só possui 11 dígitos  ");
					System.out.println("======================================\n");
				} else
					break;
			}

			String data;
			while (true) {
				System.out.print("== DATA DE NASCIMENTO (dd/mm/aaaa): ");
				data = name.nextLine();
				if (!verificaData(data)) {
					System.out.println("\n============================================");
					System.out.println("  Invalido, digite no formato (dd/mm/aaaa)  ");
					System.out.println("============================================\n");
				} else
					break;
			}

			String telefone;
			while (true) {
				System.out.print("== TELEFONE: ");
				telefone = name.nextLine();
				if (!verificaTelefone(telefone)) {
					System.out.println("\n===================================");
					System.out.println("  Invalido, digite apenas números  ");
					System.out.println("===================================\n");
				} else
					break;
			}

			String email;
			while (true) {
				System.out.print("== EMAIL: ");
				email = name.nextLine();
				if (!verificaEmail(email)) {
					System.out.println("\n=========================================");
					System.out.println("  Invalido, digite no formato de e-mail  ");
					System.out.println("=========================================\n");
				} else
					break;
			}

			String mat;
			int matricula;
			while (true) {
				System.out.print("== MATRÍCULA: ");
				mat = name.nextLine();
				try {
					matricula = Integer.parseInt(mat);
				} catch (Exception E) {
					System.out.println("\n===================================");
					System.out.println("  Invalido, digite apenas números  ");
					System.out.println("===================================\n");
					continue;
				}
				break;
			}

			System.out.print("== SENHA: ");
			String senha = name.nextLine();

			System.out.println("\n=============== Essa pessoa é? ===============\n");
			System.out.println("(1) Aluno");
			System.out.println("(2) Professor");
			System.out.println("(3) Técnico");

			int op2 = erroOP(1, 3);

			if (op2 == 1) {
				int cod = criaTurma();
				pessoas.add(new Aluno(nome, CPF, data, matricula, telefone, email, senha, cod, 1));

			} else {
				String teste;
				int ch;
				while (true) {
					System.out.print("== CARGA HORÁRIA: ");
					teste = name.nextLine();
					try {
						ch = Integer.parseInt(teste);
					} catch (Exception E) {
						System.out.println("\n=================================");
						System.out.println("  Invalido, digite apenas números  ");
						System.out.println("=================================\n");
						continue;
					}
					break;
				}
				float salario;
				while (true) {
					System.out.print("== SALÁRIO: ");
					teste = name.nextLine();
					try {
						salario = Float.parseFloat(teste);
					} catch (Exception E) {
						System.out.println("\n=================================");
						System.out.println("  Invalido, digite apenas números  ");
						System.out.println("=================================\n");
						continue;
					}
					break;
				}

				if (op2 == 2) {
					String area;
					while (true) {
						System.out.print("== ÁREA: ");
						area = name.nextLine();
						area = area.toUpperCase();
						if (!verificaNome(area)) {
							System.out.println("\n==================================");
							System.out.println("  Invalido, digite apenas Letras  ");
							System.out.println("==================================\n");
							continue;
						}
						break;
					}

					String nivel;
					while (true) {
						System.out.print("== NÍVEL: ");
						nivel = name.nextLine();
						nivel = nivel.toUpperCase();
						if (!verificaNome(nivel)) {
							System.out.println("\n==================================");
							System.out.println("  Invalido, digite apenas Letras  ");
							System.out.println("==================================\n");
							continue;
						}
						break;
					}

					pessoas.add(new Professor(nome, CPF, data, matricula, telefone, email, senha, salario, ch, area,
							nivel, 2));

				} else {
					String setor;
					while (true) {
						System.out.print("== SETOR: ");
						setor = name.nextLine();
						setor = setor.toUpperCase();
						if (!verificaNome(setor)) {
							System.out.println("\n==================================");
							System.out.println("  Invalido, digite apenas Letras  ");
							System.out.println("==================================\n");
							continue;
						}
						break;
					}

					pessoas.add(new Tecnico(nome, CPF, data, matricula, telefone, email, senha, salario, ch, setor, 3));
				}
			}
			i++;

		} else if (op == 2) {
			String tab, col, dad;
			System.out.println("==============================================");
			System.out.println("============  ATUALIZAR CADASTRO  ============");
			System.out.println("==============================================\n");

			System.out.print("== Digite a matrícula: ");
			int mat = input.nextInt();

			Connection connection;
			ResultSet rs;
			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, un, pwd);
				Statement st = connection.createStatement();
				String query = "select pp.matricula, f.nome_func from pessoa pp, funcao f where matricula = " + mat
						+ " and f.id=pp.id_func;";
				rs = st.executeQuery(query);
				while (rs.next()) {
					if (mat == rs.getInt(1)) {
						tab = rs.getString(2);
						System.out.println("\n========= O que você quer modificar? =========\n");
						System.out.println("(1) INFORMAÇÕES TÉCNICAS");
						System.out.println("(2) E-MAIL/TELEFONE");

						int op3 = erroOP(1, 2);

						if (op3 == 1) {
							if (tab.equals("ALUNO")) {
								dad = Integer.toString(criaTurma());
								atualiza(tab, "fk_turma", dad, mat, "int");
							}
							else {
								String teste;
								while (true) {
									System.out.print("== NOVA CARGA HORÁRIA: ");
									teste = name.nextLine();
									try {
										int ch = Integer.parseInt(teste);
									} catch (Exception E) {
										System.out.println("\n=================================");
										System.out.println("  Invalido, digite apenas números  ");
										System.out.println("=================================\n");
										continue;
									}
									break;
								}
								dad = teste;
								atualiza(tab, "carga_h", dad, mat, "int");
								
								while (true) {
									System.out.print("== NOVO SALÁRIO: ");
									teste = name.nextLine();
									try {
										float salario = Float.parseFloat(teste);
									} catch (Exception E) {
										System.out.println("\n=================================");
										System.out.println("  Invalido, digite apenas números  ");
										System.out.println("=================================\n");
										continue;
									}
									break;
								}
								dad = teste;
								atualiza(tab, "salario", dad, mat, "float");
								
								if(tab.equals("PROFESSOR")) {
									String area;
									while (true) {
										System.out.print("== NOVA ÁREA: ");
										area = name.nextLine();
										area = area.toUpperCase();
										if (!verificaNome(area)) {
											System.out.println("\n==================================");
											System.out.println("  Invalido, digite apenas Letras  ");
											System.out.println("==================================\n");
											continue;
										}
										break;
									}
									dad = area;
									atualiza(tab, "area", dad, mat, "String");

									String nivel;
									while (true) {
										System.out.print("== NOVO NÍVEL: ");
										nivel = name.nextLine();
										nivel = nivel.toUpperCase();
										if (!verificaNome(nivel)) {
											System.out.println("\n==================================");
											System.out.println("  Invalido, digite apenas Letras  ");
											System.out.println("==================================\n");
											continue;
										}
										break;
									}
									dad = nivel;
									atualiza(tab, "nivel", dad, mat, "String");
								
								}else {
									String setor;
									while (true) {
										System.out.print("== NOVO SETOR: ");
										setor = name.nextLine();
										setor = setor.toUpperCase();
										if (!verificaNome(setor)) {
											System.out.println("\n==================================");
											System.out.println("  Invalido, digite apenas Letras  ");
											System.out.println("==================================\n");
											continue;
										}
										break;
									}
									dad = setor;
									atualiza(tab, "setor", dad, mat, "String");
								}
							}
							
						} else {
							String telefone;
							while (true) {
								System.out.print("== NOVO TELEFONE: ");
								telefone = input.next();
								if (!verificaTelefone(telefone)) {
									System.out.println("\n===================================");
									System.out.println("  Invalido, digite apenas números  ");
									System.out.println("===================================\n");
								} else
									break;
							}
							dad = telefone;
							atualiza(tab, "telefone", dad, mat, "String");

							String email;
							while (true) {
								System.out.print("== NOVO EMAIL: ");
								email = input.next();
								if (!verificaEmail(email)) {
									System.out.println("\n=========================================");
									System.out.println("  Invalido, digite no formato de e-mail  ");
									System.out.println("=========================================\n");
								} else
									break;
							}
							dad = email;
							atualiza(tab, "email", dad, mat, "String");
						}
					}
				}
				rs.close();
				st.close();
				connection.close();
			} catch (ClassNotFoundException cnfe) {
				System.out.println("Nao foi possivel encontrar o Driver apropriado");

			} catch (SQLException sqle) {
				System.out.println("Nao foi possivel conectar ao SGBD 1");
			}
		}

		else if (op == 3)

		{
			removePessoa();

		} else if (op == 4) {
			int qAlunos = 0;
			String diario;
			int cod_diario;
			int cod_turma;
			int[] Alunos = new int[100];
			String professor = null;
			int carga_h;

			System.out.println("Codigo diario");
			cod_diario = input.nextInt();
			System.out.println("Codigo da turma");
			cod_turma = input.nextInt();
			System.out.print("Disciplina do diario: ");
			diario = input.next();
			System.out.print("quantidade de Alunos: ");
			qAlunos = input.nextInt();
			System.out.print("Alunos(cpf): ");
			for (int a = 0; a < qAlunos; a++) {
				Alunos[a] = input.nextInt();
			}
			System.out.println("Carga horaria");
			carga_h = input.nextInt();
			System.out.print("Nome do professor: ");
			professor = input.next();
			diarios[d] = new Diario(cod_diario, diario, Alunos, professor, qAlunos, carga_h, cod_turma);
			d++;

		}
	}

	// MENU PROFESSOR
	public static int menuP() {
		Scanner input = new Scanner(System.in);
		Scanner name = new Scanner(System.in);

		System.out.println("================  PROFESSOR  =================");
		System.out.println("===== Digite a opção que deseja acessar ======\n");
		System.out.println("(1) Listar todas as pessoas");
		System.out.println("(2) Pesquisar um aluno específico");
		System.out.println("(3) Pesquisar um professor específico");
		System.out.println("(4) Pesquisar um contato (pessoa) a partir do telefone");
		System.out.println("(5) Pesquisar um contato (pessoa) a partir do email");
		System.out.println("(6) Listar os nomes das pessoas que cursam uma disciplina");
		System.out.println("(7) Listar as disciplinas lecionadas por um professor");
		System.out.println("(8) Retornar a quantidade de disciplinas cursadas pelo aluno");
		System.out.println("(9) Modificar a nota de aluno");
		System.out.println("(10) Deslogar");

		int op = erroOP(1, 10);

		if (op >= 1 && op <= 8)
			menuprincipal(op);

		else if (op == 9) {
			System.out.println("Digite o codigo do diario");
			int cod = input.nextInt();
			System.out.println("Digite a matricula do aluno");
			int mat = input.nextInt();
			System.out.println("digite a nota do 1 bimestre");
			int nota1 = input.nextInt();
			Connection connection;
			ResultSet rs;
			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, un, pwd);
				Statement st = connection.createStatement();
				String query = "UPDATE diario" + cod + " SET nota_1_bi = " + nota1 + " WHERE alunos = " + mat + "";
				st.executeUpdate(query);

				st.close();
				connection.close();
			} catch (ClassNotFoundException cnfe) {
				System.out.println("Nao foi possivel encontrar o Driver apropriado");
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				System.out.println("Nao foi possivel conectar ao SGBD");
			}
		}

		else
			return 10;

		return 0;

	}

	// MENU ALUNO / TÉCNICO
	public static int menuAT() {
		Scanner input = new Scanner(System.in);
		Scanner name = new Scanner(System.in);
		System.out.println("=============  ALUNO / TÉCNICO  ==============");
		System.out.println("===== Digite a opção que deseja acessar ======\n");
		System.out.println("(1) Listar todas as pessoas");
		System.out.println("(2) Pesquisar um aluno específico");
		System.out.println("(3) Pesquisar um professor específico");
		System.out.println("(4) Pesquisar um contato (pessoa) a partir do telefone");
		System.out.println("(5) Pesquisar um contato (pessoa) a partir do email");
		System.out.println("(6) Listar os nomes das pessoas que cursam uma disciplina");
		System.out.println("(7) Listar as disciplinas lecionadas por um professor");
		System.out.println("(8) Retornar a quantidade de disciplinas cursadas pelo aluno");
		System.out.println("(9) Deslogar");

		int op = erroOP(1, 9);

		if (op >= 1 && op <= 8)
			menuprincipal(op);

		else
			return 9;

		return 0;
	}

	// TURMAS
	public static String turma() {
		Scanner input = new Scanner(System.in);

		System.out.println("==============================================");
		System.out.println("==================  TURMAS  ==================");
		System.out.println("==============================================\n");

		Connection connection;
		ResultSet rs;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, un, pwd);
			Statement st = connection.createStatement();
			String query = "SELECT * FROM TURMA;";
			rs = st.executeQuery(query);
			while (rs.next()) {
				System.out.println("== CÓDIGO: " + rs.getInt("codigo"));
				System.out.println("== NOME: " + rs.getString("nome") + "\n");
			}
			rs.close();
			st.close();
			connection.close();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Nao foi possivel encontrar o Driver apropriado");

		} catch (SQLException sqle) {
			System.out.println("Nao foi possivel conectar ao SGBD");
		}

		String op;
		System.out.println("== A TURMA ESTÁ AQUI? (s/n) ==");
		while (true) {
			op = input.next();

			if (!op.equals("n") && !op.equals("s")) {
				System.out.println("=================================");
				System.out.println("  Invalido, digite apenas (s/n)  ");
				System.out.println("=================================");
			} else
				break;

		}

		return op;
	}

	public static int criaTurma() {
		Scanner input = new Scanner(System.in);
		String res = turma();
		String codigo;
		int cod = 0;
		while (true) {
			System.out.print("== CÓDIGO DA TURMA: ");
			codigo = input.next();
			try {
				cod = Integer.parseInt(codigo);
			} catch (Exception E) {
				System.out.println("\n===============================");
				System.out.println("  Erro! Digite apenas números  ");
				System.out.println("===============================\n");
				continue;
			}
			break;
		}

		if (res.equals("n")) {
			String nomet = "";
			String teste;
			while (true) {
				System.out.print("== ANO/SÉRIE: ");
				teste = input.next();
				try {
					int ano = Integer.parseInt(teste);
				} catch (Exception E) {
					System.out.println("\n===============================");
					System.out.println("  Erro! Digite apenas números  ");
					System.out.println("===============================\n");
					continue;
				}
				break;
			}
			nomet += teste;
			nomet += "º \"";
			while (true) {
				System.out.print("== TURMA: ");
				teste = input.next();
				teste = teste.toUpperCase();
				if (!verificaNome(teste)) {
					System.out.println("\n==================================");
					System.out.println("  Invalido, digite apenas Letras  ");
					System.out.println("==================================\n");
					continue;
				}

				if (teste.length() > 1) {
					System.out.println("\n==============================================");
					System.out.println("  Digite apenas uma letra (Ex: A, B, C, ...)  ");
					System.out.println("==============================================\n");
					continue;
				}
				break;
			}
			nomet += teste;
			nomet += "\" ";

			while (true) {
				System.out.print("== CURSO: ");
				teste = input.next();
				teste = teste.toUpperCase();
				if (!verificaNome(teste)) {
					System.out.println("\n==================================");
					System.out.println("  Invalido, digite apenas Letras  ");
					System.out.println("==================================\n");
				} else
					break;
			}

			nomet += teste;
			turmas.add(new Turma(cod, nomet));
		}
		return cod;
	}

	// BUSCAR TODAS AS PESSOAS
	public static void buscaPessoa() {
		System.out.println("==============================================");
		System.out.println("=============  BUSCA DE PESSOAS  =============");
		System.out.println("==============================================\n");

		Connection connection;
		ResultSet rs;
		int cont = 0;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, un, pwd);
			Statement st = connection.createStatement();
			String query = "SELECT pp.nome, pp.telefone, pp.email, f.nome_func FROM pessoa pp, funcao f where f.id = pp.id_func ORDER BY nome ASC";
			rs = st.executeQuery(query);
			while (rs.next()) {
				System.out.println("== NOME: " + rs.getString("nome"));
				System.out.println("== TELEFONE: " + rs.getString("telefone"));
				System.out.println("== EMAIL: " + rs.getString("email"));
				System.out.println("== FUNÇÃO: " + rs.getString("nome_func") + "\n");
				cont++;
			}
			rs.close();
			st.close();
			connection.close();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Nao foi possivel encontrar o Driver apropriado");

		} catch (SQLException sqle) {
			System.out.println("Nao foi possivel conectar ao SGBD");
		}
		System.out.println("== " + cont + " PESSOA(S) ENCONTRADA(S) ==\n");
	}

	// BUSCAR ALUNO
	public static void buscaAluno(String nome) {
		System.out.println("==============================================");
		System.out.println("=========  BUSCA DE ALUNOS POR NOME  =========");
		System.out.println("==============================================\n");

		Connection connection;
		ResultSet rs;
		int cont = 0;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, un, pwd);
			Statement st = connection.createStatement();
			String query = "select f.nome, tt.nome from Aluno f, turma tt where f.nome like '%" + nome
					+ "%' and f.fk_turma = tt.codigo;";
			rs = st.executeQuery(query);
			while (rs.next()) {
				System.out.println("== NOME: " + rs.getString(1));
				System.out.println("== TURMA: " + rs.getString(2) + "\n");
				cont++;
			}
			rs.close();
			st.close();
			connection.close();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Nao foi possivel encontrar o Driver apropriado");

		} catch (SQLException sqle) {
			System.out.println("Nao foi possivel conectar ao SGBD");
		}
		System.out.println("== " + cont + " ALUNO(S) ENCONTRADO(S) ==\n");
	}

	// BUSCAR PROFESSOR
	public static void buscaProfessor(String nome) {
		System.out.println("==============================================");
		System.out.println("======  BUSCA DE PROFESSORES POR NOME  =======");
		System.out.println("==============================================\n");

		Connection connection;
		ResultSet rs;
		int cont = 0;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, un, pwd);
			Statement st = connection.createStatement();
			String query = "select nome, area, nivel from professor where nome like '%" + nome + "%';";
			rs = st.executeQuery(query);
			while (rs.next()) {
				System.out.println("== NOME: " + rs.getString(1));
				System.out.println("== ÁREA: " + rs.getString(2));
				System.out.println("== NÍVEL: " + rs.getString(3) + "\n");
				cont++;
			}
			rs.close();
			st.close();
			connection.close();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Nao foi possivel encontrar o Driver apropriado");

		} catch (SQLException sqle) {
			System.out.println("Nao foi possivel conectar ao SGBD");
		}
		System.out.println("== " + cont + " PROFESSOR(ES) ENCONTRADO(S) ==\n");
	}

	// BUSCAR TELEFONE
	public static void buscaTelefone(String tel) {
		System.out.println("==============================================");
		System.out.println("======  BUSCA DE PESSOAS POR TELEFONE  =======");
		System.out.println("==============================================\n");

		Connection connection;
		ResultSet rs;
		int cont = 0;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, un, pwd);
			Statement st = connection.createStatement();
			String query = "select pp.nome, pp.telefone, f.nome_func from pessoa pp, funcao f where telefone like '%"+tel+"%' and pp.id_func = f.id;";
			rs = st.executeQuery(query);
			while (rs.next()) {
				System.out.println("== NOME: " + rs.getString(1));
				System.out.println("== TELEFONE: " + rs.getString(2));
				System.out.println("== FUNÇÃO: " + rs.getString(3) + "\n");
				cont++;
			}
			rs.close();
			st.close();
			connection.close();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Nao foi possivel encontrar o Driver apropriado");

		} catch (SQLException sqle) {
			System.out.println("Nao foi possivel conectar ao SGBD");
		}
		System.out.println("\n== " + cont + " PESSOA(S) ENCONTRADA(S) ==\n");
	}

	// BUSCAR EMAIL
	public static void buscaEmail(String email) {
		System.out.println("==============================================");
		System.out.println("========  BUSCA DE PESSOAS POR EMAIL  ========");
		System.out.println("==============================================\n");

		Connection connection;
		ResultSet rs;
		int cont = 0;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, un, pwd);
			Statement st = connection.createStatement();
			String query = "select pp.nome, pp.email, f.nome_func from pessoa pp, funcao f where email like '%"+ email+ "%' and pp.id_func = f.id;";
			rs = st.executeQuery(query);
			while (rs.next()) {
				System.out.println("== NOME: " + rs.getString(1));
				System.out.println("== EMAIL: " + rs.getString(2));
				System.out.println("== FUNÇÃO: " + rs.getString(3) + "\n");
				cont++;
			}
			rs.close();
			st.close();
			connection.close();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Nao foi possivel encontrar o Driver apropriado");

		} catch (SQLException sqle) {
			System.out.println("Nao foi possivel conectar ao SGBD");
		}
		System.out.println("\n== " + cont + " PESSOA(S) ENCONTRADA(S) ==\n");
	}

	// REMOVER PESSOAS
	public static void removePessoa() {
		Scanner input = new Scanner(System.in);
		System.out.println("==============================================");
		System.out.println("============  REMOÇÃO DE PESSOAS  ============");
		System.out.println("==============================================\n");

		System.out.print("== Digite o CPF:");
		String cpf = input.next();

		Connection connection;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, un, pwd);
			Statement st = connection.createStatement();
			String query = "DELETE FROM pessoa WHERE (cpf = '" + cpf + "');";
			st.executeUpdate(query);
			st.close();
			connection.close();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Nao foi possivel encontrar o Driver apropriado");

		} catch (SQLException sqle) {
			System.out.println("Nao foi possivel conectar ao SGBD");
		}
	}

	// ATUALIZA DADOS
	public static void atualiza(String tabela, String coluna, String novo, int mat, String tipo) {
		Scanner input = new Scanner(System.in);

		Connection connection;

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, un, pwd);
			Statement st = connection.createStatement();
			String query;
			if (tipo.equals("String"))
				query = "UPDATE " + tabela + " SET " + coluna + " = '" + novo + "' WHERE matricula = " + mat + ";";
			else if (tipo.equals("int")) {
				query = "UPDATE " + tabela + " SET " + coluna + " = " + Integer.parseInt(novo) + " WHERE matricula = "
						+ mat + ";";
			} else {
				query = "UPDATE " + tabela + " SET " + coluna + " = " + Float.parseFloat(novo) + " WHERE matricula = "
						+ mat + ";";
			}
			st.executeUpdate(query);
			st.close();
			connection.close();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Nao foi possivel encontrar o Driver apropriado");

		} catch (SQLException sqle) {
			System.out.println("Nao foi possivel conectar ao SGBD");
		}
	}

	// FUNÇÕES COMUM PROF, TEC, ALUNO
	public static void menuprincipal(int op) {
		Scanner input = new Scanner(System.in);
		Scanner name = new Scanner(System.in);

		if (op == 1)
			buscaPessoa();
		else if (op == 2) {
			System.out.println("Digite o nome do Aluno:");
			String pessoa = name.nextLine();
			pessoa = pessoa.toUpperCase();
			buscaAluno(pessoa);
		} else if (op == 3) {
			System.out.println("Digite o nome do Professor:");
			String pessoa = name.nextLine();
			pessoa = pessoa.toUpperCase();
			buscaProfessor(pessoa);
		} else if (op == 4) {
			System.out.println("Digite o telefone:");
			String telefone = name.nextLine();
			buscaTelefone(telefone);
		} else if (op == 5) {
			System.out.println("Digite o email:");
			String email = name.nextLine();
			buscaEmail(email);
		}
	}

	// VERIFICA SE TEM ERROS NO NOME
	public static boolean verificaNome(String s) {
		boolean erro = false;
		for (int i = 0; i < s.length(); i++) {
			if (!((s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') || s.charAt(i) == ' ' || s.charAt(i) == 'Ç'
					|| s.charAt(i) == 'Ã' || s.charAt(i) == 'Õ' || s.charAt(i) == 'Á' || s.charAt(i) == 'À'
					|| s.charAt(i) == 'É' || s.charAt(i) == 'È' || s.charAt(i) == 'Í' || s.charAt(i) == 'Ì'
					|| s.charAt(i) == 'Ó' || s.charAt(i) == 'Ò' || s.charAt(i) == 'Ú' || s.charAt(i) == 'Ù')) {
				erro = true;
				break;
			}
		}
		if (erro || s.length() < 1) {
			return false;
		}

		return true;
	}

	// VERIFICA SE TEM ERROS NO CPF
	public static String verificaCPF(String s) {
		int j = 0;
		boolean erro = false;
		for (int i = 0; i < s.length(); i++) {
			if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9')) {
				erro = true;
				break;
			}
			j++;
		}
		if (erro)
			return "c";

		else if (j > 11 || j < 11)
			return "b";

		else
			return "a";
	}

	// VERIFICA SE TEM ERROS NA DATA
	public static boolean verificaData(String s) {
		int j = 0;
		int cont = 0;
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) >= '0' && s.charAt(i) <= '9')
				cont++;
			else if (s.charAt(i) >= '/')
				j++;

		if (cont == 8 && j == 2) {
			return true;
		}
		return false;
	}

	// VERIFICA SE TEM ERROS NO TELEFONE
	public static boolean verificaTelefone(String s) {
		boolean erro = false;
		for (int i = 0; i < s.length(); i++) {
			if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9')) {
				erro = true;
				break;
			}
		}
		if (erro)
			return false;

		return true;
	}

	// VERIFICA SE TEM ERROS NO EMAIL
	public static boolean verificaEmail(String s) {
		int c = 0, p = 0;
		boolean erro = false;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '@')
				c++;
			else if (s.charAt(i) == '.')
				p++;
		}

		if (s.length() >= 7 && c == 1 && p >= 1)
			return true;

		return false;
	}

	// VERIFICA SE TEM ERROS NA ESCOLHA DAS OPÇÕES
	public static int erroOP(int i, int f) {
		Scanner input = new Scanner(System.in);
		int op;
		while (true) {
			String opc = input.nextLine();
			try {
				op = Integer.parseInt(opc);
				if (op > f || op < i) {
					System.out.println("\n====================================");
					System.out.println("  Erro! Opção inválida (" + i + " a " + f + ")  ");
					System.out.println("====================================\n");
					continue;
				}
			} catch (Exception E) {
				System.out.println("\n==================================");
				System.out.println("  Erro! Digite um número (" + i + " a " + f + ")  ");
				System.out.println("==================================\n");
				continue;
			}
			break;
		}
		return op;
	}
}