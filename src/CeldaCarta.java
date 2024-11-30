import javax.swing.*;
import java.awt.*;

public class CeldaCarta extends Celda{
    private JButton button;
    private int row;
    private int col;
    private JFrame frame;
    public CeldaCarta (int row, int col, JFrame frame){
        this.row = row;
        this.col = col;
        this.frame = frame;
        button = new JButton();
        button.setBackground(Color.WHITE);
        button.setForeground(Color.GRAY);
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        button.addActionListener(e -> asignarValor());
    }
    public JButton getButton(){
        return button;
    }
    public Point getPunto(){
        return new Point(row,col);
    }
    public void asignarValor(){
        if(row == 2 && col == 2) {
            JOptionPane.showMessageDialog(frame,"Free Space!","Asignar Valor",JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            int min = col * 15 + 1;
            int max = min + 14;
            String input = JOptionPane.showInputDialog(frame, "Ingresa un número (" + min + "-" + max + "):",
                    "Asignar valor", JOptionPane.PLAIN_MESSAGE);
            if(input != null){
                try{
                    int value = Integer.parseInt(input);
                    if (value < min || value > max){
                        JOptionPane.showMessageDialog(frame, "Numero fuera de rango. " +
                                " Debe estar entre "+ min +" y "+ max, "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        setValor(value);
                        button.setText(String.valueOf(value));
                    }
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(frame,"Entrada inválida. Por favor ingresa un número.",
                            "Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}