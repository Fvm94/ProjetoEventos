package Tools;

public enum CategoriaEvento {
    FESTAS("Festas"),
    EVENTOS_ESPORTIVOS("Eventos Esportivos"),
    SHOWS("Shows"),
    FESTIVAIS("Festivais");
    private final String name;
    CategoriaEvento(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
