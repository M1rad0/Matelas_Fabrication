package back.error;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */

/**
 *
 * @author Mirado
 */
public class NotEnoughRessources extends Exception{

    /**
     * Creates a new instance of <code>NotEnoughRessources</code> without detail
     * message.
     */
    public NotEnoughRessources() {
        super("Les ressources disponibles sont insuffisantes pour cet insert");
    }

    /**
     * Constructs an instance of <code>NotEnoughRessources</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NotEnoughRessources(String msg) {
        super(msg);
    }
}
