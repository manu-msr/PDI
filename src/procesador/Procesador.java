package procesador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Universidad Nacional Autónoma de México
 * Facultad de Ciencias - Licenciatura en Ciencias de la Computación
 * PROCESO DIGITAL DE IMÁGENES 2016-1
 * Profesor:    Manuel Cristóbal López Michelone
 * Ayudante:    Yessica Martínez Reyes
 * Laboratorio: César Hernánddez Solis
 * 
 * SOTO ROMERO MANUEL 310204675
 * 
 * Clase para manejar un Procesador de Imágenes. 
 * Este procesador de imágenes contiene operaciones para abrir y guardar una 
 * imagen y agregar los siguientes filtros:
 */
public class Procesador {
    
    /* Imagen con la que trabajará el procesador */
    private BufferedImage imagen;
    /* Imagen de archivo para recuperar la imagen original */
    private BufferedImage original;
    /* Lista que guarda las imágenes para imágenes recursivas. */
    private LinkedList<String> gama;
    
    public static final int BLACK = 0;
    public static final int COLOR = 1;
    public static final int SUP_IZQ = 1;
    public static final int SUP_DER = 2;
    public static final int INF_IZQ = 3;
    public static final int INF_DER = 4;
    
    public Procesador(BufferedImage imagen) {
        this.imagen = imagen;
        gama = new LinkedList<>();
    }
    
    public Procesador() {
        imagen = null;
        original = null;
        gama = new LinkedList<>();
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
        FileNameExtensionFilter filtro = 
                new FileNameExtensionFilter("jpg, jpeg, bmp, png", "jpg", "jpeg", "bmp", "png");
        selector.setFileFilter(filtro);
        int aux = selector.showOpenDialog(null);
        if (aux == JFileChooser.APPROVE_OPTION) {//si el usuario presiona aceptar
            try {
                /* Obtenemos la imagen seleccionada */
                File archivo = selector.getSelectedFile();
                original = ImageIO.read(archivo);
            } catch (IOException e) {
                
            }
        }
        return original;
    }
    
    /**
     * Crea una copia de la imagen original.
     * Se crea una copia de la imagen original para trabajar con ella. Esto con
     * el fin de poder reiniciar la imagen.
     * @return BufferedImage copia
     */
    public BufferedImage copia() {
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
        } catch(IOException e) {
            
        }
    }
    
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
        } catch(IOException e) {
            
        }
    }
    
    /**
     * Método que aplica un filtro rojo a la imagen. Para ello deja el color
     * rojo igual y establece el verde y azul en cero.
     * @return Imagen con el filtro rojo.
     */
    public BufferedImage rojo() {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Optiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Crea el nuevo color quitando el verde y azul */
                Color color_nuevo = new Color(aux.getRed(), 0, 0);
                /* Establece el nuevo color al pixel */
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        return imagen;
    }
    
    /**
     * Método que aplica un filtro verde a la imagen. Para ello deja el color
     * rojo igual y establece el rojo y azul en cero.
     * @return Imagen con el filtro rojo.
     */
    public BufferedImage verde() {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Optiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Crea el nuevo color quitando el rojo y azul */
                Color color_nuevo = new Color(0, aux.getGreen(), 0);
                /* Establece el nuevo color al pixel */
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        return imagen;
    }
    
    /**
     * Método que aplica un filtro azul a la imagen. Para ello deja el color
     * rojo igual y establece el rojo y verde en cero.
     * @return Imagen con el filtro rojo.
     */
    public BufferedImage azul() {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Optiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Crea el nuevo color quitando el rojo y verde */
                Color color_nuevo = new Color(0, 0, aux.getBlue());
                /* Establece el nuevo color al pixel */
                imagen.setRGB(i, j, color_nuevo.getRGB());
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
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Optiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                int nr = aux.getRed() + r;
                int ng = aux.getGreen() + g;
                int nb = aux.getBlue() + b;
                nr = acota(nr);
                ng = acota(ng);
                nb = acota(nb);
                Color color_nuevo = new Color(nr, ng, nb);
                imagen.setRGB(i, j, color_nuevo.getRGB());
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
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Optiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                int r = aux.getRed() + k;
                int g = aux.getGreen() + k;
                int b = aux.getBlue() + k;
                r = acota(r);
                g = acota(g);
                b = acota(b);
                Color color_nuevo = new Color(r, g, b);
                imagen.setRGB(i, j, color_nuevo.getRGB());
            }
        return imagen;
    }
    
    /**
     * Método que modifica aplica un filtro de azar.
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage azar() {
        Random random = new Random();
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                Color aux = new Color(imagen.getRGB(i, j));
                int n = random.nextInt(3);
                Color nuevo = aux;
                if (n == 0)
                    nuevo = new Color(aux.getRed(), 0, 0);
                if (n == 1)
                    nuevo = new Color(0, aux.getRed(), 0);
                if (n == 2)
                    nuevo = new Color(0, 0, aux.getBlue());
                imagen.setRGB(i, j, nuevo.getRGB());
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
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Obtiene el promedio de las componentes */
                int promedio = 
                        (aux.getRed() + aux.getGreen() + aux.getBlue()) / 3;
                /* Crea el nuevo color */
                Color color_nuevo = new Color(promedio, promedio, promedio);
                /* Establece el nuevo color al pixe */
                imagen.setRGB(i, j, color_nuevo.getRGB());
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
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Calcula la luminosidad */
                int gris = (int)(aux.getRed() * 0.3 + aux.getGreen() * 0.59 
                        + aux.getBlue() * 0.11);
                /* Crea el nuevo color */
                Color color_nuevo = new Color(gris, gris, gris);
                /* Establece el nuevo color al pixe */
                imagen.setRGB(i, j, color_nuevo.getRGB());
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
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Calcula la desaturación */
                int gris = (getMax(aux.getRed(), aux.getGreen(), aux.getBlue()) 
                        + getMin(aux.getRed(), aux.getGreen(), aux.getBlue())) / 2;
                /* Crea el nuevo color */
                Color color_nuevo = new Color(gris, gris, gris);
                /* Establece el nuevo color al pixe */
                imagen.setRGB(i, j, color_nuevo.getRGB());
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
        for (int i = 0; i < imagen.getWidth(); i++)
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
        return imagen;
    }
    
    /**
     * Método que aplica un filtro de tonos de gris. Para ello utiliza un número
     * de color pasado como parámetro.
     * @param singleColor Color para el tono de gris (0 - rojo, 1 - verde,
     * 2 - azul).
     * @return Imagen con el filtro de escala de grises.
     */
    public BufferedImage grisesSingleColor(int singleColor) {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Crea el nuevo color */
                Color color_nuevo;
                if (singleColor == 0)
                    color_nuevo = new Color(aux.getRed(), aux.getRed(), aux.getRed());
                else if (singleColor == 1)
                    color_nuevo = new Color(aux.getGreen(), aux.getGreen(), aux.getGreen());
                else
                    color_nuevo = new Color(aux.getBlue(), aux.getBlue(), aux.getBlue());
                /* Establece el nuevo color al pixel */
                imagen.setRGB(i, j, color_nuevo.getRGB());
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
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                int factorDeConversion = 255 / (shades - 1);
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Promedio */
                int promedio = (aux.getRed() + aux.getGreen() + aux.getBlue()) / 3;
                /* Gris */
                int gris = (int)(((promedio / factorDeConversion) + 0.5) * 
                        factorDeConversion);
                /* Crea el nuevo color */
                Color color_nuevo = new Color(gris, gris, gris);
                /* Establece el nuevo color al pixe */
                imagen.setRGB(i, j, color_nuevo.getRGB());
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
            int error = 0;
            for (int j = 0; j < imagen.getHeight(); j++) {
                int factorDeConversion = 255 / (shades - 1);
                Color aux = new Color(imagen.getRGB(i, j));
                int promedio = (aux.getRed() + aux.getGreen() + aux.getBlue()) / 3;
                int grayTempCalc = promedio;
                grayTempCalc += error;
                grayTempCalc = (int)(((grayTempCalc / factorDeConversion) + 0.5) 
                        * factorDeConversion);
                error = promedio + error - grayTempCalc;
                promedio = grayTempCalc;
                promedio = acota(promedio);
                Color nuevo = new Color(promedio,promedio,promedio);
                imagen.setRGB(i, j, nuevo.getRGB());
            }
        }
        return imagen;
    }
    
    /**
     * Método que aplica el filtro de convolución a una imagen.
     * @param mat Matriz para aplicar el filtro.
     * @return Imagen con el filtro.
     */
    public BufferedImage convolucion(double[][] mat) {
        int k = mat.length;
        BufferedImage enmarcada = enmarca(imagen, k/2, k/2);
        WritableRaster img = enmarcada.getRaster();
        int n = enmarcada.getWidth();
        int m = enmarcada.getHeight();
        WritableRaster nue = enmarcada.getRaster();
        if (n  < k || m < k)
            return imagen;
        double factor = suma(mat);
        for (int i = 0; i < n-k; i++) {
            for (int j = 0; j < m-k; j++) {
                int r = 0;
                int g = 0;
                int b = 0;
                for (int x = i; x < i+k; x++) {
                    for (int y = j; y < j+k; y++) {
                        r += img.getSample(x, y, 0)*mat[x-i][y-j];
                        g += img.getSample(x, y, 1)*mat[x-i][y-j];
                        b += img.getSample(x, y, 2)*mat[x-i][y-j];
                    }
                }
                if (factor != 0) {
                    r /= Math.abs(factor);
                    g /= Math.abs(factor);
                    b /= Math.abs(factor);
                }
                r = acota(r);
                g = acota(g);
                b = acota(b);
                nue.setSample(i+k/2, j+k/2, 0, r);
                nue.setSample(i+k/2, j+k/2, 1, g);
                nue.setSample(i+k/2, j+k/2, 2, b);
            }
        }
        BufferedImage nueva = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        nueva.setData(nue);
        imagen = desenmarca(nueva,k/2,k/2);
        return imagen;
    }
    
    /* AUXILIAR PARA FILTRO CONVOLUCIÓN */
    private BufferedImage enmarca(BufferedImage imagen, int alto, int ancho) {
        int w = imagen.getWidth();
        int h = imagen.getHeight();
        BufferedImage nueva = new BufferedImage(w + 2 * alto, h + 2 * ancho, BufferedImage.TYPE_INT_RGB);
        WritableRaster img = imagen.getRaster();
        WritableRaster wr = nueva.getRaster();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int r = img.getSample(i, j, 0);
                int g = img.getSample(i, j, 1);
                int b = img.getSample(i, j, 2);
                wr.setSample(i + alto, j + ancho, 0, r);
                wr.setSample(i + alto, j + ancho, 1, g);
                wr.setSample(i + alto, j + ancho, 2, b);
            }
        }
        nueva.setData(wr);
        return nueva;
    }
    
    /* AUXILIAR PARA FILTRO CONVOLUCIÓN. */
    private double suma(double[][] mat) {
        double suma = 0;
        for (double[] mat1 : mat) {
            for (int j = 0; j < mat[0].length; j++) {
                suma += mat1[j];
            }
        }
        return suma;
    }
    
    /* AUXILIAR PARA CONVOLUCIÓN. */
    private BufferedImage desenmarca(BufferedImage imagen, int ancho, int alto) {
        int w = imagen.getWidth();
        int h = imagen.getHeight();
        return imagen.getSubimage(ancho, alto, w - 2 * ancho, h - 2 * alto);
    }
    
    /**
     * Método que combina dos imágenes. Para ello utiliza un porcentaje de
     * transparencia.
     * @param otra Imagen a combinar.
     * @param alpha Porcentaje de transparencia.
     * @return Imagen combinada con la 'otra' imagen.
     */
    public BufferedImage blending(BufferedImage otra, double alpha) {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual de cada imagen */
                Color aux1 = new Color(imagen.getRGB(i, j));
                Color aux2 = new Color(otra.getRGB(i, j));
                /* Color en rojo. */
                int br = (int)((aux1.getRed() * alpha) + (aux2.getRed() * (1.0 - alpha)));
                /* Color en verde. */
                int bg = (int)((aux1.getGreen() * alpha) + (aux2.getGreen() * (1.0 - alpha)));
                /* Color en azul. */
                int bb = (int)((aux1.getBlue() * alpha) + (aux2.getBlue() * (1.0 - alpha)));
                Color nuevo = new Color(br, bg, bb);
                /* Establece el nuevo color al pixe */
                imagen.setRGB(i, j, nuevo.getRGB());
            }
        return imagen;
    }
    
    /**
     * Método que aplica un filto de luz negra a la imagen.
     * @return BufferedImage - Imagen con el filtro.
     */
    public BufferedImage blacklight() {
        /* Recorremos la imagen pixel por pixel. */
        for (int i = 0; i < imagen.getWidth(); i++)
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
        for (int i = 0; i < copia.getWidth(); i++)
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
        return imagen;

    }
    
    /**
     * Método que rota una imagen 0, 90, 180 o 270 grados.
     * @param grados Grados a rotar.
     * @return BufferedImage - Imagen rotada.
     */
    public BufferedImage rota(int grados) {
        /* Creamos una copia de la imagen. */
        BufferedImage nueva = imagen;
        /* Si grados es 0 o 180 la imagen queda vertical. */
        if (grados == 0 || grados == 180)
            nueva = new BufferedImage(imagen.getWidth(), imagen.getHeight(), 
                    imagen.getType());
        /* Si grados es 90 o 270 la imagen queda horizontal. */
        if (grados == 90 || grados == 270)
            nueva = new BufferedImage(imagen.getHeight(), imagen.getWidth(), 
                    imagen.getType());
        /* Recorremos la imagen pixel por pixel. */
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                switch(grados) {
                    case 0: // si grados = 0
                        nueva.setRGB(i, j, imagen.getRGB(i, j));
                        break;
                    case 90: // si grados = 90
                        nueva.setRGB(j, imagen.getWidth()-i-1, 
                                imagen.getRGB(i, j));
                        break;
                    case 180: // si grados = 180
                        nueva.setRGB(imagen.getWidth()-i-1, 
                                imagen.getHeight()-j-1, imagen.getRGB(i, j));
                        break;
                    case 270: // si grados = 270
                        nueva.setRGB(imagen.getHeight()-j-1, i, 
                                imagen.getRGB(i, j));
                        break;
                }
            }
        imagen = nueva;
        return imagen;
    }
    
    /**
     * Método que rota una imagen 0, 90, 180 o 270 grados. Para hacer la rotación
     * usa una matriz de rotación.
     * @param grados Grados a rotar.
     * @return BufferedImage - Imagen rotada.
     */
    public BufferedImage rota(double grados) {
        /* Creamos una copia de la imagen. */
        BufferedImage nueva = imagen;
        /* Si grados es 0 o 180 la imagen queda vertical. */
        if (grados == 0 || grados == 180)
            nueva = new BufferedImage(imagen.getWidth(), imagen.getHeight(), 
                    imagen.getType());
        /* Si grados es 90 o 270 la imagen queda horizontal. */
        if (grados == 90 || grados == 270)
            nueva = new BufferedImage(imagen.getHeight(), imagen.getWidth(), 
                    imagen.getType());
        /* Recorremos la imagen pixel por pixel. */
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                nueva.setRGB(matrizX(i,j,grados), matrizY(i,j,grados)+imagen.getHeight()-1, imagen.getRGB(i, j));
            }
        imagen = nueva;
        return imagen;
    }
    
    private int matrizX(double i, double j, double angulo) {
        return (int)(i * Math.cos(angulo) + j * Math.sin(angulo));
    }
    
    private int matrizY(double i, double j, double angulo) {
        return (int)(-i * Math.sin(angulo) + j * Math.cos(angulo));
    }
    
    /**
     * Método que aplica el filtro inverso a una imagen.
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage filtroInverso() {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++)
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
        return imagen;
    }
    
    /**
     * Método que aplica el filtro de alto constraste a una imagen.
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage altoContraste() {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++)
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
        return imagen;
    }
    
    /**
     * Método que aplica el filtro AT&T a una imagen.
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage att() {
        BufferedImage ac = altoContraste();
        int w = ac.getWidth();
        int h = ac.getHeight();
        Raster rac = ac.getData();
        BufferedImage nueva = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        WritableRaster wrn = nueva.getRaster();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h - 12; j += 12) {
                int puntos = 0;
                for (int y = j; y < j + 12; y++) {
                    if (rac.getSample(i, y, 0) == 0) {
                        puntos++;
                    }
                }
                boolean[] acomodados = centra(12, puntos);
                for (int y = j; y < j + 12; y++) {
                    if (acomodados[y-j]) {
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
            for (int j = 0; j < wr.getHeight()-12; j+=12) {
                wr.setSample(i, j, 0, 0xff);
                wr.setSample(i, j, 1, 0xff);
                wr.setSample(i, j, 2, 0xff);
            }
        }
    }
    
    /**
     * Método que aplica pone una marca de agua a una imagen.
     * @param s Mensaje a mostrar.
     * @param region Región donde se mostrará el mensaje.
     * @param tipo Indica si el texto es de color o negro.
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage marcaAgua(String s, int region, int tipo) {
        BufferedImage nueva = copia();
        int w = nueva.getWidth();
        int h = nueva.getHeight();
        int size = (w / s.length()) / 2;
        Graphics g = nueva.getGraphics();
        Font f = new Font(Font.SANS_SERIF, Font.PLAIN, size);
        g.setFont(f);
        if (tipo == COLOR) {
            g.setColor(Color.GREEN);
        } else {
            if (tipo == BLACK) {
                g.setColor(Color.BLACK);
            }
        }
        switch(region) {
            case SUP_IZQ:
                g.drawString(s, 0, size);
                break;
            case SUP_DER:
                g.drawString(s, w-s.length()*size, size);
                break;
            case INF_IZQ:
                g.drawString(s, 0, h-size);
                break;
            case INF_DER:
                g.drawString(s, w-s.length()*size, h-size);
        }
        g.dispose();
        imagen = copia();
        imagen = blending(nueva, 0.7);
        return imagen;
    }
    
    /**
     * Método que aplica el filtro mosaico a una imagen.
     * @param ancho Ancho del mosaico.
     * @param alto Alto del mosaico.
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage mosaico(int ancho, int alto) {
        int id = 0;
        for (int i = 0; i < imagen.getWidth(); i+=ancho) {
            for (int j = 0;  j < imagen.getHeight(); j+=alto) {
                int x = i + ancho;
                int y = j + alto;
                int d1 = ancho;
                int d2 = alto;
                if (x > imagen.getWidth()) {
                    x = imagen.getWidth();
                    d1 = imagen.getWidth()-i;
                }
                if (y > imagen.getHeight()) {
                    y = imagen.getHeight();
                    d2 = imagen.getHeight()-j;
                }
                pinta(imagen, i, j, x, y, d1*d2);
            }
        }
        return imagen;
    }
    
    /* AUXILIAR PARA FILTRO MOSAICO. */
    private void pinta(BufferedImage img, int x, int y, int ancho, int alto, int tot) {
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
        for (int i = x; i < ancho; i++) {
            for (int j = y; j < alto; j++) {
                img.setRGB(i, j, new Color(r, g, b).getRGB());
            }
        }
    }
    
    /**
     * Método que convierte una imagen en icono.
     * @param ancho Ancho del ícono.
     * @param alto Alto del ícono.
     * @return Icono de la imagen.
     */
    public BufferedImage icono(int ancho, int alto) {
        int tam_pix_x = imagen.getWidth() / ancho;
        int tam_pix_y = imagen.getHeight() / alto;
        BufferedImage mosaico = mosaico(tam_pix_x, tam_pix_y);
        Image icono = mosaico.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        BufferedImage bi = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        bi.getGraphics().drawImage(icono, 0, 0, null);
        imagen = bi;
        return imagen;
    }
    
    /**
     * Método que aplica el filtro de semitonos a una imagen.
     * @param umbral Umbral para aplicar el filtro.
     * @return Imagen con el filtro aplicado.
     */
    public BufferedImage semitonos(int umbral) {
        /* Recorremos la imagen pixel por pixel */
        for (int i = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                /* Obtiene el color del pixel actual */
                Color aux = new Color(imagen.getRGB(i, j));
                /* Promedio */
                int promedio = (aux.getRed() + aux.getGreen() + aux.getBlue()) / 3;
                /* Si los tonos son mayores al ummbral ponemos un punto blanco */
                if (promedio > umbral)
                    aux = new Color(255, 255, 255);
                /* Si los tonos son menores o iguales al umbral ponemos un punto negro */
                else
                    aux = new Color(0, 0, 0);
                /* Establece el nuevo color al pixel */
                imagen.setRGB(i, j, aux.getRGB());
            }
        return imagen;
    }
    
    /**
     * Método que crea una imagen recursiva. La guarda como una tabla html. (En
     * formato cadena).
     * @param ancho Ancho del mosaico.
     * @param alto Alto del mosaico.
     * @return Cadena con el html de la imagen recursiva.
     */
    public String recursiva(int ancho, int alto) {
        int height = imagen.getHeight() / alto;
        int width = imagen.getWidth() / ancho;
        String[][] tabla = new String[imagen.getHeight()/alto][imagen.getWidth()/ancho];
        String cadena = "<table border = \"0\" cellspacing=\"0\" cellpadding=\"0\"\n";
        int l = 0;
        for (int i = 0; i < imagen.getWidth(); i+=ancho) {
            int k = 0;
            for (int j = 0;  j < imagen.getHeight(); j+=alto) {
                int x = i + ancho;
                int y = j + alto;
                int d1 = ancho;
                int d2 = alto;
                if (x > imagen.getWidth()) {
                    x = imagen.getWidth();
                    d1 = imagen.getWidth()-i;
                }
                if (y > imagen.getHeight()) {
                    y = imagen.getHeight();
                    d2 = imagen.getHeight()-j;
                }
                if (k >= height || l >= width)
                    break;
                tabla[k++][l] = construye(imagen, i, j, x, y, d1*d2);
            }
            l++;
        }
        for (int i = 0; i < imagen.getHeight()/alto; i++) {
            cadena += "\t<tr>\n";
            for (int j = 0; j < imagen.getWidth()/ancho; j++) {
                cadena += tabla[i][j];
            }
             cadena += "</tr>";
        }
        cadena += "</table>";
        return cadena;
    }
    
    /* AUXILIAR PARA FILTRO MOSAICO. */
    private String construye(BufferedImage img, int x, int y, int ancho, int alto, int tot) {
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
        String id = String.format("%s%s%s", getID(r), getID(g), getID(b));
        BufferedImage nueva = copia();
        Procesador p = new Procesador(nueva);
        nueva = p.rgb(r, g, b);
        try {
            File guarda = new File(String.format("imagenes/%s.jpg", id));
            ImageIO.write(nueva, "jpg", guarda);
        } catch (IOException ioe) { }
        imagen = img;
        return String.format("\t\t<nobr><td><img src=\"%s.jpg\" width=\"20\", height=\"20\"></td></nobr>\n", id);
    }
    
    private String getID(int id) {
        if (id > 99) {
            return "" + id;
        }
        if (id > 9) {
            return "0" + id;
        }
        return "00" + id;
    }
    
    public String sopaLetras(int ancho, int alto) {
        int height = imagen.getHeight() / alto;
        int width = imagen.getWidth() / ancho;
        String[][] tabla = new String[height][width];
        String cadena = "<table border = \"0\" cellspacing=\"0\" cellpadding=\"0\"\n";
        int l = 0;
        for (int i = 0; i < imagen.getWidth(); i+=ancho) {
            int k = 0;
            for (int j = 0;  j < imagen.getHeight(); j+=alto) {
                int x = i + ancho;
                int y = j + alto;
                int d1 = ancho;
                int d2 = alto;
                if (x > imagen.getWidth()) {
                    x = imagen.getWidth();
                    d1 = imagen.getWidth()-i;
                }
                if (y > imagen.getHeight()) {
                    y = imagen.getHeight();
                    d2 = imagen.getHeight()-j;
                }
                if (k >= height || l >= width)
                    break;
                tabla[k++][l] = construyeLetras(imagen, i, j, x, y, d1*d2);
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
    
        
    private String construyeLetras(BufferedImage img, int x, int y, int ancho, int alto, int tot) {
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
        String hex = String.format("#%02x%02x%02x", r, g, b); // convierte el color a hexadecimal
        return String.format("\t\t<nobr><td><b><font size=\"1\" color=\"%s\">M</font></b></td></nobr>\n", hex);
    }
    
    public String menInBlack(int ancho, int alto) {
        imagen = grisesAverage();
        int height = imagen.getHeight() / alto;
        int width = imagen.getWidth() / ancho;
        String[][] tabla = new String[imagen.getHeight()/alto][imagen.getWidth()/ancho];
        String cadena = "<table border = \"0\" cellspacing=\"10\" cellpadding=\"0\"\n";
        int l = 0;
        for (int i = 0; i < imagen.getWidth(); i+=ancho) {
            int k = 0;
            for (int j = 0;  j < imagen.getHeight(); j+=alto) {
                int x = i + ancho;
                int y = j + alto;
                int d1 = ancho;
                int d2 = alto;
                if (x > imagen.getWidth()) {
                    x = imagen.getWidth();
                    d1 = imagen.getWidth()-i;
                }
                if (y > imagen.getHeight()) {
                    y = imagen.getHeight();
                    d2 = imagen.getHeight()-j;
                }
                if (k >= height || l >= width)
                    break;
                tabla[k++][l] = mib(imagen, i, j, x, y, d1*d2);
            }
            l++;
        }
        for (int i = 0; i < imagen.getHeight()/alto; i++) {
            cadena += "\t<tr>\n";
            for (int j = 0; j < imagen.getWidth()/ancho; j++) {
                cadena += tabla[i][j];
            }
             cadena += "</tr>";
        }
        cadena += "</table>";
        return cadena;
    }
    
    /* AUXILIAR PARA FILTRO MOSAICO. */
    private String mib(BufferedImage img, int x, int y, int ancho, int alto, int tot) {
        int r = 0;
        for (int i = x; i < ancho; i++) {
            for (int j = y; j < alto; j++) {
                Color aux = new Color(img.getRGB(i, j));
                r += aux.getRed();
            }
        }
        r /= tot;
        int id = 0;
        if (r >= 0 && r <= 27)
            id = 1;
        if (r >= 28 && r <= 55)
            id = 2;
        if (r >= 55 && r <= 83)
            id = 3;
        if (r >= 84 && r <= 111)
            id = 4;
        if (r >= 112 && r <= 139)
            id = 5;
        if (r >= 140 && r <= 167)
            id = 6;
        if (r >= 168 && r <= 195)
            id = 7;
        if (r >= 196 && r <= 223)
            id = 8;
        if (r >= 224)
            id = 9;
        System.out.println(":D");
        return String.format("\t\t<nobr><td><img src=\"%d.jpg\" width=\"20\", height=\"20\"></td></nobr>\n", id);
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
