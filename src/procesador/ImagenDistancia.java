package procesador;

/**
 * Clase que representa una imagen con su nombre y distancia.
 * @author manu
 */
public class ImagenDistancia {
    
    protected String nombre;
    protected double[] distancias;
    
    public ImagenDistancia(String nombre, double[] distancias) {
        this.nombre = nombre;
        this.distancias = distancias;
    }
}
