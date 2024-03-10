import java.io.*;

public class GerenciadorDeUsuario {

    private static Usuario usuario = new Usuario();
    private final static String FILE_NAME = "usuario.data";

    protected static String getNomeCompleto() {
        return usuario.getPrimeiroNome() + " " + usuario.getSobrenome();
    }

    protected static int getIdade() {
        return usuario.getIdade();
    }

    public static String getEstado() {
        return usuario.getEstado();
    }

    public static void cadastrarUsuario(Usuario novoUsuario) {
        usuario = novoUsuario;
        salvarUsuario();
    }

    public static String getPrimeiroNomeUsuario() {
        return usuario.getPrimeiroNome();
    }

    public static void salvarUsuario() {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(usuario);
            oos.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean carregarUsuario() {
        try {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            usuario = (Usuario) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}