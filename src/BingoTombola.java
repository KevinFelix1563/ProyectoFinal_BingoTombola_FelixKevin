import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BingoTombola {
    private JFrame frame;
    private JFrame frame2;
    private CeldaTombola[][] celdas;
    private CeldaCarta[][] celdasCarta;
    private JLabel contador;
    private static final int FILAS = 15;
    private static final int COLUMNAS = 5;
    private static final int SIZE = 5;
    private Set<Integer> numDisponibles;
    private Set<Integer> numGirados;
    private int cont;
    private Random random;
    private Set<Point> patronGanador;


    public BingoTombola(Set<Point> patronGanador) {
        this.patronGanador=patronGanador;
        frame = new JFrame("Bingo Tombola");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());

        frame2 = new JFrame("Verificar carta");
        frame2.setSize(500, 600);
        frame2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame2.setLayout(new BorderLayout());

        JPanel cartaPanel = new JPanel(new GridLayout(SIZE+1,SIZE));//+1 Para las letras
        JPanel gridPanel = new JPanel(new GridLayout(FILAS + 1, COLUMNAS)); //+1 Para las letras

        // Añade los labels del B-I-N-G-O
        String[] labels = {"B", "I", "N", "G", "O"};
        for (String label : labels) {
            JLabel bingoLabel = new JLabel(label, SwingConstants.CENTER);
            bingoLabel.setFont(new Font("Arial", Font.BOLD, 30));
            bingoLabel.setOpaque(true); //Para poder asignarle colores
            bingoLabel.setBackground(getColorLabel(label));
            bingoLabel.setForeground(Color.WHITE);
            //Otros labels para la carta
            JLabel bingoLabel2 = new JLabel(label, SwingConstants.CENTER);
            bingoLabel2.setFont(new Font("Arial", Font.BOLD, 30));
            bingoLabel2.setOpaque(true); //Para poder asignarle colores
            bingoLabel2.setBackground(getColorLabel(label));
            bingoLabel2.setForeground(Color.WHITE);
            gridPanel.add(bingoLabel);
            cartaPanel.add(bingoLabel2);
        }

        celdas = new CeldaTombola[FILAS][COLUMNAS];// Array de celdas tipo CeldaTombola
        celdasCarta = new CeldaCarta[5][5]; //Array para las celdas de la carta
        numDisponibles = new HashSet<>();// HashSet de los numeros que aun no se han obtenido
        numGirados = new HashSet<>();//HashSet para los numeros que ya salieron
        numGirados.add(0);//Se añade el 0 que equivale al numero del free space
        random = new Random();

        for (int row = 0; row < FILAS; row++) {
            for (int col = 0; col < COLUMNAS; col++) {
                int num = col * 15 + row + 1; // Se calcula el numero correspondiente, ejemplo:
                celdas[row][col] = new CeldaTombola(num); // en [2,1] col*15=15 + 3 = 18
                gridPanel.add(celdas[row][col].getLabel()); // Se crea y añade el label al panel
                numDisponibles.add(num); // Se añade el numero al Set de numero disponibles
            }
        }
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if(row == 2 && col == 2){// Condiciones especificas para la celda de espacio libre de en medio
                    celdasCarta[row][col] = new CeldaCarta(row,col,frame2);
                    celdasCarta[row][col].setValor(0);
                    celdasCarta[row][col].getButton().setFont(new Font("Arial", Font.PLAIN, 20));
                    celdasCarta[row][col].getButton().setText("Free");
                    cartaPanel.add(celdasCarta[row][col].getButton());
                }else{
                    celdasCarta[row][col] = new CeldaCarta(row,col,frame2);//Se crean y añaden los botones
                    cartaPanel.add(celdasCarta[row][col].getButton());//al panel de la carta
                }
            }
        }
        // Panel para los botones
        JPanel controlPanel = new JPanel();
        JPanel controlPanel2 = new JPanel();

        //JButton para verificar una carta
        JButton verificarCartaButton = new JButton("Verificar carta");
        verificarCartaButton.setFont(new Font("Arial", Font.BOLD, 20));
        verificarCartaButton.setBackground(Color.GRAY);
        verificarCartaButton.setForeground(Color.WHITE);
        verificarCartaButton.addActionListener(e -> abrirCarta());
        controlPanel.add(verificarCartaButton);

        //JButton para girar la tombola
        JButton nextNumberButton = new JButton("Girar");
        nextNumberButton.setFont(new Font("Arial", Font.BOLD, 20));
        nextNumberButton.setBackground(Color.GRAY);
        nextNumberButton.setForeground(Color.WHITE);
        nextNumberButton.addActionListener(e -> girar());
        controlPanel.add(nextNumberButton);

        //JButton para resetear la tombola
        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetButton.setBackground(Color.GRAY);
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(e -> resetGrid());
        controlPanel.add(resetButton);

        cont=0; //Contador de giros
        //JLabel para llevar el conteo de giros
        contador = new JLabel("Giros: "+String.valueOf(cont));
        contador.setFont(new Font("Arial", Font.BOLD, 20));
        contador.setForeground(Color.BLACK);
        controlPanel.add(contador);

        //JButton para confirmar la carta y verificarla
        JButton verificarButton = new JButton("Verificar carta");
        verificarButton.setFont(new Font("Arial", Font.BOLD, 20));
        verificarButton.setBackground(Color.GRAY);
        verificarButton.setForeground(Color.WHITE);
        verificarButton.addActionListener(e -> verificarCarta());
        controlPanel2.add(verificarButton);

        //JButton para llenar carta con numeros aleatorios
        JButton llenarButton = new JButton("Llenar carta");
        llenarButton.setFont(new Font("Arial", Font.BOLD, 20));
        llenarButton.setBackground(Color.GRAY);
        llenarButton.setForeground(Color.WHITE);
        llenarButton.addActionListener(e -> llenarCarta());
        controlPanel2.add(llenarButton);

        //JButton para resetear la carta
        JButton resetCartaButton = new JButton("Reset");
        resetCartaButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetCartaButton.setBackground(Color.GRAY);
        resetCartaButton.setForeground(Color.WHITE);
        resetCartaButton.addActionListener(e -> resetCarta());
        controlPanel2.add(resetCartaButton);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame2.add(cartaPanel, BorderLayout.CENTER);
        frame2.add(controlPanel2, BorderLayout.SOUTH);
    }


    private void resetGrid() {
        for (int row = 0; row < FILAS; row++) {
            for (int col = 0; col < COLUMNAS; col++) {
                celdas[row][col].setInPlay(false); // Se regresan todas las celdas a false
            }
        }
        numDisponibles.clear(); //Se limpian los HashSets
        numGirados.clear();
        numGirados.add(0); //Valor del free space
        for (int i = 1; i <= FILAS * COLUMNAS; i++) {
            numDisponibles.add(i); //Se vuelve a llenar manualmente
        }
        cont=0; //Se resetea el contador de giros
        actualizarGiros();
    }

    private void girar() {
        if (numDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Ya no hay bolas", "Bingo Tombola", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //Si numDisponibles ya no tiene numeros se le avisa al usuario y se acaba el metodo con return
        // Obtiene un numero random del set
        int randomNum = getRandomDelSet();
        for (int row = 0; row < FILAS; row++) {
            for (int col = 0; col < COLUMNAS; col++) {
                if (celdas[row][col].getValor() == randomNum) {
                    celdas[row][col].setInPlay(true); // Se busca y activa la celda correspondiente
                    break;
                }
            }
        }
        cont=cont+1; //Se actualiza el contador de giro
        actualizarGiros();
        numGirados.add(randomNum);//Se añade el numero al HashSet de girados
        numDisponibles.remove(randomNum); // Se elimina el numero del HashSet de disponibles
    }

    private int getRandomDelSet() {
        //Se obtiene un index de la cantidad de numeros en el HashSet
        int index = random.nextInt(numDisponibles.size());
        int i = 0;
        for (int num : numDisponibles) {
            if (i == index) {
                return num;
            }
            i++;
        }
        //Luego por un For Each se recorre el HashSet hasta encontrar el index que se obtuvo en el nextInt()
        return -1; // No deberia pasar
    }

    private Color getColorLabel(String label) {
        //Manera simple de regresar el color correspondiente a cada letra
        switch (label) {
            case "B": return Color.BLUE;
            case "I": return Color.RED;
            case "N": return Color.GREEN;
            case "G": return Color.ORANGE;
            case "O": return Color.MAGENTA;
            default: return Color.BLACK;
        }
    }

    private void actualizarGiros(){
        //Se actualiza el texto del Label y se vuelve a dibujar
        contador.setText("Giros: "+String.valueOf(cont));
        contador.repaint();
    }
    private Set<Integer> getNumGirados(){
        return numGirados;
    }

    private void abrirCarta() {
        frame2.setVisible(true);
    }
    private void verificarCarta(){
        if(verificarCartaInvalida()){
            Set<Point> patronCeldas = new HashSet<Point>();
            //Set de puntos para las celdas que ya salieron en la tombola
            for(Point p : patronGanador){
                if(numGirados.contains(celdasCarta[p.x][p.y].getValor())){
                    patronCeldas.add(celdasCarta[p.x][p.y].getPunto());
                }
            }
            if(patronGanador.equals(patronCeldas)){
                for(Point p : patronCeldas){
                    celdasCarta[p.x][p.y].getButton().setBackground(Color.LIGHT_GRAY);
                }
                JOptionPane.showMessageDialog(frame2,"La carta es ganadora!","Verificar carta",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(frame2,"La carta no es ganadora","Verificar carta",JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(frame2,"Carta invalida!","Verificar carta",JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean verificarCartaInvalida(){
        Set<Integer> valoresEncontrados = new HashSet<>();
        for(int row = 0; row < SIZE; row++){
            for(int col = 0; col < SIZE; col++){
                int valor = celdasCarta[row][col].getValor();
                if(valor == -1){ //Si se encuentra un -1 sabemos que hay por lo menos una celda sin llenar
                    return false;
                }else if(!valoresEncontrados.add(valor)){
                    //Si el valor ya existe en el Set se regresa false, con ! entramos al if pero regresamos false;
                    return false;
                }
            }
        }
        return true;//Si se cicla el for por completo significa que la carta es valida
    }

    private void resetCarta(){
        for(int row = 0; row < SIZE; row++){
            for(int col = 0; col < SIZE; col++){
                if(row == 2 && col == 2){
                    celdasCarta[row][col].setValor(0);
                }else{
                    celdasCarta[row][col].setValor(-1);
                    celdasCarta[row][col].getButton().setText("");
                }
                celdasCarta[row][col].getButton().setBackground(Color.WHITE);
            }
        }
    }

    private void llenarCarta(){
        for (int col = 0; col < SIZE; col++) {
            HashSet<Integer> numerosUsados = new HashSet<>();
            for (int row = 0; row < SIZE; row++) {
                if(row == 2 && col == 2){

                }else{
                    int num;
                    int min = col * 15 + 1;
                    do{
                        num = random.nextInt(15)+min;
                    }while(numerosUsados.contains(num));
                    celdasCarta[row][col].setValor(num);
                    celdasCarta[row][col].getButton().setText(String.valueOf(num));
                    numerosUsados.add(num);
                }
            }
        }
    }
}