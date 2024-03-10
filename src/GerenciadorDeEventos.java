import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static Tools.Colors.*;
import static Tools.StatusEvento.*;


public class GerenciadorDeEventos {
    private static final String FILE_NAME = "eventos.data";
    private static List<Evento> eventos = new ArrayList<>();
    private static List<Evento> eventosMeuEstado = new ArrayList<>();

    public static void adicionarEvento(Evento evento) {
        eventos.add(evento);
        salvarEventos();
        Main.menuMain();
    }
    public static void addParticipante(int id, String nomeUsuario, int idadeUsuario) {
        if (idadeUsuario >= eventosMeuEstado.get(id).getClassificacao()) {
            if (!eventosMeuEstado.get(id).getParticipante().contains(nomeUsuario)) {
                eventosMeuEstado.get(id).addParticipante(nomeUsuario);
                System.out.println("\nOba! Sua participacão foi confirmada.\nNos vemos em breve!\n");
            } else {
                System.out.println("\nVocê já confirmou sua presença nesse evento\n");
            }
        } else
            System.out.println("\nDesculpe, você não atingiu a idade minima nescessaria para entrar nesse evento!\n");
        salvarEventos();
        Main.menuMain();
    }
    public static void removeParticipante(int id, String nomeUsuario) {

        eventosMeuEstado.get(id).removeParticipantes(nomeUsuario);
        salvarEventos();
        Main.menuMain();
    }
    public static int getTamanho() {
        return eventosMeuEstado.size();
    }
    public static boolean mostraEvento(int i) {
        System.out.println("\n" + YELLOW_BACKGROUND_BRIGHT + BLACK_BOLD + "   " + eventosMeuEstado.get(i).getNome() + "   " + RESET + "\n");
        System.out.println(eventosMeuEstado.get(i).getDescricao() + "\n");
        System.out.println(CYAN_BACKGROUND + WHITE_BOLD_BRIGHT + "   " + eventosMeuEstado.get(i).getFormatedData() + "   " + RESET);

        return eventosMeuEstado.get(i).getParticipante().contains(GerenciadorDeUsuario.getNomeCompleto());
    }
    public static void carregarEventos() {
        try {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            eventos = (List<Evento>) ois.readObject();
            ois.close();
            eventos.sort(Comparator.comparing(Evento::getData));
            eventosMeuEstado = eventos.stream().filter(evento -> evento.getEstado().equalsIgnoreCase(GerenciadorDeUsuario.getEstado())).toList();
        } catch (FileNotFoundException e) {

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        eventos.sort(Comparator.comparing(Evento::getData));
        eventosMeuEstado = eventos.stream().filter(evento -> evento.getEstado().equalsIgnoreCase(GerenciadorDeUsuario.getEstado())).toList();
    }
    public static void salvarEventos() {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(eventos);
            oos.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        carregarEventos();
    }
    public static int statusEvento(int index) {
        LocalDate currentDate = LocalDate.now();
        LocalDate eventDate = eventosMeuEstado.get(index).getData();
        if (eventDate.isAfter(currentDate)) {
            return EVENTO_FUTURO.ordinal();
        } else if (eventDate.isEqual(currentDate)) {
            return EVENTO_AGORA.ordinal();
        } else {
            return EVENTO_PASSADO.ordinal();
        }
    }
    public static void listarEventosMeuEstado() {
        for (int i = 0; i < eventosMeuEstado.size(); i++) {
            String statusEvento;

            switch (statusEvento(i)) {
                case 0 -> statusEvento = RED_BACKGROUND + WHITE_BOLD_BRIGHT + " Passado " + RESET;
                case 1 -> statusEvento = BLUE_BACKGROUND + WHITE_BOLD_BRIGHT + " Hoje    " + RESET;
                case 2 -> statusEvento = GREEN_BACKGROUND + WHITE_BOLD_BRIGHT + " Futuro  " + RESET;
                default -> throw new IllegalStateException("Unexpected value: " + statusEvento(i));
            }
            int index = i + 1;                                      //UX adicona 1 para manter o zero para navegacao no menu
            System.out.println(statusEvento + " " + index + " - " + eventosMeuEstado.get(i).getNome() + " " + CYAN_BOLD + eventosMeuEstado.get(i).getFormatedData() + RESET);
        }
    }
    public static void listarEventosUsuario() {
        List<Evento> meusEventos = eventosMeuEstado.stream().filter(evento -> evento.getParticipante().contains(GerenciadorDeUsuario.getNomeCompleto())).toList();

        if (!meusEventos.isEmpty()) {
            for (Evento meusEvento : meusEventos) {
                System.out.println("-" + meusEvento.getNome() + " " + CYAN_BOLD + meusEvento.getFormatedData() + RESET);
            }
        }else{
            System.out.println("Você não se cadastoru em nenhum evento ainda.");
        }
    }
    public static void listarEventosHoje() {

        List<Evento> eventosHoje = eventosMeuEstado.stream().filter(evento -> evento.getData().isEqual(LocalDate.now())).toList();
        if (!eventosHoje.isEmpty()) {
            for (Evento eventoHoje : eventosHoje) {
                System.out.println("-" + eventoHoje.getNome() + " " + eventoHoje.getFormatedData() + " " + CYAN_BOLD + eventoHoje.getEstado() + RESET);
            }
        }else{
            System.out.println("Nenhum evento marcado pra hoje :( ");
        }
    }
}