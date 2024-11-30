import javax.swing.*;
import java.awt.*;

public class CeldaTombola extends Celda{
    private boolean inPlay;
    private JLabel label;
    public CeldaTombola(int valor) {
        super(valor);
        this.inPlay = false;
        this.label = new JLabel(String.valueOf(valor),SwingConstants.CENTER);
        this.label.setOpaque(true);
        this.label.setBackground(Color.DARK_GRAY);
        this.label.setForeground(Color.GRAY);
        this.label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.label.setFont(new Font("Arial", Font.BOLD, 25));
    }
    public void setInPlay(boolean inPlay) {
        this.inPlay = inPlay;
        cambiarAparencia();
    }
    public JLabel getLabel() {
        return this.label;
    }
    public void cambiarAparencia(){
        if(inPlay){
            label.setForeground(Color.YELLOW);
        }else{
            label.setForeground(Color.GRAY);
        }
    }
}
