import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class CartaPatrones {
    private JFrame frame;
    private static final int SIZE = 5;
    private CeldaPatron[][] celdas;
    private Set<Point> patronEscogido;
    public CartaPatrones() {
        frame = new JFrame("Seleccionar patron");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,600);
        frame.setLayout(new BorderLayout());
        patronEscogido = new HashSet<Point>();
        JPanel gridPanel = new JPanel(new GridLayout(SIZE+1,SIZE));//+1 Para letras Bingo

        String[] labels = {"B", "I", "N", "G", "O"};
        for (String label : labels) {
            JLabel bingoLabel = new JLabel(label, SwingConstants.CENTER);
            bingoLabel.setFont(new Font("Arial", Font.BOLD, 30));
            bingoLabel.setOpaque(true); //Para poder asignarle colores
            bingoLabel.setBackground(getColorLabel(label));
            bingoLabel.setForeground(Color.WHITE);
            gridPanel.add(bingoLabel);
        }

        celdas = new CeldaPatron[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                celdas[i][j] = new CeldaPatron();
                gridPanel.add(celdas[i][j].getButton());
            }
        }
        JPanel controlPanel = new JPanel();
        JButton confirmarButton = new JButton("Confirmar Patron");
        confirmarButton.setFont(new Font("Arial", Font.BOLD, 20));
        confirmarButton.setBackground(Color.GRAY);
        confirmarButton.setForeground(Color.WHITE);
        confirmarButton.addActionListener(e -> validarPatron());
        controlPanel.add(confirmarButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetButton.setBackground(Color.GRAY);
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(e -> reset());
        controlPanel.add(resetButton);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    public void validarPatron(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(celdas[i][j].isSeleccionado()){
                    patronEscogido.add(new Point(i, j));
                }
            }
        }
        if(getPatronesValidos().contains(patronEscogido) && !patronEscogido.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Patron valido", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            new BingoTombola(patronEscogido);
        }else{
            JOptionPane.showMessageDialog(frame, "Patron invalido", "Mensaje", JOptionPane.ERROR_MESSAGE);
            reset();
        }
    }
    public void reset(){
        patronEscogido.clear();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                celdas[i][j].setSeleccionado(false);
            }
        }
    }

    private Set<Set<Point>> getPatronesValidos() {
        Set<Set<Point>> patrones = new HashSet<>();

        //5 in a Row horizontales
        for (int i = 0; i < SIZE; i++) {
            Set<Point> horizontal = new HashSet<>();
            for (int j = 0; j < SIZE; j++) {
                horizontal.add(new Point(i, j));
            }
            patrones.add(horizontal);
        }
        for (int j = 0; j < SIZE; j++) {
            Set<Point> vertical = new HashSet<>();
            for (int i = 0; i < SIZE; i++) {
                vertical.add(new Point(i, j));
            }
            patrones.add(vertical);
        }
        //Diagonales
        Set<Point> diagonal1 = new HashSet<>();
        for (int i = 0; i < SIZE; i++) {
            diagonal1.add(new Point(i, i));
        }
        patrones.add(diagonal1);
        Set<Point> diagonal2 = new HashSet<>();
        for (int i = 0; i < SIZE; i++) {
            diagonal2.add(new Point(i, SIZE -1 -i));
        }
        patrones.add(diagonal2);
        //6 pack horizontales
        for(int filInicial = 0; filInicial <= 3; filInicial++){
            for(int colInicial = 0; colInicial <= 2; colInicial++){
                //En un 6 pack horizontal las filas solo puede llegar a la 3
                //y las columnas a la 2.
                Set<Point> horizontal = new HashSet<>();
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 3; j++) {
                        horizontal.add(new Point(filInicial+i, colInicial+j));
                        //Se recorren las filas 2 veces y las columnas 3 veces para llenar todos los
                        //6 pack horizontales posibles.
                    }
                }
                patrones.add(horizontal);
            }
        }

        //6 pack verticales
        for(int filInicial = 0; filInicial <= 2; filInicial++){
            for(int colInicial = 0; colInicial <= 4; colInicial++){
                //Misma manera que en las horizontales solo que esta vez
                //las filas solo llegan a la 2 y las columnas solo llegan
                //a la 4
                Set<Point> vertical = new HashSet<>();
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 2; j++) {
                        vertical.add(new Point(filInicial+i, colInicial+j));
                        //Igual, pero ya que estamos llenando verticalmente se recorre
                        //3 veces en las filas y 2 en las columnas
                    }
                }
                patrones.add(vertical);
            }
        }
        //8 diamond se ingresa manualmente
        Set<Point> diamante = new HashSet<>();
        diamante.add(new Point(0, 2));
        diamante.add(new Point(1, 1));
        diamante.add(new Point(1, 3));
        diamante.add(new Point(2, 0));
        diamante.add(new Point(2, 4));
        diamante.add(new Point(3, 1));
        diamante.add(new Point(3, 3));
        diamante.add(new Point(4, 2));
        patrones.add(diamante);

        //8 Center Box se ingresa manualmente
        Set<Point> smallCenterBox = new HashSet<>();
        smallCenterBox.add(new Point(1, 1));
        smallCenterBox.add(new Point(1, 2));
        smallCenterBox.add(new Point(1, 3));
        smallCenterBox.add(new Point(2, 1));
        smallCenterBox.add(new Point(2, 3));
        smallCenterBox.add(new Point(3, 1));
        smallCenterBox.add(new Point(3, 2));
        smallCenterBox.add(new Point(3, 3));
        patrones.add(smallCenterBox);
        return patrones;
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
}
