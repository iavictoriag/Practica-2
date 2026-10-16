package com.mycompany._practica2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JuegoVentana extends JDialog {

    public JuegoVentana() {
        // la ventana
        setTitle("Quetzal Space Defender");
        setSize(850, 600);
        
        // pausa el Main hasta que se cierre el juego
        setModal(true);
        
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // panel del juego con los gráficos 
        PanelJuego panel = new PanelJuego();
        add(panel);
    }
}

// gráficos del juego
class PanelJuego extends JPanel implements ActionListener {
    
    // Variables 
    private Timer timer;
    private int naveX = 80;
    private int naveY = 250;
    private int puntaje = 0;
    private int vidas = 3;
    
    // Posición del enemigo 1
    private int enemigoX = 600;
    private int enemigoY = 250;
    
    // Posición del enemigo 2
    private int enemigo2X = 900;
    private int enemigo2Y = 380;
    
    // disparo
    private int disparoX = -100;
    private int disparoY = -100;
    private boolean disparoActivo = false;
    
    // El disparo con dificultad 
    private long ultimoDisparo = 0;
    private int intervalo = 1000; // 1 segundo es Normal
    
    // Premios y Obstáculos ---
    private int quaffleX = 1000, quaffleY = (int)(Math.random() * 400) + 120;
    private int snitchX = 1400, snitchY = (int)(Math.random() * 400) + 120;
    private int bludgerX = 1200, bludgerY = (int)(Math.random() * 400) + 120;
    
    private boolean naveBloqueada = false;
    private long tiempoBloqueo = 0;

    public PanelJuego() {
        // intervalo del piloto actual si existe
        if (Main.pilotoActual != null) {
            intervalo = Main.pilotoActual.getCadenciaDisparo();
        }

        // Fondo de color del juego
        setBackground(Color.BLACK);
        setFocusable(true);

        // Detectar las teclas 
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int tecla = e.getKeyCode();

                // Mover nave hacia arriba
                if (tecla == KeyEvent.VK_UP && naveY > 120) {
                    naveY -= 20;
                }
                // Mover nave hacia abajo
                if (tecla == KeyEvent.VK_DOWN && naveY < 500) {
                    naveY += 20;
                }
                // Disparar con la barra  espacio 
                if (tecla == KeyEvent.VK_SPACE) {
                    long tiempoActual = System.currentTimeMillis();
                    if (!disparoActivo && (tiempoActual - ultimoDisparo >= intervalo)) {
                        disparoX = naveX + 35; // desde la punta de la nave
                        disparoY = naveY;      // al centro de la nave
                        disparoActivo = true;
                        ultimoDisparo = tiempoActual; // Registramos el momento del disparo
                    }
                }
            }
        });

        // Timer cada 20 milisegundos
        timer = new Timer(20, this);
        timer.start();
    }

    // pantalla
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // texto en la parte superior 
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Quetzal Space Defender", 30, 40);
        g.drawString("Puntaje: " + puntaje, 30, 70);
        g.drawString("Vidas: " + vidas, 160, 70);
        
        if (Main.pilotoActual != null) {
            g.drawString("Piloto: " + Main.pilotoActual.getNombre() + " (" + Main.pilotoActual.getTipoNave() + ")", 300, 70);
        }
        
        g.drawString("↑ ↓ Mover   |   ESPACIO: Disparar", 30, 95);

        // Línea divisoria
        g.setColor(Color.GRAY);
        g.drawLine(30, 110, 800, 110);

        // laser
        if (disparoActivo) {
            g.setColor(Color.YELLOW);
            g.fillRect(disparoX, disparoY, 12, 4);
        }

        // la nave
        if (naveBloqueada) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.CYAN);
        }
        int[] xPuntos = {naveX, naveX + 35, naveX};
        int[] yPuntos = {naveY + 15, naveY, naveY - 15};
        g.fillPolygon(xPuntos, yPuntos, 3);

        // los enemigos 
        g.setColor(new Color(255, 0, 127)); 
        g.fillRect(enemigoX, enemigoY, 40, 40);
        g.fillRect(enemigo2X, enemigo2Y, 40, 40);
        
        // Quaffle 
        g.setColor(Color.YELLOW);
        g.fillOval(quaffleX, quaffleY, 20, 20);

        // Snitch  
        g.setColor(new Color(0, 255, 128));
        g.fillOval(snitchX, snitchY, 15, 15);

        g.setColor(Color.GRAY);
        g.fillOval(bludgerX, bludgerY, 30, 30);
        
        // las estrellas de fondo
        g.setColor(Color.WHITE); 
        g.fillRect(200, 150, 2, 2); g.fillRect(234, 765, 2, 3); g.fillRect(190, 431, 2, 3); g.fillRect(334, 121, 2, 2);
        g.fillRect(500, 380, 3, 3); g.fillRect(432, 567, 3, 2); g.fillRect(222, 315, 3, 2); g.fillRect(234, 113, 3, 3);
        g.fillRect(700, 180, 2, 2); g.fillRect(345, 654, 2, 3); g.fillRect(303, 143, 3, 2); g.fillRect(434, 131, 3, 2);
        g.fillRect(400, 250, 2, 3); g.fillRect(543, 456, 3, 2); g.fillRect(444, 416, 2, 3); g.fillRect(734, 146, 2, 3);
        g.fillRect(360, 480, 3, 2); g.fillRect(456, 543, 2, 3); g.fillRect(505, 651, 2, 3); g.fillRect(434, 519, 3, 2);
        g.fillRect(478, 155, 2, 3); g.fillRect(654, 345, 3, 2); g.fillRect(660, 517, 3, 2); g.fillRect(234, 131, 3, 3);
        g.fillRect(367, 255, 3, 2); g.fillRect(567, 432, 2, 3); g.fillRect(707, 165, 2, 2); g.fillRect(434, 143, 2, 2);
        g.fillRect(267, 355, 2, 3); g.fillRect(765, 234, 3, 2); g.fillRect(102, 311, 2, 3); g.fillRect(634, 512, 3, 3);
        g.fillRect(153, 455, 3, 2); g.fillRect(123, 321, 2, 3); g.fillRect(211, 112, 2, 3); g.fillRect(234, 631, 3, 2);
    }

    //  colisiones 
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // tiempo del bloqueo con Bludger
        if (naveBloqueada) {
            if (System.currentTimeMillis() - tiempoBloqueo > 2000) {
                naveBloqueada = false; // libera la nave a los 2 segundos
            }
        }
        
        // a la izquierda
        enemigoX -= 4; // enemigo 1
        enemigo2X -= 5; // enemigo 2
        quaffleX -= 3;
        snitchX -= 7;
        bludgerX -= 4;

        // reiniciar enemigo 1 si pasa de los bordes
        if (enemigoX < -50) {
            enemigoX = 850;
            enemigoY = (int) (Math.random() * 400) + 120;
            puntaje += 5;
        }
        // reiniciar enemigo 2 si pasa del borde
        if (enemigo2X < -50) {
            enemigo2X = 999;
            enemigo2Y = (int) (Math.random() * 400) + 120;
            puntaje += 5;
        }
        
        // Reiniciar Quaffle
        if (quaffleX < -50) {
            quaffleX = 950;
            quaffleY = (int) (Math.random() * 400) + 120;
        }

        // Reiniciar Snitch
        if (snitchX < -50) {
            snitchX = 1200;
            snitchY = (int) (Math.random() * 400) + 120;
        }

        // Reiniciar Bludger
        if (bludgerX < -50) {
            bludgerX = 1100;
            bludgerY = (int) (Math.random() * 400) + 120;
        }
        
        // Rectángulos del choque
        Rectangle rectNave = new Rectangle(naveX, naveY - 15, 35, 30);
        Rectangle rectObs1 = new Rectangle(enemigoX, enemigoY, 40, 40);
        Rectangle rectObs2 = new Rectangle(enemigo2X, enemigo2Y, 40, 40);
        Rectangle rectQuaffle = new Rectangle(quaffleX, quaffleY, 20, 20);
        Rectangle rectSnitch = new Rectangle(snitchX, snitchY, 15, 15);
        Rectangle rectBludger = new Rectangle(bludgerX, bludgerY, 30, 30);
        
        // Si choca con enemigo 1
        if (rectNave.intersects(rectObs1)) {
            vidas--;
            enemigoX = 850; 
            enemigoY = (int) (Math.random() * 400) + 120;
        }

        // Si choca con enemigo 2
        if (rectNave.intersects(rectObs2)) {
            vidas--;
            enemigo2X = 900;
            enemigo2Y = (int) (Math.random() * 400) + 120;
        }

        // Si choca con la Quaffle 
        if (rectNave.intersects(rectQuaffle)) {
            puntaje += 20;
            quaffleX = 1000;
            quaffleY = (int) (Math.random() * 400) + 120;
        }
        
        // Si choca con la Snitch 
        if (rectNave.intersects(rectSnitch)) {
            puntaje += 50;
            vidas++; // Vida extra 
            
            // Elimina y reinicia al enemigo 1
            enemigoX = 850;
            enemigoY = (int) (Math.random() * 400) + 120;
            puntaje += 15; // puntos extra 

            // Elimina y einicia al enemigo 2
            enemigo2X = 990;
            enemigo2Y = (int) (Math.random() * 400) + 120;
            puntaje += 15; // puntos extras

            // Reinicia el Snitch 
            snitchX = 1400;
            snitchY = (int) (Math.random() * 400) + 120;
        }

        // Si choca con la Bludger 
        if (rectNave.intersects(rectBludger)) {
            naveBloqueada = true;
            tiempoBloqueo = System.currentTimeMillis();
            bludgerX = 1200;
            bludgerY = (int) (Math.random() * 400) + 120;
        }
        
        // Muerte
        if (vidas <= 0) {
            timer.stop(); // Detiene el bucle del juego
    
            // guarda el punteo 
            if (Main.pilotoActual != null) {
                if (puntaje > Main.pilotoActual.getPuntajeMaximo()) {
                    Main.pilotoActual.setPuntajeMaximo(puntaje); // actualiza el maximo
                }
            }

            JOptionPane.showMessageDialog(this, "¡Moriste! Fin del juego.", "Game Over", JOptionPane.WARNING_MESSAGE);
    
            // regresa al menú principal
            Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
            if (ventanaPadre != null) {
                ventanaPadre.dispose();
            }
        }

        // movimientos y choque del disparo
        if (disparoActivo) {
            disparoX += 65;

            // choque con enemigo 1
            if (disparoX >= enemigoX && disparoX <= enemigoX + 40 &&
                disparoY >= enemigoY && disparoY <= enemigoY + 40) {
                puntaje += 15;
                enemigoX = 850;
                enemigoY = (int) (Math.random() * 400) + 120;
                disparoActivo = false;
            }

            // choque con enemigo 2
            if (disparoX >= enemigo2X && disparoX <= enemigo2X + 40 &&
                disparoY >= enemigo2Y && disparoY <= enemigo2Y + 40) {
                puntaje += 15;
                enemigo2X = 990;
                enemigo2Y = (int) (Math.random() * 400) + 120;
                disparoActivo = false;
            }

            // quitar el láser si sale de la pantalla
            if (disparoX > 850) {
                disparoActivo = false;
            }
        }

        repaint();
    }
}