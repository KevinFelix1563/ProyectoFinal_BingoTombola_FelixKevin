public abstract class Celda {
    private int valor;
    public Celda(int valor) {
        this.valor = valor;
    }
    public Celda(){
        //Constructor vacio para el momento de crear las cartas
        this.valor = -1;//-1 para representar una celda vacia en la Carta de Bingo
    }
    public int getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }
}
