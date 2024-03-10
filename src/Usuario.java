import java.io.Serializable;

public class Usuario implements Serializable {
    private String primeiroNome, sobrenome, email,estado;
    private int idade;

    public Usuario(){

    }
    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public  String getPrimeiroNome(){
        return primeiroNome;
    }
    public String getSobrenome(){
        return sobrenome;
    }
    public String getEstado(){
        return estado;
    }
    public int getIdade(){
        return idade;
    }
}
