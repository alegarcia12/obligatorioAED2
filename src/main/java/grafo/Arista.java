package grafo;

public class Arista {
    private String peso;
    private boolean existe;

    public Arista(String peso) {
        this.peso = peso;
        this.existe = true;
    }

    public boolean getExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }
}
