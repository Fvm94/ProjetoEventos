import Tools.CategoriaEvento;
import Tools.Colors;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private final static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        if (!GerenciadorDeUsuario.carregarUsuario()) {
            cadastrarUsuario();
        } else {
            GerenciadorDeEventos.carregarEventos();
            menuMain();
        }
    }
    public static void menuMain() {
        int opcaoEscolhida;

        System.out.println(
                "\nOlá "
                        + Colors.PURPLE_BOLD
                        + GerenciadorDeUsuario.getPrimeiroNomeUsuario()
                        + Colors.RESET
                        + " Escolha uma opção: "
                        + System.lineSeparator()
                        + "1- Ver Eventos"
                        + System.lineSeparator()
                        + "2- Cadastrar evento");
        while (!scan.hasNextInt()) scan.next();
        opcaoEscolhida = scan.nextInt();
        scan.nextLine();                                            //Inserido para conmsumir o \n deixado pelo enter no scan.nextInt e nao pular o proximo scan

        switch (opcaoEscolhida) {
            case 1 -> menuEventos();
            case 2 -> GerenciadorDeEventos.adicionarEvento(cadastrarEvento());
            default -> {
                System.out.println("\nPor favor escolha uma opção valida! ");
                menuMain();
            }
        }
    }
    private static Evento cadastrarEvento() {
        String nome, descricao, estado;
        LocalDate data;
        CategoriaEvento categoria = null;
        int classificacao;

        System.out.println("Escolha o tipo de evento: "
                + System.lineSeparator()
                + "1- " + CategoriaEvento.EVENTOS_ESPORTIVOS.getName()
                + System.lineSeparator()
                + "2- " + CategoriaEvento.FESTAS.getName()
                + System.lineSeparator()
                + "3- " + CategoriaEvento.SHOWS.getName()
                + System.lineSeparator()
                + "4- " + CategoriaEvento.FESTIVAIS.getName());
        System.out.println("0- Voltar ao menu");
        while (!scan.hasNextInt()) scan.next();
        int eventoID = scan.nextInt();
        scan.nextLine();                                            // Inserido para conmsumir o \n deixado pelo enter no scan.nextInt e nao pular o proximo scan

        switch (eventoID) {
            case 0 -> menuMain();
            case 1 -> categoria = CategoriaEvento.EVENTOS_ESPORTIVOS;
            case 2 -> categoria = CategoriaEvento.FESTAS;
            case 3 -> categoria = CategoriaEvento.SHOWS;
            case 4 -> categoria = CategoriaEvento.FESTIVAIS;
            default -> {
                System.out.println("Selecione uma categoria");
                cadastrarEvento();
            }
        }

        System.out.println("Qual o nome do evento?");
        nome = scan.nextLine();
        System.out.println("Qual a classificação indicativa do evento? " + Colors.GREEN_BRIGHT + " 0 = Livre" + Colors.RESET);
        while (!scan.hasNextInt()) scan.nextLine();
        int idade = scan.nextInt();
        while (idade < 0) {
            System.out.println("Por favor insira uma idade valida!");
            while (!scan.hasNextInt()) scan.nextLine();
            idade = scan.nextInt();
        }
        classificacao = idade;
        scan.nextLine();                                        //Inserido para conmsumir o \n deixado pelo enter no scan.nextInt e nao pular o proximo scan
        System.out.println("Em qual estado vai ancontecer(UF)" + Colors.PURPLE_BOLD_BRIGHT + " ex. SP" + Colors.RESET);
        estado = scan.nextLine();
        while (estado.length() > 2) {
            System.out.println("Favor inserir seu estado com duas letras ex.SC");
            estado = scan.nextLine();
        }
        data = getDataHora();
        System.out.println("Por favor descreva o evento: ");
        descricao = scan.nextLine();

        return new Evento(
                nome,
                estado,
                categoria,
                data,
                descricao,
                classificacao
        );
    }
    private static void menuEventos() {

        int listSize = GerenciadorDeEventos.getTamanho();
        if (listSize > 0) listarEventos(listSize);
        else {
            System.out.println("Não existem eventos cadastrados proximos a você\n");
            System.out.println("1- Cadastrar evento");
            System.out.println("0- Voltar");

            while (!scan.hasNext()) scan.next();
            int i = scan.nextInt();
            scan.nextLine();                                        //Inserido para conmsumir o \n deixado pelo enter no scan.nextInt e nao pular o proximo scan

            switch (i) {
                case 0 -> menuMain();
                case 1 -> GerenciadorDeEventos.adicionarEvento(cadastrarEvento());
                default -> System.out.println("Escolha uma opção valida");
            }
        }

        System.out.println("0- Voltar ao menu");

        while (!scan.hasNextInt()) scan.next();
        int i = scan.nextInt();
        scan.nextLine();                                        //Inserido para conmsumir o \n deixado pelo enter no scan.nextInt e nao pular o proximo scan

        while (i != 0) {
            System.out.println("0- Voltar ao menu");
            i = scan.nextInt();
        }
        menuMain();
    }
    private static void listarEventos(int listSize) {
        System.out.println("1- Eventos do meu estado");
        System.out.println("2- Eventos Hoje");
        System.out.println("3- Eventos que estou cadastrado");
        System.out.println("0- Voltar ao menu");
        while (!scan.hasNextInt()) scan.next();
        int id = scan.nextInt();
        switch (id) {
            case 0 -> menuMain();
            case 1 -> listarEventosMeuEstado(listSize);
            case 2 -> GerenciadorDeEventos.listarEventosHoje();
            case 3 -> GerenciadorDeEventos.listarEventosUsuario();
            default -> listarEventos(listSize);
        }
    }
    private static void listarEventosMeuEstado(int listSize) {
        GerenciadorDeEventos.listarEventosMeuEstado();
        System.out.println("0- Voltar ao menu");

        while (!scan.hasNextInt()) scan.next();
        int idEvento = scan.nextInt();
        scan.nextLine();                                        //Inserido para conmsumir o \n deixado pelo enter no scan.nextInt e nao pular o proximo scan

        if (idEvento <= listSize && idEvento > 0) {
            idEvento--;
            boolean participante = GerenciadorDeEventos.mostraEvento(idEvento);
            int statusEvento = GerenciadorDeEventos.statusEvento(idEvento);
            switch (statusEvento) {
                case 0 -> eventoPassado();
                case 1 -> eventoHoje(participante);
                case 2 -> eventoFuturo(participante, idEvento);
            }
        } else if (idEvento == 0) {
            menuEventos();
        } else {
            listarEventosMeuEstado(listSize);
        }
    }
    private static void eventoPassado() {
        System.out.println("\n0- voltar ao menu");
        while (!scan.hasNext()) scan.next();
        int i = scan.nextInt();
        if (i == 0) menuEventos();
    }
    private static void eventoHoje(boolean participante) {
        if (participante) {
            System.out.println("\nOba! começe a aquecer os motores");
            System.out.print("é daqui a pouco\n");
        } else {
            System.out.println("\n" + Colors.RED_BOLD_BRIGHT + ":(" + Colors.RESET);
            System.out.println("Que pena você não confirmou sua presença\n");
        }
        System.out.println("\n0- voltar ao menu");

        while (!scan.hasNext()) scan.next();
        int i = scan.nextInt();
        scan.nextLine();                                        //Inserido para conmsumir o \n deixado pelo enter no scan.nextInt e nao pular o proximo scan

        if (i == 0) {
            menuEventos();
        }
    }
    private static void eventoFuturo(boolean participante, int idEvento) {
        if (!participante) {
            System.out.println("\n1- Confirmar presença!\n");
        } else {
            System.out.println("\n1- remover presença!\n");
        }
        System.out.println("0- voltar ao menu");
        while (!scan.hasNext()) scan.next();
        int i = scan.nextInt();

        if (i == 0) {
            menuEventos();
        } else if (i == 1 && !participante) {
            GerenciadorDeEventos.addParticipante(idEvento, GerenciadorDeUsuario.getNomeCompleto(), GerenciadorDeUsuario.getIdade());
        } else if (i == 1) {
            GerenciadorDeEventos.removeParticipante(idEvento, GerenciadorDeUsuario.getNomeCompleto());
        } else {
            System.out.println("opção inválida");
            menuEventos();
        }
    }
    private static void cadastrarUsuario() {
        Usuario usuario = new Usuario();

        System.out.println(Colors.RED_BACKGROUND + "    usuario não encontrado!    " + Colors.RESET + "\nVamos fazer seu cadastro\n");
        System.out.println("Qual seu primeiro nome?");
        usuario.setPrimeiroNome(scan.nextLine());
        System.out.println("Qual seu sobrenome?");
        usuario.setSobrenome(scan.nextLine());
        System.out.println("Qual seu e-mail?");
        String testEmail = scan.nextLine();
        while (!testEmail.contains("@")) {
            System.out.println("Digite um email valido");
            testEmail = scan.nextLine();
        }
        usuario.setEmail(testEmail);
        System.out.println("Qual a sua idade?");
        while (!scan.hasNextInt()) scan.next();
        int idade = scan.nextInt();                                      //Inserido para conmsumir o \n deixado pelo enter no scan.nextInt e nao pular o proximo scan
        while (idade < 0) {
            System.out.println("Digite uma idade valida");
            idade = scan.nextInt();
        }usuario.setIdade(idade);
        scan.nextLine();                                        //Inserido para conmsumir o \n deixado pelo enter no scan.nextInt e nao pular o proximo scan
        System.out.println("Me diz qual seu estado(UF) para eu recomendar os melhores eventos para você");
        String testEstado = scan.nextLine();
        while (testEstado.length() > 2) {
            System.out.println("Favor inserir seu estado com duas letras" + Colors.PURPLE_BOLD_BRIGHT + "ex.SC" + Colors.RESET);
            testEstado = scan.nextLine();
        }
        usuario.setEstado(testEstado);
        GerenciadorDeUsuario.cadastrarUsuario(usuario);
        GerenciadorDeEventos.carregarEventos();
        menuMain();
    }
    private static LocalDate getDataHora() {
        LocalDate data= null;
        while (data == null) {

            System.out.println("Qual a data? (dd MM yyyy)");
            String _data = scan.nextLine();
            final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd MM yyyy");
            try {
                data = LocalDate.parse(_data, df);
            } catch (Exception e) {
                System.out.println("Data inserida invalida favor certifique-se se esta no formato certo\n" + Colors.PURPLE_BOLD + "ex: 25 03 1994" + Colors.RESET);
            }
        }
        return data;
    }
}