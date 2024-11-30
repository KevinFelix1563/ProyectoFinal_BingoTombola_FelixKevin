import javax.swing.*;
import java.awt.*;

public class CeldaPatron {
    private boolean seleccionado;
    private JButton button;
    public CeldaPatron() {
        seleccionado = false;
        button = new JButton();
        button.setBackground(Color.WHITE);
        button.addActionListener(e -> toggle());
    }
    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
        cambiarAparencia();
    }
    public JButton getButton(){
        return button;
    }
    public boolean isSeleccionado(){
        return seleccionado;
    }
    public void toggle(){
        seleccionado = !seleccionado;
        cambiarAparencia();
    }
    public void cambiarAparencia(){
        if(seleccionado){
            button.setBackground(Color.LIGHT_GRAY);
        }else{
            button.setBackground(Color.WHITE);
        }
    }
}
