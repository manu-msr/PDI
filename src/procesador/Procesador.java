package procesador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Universidad Nacional Autónoma de México Facultad de Ciencias - Licenciatura
 * en Ciencias de la Computación PROCESO DIGITAL DE IMÁGENES 2016-1 Profesor:
 * Manuel Cristóbal López Michelone Ayudante: Yessica Martínez Reyes
 * Laboratorio: César Hernánddez Solis
 *
 * SOTO ROMERO MANUEL 310204675
 *
 * Clase para manejar un Procesador de Imágenes. Este procesador de imágenes
 * contiene operaciones para abrir y guardar una imagen y agregar diversos
 * filtros.
 */
public class Procesador {

    /* Imagen con la que trabajará el procesador */
    private BufferedImage imagen;
    /* Imagen de archivo para recuperar la imagen original */
    private BufferedImage original;
    /* Lista que guarda las imágenes para imágenes recursivas. */
    private final LinkedList<double[]> distancias;
    /* Lista que guarda las imágenes para fotomosaicos. */
    private final LinkedList<ImagenDistancia> imgs;
    /* Variables estáticas para determinar la posición de la marca de agua. */
    public static final int SUP_IZQ = 1;
    public static final int SUP_DER = 2;
    public static final int INF_IZQ = 3;
    public static final int INF_DER = 4;

    /**
     * Constructor del procesador a partir de una imagen.
     * @param imagen Imagen a procesar.
     */
    public Procesador(BufferedImage imagen) {
        this.imagen = imagen;
        distancias = new LinkedList<>();
        imgs = new LinkedList<>();
    }

    /**
     * Constructor por omisión.
     */
    public Procesador() {
        imagen = null;
        original = null;
        distancias = new LinkedList<>();
        imgs = new LinkedList<>();
    }

    /**
     * Método que abre una imagen y la devuelve para trabajar con ella.
     * @return BufferedImage Imagen que se abrió.
     */
    public BufferedImage abreImagen() {
        /* El usuario escoge la imagen por medio de JFileChooser */
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Selecciona una imagen");
        /* Establecemos el filtro para que sólo aparezcan arhchivos tipo jpg y
         bmp */
        FileNameExtensionFilter filtro
                = new FileNameExtensionFilter("jpg, jpeg, bmp, png", "jpg",
                        "jpeg", "bmp", "png");
        selector.setFileFilter(filtro);
        int aux = selector.showOpenDialog(null);
        if (aux == JFileChooser.APPROVE_OPTION) {//si el usuario presiona aceptar
            try {
                /* Obtenemos la imagen seleccionada */
                File archivo = selector.getSelectedFile();
                original = ImageIO.read(archivo);
            } catch (IOException e) { } //
        }
        return original;
    }

    /**
     * Crea una copia de la imagen original. Se crea una copia de la imagen
     * original para trabajar con ella. Esto con el fin de poder reiniciar la
     * imagen.
     * @return BufferedImage copia
     */
    public BufferedImage copia() {
        /* Creamos una imagen del mismo tamaño y tipo. */
        BufferedImage copia = new BufferedImage(original.getWidth(),
                original.getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics g = copia.createGraphics();
        g.drawImage(original, 0, 0, null);
        imagen = copia;
        return copia;
    }

    /**
     * Método que guarda una imagen.
     */
    public void guardaImagen() {
        try {
            /* El usuario escoge la ruta donde se guardará el archivo con
             JFileChooser */
            JFileChooser selector = new JFileChooser();
            selector.showSaveDialog(null);
            /* Obtiene la ruta seleccionada */
            File guarda = selector.getSelectedFile();
            if (guarda != null) {
                /* Le agregamos la extensión .jpg" */
                guarda = new File(guarda.getPath() + ".jpg");
                System.out.println(guarda.getPath());
                /* Guarda la imagen y le indica al usuario que su imagen ha sido
                 guardada */
                ImageIO.write(imagen, "jpg", guarda);
                JOptionPane.showMessageDialog(null, "Imagen guardada");
            }
        } catch (IOException e) { }
    }

    /**
     * Método que guarda un icono.
     */
    public void guardaIcono() {
        try {
            /* El usuario escoge la ruta donde se guardará el archivo con
             JFileChooser */
            JFileChooser selector = new JFileChooser();
            selector.showSaveDialog(null);
            /* Obtiene la ruta seleccionada */
            File guarda = selector.getSelectedFile();
            if (guarda != null) {
                /* Le agregamos la extensión .jpg" */
                guarda = new File(guarda.getPath() + ".ico");
                /* Guarda la imagen y le indica al usuario que su imagen ha sido
                 guardada */
                ImageIO.write(imagen, "jpg", guarda);
                JOptionPane.showMessageDialog(null, "Icono guardado");
            }
        } catch (IOException e) { }
    }

    /**
     * Método que aplica un filtro rojo a la imagen. Para ello deja el color
     * rojo igual y establece el verde y azul en cero.
     * @return Imagen con el filtro rojo.
     */
    public BufferedImage rojo() {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Optiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Crea el nuevo color quitando el verde y azul */
                Color color_nuevo = new Color(aux.getRed(), 0, 0);
                /* Establece el nuevo color al pixel */
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que aplica un filtro verde a la imagen. Para ello deja el color
     * verde igual y establece el rojo y azul en cero.
     * @return Imagen con el filtro rojo.
     */
    public BufferedImage verde() {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Optiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Crea el nuevo color quitando el rojo y azul */
                Color color_nuevo = new Color(0, aux.getGreen(), 0);
                /* Establece el nuevo color al pixel */
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que aplica un filtro azul a la imagen. Para ello deja el color
     * azul igual y establece el rojo y verde en cero.
     * @return Imagen con el filtro rojo.
     */
    public BufferedImage azul() {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Optiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Crea el nuevo color quitando el rojo y verde */
                Color color_nuevo = new Color(0, 0, aux.getBlue());
                /* Establece el nuevo color al pixel */
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que aplica un filtro de mica a la imagen. Para ello modifica el
     * tono de rojos, verdes y azules.
     * @param r Rojo a modificar.
     * @param g Verde a modificar.
     * @param b Azul a modificar.
     * @return Imagen con el filtro de mica.
     */
    public BufferedImage rgb(int r, int g, int b) {
        /* Recorremos la imagen pixel a pixel. */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Optiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Suma una constante al rojo actual. */
                int nr = aux.getRed() + r;
                /* Suma una constante al verde actual. */
                int ng = aux.getGreen() + g;
                /* Suma una constante al azul actual. */
                int nb = aux.getBlue() + b;
                /* Acotamos los valores por si se salieron de rango. */
                nr = acota(nr);
                ng = acota(ng);
                nb = acota(nb);
                /* Nuevo color. */
                Color color_nuevo = new Color(nr, ng, nb);
                /* Aplicamos el nuevo color. */
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que modifica el brillo de la imagen. Se modifica a partir de una
     * constante.
     * @param k Brillo a modificar en la imagen.
     * @return Imagen con el filtro de brillo.
     */
    public BufferedImage brillo(int k) {
        /* Recorremos la imagen pixel a pixel. */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Optiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Sumamos la constante a todos las componentes. */
                int r = aux.getRed() + k;
                int g = aux.getGreen() + k;
                int b = aux.getBlue() + k;
                /* Acotamos por si algún valor se sale de rango. */
                r = acota(r);
                g = acota(g);
                b = acota(b);
                /* Nuevo color. */
                Color color_nuevo = new Color(r, g, b);
                /* Aplicamos el nuevo color. */
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que modifica aplica un filtro de azar.
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage azar() {
        /* Generador de números aleatorios. */
        Random random = new Random();
        /* Recorremos la imagen pixel a pixel. */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Color del pixel actual. */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Generamos un número aleatorio. */
                int n = random.nextInt(3);
                Color nuevo = aux;
                if (n == 0) // conservamos la componente roja.
                    nuevo = new Color(aux.getRed(), 0, 0);
                if (n == 1) // conservamos la componente verde.
                    nuevo = new Color(0, aux.getRed(), 0);
                if (n == 2) // convervamos la componente azul.
                    nuevo = new Color(0, 0, aux.getBlue());
                /* Asignamos el nuevo color. */
                imagen.setRGB(i, j, nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que aplica un filtro de tonos de gris. Para ello calcula el
     * promedio de las componentes rgb.
     * @return Imagen con el filtro de escala de grises.
     */
    public BufferedImage grisesAverage() {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Obtiene el promedio de las componentes */
                int promedio
                        = (aux.getRed() + aux.getGreen() + aux.getBlue()) / 3;
                /* Crea el nuevo color */
                Color color_nuevo = new Color(promedio, promedio, promedio);
                /* Establece el nuevo color al pixel. */
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que aplica un filtro de tonos de gris. Para ello utiliza distintos
     * porcentajes de rojo, verde y azul.
     * @return Imagen con el filtro de escala de grises.
     */
    public BufferedImage grisesLuma() {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Calcula la luminosidad */
                int gris = (int) (aux.getRed() * 0.3 + aux.getGreen() * 0.59
                        + aux.getBlue() * 0.11);
                /* Crea el nuevo color */
                Color color_nuevo = new Color(gris, gris, gris);
                /* Establece el nuevo color al pixe */
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que aplica un filtro de tonos de gris. Para ello obtiene el mínimo
     * de las componentes rgb y el máximo y los divide entre 2.
     * @return Imagen con el filtro de escala de grises.
     */
    public BufferedImage grisesDesaturation() {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Calcula la desaturación */
                int gris = (getMax(aux.getRed(), aux.getGreen(), aux.getBlue())
                        + getMin(aux.getRed(), aux.getGreen(),
                                aux.getBlue())) / 2;
                /* Crea el nuevo color */
                Color color_nuevo = new Color(gris, gris, gris);
                /* Establece el nuevo color al pixe */
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que aplica un filtro de tonos de gris. Para ello calcula el máximo
     * o mínimo de RGB según el parámetro indicado.
     * @param descomposition - Minimo o máximo.
     * @return Imagen con el filtro de escala de grises.
     */
    public BufferedImage grisesDescomposition(int descomposition) {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Calcula la descomposición */
                int gris = 0;
                if (descomposition == 0)
                    gris = getMin(aux.getRed(), aux.getGreen(), aux.getBlue());
                if (descomposition == 1)
                    gris = getMax(aux.getRed(), aux.getGreen(), aux.getBlue());
                /* Crea el nuevo color */
                Color color_nuevo = new Color(gris, gris, gris);
                /* Establece el nuevo color al pixel */
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que aplica un filtro de tonos de gris. Para ello utiliza un número
     * de color pasado como parámetro.
     * @param singleColor Color para el tono de gris (0 - rojo, 1 - verde, 2 -
     * azul).
     * @return Imagen con el filtro de escala de grises.
     */
    public BufferedImage grisesSingleColor(int singleColor) {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Crea el nuevo color */
                Color color_nuevo;
                if (singleColor == 0) // rojo
                    color_nuevo = new Color(aux.getRed(), aux.getRed(),
                            aux.getRed());
                else if (singleColor == 1) // verde
                    color_nuevo = new Color(aux.getGreen(), aux.getGreen(),
                            aux.getGreen());
                else // azul
                    color_nuevo = new Color(aux.getBlue(), aux.getBlue(),
                            aux.getBlue());
                /* Establece el nuevo color al pixel */
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que aplica un filtro de tonos de gris. Para ello utiliza un número
     * de sombras.
     * @param shades Número de sombras (2 - 256).
     * @return Imagen con el filtro de escala de grises.
     */
    public BufferedImage grisesShades(int shades) {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                int factorDeConversion = 255 / (shades - 1);
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Promedio */
                int promedio
                        = (aux.getRed() + aux.getGreen() + aux.getBlue()) / 3;
                /* Gris */
                int gris = (int) (((promedio / factorDeConversion) + 0.5)
                        * factorDeConversion);
                /* Crea el nuevo color */
                Color color_nuevo = new Color(gris, gris, gris);
                /* Establece el nuevo color al pixe */
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que aplica un filtro de tonos de gris. Para ello utiliza un número
     * de umbral.
     * @param shades
     * @return Imagen con el filtro.
     */
    public BufferedImage grisesDithering(int shades) {
        /* Poblamos la tabla de búsqueda. */
        for (int i = 0; i < imagen.getWidth(); i++) {
            /* El factor de error es 0 en cada iteración. */
            int error = 0;
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Factor de conversión. */
                int factorDeConversion = 255 / (shades - 1);
                /* Color del pixel actual. */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Promedio RGB. */
                int promedio
                        = (aux.getRed() + aux.getGreen() + aux.getBlue()) / 3;
                /* Aplicamos el algoritmo. */
                int grayTempCalc = promedio;
                grayTempCalc += error;
                grayTempCalc = (int) (((grayTempCalc / factorDeConversion) + 0.5)
                        * factorDeConversion);
                error = promedio + error - grayTempCalc;
                promedio = grayTempCalc;
                promedio = acota(promedio);
                /* Color nuevo. */
                Color nuevo = new Color(promedio, promedio, promedio);
                /* Asignamos el nuevo color. */
                imagen.setRGB(i, j, nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Metodo que aplica el filtro de convolucion a una imagen.
     *
     * @param filter Matriz para el filtro.
     * @param factor Factor de conversion.
     * @param bias de conversion.
     * @return
     */
    public BufferedImage convolucion(double[][] filter, double factor,
            double bias) {
        /* Copiamos la imagen, para no trabajar con la original, pues puede
         producir resultados inesperados. */
        BufferedImage nueva = new BufferedImage(imagen.getWidth(),
                imagen.getHeight(), imagen.getType());
        /* Dimensiones de la imagen. */
        int w = imagen.getWidth();
        int h = imagen.getHeight();
        /* Recorremos la imagen pixel a pixel. */
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                double red = 0.0;
                double green = 0.0;
                double blue = 0.0;
                /* Aquí se aplica la matriz de n x m. */
                for (int filterX = 0; filterX < filter.length; filterX++) {
                    for (int filterY = 0; filterY < filter[0].length; filterY++) {
                        int imageX = (x - filter.length / 2 + filterX + w) % w;
                        int imageY = (y - filter[0].length / 2 + filterY + h) % h;
                        Color aux = new Color(imagen.getRGB(imageX, imageY));
                        red += aux.getRed() * filter[filterX][filterY];
                        green += aux.getGreen() * filter[filterX][filterY];
                        blue += aux.getBlue() * filter[filterX][filterY];
                    }
                }
                /* Acota r, g y b respectivamwnte. */
                int r = Math.min(Math.max((int) (factor * red + bias), 0), 255);
                int g = Math.min(Math.max((int) (factor * green + bias), 0), 255);
                int b = Math.min(Math.max((int) (factor * blue + bias), 0), 255);
                /* Nuevo color. */
                Color nuevo = new Color(r, g, b);
                /* Aplica el nuevo color a la copia. */
                nueva.setRGB(x, y, nuevo.getRGB());
            }
        }
        imagen = nueva;
        return imagen;
    }

    /**
     * Método que aplica el filtro de borde mediante una matriz de convolución.
     *
     * @return Imagen con el filtro de borde.
     */
    public BufferedImage borde() {
        double[][] filter = {{0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {-1, -1, 2, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0}};
        return convolucion(filter, 1.0, 0.0);
    }

    /**
     * Método que aplica el filtro de sharpen mediante una matriz de
     * convolución.
     *
     * @return Imagen con el filtro de sharpen.
     */
    public BufferedImage sharpen() {
        double[][] filter = {{-1, -1, -1}, {-1, 9, -1}, {-1, -1, -1}};
        return convolucion(filter, 1.0, 0.0);
    }

    /**
     * Método que aplica el filtro de blur mediante una matriz de convolución.
     *
     * @return Imagen con el filtro de blur.
     */
    public BufferedImage blur() {
        double[][] filter = {{0.0, 0.2, 0.0}, {0.2, 0.2, 0.2}, {0.0, 0.2, 0.0}};
        return convolucion(filter, 1.0, 0.0);
    }

    /**
     * Método que aplica el filtro de motionBlur mediante una matriz de
     * convolución.
     *
     * @return Imagen con el filtro de motionBlur.
     */
    public BufferedImage motionBlur() {
        double[][] filter = new double[9][9];
        /* Matriz con 1's en la diagonal. */
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i == j) {
                    filter[i][j] = 1.0;
                } else {
                    filter[i][j] = 0.0;
                }
            }
        }
        return convolucion(filter, 1.0 / 9.0, 0.0);
    }

    /**
     * Método que aplica el filtro promedio mediante una matriz de convolución.
     *
     * @return Imagen con el filtro de promedio.
     */
    public BufferedImage promedio() {
        double[][] filter = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
        return convolucion(filter, 1.0 / 9.0, 0.0);
    }

    /**
     * Método que aplica el fintro de media mediante una matriz de convolución.
     * Para ello utiliza el algoritmo de combsort.
     *
     * @param filter Matriz de convolución a usar.
     * @return Imagen con el filtro de mediana.
     */
    public BufferedImage media(double[][] filter) {
        /* Dimensiones de la imagen. */
        int w = imagen.getWidth();
        int h = imagen.getHeight();
        /* Histogramas de rojos, verdes y azules. */
        int[] red = new int[filter.length * filter[0].length];
        int[] green = new int[filter.length * filter[0].length];
        int[] blue = new int[filter.length * filter[0].length];
        /* Recorremos la imagen pixel a pixel. */
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int n = 0;
                /* Matriz. */
                for (int filterX = 0; filterX < filter.length; filterX++) {
                    for (int filterY = 0; filterY < filter[0].length; filterY++) {
                        int imageX = (x - filter.length / 2 + filterX + w) % w;
                        int imageY = (y - filter[0].length / 2 + filterY + h) % h;
                        Color aux = new Color(imagen.getRGB(imageX, imageY));
                        red[n] = aux.getRed();
                        green[n] = aux.getGreen();
                        blue[n] = aux.getBlue();
                        n++;
                    }
                }
                /* Ordenamos los histogramas. */
                combsort(red, filter.length * filter[0].length);
                combsort(green, filter.length * filter[0].length);
                combsort(blue, filter.length * filter[0].length);
                /* Acotamos. */
                if ((filter.length * filter[0].length) % 2 == 1) {
                    int r = red[filter.length * filter[0].length / 2];
                    int g = green[filter.length * filter[0].length / 2];
                    int b = blue[filter.length * filter[0].length / 2];
                    Color nuevo = new Color(r, g, b);
                    imagen.setRGB(x, y, nuevo.getRGB());
                } else if (filter.length >= 2) {
                    int r =
                        (red[filter.length * filter[0].length / 2] 
                        + red[filter.length * filter[0].length / 2 + 1]) / 2;
                    int g = 
                        (green[filter.length * filter[0].length / 2] 
                        + green[filter.length * filter[0].length / 2 + 1]) / 2;
                    int b = 
                        (blue[filter.length * filter[0].length / 2] 
                        + blue[filter.length * filter[0].length / 2 + 1]) / 2;
                    Color nuevo = new Color(r, g, b);
                    imagen.setRGB(x, y, nuevo.getRGB());
                }
            }
        }
        return imagen;
    }

    /* Ordena el arreglo usando combsort. */
    private void combsort(int[] data, int amount) {
        int gap = amount;
        boolean swapped = false;
        while (gap > 1 || swapped) {
            gap = (gap * 10) / 13;
            if (gap == 9 || gap == 10) {
                gap = 11;
            }
            swapped = false;
            for (int i = 0; i < amount - gap; i++) {
                int j = i + gap;
                if (data[i] > data[j]) {
                    data[i] += data[j];
                    data[j] = data[i] - data[j];
                    data[i] -= data[j];
                    swapped = true;
                }
            }
        }
    }

    /**
     * Método que convierte una imagen en ícono.
     * @param ancho de la nueva imagen.
     * @param altura de la nueva imagen.
     * @return Icono de la imagen.
     */
    public BufferedImage icono(int ancho, int altura) {
        BufferedImage nueva = new BufferedImage(ancho, altura, 
                imagen.getType());
        /* Tamaño en x. */
        int tam_pix_x = imagen.getWidth() / ancho;
        /* Tamaño en y. */
        int tam_pix_y = imagen.getHeight() / altura;
        /* Recorremos la imagen pixel a pixel. */
        int ii = 0;
        for (int i = 0; i < imagen.getWidth(); i += tam_pix_x) {
            int jj = 0;
            for (int j = 0; j < imagen.getHeight(); j += tam_pix_y) {
                int x = i + tam_pix_x;
                int y = j + tam_pix_y;
                int d1 = tam_pix_x;
                int d2 = tam_pix_y;
                if (x > imagen.getWidth()) {
                    x = imagen.getWidth();
                    d1 = imagen.getWidth() - i;
                }
                if (y > imagen.getHeight()) {
                    y = imagen.getHeight();
                    d2 = imagen.getHeight() - j;
                }
                if (ii < ancho && jj < altura) // modificamos la nueva imagen
                {
                    nueva.setRGB(ii, jj++, 
                            colorPixel(imagen, i, j, x, y, d1 * d2).getRGB());
                }
            }
            ii++;
        }
        imagen = nueva;
        return imagen;
    }

    /* Método auxiliar que obtiene el color del pixel en la nueva imagen. */
    private Color colorPixel(BufferedImage img, int x, int y, int ancho, 
            int alto, int tot) {
        int r = 0;
        int g = 0;
        int b = 0;
        /* Revisamos cada pixel de la región. */
        for (int i = x; i < ancho; i++) {
            for (int j = y; j < alto; j++) {
                Color aux = new Color(img.getRGB(i, j));
                /* Sumamos las componentes r, g y b de TODA la región. */
                r += aux.getRed();
                g += aux.getGreen();
                b += aux.getBlue();
            }
        }
        /* Dividimos para obtener el color promedio. */
        r /= tot;
        g /= tot;
        b /= tot;
        return new Color(r, g, b);
    }

    /**
     * Método que cambia el tamaño de la imagen.
     * @param ancho Nuevo ancho de la imagen.
     * @param alto Nuevo alto de la imagen.
     * @return Imagen con el nuevo tamaño.
     */
    public BufferedImage amplia(int ancho, int alto) {
        BufferedImage nueva = new BufferedImage(ancho, alto, 
                BufferedImage.TYPE_INT_RGB);
        nueva.createGraphics().drawImage(imagen, 0, 0, ancho, alto, null);
        imagen = nueva;
        return imagen;
    }

    /**
     * Método que aplica el filtro de óleo a una imagen. Para ello usa una
     * matriz de convolución de 7x7.
     * @return Imagen con el filtro de óleo aplicado.
     */
    public BufferedImage oleo() {
        /* Creamos una copia, para trabajar con ella. */
        BufferedImage nueva = new BufferedImage(imagen.getWidth(),
                imagen.getHeight(), imagen.getType());
        /* Recorremos la imagen pixel a pixel. */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                int x = i + 7; // dimensiones de la matriz de conv.
                int y = j + 7; // dimensiones de la matriz de conv.
                int d1 = 7;
                int d2 = 7;
                if (x > imagen.getWidth()) { // si se sale de rango en x.
                    x = imagen.getWidth();
                    d1 = imagen.getWidth() - i;
                }
                if (y > imagen.getHeight()) { // si se sale de rango en y.
                    y = imagen.getHeight();
                    d2 = imagen.getHeight() - j;
                }
                /* Aplica el óleo, para ello revisa qué color se está repitiendo
                 más en en la región o vecindad. Una vez que encuentra este
                 color, se lo aplica al pixel actual (en la copia).*/
                nueva.setRGB(i, j, pintaOleo(imagen, i, j, x, y).getRGB());
            }
        }
        imagen = nueva;
        return imagen;
    }

    /* Devuelve el color que le corresponde al pixel de interés */
    private Color pintaOleo(BufferedImage img, int x, int y, int ancho, int alto) {
        /* Estas listas se recorren simultáneamente. */
        LinkedList<Color> colores = new LinkedList<>(); // histograma de colores
        LinkedList<Integer> veces = new LinkedList<>(); // histograma de repeticiones
        /* Recorremos la región. */
        for (int i = x; i < ancho; i++) {
            for (int j = y; j < alto; j++) {
                /* Obtenemos el color actual. */
                Color aux = new Color(img.getRGB(i, j));
                if (!colores.contains(aux)) { // si no está, lo agrega.
                    colores.add(aux);
                    veces.add(1); // aparece 1 vez.
                } else { // si ya está, actualizamos las repeticiones.
                    actualiza(colores, veces, aux);
                }
            }
        }
        /* Devuelve el color que se repitió más veces. */
        return maximo(colores, veces);
    }

    /* Método auxiliar que aumenta el número de veces que aparece un color. */
    private void actualiza(LinkedList<Color> list, LinkedList<Integer> list2, 
            Color v) {
        for (int i = 0; i < list.size(); i++) {
            if (v.equals(list.get(i))) { // buscamos el color actual.
                int aux = list2.get(i);
                /* Aumentamos su repetición en la lista correspondiente. */
                aux += 1;
                list2.set(i, aux);
            }
        }
    }

    /* Determina el color que más se repitió en una lista. */
    private Color maximo(LinkedList<Color> colores, LinkedList<Integer> veces) {
        int indmayor = 0;
        int mayor = 0;
        int x = 0;
        for (Integer i : veces) {
            if (i > mayor) { // si es mayor, actualizamos el índice.
                mayor = i;
                indmayor = x;
            }
            x++;
        }
        /* Devolvemos el color en el índice con mayor número de repeticiones. */
        return colores.get(indmayor);
    }

    /**
     * Método que combina dos imágenes. Para ello utiliza un porcentaje de
     * transparencia.
     *
     * @param otra Imagen a combinar.
     * @param alpha Porcentaje de transparencia.
     * @return Imagen combinada con la 'otra' imagen.
     */
    public BufferedImage blending(BufferedImage otra, double alpha) {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual de cada imagen */
                Color aux1 = new Color(imagen.getRGB(i, j));
                Color aux2 = new Color(otra.getRGB(i, j));
                /* Color en rojo. */
                int br = 
                    (int) ((aux1.getRed() * alpha) 
                        + (aux2.getRed() * (1.0 - alpha)));
                /* Color en verde. */
                int bg = 
                    (int) ((aux1.getGreen() * alpha) 
                        + (aux2.getGreen() * (1.0 - alpha)));
                /* Color en azul. */
                int bb = 
                    (int) ((aux1.getBlue() * alpha) 
                        + (aux2.getBlue() * (1.0 - alpha)));
                Color nuevo = new Color(br, bg, bb);
                /* Establece el nuevo color al pixe */
                imagen.setRGB(i, j, nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que aplica un filto de luz negra a la imagen.
     * @return BufferedImage - Imagen con el filtro.
     */
    public BufferedImage blacklight() {
        /* Recorremos la imagen pixel por pixel. */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual. */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Calcula el promedio y lo nombra L. */
                int L = (aux.getRed() + aux.getGreen() + aux.getBlue()) / 3;
                /* Valor rojo. */
                int r = Math.abs(aux.getRed() - L) * 2;
                /* Valor verde. */
                int g = Math.abs(aux.getGreen() - L) * 2;
                /* Valor azul. */
                int b = Math.abs(aux.getBlue() - L) * 2;
                /* Revisa que no se salga de rango. */
                r = acota(r);
                g = acota(g);
                b = acota(b);
                /* Crea el nuevo color. */
                Color nuevo = new Color(r, g, b);
                /* Establece el nuevo color para el pixel. */
                imagen.setRGB(i, j, nuevo.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que modifica los tonos sepia de la imagen. Se modifica a partir de
     * una constante que indica qué tan amarilla es la imagen.
     * @param k Amarillos a modificar en la imagen.
     * @return Imagen con el filtro de brillo.
     */
    public BufferedImage sepia(int k) {
        BufferedImage copia = grisesAverage();
        for (int i = 0; i < copia.getWidth(); i++) {
            for (int j = 0; j < copia.getHeight(); j++) {
                /* Obtiene el color del pixel actual. */
                Color aux = new Color(copia.getRGB(i, j));
                /* Valor rojo. */
                int r = aux.getRed() * 2 * k;
                /* Valor verde. */
                int g = aux.getGreen() * k;
                /* Crea el nuevo color. */
                r = acota(r);
                g = acota(g);
                Color nuevo = new Color(r, g, aux.getBlue());
                /* Establece el nuevo color para el pixel. */
                imagen.setRGB(i, j, nuevo.getRGB());
            }
        }
        return imagen;

    }

    /**
     * Método que rota una imagen 0, 90, 180 o 270 grados.
     *
     * @param grados Grados a rotar.
     * @return BufferedImage - Imagen rotada.
     */
    public BufferedImage rota(int grados) {
        /* Creamos una copia de la imagen. */
        BufferedImage nueva = imagen;
        /* Si grados es 0 o 180 la imagen queda vertical. */
        if (grados == 0 || grados == 180) {
            nueva = new BufferedImage(imagen.getWidth(), imagen.getHeight(),
                    imagen.getType());
        }
        /* Si grados es 90 o 270 la imagen queda horizontal. */
        if (grados == 90 || grados == 270) {
            nueva = new BufferedImage(imagen.getHeight(), imagen.getWidth(),
                    imagen.getType());
        }
        /* Recorremos la imagen pixel por pixel. */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                switch (grados) {
                    case 0: // si grados = 0
                        nueva.setRGB(i, j, imagen.getRGB(i, j));
                        break;
                    case 90: // si grados = 90
                        nueva.setRGB(j, imagen.getWidth() - i - 1,
                                imagen.getRGB(i, j));
                        break;
                    case 180: // si grados = 180
                        nueva.setRGB(imagen.getWidth() - i - 1,
                                imagen.getHeight() - j - 1, imagen.getRGB(i, j));
                        break;
                    case 270: // si grados = 270
                        nueva.setRGB(imagen.getHeight() - j - 1, i,
                                imagen.getRGB(i, j));
                        break;
                }
            }
        }
        imagen = nueva;
        return imagen;
    }

    /**
     * Método que rota una imagen 0, 90, 180 o 270 grados. Para hacer la
     * rotación usa una matriz de rotación.
     * @param grados Grados a rotar.
     * @return BufferedImage - Imagen rotada.
     */
    public BufferedImage rotaMatriz(int grados) {
        double sin = Math.sin(Math.toRadians(grados));
        double cos = Math.cos(Math.toRadians(grados+01));
        /* Creamos una copia de la imagen. */
        BufferedImage nueva = imagen;
        /* Si grados es 0 o 180 la imagen queda vertical. */
        if (grados == 0 || grados == 180)
            nueva = new BufferedImage(imagen.getWidth(), imagen.getHeight(),
                    imagen.getType());
        /* Si grados es 90 o 270 la imagen queda horizontal. */
        if (grados == 90 || grados == 270) {
            nueva = new BufferedImage(imagen.getHeight(), imagen.getWidth(),
                    imagen.getType());
            sin = Math.sin(Math.toRadians(grados+.01));
            cos = Math.cos(Math.toRadians(grados));
        }
        int ox = (imagen.getWidth()/2)-1;
        int oy = (imagen.getHeight()/2)-1;
        /* Recorremos la imagen pixel por pixel. */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                Color aux = new Color(imagen.getRGB(i, j));
                int ii = i - ox;
                int jj = j - oy;
                int nx = (int)((ii*cos)+(jj*sin));
                int ny = (int)((-1*ii*sin)+(jj*cos));
                if (grados == 180) {
                    nx += ox;
                    ny += oy;
                } else {
                    nx += oy;
                    ny += ox;
                }
                if (nx >= 0 && ny >= 0) 
                    nueva.setRGB(nx, ny, aux.getRGB());
            }
        }
        imagen = nueva;
        return imagen;
    }

    /**
     * Método que aplica el filtro inverso a una imagen.
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage filtroInverso() {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Promedio */
                int promedio = (aux.getRed() + aux.getGreen() + aux.getBlue()) / 3;
                /* Si los tonos son mayores al ummbral ponemos un punto blanco */
                if (promedio < 127)
                    aux = new Color(255, 255, 255);
                /* Si los tonos son menores o iguales al umbral ponemos un punto negro */ 
                else
                    aux = new Color(0, 0, 0);
                /* Establece el nuevo color al pixel */
                imagen.setRGB(i, j, aux.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que aplica el filtro de alto constraste a una imagen.
     *
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage altoContraste() {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Promedio */
                int promedio = (aux.getRed() + aux.getGreen() + aux.getBlue()) / 3;
                /* Si los tonos son mayores al ummbral ponemos un punto blanco */
                if (promedio < 127)
                    aux = new Color(0, 0, 0);
                /* Si los tonos son menores o iguales al umbral ponemos un punto negro */ 
                else
                    aux = new Color(255, 255, 255);
                /* Establece el nuevo color al pixel */
                imagen.setRGB(i, j, aux.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que aplica el filtro AT&T a una imagen.
     *
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage att() {
        /* Primero aplicamos el filtro de alto contraste. */
        BufferedImage ac = altoContraste();
        /* Dimensiones de la imagen. */
        int w = ac.getWidth();
        int h = ac.getHeight();
        Raster rac = ac.getData();
        /* Creamos una copia para trabajar con ella. */
        BufferedImage nueva = new BufferedImage(w, h, 
                BufferedImage.TYPE_INT_RGB);
        WritableRaster wrn = nueva.getRaster();
        /* Recorremos la imagen pixel a pixel. */
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h - 12; j += 12) {
                int puntos = 0;
                for (int y = j; y < j + 12; y++) {
                    if (rac.getSample(i, y, 0) == 0) {
                        puntos++;
                    }
                }
                /* Formamos cada "franja". */
                boolean[] acomodados = centra(12, puntos);
                for (int y = j; y < j + 12; y++) {
                    if (acomodados[y - j]) {
                        wrn.setSample(i, y, 0, 0);
                        wrn.setSample(i, y, 1, 0);
                        wrn.setSample(i, y, 2, 0);
                    } else {
                        wrn.setSample(i, y, 0, 0xff);
                        wrn.setSample(i, y, 1, 0xff);
                        wrn.setSample(i, y, 2, 0xff);
                    }
                }
            }
        }
        lineas(nueva);
        imagen = nueva;
        return imagen;
    }

    /* AUXILIAR PARA ATT */
    private boolean[] centra(int tam, int puntos) {
        boolean[] acomodados = new boolean[tam];
        int n = puntos / 2;
        int m = puntos % 2 == 0 ? n - 1 : n;
        for (int i = (tam / 2) - n; i <= (tam / 2) + m; i++) {
            acomodados[i] = true;
        }
        return acomodados;
    }

    /* AUXILIAR PARA ATT */
    private void lineas(BufferedImage src) {
        WritableRaster wr = src.getRaster();
        for (int i = 0; i < wr.getWidth(); i++) {
            for (int j = 0; j < wr.getHeight() - 12; j += 12) {
                wr.setSample(i, j, 0, 0xff);
                wr.setSample(i, j, 1, 0xff);
                wr.setSample(i, j, 2, 0xff);
            }
        }
    }

    /**
     * Método que aplica pone una marca de agua a una imagen.
     *
     * @param s Mensaje a mostrar.
     * @param region Región donde se mostrará el mensaje.
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage marcaAgua(String s, int region) {
        /* Creamos una copia. */
        BufferedImage nueva = copia();
        /* Dimensiones. */
        int w = nueva.getWidth();
        int h = nueva.getHeight();
        int size = (w / s.length()) / 2;
        Graphics g = nueva.getGraphics();
        /* Elegimos el tipo de letra y tamaño. */
        Font f = new Font(Font.SANS_SERIF, Font.PLAIN, size);
        g.setFont(f);
        g.setColor(Color.BLACK);
        switch (region) { // escogemos la región en la que aparecerá el texto.
            case SUP_IZQ:
                g.drawString(s, 0, size);
                break;
            case SUP_DER:
                g.drawString(s, w - s.length() * size, size);
                break;
            case INF_IZQ:
                g.drawString(s, 0, h - size);
                break;
            case INF_DER:
                g.drawString(s, w - s.length() * size, h - size);
        }
        g.dispose();
        imagen = copia();
        /* Aplicamos blending para que se vea transparente. */
        imagen = blending(nueva, 0.7);
        return imagen;
    }

    /**
     * Método que aplica el filtro mosaico a una imagen.
     *
     * @param ancho Ancho del mosaico.
     * @param alto Alto del mosaico.
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage mosaico(int ancho, int alto) {
        /* Recorremos la imagen pixel a pixel. */
        for (int i = 0; i < imagen.getWidth(); i += ancho) {
            for (int j = 0; j < imagen.getHeight(); j += alto) {
                int x = i + ancho; // tamaño del mosaico en x.
                int y = j + alto; // yamaño del mosaico en y.
                int d1 = ancho;
                int d2 = alto;
                /* Revisamos que no se salga del marco. */
                if (x > imagen.getWidth()) {
                    x = imagen.getWidth();
                    d1 = imagen.getWidth() - i;
                }
                if (y > imagen.getHeight()) {
                    y = imagen.getHeight();
                    d2 = imagen.getHeight() - j;
                }
                /* Pintamos la región. */
                pinta(imagen, i, j, x, y, d1 * d2);
            }
        }
        return imagen;
    }

    /* AUXILIAR PARA FILTRO MOSAICO. */
    private void pinta(BufferedImage img, int x, int y, int ancho, int alto, 
            int tot) {
        int r = 0;
        int g = 0;
        int b = 0;
        /* Revisamos cada pixel de la región. */
        for (int i = x; i < ancho; i++) {
            for (int j = y; j < alto; j++) {
                Color aux = new Color(img.getRGB(i, j));
                /* Sumamos las componentes r, g y b de TODA la región. */
                r += aux.getRed();
                g += aux.getGreen();
                b += aux.getBlue();
            }
        }
        /* Dividimos para obtener el color promedio. */
        r /= tot;
        g /= tot;
        b /= tot;
        /* Aplicamos dicho color promedio a toda la región. */
        for (int i = x; i < ancho; i++) {
            for (int j = y; j < alto; j++) {
                img.setRGB(i, j, new Color(r, g, b).getRGB());
            }
        }
    }

    /**
     * Método que aplica el filtro de semitonos a una imagen.
     *
     * @param umbral Umbral para aplicar el filtro.
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage semitonos(int umbral) {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Promedio */
                int promedio = (aux.getRed() + aux.getGreen() + aux.getBlue()) / 3;
                /* Si los tonos son mayores al ummbral ponemos un punto blanco */
                if (promedio > umbral) {
                    aux = new Color(255, 255, 255);
                } /* Si los tonos son menores o iguales al umbral ponemos un punto negro */ else {
                    aux = new Color(0, 0, 0);
                }
                /* Establece el nuevo color al pixel */
                imagen.setRGB(i, j, aux.getRGB());
            }
        }
        return imagen;
    }

    /**
     * Método que crea una imagen recursiva. La guarda como una tabla html. (En
     * formato cadena). Este método utiliza la paleta web safe color.
     * @param ancho Ancho del mosaico.
     * @param alto Alto del mosaico.
     * @return Cadena en html de la imagen recuriva.
     */
    public String recursivaWSC(int ancho, int alto) {
        /* Generamo los 216 colores de la web pallete. */
        generaWebSafeColors(imagen);
        /* Altura de la imagen. */
        int height = imagen.getHeight() / alto;
        /* Anchura de la imagen. */
        int width = imagen.getWidth() / ancho;
        /* Tabla que guardará la información de la nueva imagen. */
        String[][] tabla = 
                new String[imagen.getHeight() / alto][imagen.getWidth() / ancho];
        /* Encabezado el HTML. */
        String cadena = 
                "<table border = \"0\" cellspacing=\"0\" cellpadding=\"0\"\n";
        int l = 0; // renglón actual
        for (int i = 0; i < imagen.getWidth(); i += ancho) {
            int k = 0; // columna actual
            for (int j = 0; j < imagen.getHeight(); j += alto) {
                /* Delimitamos la región como en el filtro mosaico. */
                int x = i + ancho;
                int y = j + alto;
                int d1 = ancho;
                int d2 = alto;
                if (x > imagen.getWidth()) {
                    x = imagen.getWidth();
                    d1 = imagen.getWidth() - i;
                }
                if (y > imagen.getHeight()) {
                    y = imagen.getHeight();
                    d2 = imagen.getHeight() - j;
                }
                /* Revisamos renglones y columnas. */
                if (k >= height || l >= width)
                    break;
                /* Construimos la imagen. */
                tabla[k++][l] = construyeWSC(imagen, i, j, x, y, d1 * d2);
            }
            l++; // siguiente renglón.
        }
        /* Leemos la información de la tabla y construimos el HTML. */
        for (int i = 0; i < imagen.getHeight() / alto; i++) {
            cadena += "\t<tr>\n";
            for (int j = 0; j < imagen.getWidth() / ancho; j++)
                cadena += tabla[i][j];
            cadena += "</tr>";
        }
        cadena += "</table>";
        // vaciamos la lista de distancias, para un próximo uso
        distancias.clear();
        return cadena;
    }

    /* Método auxiliar que genera las 216 imágenes de Web Safe Colors. */
    private void generaWebSafeColors(BufferedImage img) {
        int x = 0;
        /* Generamos las posibles combinaciones entre múltiplos de 51 entre 0 y
         256. */
        for (int i = 0; i < 256; i += 51) {
            for (int j = 0; j < 256; j += 51) {
                for (int k = 0; k < 256; k += 51) {
                    BufferedImage nueva = copia();
                    Procesador p = new Procesador(nueva);
                    nueva = p.rgb(i, j, k); // aplicamos el filtro rgb a la imagen.
                    /* Guardamos el valor de sus componentes para aplicar la
                     distancia posteriormente.*/
                    double[] d = {(double) i, (double) j, (double) k};
                    distancias.add(d); // las agregamos a la lista
                    /* Guardamos el archivo. */
                    try {
                        File guarda = new File(String.format("imagenes/%s.jpg", 
                                x++));
                        ImageIO.write(nueva, "jpg", guarda);
                    } catch (IOException ioe) {
                    }
                    imagen = img;
                }
            }
        }
    }

    /* Método auxiliar que genera el mosaico usando WSC. */
    private String construyeWSC(BufferedImage img, int x, int y, int ancho, 
            int alto, int tot) {
        /* Buscamos el color del mosaico, tal y como se hace en el filtro mosaico. */
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = x; i < ancho; i++) {
            for (int j = y; j < alto; j++) {
                Color aux = new Color(img.getRGB(i, j));
                r += aux.getRed();
                g += aux.getGreen();
                b += aux.getBlue();
            }
        }
        r /= tot;
        g /= tot;
        b /= tot;
        /* id aquí será la imagen con la menor distancia. */
        int id = distancia(r, g, b);
        return String.format("\t\t<nobr><td><img src=\"%d.jpg\" width=\"20\", height=\"20\"></td></nobr>\n", 
                id);
    }

    /* Obtiene las imagen con el color más cercano. */
    private int distancia(double r, double g, double b) {
        double minimo = 1000000;
        int min_aux = 0;
        int i;
        /* Recorremos la lista distancias en busca del punto más cercano. */
        for (i = 0; i < distancias.size(); i++) {
            double[] a = distancias.get(i);
            /* Obtenemos las componentes del punto actual. */
            double aux = distancia(r, a[0], g, a[1], b, a[2]);
            if (aux <= minimo) { // si encuentra uno menor.
                minimo = aux;
                min_aux = i;
            }
        }
        /* Devolvemos la imagen más cercana al punto (su índice en la lista). */
        return min_aux;
    }

    /* Encuentra la distancia de la imagen. */
    private double distancia(double rx, double ry, double gx, double gy, 
            double bx, double by) {
        return Math.sqrt((rx - ry) * (rx - ry) + (gx - gy) * (gx - gy) 
                + (bx - by) * (bx - by));
    }

    /**
     * Método que crea una imagen recursiva. La guarda como una tabla html. (En
     * formato cadena). Este método utiliza el color original de la imagen.
     * @param ancho Ancho del mosaico.
     * @param alto Alto del mosaico.
     * @return Cadena con el html de la imagen recursiva.
     */
    public String recursiva(int ancho, int alto) {
        /* Dimensiones de la imagen. */
        int height = imagen.getHeight() / alto;
        int width = imagen.getWidth() / ancho;
        /* Tabla para guardar información del HTML y evitar que se voltée la
         imagen. */
        String[][] tabla = 
                new String[imagen.getHeight() / alto][imagen.getWidth() / ancho];
        String cadena = 
                "<table border = \"0\" cellspacing=\"0\" cellpadding=\"0\"\n";
        /* Recorremos la imagen tal y como lo hacíamos en el filtro mosaico, a
         diferencia de aquí actualizamos la tabla. */
        int l = 0; // renglones.
        for (int i = 0; i < imagen.getWidth(); i += ancho) {
            int k = 0; // columnas.
            for (int j = 0; j < imagen.getHeight(); j += alto) {
                int x = i + ancho;
                int y = j + alto;
                int d1 = ancho;
                int d2 = alto;
                if (x > imagen.getWidth()) {
                    x = imagen.getWidth();
                    d1 = imagen.getWidth() - i;
                }
                if (y > imagen.getHeight()) {
                    y = imagen.getHeight();
                    d2 = imagen.getHeight() - j;
                }
                if (k >= height || l >= width)
                    break;
                tabla[k++][l] = construye(imagen, i, j, x, y, d1 * d2);
            }
            l++;
        }
        /* Reconstruimos la tabla HTML. */
        for (int i = 0; i < imagen.getHeight() / alto; i++) {
            cadena += "\t<tr>\n";
            for (int j = 0; j < imagen.getWidth() / ancho; j++)
                cadena += tabla[i][j];
            cadena += "</tr>";
        }
        cadena += "</table>";
        return cadena;
    }

    /* AUXILIAR PARA FILTRO RECURSIVO. */
    private String construye(BufferedImage img, int x, int y, int ancho, 
            int alto, int tot) {
        /* Obtenemos el color de cada región con el filtro mosaico. */
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = x; i < ancho; i++) {
            for (int j = y; j < alto; j++) {
                Color aux = new Color(img.getRGB(i, j));
                r += aux.getRed();
                g += aux.getGreen();
                b += aux.getBlue();
            }
        }
        r /= tot;
        g /= tot;
        b /= tot;
        /* Guardamos cada imagen con el filtro rgb aplicado, el color será,
         claramente el valor que debería ir en el mosaico. Las imagenes se
         guardarán con un nombre único (para evitar repeticiones), en este 
         caso será el valor RGB correspondiente. Por ejemplo, una imagen cuyo 
         RGB sea 0, 125, 30 se guardará como 000125030.jpg.*/
        String id = String.format("%s%s%s", getID(r), getID(g), getID(b));
        BufferedImage nueva = copia();
        Procesador p = new Procesador(nueva);
        nueva = p.rgb(r, g, b); // aplicamos la mica
        /* Guardamos el archivo. */
        try {
            File guarda = new File(String.format("imagenes/%s.jpg", id));
            ImageIO.write(nueva, "jpg", guarda);
        } catch (IOException ioe) {
        }
        imagen = img;
        return String.format("\t\t<nobr><td><img src=\"%s.jpg\" width=\"20\", height=\"20\"></td></nobr>\n", 
                id);
    }

    /* Método auxiliar para guardar las imágenes. */
    private String getID(int id) {
        if (id > 99) { // números de tres cifras se quedan igual
            return "" + id;
        }
        if (id > 9) { // decimas les agregamos un cero.
            return "0" + id;
        }
        // unidades necesitan dos ceros.
        return "00" + id;
    }

    /**
     * Método que genera fotomosaicos. Para ello selecciona una carpeta de donde
     * extraerá las fotos.
     *
     * @param ancho Ancho del mosaico.
     * @param alto Alto del mosaico.
     * @param carpeta Carpeta de donde se extraerán las imagenes.
     * @return Fotomosaico de la imagen.
     */
    public String fotoMosaicos(int ancho, int alto, File carpeta) {
        /* Obtenemos todos los archivos de la carpeta. */
        File[] archivos = carpeta.listFiles();
        /* Calculamos el color promedio de cada imagen para evitar el cálculo
         en repetidas ocaciones. */
        calculaDistancias(archivos);
        /* A PARTIR DE AQUÍ TODO ES IDÉNTICO A LOS FILTROS DE IMÁGENES
         RECURSIVAS. Las variaciones se comentarán en los métodos auxiliares.*/
        int height = imagen.getHeight() / alto;
        int width = imagen.getWidth() / ancho;
        String[][] tabla = 
                new String[imagen.getHeight() / alto][imagen.getWidth() / ancho];
        String cadena = 
                "<table border = \"0\" cellspacing=\"0\" cellpadding=\"0\"\n";
        int l = 0;
        for (int i = 0; i < imagen.getWidth(); i += ancho) {
            int k = 0;
            for (int j = 0; j < imagen.getHeight(); j += alto) {
                int x = i + ancho;
                int y = j + alto;
                int d1 = ancho;
                int d2 = alto;
                if (x > imagen.getWidth()) {
                    x = imagen.getWidth();
                    d1 = imagen.getWidth() - i;
                }
                if (y > imagen.getHeight()) {
                    y = imagen.getHeight();
                    d2 = imagen.getHeight() - j;
                }
                if (k >= height || l >= width) {
                    break;
                }
                /* Construimos la imagen. */
                tabla[k++][l] = construyeMosaicos(imagen, i, j, x, y, d1 * d2);
            }
            l++;
        }
        for (int i = 0; i < imagen.getHeight() / alto; i++) {
            cadena += "\t<tr>\n";
            for (int j = 0; j < imagen.getWidth() / ancho; j++) {
                cadena += tabla[i][j];
            }
            cadena += "</tr>";
        }
        cadena += "</table>";
        return cadena;
    }

    /* Método auxiliar que calcula la distancia de cada archivo. */
    private void calculaDistancias(File[] archivos) {
        /* Escogemos aquellos archivos que sean imágenes. Si se encontrase otra
         extensión, se agregará en futuras actualizaciones. */
        for (File f : archivos) {
            if (f.getName().contains(".JPG") || f.getName().contains(".jpg")
                    || f.getName().contains(".png") || f.getName().contains(".bmp")) {
                try {
                    BufferedImage aux = ImageIO.read(f);
                    /* Calculamos el color promedio de la imagen. */
                    calculaDistancias(f.getName(), aux);
                } catch (IOException ioe) {
                }
            }
        }
    }

    /* Método auxiliar que encuentra el valor promedio de la imagen. */
    private void calculaDistancias(String nombre, BufferedImage img) {
        /* Calculamos el valor promedio como en filtros anteriores. */
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color aux = new Color(img.getRGB(i, j));
                r += aux.getRed();
                g += aux.getGreen();
                b += aux.getBlue();
            }
        }
        r /= img.getWidth() * img.getHeight();
        g /= img.getWidth() * img.getHeight();
        b /= img.getWidth() * img.getHeight();
        /* Guadamos las componentes r,g y b. y el nombre de la imagen en una
         lista. */
        double[] cols = {r, g, b};
        imgs.add(new ImagenDistancia(nombre, cols));
    }

    /* Método auxiliar que genera el mosaico.*/
    private String construyeMosaicos(BufferedImage img, int x, int y, int ancho, 
            int alto, int tot) {
        /* Calcula el valor promedio de cada región como en filtros anteriores. */
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = x; i < ancho; i++) {
            for (int j = y; j < alto; j++) {
                Color aux = new Color(img.getRGB(i, j));
                r += aux.getRed();
                g += aux.getGreen();
                b += aux.getBlue();
            }
        }
        r /= tot;
        g /= tot;
        b /= tot;
        /* Busca la imagen con la distancia más corta. */
        String id = distanciaMosaicos(r, g, b);
        return String.format("\t\t<nobr><td><img src=\"%s\" width=\"20\", height=\"20\"></td></nobr>\n", 
                id);
    }

    /* Obtiene las imagen con el color más cercano. */
    private String distanciaMosaicos(double r, double g, double b) {
        /* Busca la distancia exactamente igual que en el de fotos recutsivas
         con web palette. La diferencia es que nos devuelve el nombre de la
         imagen. */
        double minimo = 1000000;
        String min_aux = "";
        int i;
        for (i = 0; i < imgs.size(); i++) {
            double[] a = imgs.get(i).distancias;
            double aux = distancia(r, a[0], g, a[1], b, a[2]);
            if (aux <= minimo) {
                minimo = aux;
                min_aux = imgs.get(i).nombre;
            }
        }
        return min_aux;
    }

    /**
     * Método que crea una imagen usando letras. La guarda como una tabla html.
     * (En formato cadena).
     *
     * @param ancho Ancho del mosaico.
     * @param alto Alto del mosaico.
     * @return Cadena con el html de la imagen de letras.
     */
    public String sopaLetras(int ancho, int alto) {
        /* TODO ES IGUAL AL FILTRO MOSAICO Y A LAS VARIACIONES DE FOTOS 
         RECURSIVAS. Los cambios se comentarán.*/
        int height = imagen.getHeight() / alto;
        int width = imagen.getWidth() / ancho;
        String[][] tabla = new String[height][width];
        String cadena = "<table border = \"0\" cellspacing=\"0\" cellpadding=\"0\"\n";
        int l = 0;
        for (int i = 0; i < imagen.getWidth(); i += ancho) {
            int k = 0;
            for (int j = 0; j < imagen.getHeight(); j += alto) {
                int x = i + ancho;
                int y = j + alto;
                int d1 = ancho;
                int d2 = alto;
                if (x > imagen.getWidth()) {
                    x = imagen.getWidth();
                    d1 = imagen.getWidth() - i;
                }
                if (y > imagen.getHeight()) {
                    y = imagen.getHeight();
                    d2 = imagen.getHeight() - j;
                }
                if (k >= height || l >= width) {
                    break;
                }
                tabla[k++][l] = construyeLetras(imagen, i, j, x, y, d1 * d2);
            }
            l++;
        }
        for (int i = 0; i < height; i++) {
            cadena += "\t<tr>\n";
            for (int j = 0; j < width; j++) {
                cadena += tabla[i][j];
            }
            cadena += "</tr>";
        }
        cadena += "</table>";
        return cadena;
    }

    /* Método auxiliar que pone una letra m del color indicado. */
    private String construyeLetras(BufferedImage img, int x, int y, 
            int ancho, int alto, int tot) {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = x; i < ancho; i++) {
            for (int j = y; j < alto; j++) {
                Color aux = new Color(img.getRGB(i, j));
                r += aux.getRed();
                g += aux.getGreen();
                b += aux.getBlue();
            }
        }
        r /= tot;
        g /= tot;
        b /= tot;
        /* CREAMOS EL COLOR DE LA LETRA EN HEXADECIMAL, PUES LOS EXPLORADORES
         TIENEN PROBLEMAS AL USAR RGB. Notemos que se usa la letra M porque
         ocupa más espacio que cualquier otra. */
        String hex = String.format("#%02x%02x%02x", r, g, b); // convierte el color a hexadecimal
        return String.format("\t\t<nobr><td><b><font size=\"1\" color=\"%s\">M</font></b></td></nobr>\n", 
                hex);
    }

    /**
     * Método que crea una con el logo de hombres de negro. La guarda como una
     * tabla html. (En formato cadena).
     * @param ancho Ancho del mosaico.
     * @param alto Alto del mosaico.
     * @return Cadena con el html de la imagen recursiva.
     */
    public String menInBlack(int ancho, int alto) {
        /* TODO ES IGUAL A FILTROS ANTERIORES. Los cambios se comentarán. */
        /* Pasamos la imagen a tonos de gris. */
        imagen = grisesAverage();
        int height = imagen.getHeight() / alto;
        int width = imagen.getWidth() / ancho;
        String[][] tabla = 
                new String[imagen.getHeight() / alto][imagen.getWidth() / ancho];
        String cadena = 
                "<table border = \"0\" cellspacing=\"10\" cellpadding=\"0\"\n";
        int l = 0;
        for (int i = 0; i < imagen.getWidth(); i += ancho) {
            int k = 0;
            for (int j = 0; j < imagen.getHeight(); j += alto) {
                int x = i + ancho;
                int y = j + alto;
                int d1 = ancho;
                int d2 = alto;
                if (x > imagen.getWidth()) {
                    x = imagen.getWidth();
                    d1 = imagen.getWidth() - i;
                }
                if (y > imagen.getHeight()) {
                    y = imagen.getHeight();
                    d2 = imagen.getHeight() - j;
                }
                if (k >= height || l >= width) {
                    break;
                }
                tabla[k++][l] = mib(imagen, i, j, x, y, d1 * d2);
            }
            l++;
        }
        for (int i = 0; i < imagen.getHeight() / alto; i++) {
            cadena += "\t<tr>\n";
            for (int j = 0; j < imagen.getWidth() / ancho; j++) {
                cadena += tabla[i][j];
            }
            cadena += "</tr>";
        }
        cadena += "</table>";
        return cadena;
    }

    /* Método auxiliar que pone el logo de mib correspondiente. */
    private String mib(BufferedImage img, int x, int y, int ancho, int alto, 
            int tot) {
        int r = 0;
        for (int i = x; i < ancho; i++) {
            for (int j = y; j < alto; j++) {
                Color aux = new Color(img.getRGB(i, j));
                r += aux.getRed();
            }
        }
        r /= tot;
        int id = 0;
        /* SELECCIONAMOS EL COLOR MÁS ADECUADO, DE ACUERDO A SU TONO DE GRIS. */
        if (r >= 0 && r <= 27) // 1.jpg
        {
            id = 1;
        }
        if (r >= 28 && r <= 55) // 2.jpg ...
        {
            id = 2;
        }
        if (r >= 55 && r <= 83) {
            id = 3;
        }
        if (r >= 84 && r <= 111) {
            id = 4;
        }
        if (r >= 112 && r <= 139) {
            id = 5;
        }
        if (r >= 140 && r <= 167) {
            id = 6;
        }
        if (r >= 168 && r <= 195) {
            id = 7;
        }
        if (r >= 196 && r <= 223) {
            id = 8;
        }
        if (r >= 224) {
            id = 9;
        }
        return String.format("\t\t<nobr><td><img src=\"%d.jpg\" width=\"20\", height=\"20\"></td></nobr>\n", id);
    }

    /**
     * Método que comprime una imagen sin pérdida de información. (Losseless).
     * @param ruta Ruta donde guardar el archivo.
     */
    public void comprimeLosseless(String ruta) {
        try {
            FileWriter fw = new FileWriter(new File(ruta));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(imagen.getWidth() + " " + imagen.getHeight() + "\n");
            bw.flush();
            for (int i = 0; i < imagen.getWidth(); i++) {
                for (int j = 0; j < imagen.getHeight(); j++) {
                    Color aux = new Color(imagen.getRGB(i, j));
                    int promedio = (int)((aux.getRed() + aux.getGreen() + aux.getBlue()) / 3);
                    bw.write(Integer.toHexString(promedio) + " ");
                    bw.flush();
                }
                bw.write("\n");
                bw.flush();
            }
            
        } catch (IOException e) { }
    }
    
    /**
     * Método que comprime una imagen con pérdida de información. (Lossy).
     * @param ruta Ruta donde guardar el archivo.
     * @param color Color escogido por el usuario. 0 para rojo, 1 para verde y
     * 2 para azul.
     */
    public void comprimeLossy(String ruta, int color) {
        try {
            FileWriter fw = new FileWriter(new File(ruta));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(imagen.getWidth() + " " + imagen.getHeight() + "\n");
            bw.flush();
            for (int i = 0; i < imagen.getWidth(); i++) {
                for (int j = 0; j < imagen.getHeight(); j++) {
                    Color aux = new Color(imagen.getRGB(i, j));
                    int promedio = (int)((aux.getRed() + aux.getGreen() + aux.getBlue()) / 3);
                    if (color == 0) {
                        bw.write(Integer.toHexString(aux.getRed()) + " ");
                        bw.flush();
                    }
                    if (color == 1) {
                        bw.write(Integer.toHexString(aux.getGreen()) + " ");
                        bw.flush();
                    }
                    if (color == 2) {
                        bw.write(Integer.toHexString(aux.getBlue()) + " ");
                        bw.flush();
                    }
                }
                bw.write("\n");
                bw.flush();
            }
            
        } catch (IOException e) { }
    }

    /**
     * Metodo que descomprime una imagen.
     * @param archivo Archivo a descomprimir
     * @return Imagen descomprimida.
     */
    public BufferedImage descomprime(File archivo) {
        if (archivo == null)
            return null;
        BufferedImage nueva = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            StringTokenizer tk = new StringTokenizer(br.readLine());
            int ancho = Integer.parseInt(tk.nextToken());
            int alto = Integer.parseInt(tk.nextToken());
            nueva = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
            int j = 1;
            for (int i = 0; i < ancho; i++) {
                String linea = br.readLine();
                if (linea == null)
                    continue;
                tk = new StringTokenizer(linea);
                int contador = 0;
                while (tk.hasMoreTokens()) {
                    int color = Integer.parseInt(tk.nextToken(), 16);
                    nueva.setRGB(i, contador++, new Color(color, color, color).getRGB());
                }
                System.out.println(j++);
            }
        } catch(FileNotFoundException e) { 
        } catch(IOException e) { }
        return nueva;
    }

    /* AUXILIAR PARA DIVERSOS FILTROS. */
    private int acota(int x) {
        if (x < 0)
            return 0;
        if (x > 255)
            return 255;
        return x;
    }

    /* Obtiene el máximo de tres números */
    private int getMax(int a, int b, int c) {
        int maximo = a;
        if (b > maximo)
            maximo = b;
        if (c > maximo)
            maximo = c;
        return maximo;
    }

    /* Obtiene el mínimo de tres números */
    private int getMin(int a, int b, int c) {
        int minimo = a;
        if (b < minimo)
            minimo = b;
        if (c < minimo)
            minimo = c;
        return minimo;
    }
}