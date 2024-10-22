package org.yourorghere;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class LaberintoV implements GLEventListener, KeyListener {

    static double tx = 3, ty = 2, tz = 3;
    static float rx, ry, rz, a = -90;

    public static void main(String[] args) {
        Frame frame = new Frame("Laberinto");

        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new LaberintoV());
        canvas.addKeyListener(new LaberintoV());

        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        gl.setSwapInterval(1);

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH);

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) {

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 500.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        GLUT glut = new GLUT();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0.0f, -6.0f);

        float cx = (float) Math.cos(Math.toRadians(a)),
                cz = (float) Math.sin(Math.toRadians(a));
        //Se colocan las funciones X y Z para que este pueda girar con las Teclas A y D

        glu.gluLookAt(tx, ty, tz,
                tx + cx,
                ty,
                tz + cz,
                0, 1, 0);//Camara

        gl.glRotatef(rx, 1, 0, 0);
        gl.glRotatef(ry, 0, 1, 0);
        gl.glRotatef(rz, 0, 0, 1);
        //PATA 1
        //CAJA 1
        //Suelo principal part1
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -2.0f); // 3
        gl.glVertex3f(-6.0f, 0.0f, -2.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -2.0f); // 5
        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -2.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -10.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 4 
        gl.glVertex3f(-6.0f, 0.5f, -10.0f); // 8 
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 7
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 3

        gl.glVertex3f(-6.0f, 0.0f, -2.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -2.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -2.0f); // 6
        gl.glVertex3f(-6.0f, 0.5f, -2.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -2.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -2.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -2.0f); // 1
        gl.glVertex3f(-6.0f, 0.5f, -2.0f); // 5
        gl.glVertex3f(-6.0f, 0.5f, -10.0f); // 8 
        gl.glEnd();

        //Suelo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(6.0f, 0.0f, -2.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -2.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 0.5f, -2.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.5f, -2.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -10.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 4 
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 8 
        gl.glVertex3f(6.0f, 0.5f, -10.0f); // 7
        gl.glVertex3f(6.0f, 0.0f, -10.0f); // 3

        gl.glVertex3f(0.0f, 0.0f, -2.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -2.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -2.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -2.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 0.0f, -10.0f); // 3
        gl.glVertex3f(6.0f, 0.0f, -2.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -2.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -10.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -2.0f); // 1
        gl.glVertex3f(0.0f, 0.5f, -2.0f); // 5
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 8 
        gl.glEnd();

        //Techo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -2.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -2.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 9.5f, -2.0f); // 5 
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -2.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 9.5f, -10.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 4 
        gl.glVertex3f(-6.0f, 9.5f, -10.0f); // 8 
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 7
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 3

        gl.glVertex3f(-6.0f, 10.0f, -2.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -2.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -2.0f); // 6
        gl.glVertex3f(-6.0f, 9.5f, -2.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -2.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -2.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 4 
        gl.glVertex3f(-6.0f, 10.0f, -2.0f); // 1
        gl.glVertex3f(-6.0f, 9.5f, -2.0f); // 5
        gl.glVertex3f(-6.0f, 9.5f, -10.0f); // 8 
        gl.glEnd();

        //Techo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -10.0f); // 2
        gl.glVertex3f(6.0f, 10.0f, -2.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -2.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(0.0f, 9.5f, -2.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 9.5f, -2.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -10.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 4 
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 8 
        gl.glVertex3f(6.0f, 9.5f, -10.0f); // 7
        gl.glVertex3f(6.0f, 10.0f, -10.0f); // 3

        gl.glVertex3f(0.0f, 10.0f, -2.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -2.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -2.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -2.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -10.0f); // 3
        gl.glVertex3f(6.0f, 10.0f, -2.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -2.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -10.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 4 
        gl.glVertex3f(0.0f, 10.0f, -2.0f); // 1
        gl.glVertex3f(0.0f, 9.5f, -2.0f); // 5
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 8 
        gl.glEnd();

        // Pared 1 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 0.0f, -10.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -2.0f);// 2
        gl.glVertex3f(6.5f, 5.0f, -2.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 4

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.0f, -10.0f);// 5
        gl.glVertex3f(6.0f, 0.0f, -2.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(6.0f, 5.0f, -2.0f);// 7
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 4
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 8
        gl.glVertex3f(6.0f, 5.0f, -2.0f);// 7
        gl.glVertex3f(6.5f, 5.0f, -2.0f);// 3

        gl.glVertex3f(6.5f, 0.0f, -10.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -2.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -2.0f);// 6
        gl.glVertex3f(6.0f, 0.0f, -10.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 5.0f, -2.0f);// 3
        gl.glVertex3f(6.5f, 0.0f, -2.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -2.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -2.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 4
        gl.glVertex3f(6.5f, 0.0f, -10.0f);// 1
        gl.glVertex3f(6.0f, 0.0f, -10.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 8

        // Pared 1 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -2.0f);// 2
        gl.glVertex3f(6.5f, 10.0f, -2.0f);// 3
        gl.glVertex3f(6.5f, 10.0f, -10.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -2.0f);// 6
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 10.0f, -2.0f);// 7
        gl.glVertex3f(6.0f, 10.0f, -10.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 10.0f, -10.0f);// 4
        gl.glVertex3f(6.0f, 10.0f, -10.0f);// 8
        gl.glVertex3f(6.0f, 10.0f, -2.0f);// 7
        gl.glVertex3f(6.5f, 10.0f, -2.0f);// 3

        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -2.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -2.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 10.0f, -2.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -2.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -2.0f);// 6
        gl.glVertex3f(6.0f, 10.0f, -2.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 10.0f, -10.0f);// 4
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 1
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 5
        gl.glVertex3f(6.0f, 10.0f, -10.0f);// 8
        gl.glEnd();

        // Pared 2 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 0.0f, -10.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -2.0f);// 2
        gl.glVertex3f(-6.5f, 5.0f, -2.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 4

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.0f, -10.0f);// 5
        gl.glVertex3f(-6.0f, 0.0f, -2.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -2.0f);// 7
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 4
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 8
        gl.glVertex3f(-6.0f, 5.0f, -2.0f);// 7
        gl.glVertex3f(-6.5f, 5.0f, -2.0f);// 3

        gl.glVertex3f(-6.5f, 0.0f, -10.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -2.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -2.0f);// 6
        gl.glVertex3f(-6.0f, 0.0f, -10.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 5.0f, -2.0f);// 3
        gl.glVertex3f(-6.5f, 0.0f, -2.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -2.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -2.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 4
        gl.glVertex3f(-6.5f, 0.0f, -10.0f);// 1
        gl.glVertex3f(-6.0f, 0.0f, -10.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 8
        gl.glEnd();

        // Pared 2 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -2.0f);// 2
        gl.glVertex3f(-6.5f, 10.0f, -2.0f);// 3
        gl.glVertex3f(-6.5f, 10.0f, -10.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -2.0f);// 6
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 10.0f, -2.0f);// 7
        gl.glVertex3f(-6.0f, 10.0f, -10.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 10.0f, -10.0f);// 4
        gl.glVertex3f(-6.0f, 10.0f, -10.0f);// 8
        gl.glVertex3f(-6.0f, 10.0f, -2.0f);// 7
        gl.glVertex3f(-6.5f, 10.0f, -2.0f);// 3

        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -2.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -2.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 10.0f, -2.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -2.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -2.0f);// 6
        gl.glVertex3f(-6.0f, 10.0f, -2.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 10.0f, -10.0f);// 4
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 1
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 5
        gl.glVertex3f(-6.0f, 10.0f, -10.0f);// 8
        gl.glEnd();

        //CAJA 2
        //Suelo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -18.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 3
        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -10.0f); // 5
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -18.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 0.0f, -18.0f); // 4 
        gl.glVertex3f(-6.0f, 0.5f, -18.0f); // 8 
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 7
        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 3

        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 6
        gl.glVertex3f(-6.0f, 0.5f, -10.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 0.0f, -18.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(-6.0f, 0.5f, -10.0f); // 5
        gl.glVertex3f(-6.0f, 0.5f, -18.0f); // 8 
        gl.glEnd();

        //Suelo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -18.0f); // 2
        gl.glVertex3f(6.0f, 0.0f, -10.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.5f, -10.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -18.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 4 
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 8 
        gl.glVertex3f(6.0f, 0.5f, -18.0f); // 7
        gl.glVertex3f(6.0f, 0.0f, -18.0f); // 3

        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -10.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 0.0f, -18.0f); // 3
        gl.glVertex3f(6.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -10.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -18.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 5
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 8 
        gl.glEnd();

        //Techo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 10.0f, -18.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 9.5f, -10.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 9.5f, -18.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 10.0f, -18.0f); // 4 
        gl.glVertex3f(-6.0f, 9.5f, -18.0f); // 8 
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 7
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 3

        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 6
        gl.glVertex3f(-6.0f, 9.5f, -10.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -18.0f); // 4 
        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 1
        gl.glVertex3f(-6.0f, 9.5f, -10.0f); // 5
        gl.glVertex3f(-6.0f, 9.5f, -18.0f); // 8 
        gl.glEnd();

        //Techo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -18.0f); // 2
        gl.glVertex3f(6.0f, 10.0f, -10.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 4 

        gl.glColor3f(0.0f, 1.0f, 0.0f);    // GREEN

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 9.5f, -10.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -18.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 4 
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 8 
        gl.glVertex3f(6.0f, 9.5f, -18.0f); // 7
        gl.glVertex3f(6.0f, 10.0f, -18.0f); // 3

        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -10.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -10.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -18.0f); // 3
        gl.glVertex3f(6.0f, 10.0f, -10.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -10.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -18.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 4 
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 5
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 8 
        gl.glEnd();

        // Pared 1 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 0.0f, -18.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -10.0f);// 2
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 4

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.0f, -18.0f);// 5
        gl.glVertex3f(6.0f, 0.0f, -10.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 7
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 4
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 8
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 7
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 3

        gl.glVertex3f(6.5f, 0.0f, -18.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -10.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -10.0f);// 6
        gl.glVertex3f(6.0f, 0.0f, -18.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 3
        gl.glVertex3f(6.5f, 0.0f, -10.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -10.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 4
        gl.glVertex3f(6.5f, 0.0f, -18.0f);// 1
        gl.glVertex3f(6.0f, 0.0f, -18.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 8

        // Pared 1 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 2
        gl.glVertex3f(6.5f, 10.0f, -10.0f);// 3
        gl.glVertex3f(6.5f, 10.0f, -18.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 6
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 10.0f, -10.0f);// 7
        gl.glVertex3f(6.0f, 10.0f, -18.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 10.0f, -18.0f);// 4
        gl.glVertex3f(6.0f, 10.0f, -18.0f);// 8
        gl.glVertex3f(6.0f, 10.0f, -10.0f);// 7
        gl.glVertex3f(6.5f, 10.0f, -10.0f);// 3

        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 10.0f, -10.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 6
        gl.glVertex3f(6.0f, 10.0f, -10.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 10.0f, -18.0f);// 4
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 1
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 5
        gl.glVertex3f(6.0f, 10.0f, -18.0f);// 8
        gl.glEnd();

        // Pared 2 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 0.0f, -18.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -10.0f);// 2
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 4

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.0f, -18.0f);// 5
        gl.glVertex3f(-6.0f, 0.0f, -10.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 7
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 4
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 8
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 7
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 3

        gl.glVertex3f(-6.5f, 0.0f, -18.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -10.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -10.0f);// 6
        gl.glVertex3f(-6.0f, 0.0f, -18.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 3
        gl.glVertex3f(-6.5f, 0.0f, -10.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -10.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 4
        gl.glVertex3f(-6.5f, 0.0f, -18.0f);// 1
        gl.glVertex3f(-6.0f, 0.0f, -18.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 8
        gl.glEnd();

        // Pared 2 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 2
        gl.glVertex3f(-6.5f, 10.0f, -10.0f);// 3
        gl.glVertex3f(-6.5f, 10.0f, -18.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 6
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 10.0f, -10.0f);// 7
        gl.glVertex3f(-6.0f, 10.0f, -18.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 10.0f, -18.0f);// 4
        gl.glVertex3f(-6.0f, 10.0f, -18.0f);// 8
        gl.glVertex3f(-6.0f, 10.0f, -10.0f);// 7
        gl.glVertex3f(-6.5f, 10.0f, -10.0f);// 3

        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 10.0f, -10.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 6
        gl.glVertex3f(-6.0f, 10.0f, -10.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 10.0f, -18.0f);// 4
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 1
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 5
        gl.glVertex3f(-6.0f, 10.0f, -18.0f);// 8
        gl.glEnd();

        //CAJA 3
        //Suelo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 3
        gl.glVertex3f(-6.0f, 0.0f, -18.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -26.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 0.5f, -18.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 0.0f, -18.0f); // 4 
        gl.glVertex3f(-6.0f, 0.5f, -18.0f); // 8 
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 7
        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 3

        gl.glVertex3f(-6.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(-6.0f, 0.5f, -26.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 0.0f, -18.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(-6.0f, 0.5f, -18.0f); // 5
        gl.glVertex3f(-6.0f, 0.5f, -26.0f); // 8 
        gl.glEnd();

        //Suelo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(6.0f, 0.0f, -18.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.5f, -18.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -26.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 4 
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 8 
        gl.glVertex3f(6.0f, 0.5f, -26.0f); // 7
        gl.glVertex3f(6.0f, 0.0f, -26.0f); // 3

        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -18.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -18.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 0.0f, -26.0f); // 3
        gl.glVertex3f(6.0f, 0.0f, -18.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -18.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -26.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 1
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 5
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 8 
        gl.glEnd();

        //Techo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 10.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -18.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 9.5f, -18.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 9.5f, -26.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 10.0f, -26.0f); // 4 
        gl.glVertex3f(-6.0f, 9.5f, -26.0f); // 8 
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 7
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 3

        gl.glVertex3f(-6.0f, 10.0f, -18.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 6
        gl.glVertex3f(-6.0f, 9.5f, -18.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -26.0f); // 4 
        gl.glVertex3f(-6.0f, 10.0f, -18.0f); // 1
        gl.glVertex3f(-6.0f, 9.5f, -18.0f); // 5
        gl.glVertex3f(-6.0f, 9.5f, -26.0f); // 8 
        gl.glEnd();

        //Techo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -26.0f); // 2
        gl.glVertex3f(6.0f, 10.0f, -18.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE DARK
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 9.5f, -18.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -26.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE DARK
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 4 
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 8 
        gl.glVertex3f(6.0f, 9.5f, -26.0f); // 7
        gl.glVertex3f(6.0f, 10.0f, -26.0f); // 3

        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -18.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -18.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -26.0f); // 3
        gl.glVertex3f(6.0f, 10.0f, -18.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -18.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -26.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 4 
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 1
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 5
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 8 
        gl.glEnd();

        // Pared 1 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 0.0f, -26.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -18.0f);// 2
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 4

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.0f, -26.0f);// 5
        gl.glVertex3f(6.0f, 0.0f, -18.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 7
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 4
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 8
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 7
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 3

        gl.glVertex3f(6.5f, 0.0f, -26.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -18.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -18.0f);// 6
        gl.glVertex3f(6.0f, 0.0f, -26.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 3
        gl.glVertex3f(6.5f, 0.0f, -18.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -18.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 4
        gl.glVertex3f(6.5f, 0.0f, -26.0f);// 1
        gl.glVertex3f(6.0f, 0.0f, -26.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 8
        gl.glEnd();

        // Pared 1 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 2
        gl.glVertex3f(6.5f, 10.0f, -18.0f);// 3
        gl.glVertex3f(6.5f, 10.0f, -26.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 6
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 10.0f, -18.0f);// 7
        gl.glVertex3f(6.0f, 10.0f, -26.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 10.0f, -26.0f);// 4
        gl.glVertex3f(6.0f, 10.0f, -26.0f);// 8
        gl.glVertex3f(6.0f, 10.0f, -18.0f);// 7
        gl.glVertex3f(6.5f, 10.0f, -18.0f);// 3

        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 10.0f, -18.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 6
        gl.glVertex3f(6.0f, 10.0f, -18.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 10.0f, -26.0f);// 4
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 1
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 5
        gl.glVertex3f(6.0f, 10.0f, -26.0f);// 8
        gl.glEnd();

        // Pared 2 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 0.0f, -26.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -18.0f);// 2
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 4

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.0f, -26.0f);// 1
        gl.glVertex3f(-6.0f, 0.0f, -18.0f);// 2
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 3
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 4

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 4
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 8
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 7
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 3

        gl.glVertex3f(-6.5f, 0.0f, -26.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -18.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -18.0f);// 6
        gl.glVertex3f(-6.0f, 0.0f, -26.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 3
        gl.glVertex3f(-6.5f, 0.0f, -18.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -18.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 4
        gl.glVertex3f(-6.5f, 0.0f, -26.0f);// 1
        gl.glVertex3f(-6.0f, 0.0f, -26.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 8
        gl.glEnd();

        // Pared 2 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 2
        gl.glVertex3f(-6.5f, 10.0f, -18.0f);// 3
        gl.glVertex3f(-6.5f, 10.0f, -26.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 6
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 10.0f, -18.0f);// 7
        gl.glVertex3f(-6.0f, 10.0f, -26.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 10.0f, -26.0f);// 4
        gl.glVertex3f(-6.0f, 10.0f, -26.0f);// 8
        gl.glVertex3f(-6.0f, 10.0f, -18.0f);// 7
        gl.glVertex3f(-6.5f, 10.0f, -18.0f);// 3

        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 10.0f, -18.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 6
        gl.glVertex3f(-6.0f, 10.0f, -18.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 10.0f, -26.0f);// 4
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 1
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 5
        gl.glVertex3f(-6.0f, 10.0f, -26.0f);// 8
        gl.glEnd();

        //CAJA 4
        //Suelo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 3
        gl.glVertex3f(-6.0f, 0.0f, -26.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 0.5f, -34.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -26.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 0.0f, -34.0f); // 4 
        gl.glVertex3f(-6.0f, 0.5f, -34.0f); // 8 
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 7
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 3

        gl.glVertex3f(-6.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(-6.0f, 0.5f, -26.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 0.0f, -34.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(-6.0f, 0.5f, -26.0f); // 5
        gl.glVertex3f(-6.0f, 0.5f, -34.0f); // 8 
        gl.glEnd();

        //Suelo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -34.0f); // 2
        gl.glVertex3f(6.0f, 0.0f, -26.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -34.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 4 
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 8 
        gl.glVertex3f(6.0f, 0.5f, -34.0f); // 7
        gl.glVertex3f(6.0f, 0.0f, -34.0f); // 3

        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 0.0f, -34.0f); // 3
        gl.glVertex3f(6.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -34.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 5
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 8 
        gl.glEnd();

        //Techo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -26.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 9.5f, -26.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 9.5f, -34.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 10.0f, -34.0f); // 4 
        gl.glVertex3f(-6.0f, 9.5f, -34.0f); // 8 
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 7
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 3

        gl.glVertex3f(-6.0f, 10.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 6
        gl.glVertex3f(-6.0f, 9.5f, -26.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -34.0f); // 4 
        gl.glVertex3f(-6.0f, 10.0f, -26.0f); // 1
        gl.glVertex3f(-6.0f, 9.5f, -26.0f); // 5
        gl.glVertex3f(-6.0f, 9.5f, -34.0f); // 8 
        gl.glEnd();

        //Techo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(6.0f, 10.0f, -26.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE

        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 5
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 9.5f, -26.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -34.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 4 
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 8 
        gl.glVertex3f(6.0f, 9.5f, -34.0f); // 7
        gl.glVertex3f(6.0f, 10.0f, -34.0f); // 3

        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -26.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -26.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -34.0f); // 3
        gl.glVertex3f(6.0f, 10.0f, -26.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -26.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -34.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 4 
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 5
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 8 
        gl.glEnd();

        // Pared 1 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 0.0f, -37.5f);// 1
        gl.glVertex3f(6.5f, 0.0f, -26.0f);// 2
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -37.5f);// 4

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.0f, -37.5f);// 5
        gl.glVertex3f(6.0f, 0.0f, -26.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 7
        gl.glVertex3f(6.0f, 5.0f, -37.5f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 5.0f, -37.5f);// 4
        gl.glVertex3f(6.0f, 5.0f, -37.5f);// 8
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 7
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 3

        gl.glVertex3f(6.5f, 0.0f, -37.5f);// 1
        gl.glVertex3f(6.5f, 0.0f, -26.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -26.0f);// 6
        gl.glVertex3f(6.0f, 0.0f, -37.5f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 3
        gl.glVertex3f(6.5f, 0.0f, -26.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -26.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 5.0f, -37.5f);// 4
        gl.glVertex3f(6.5f, 0.0f, -37.5f);// 1
        gl.glVertex3f(6.0f, 0.0f, -37.5f);// 5
        gl.glVertex3f(6.0f, 5.0f, -37.5f);// 8

        // Pared 1 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 5.0f, -37.5f);// 1
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 2
        gl.glVertex3f(6.5f, 10.0f, -26.0f);// 3
        gl.glVertex3f(6.5f, 10.0f, -37.5f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(6.0f, 5.0f, -37.5f);// 5
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 6
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 10.0f, -26.0f);// 7
        gl.glVertex3f(6.0f, 10.0f, -37.5f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 10.0f, -37.5f);// 4
        gl.glVertex3f(6.0f, 10.0f, -37.5f);// 8
        gl.glVertex3f(6.0f, 10.0f, -26.0f);// 7
        gl.glVertex3f(6.5f, 10.0f, -26.0f);// 3

        gl.glVertex3f(6.5f, 5.0f, -37.5f);// 1
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -37.5f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 10.0f, -26.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 6
        gl.glVertex3f(6.0f, 10.0f, -26.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 10.0f, -37.5f);// 4
        gl.glVertex3f(6.5f, 5.0f, -37.5f);// 1
        gl.glVertex3f(6.0f, 5.0f, -37.5f);// 5
        gl.glVertex3f(6.0f, 10.0f, -37.5f);// 8
        gl.glEnd();

        // Pared 2 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 0.0f, -34.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -26.0f);// 2
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 4

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.0f, -34.0f);// 5
        gl.glVertex3f(-6.0f, 0.0f, -26.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 7
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 4
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 8
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 7
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 3

        gl.glVertex3f(-6.5f, 0.0f, -34.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -26.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -26.0f);// 6
        gl.glVertex3f(-6.0f, 0.0f, -34.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 3
        gl.glVertex3f(-6.5f, 0.0f, -26.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -26.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 4
        gl.glVertex3f(-6.5f, 0.0f, -34.0f);// 1
        gl.glVertex3f(-6.0f, 0.0f, -34.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 8
        gl.glEnd();

        // Pared 2 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -24.0f);// 2
        gl.glVertex3f(-6.5f, 10.0f, -24.0f);// 3
        gl.glVertex3f(-6.5f, 10.0f, -34.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 6
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 10.0f, -26.0f);// 7
        gl.glVertex3f(-6.0f, 10.0f, -34.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 10.0f, -34.0f);// 4
        gl.glVertex3f(-6.0f, 10.0f, -34.0f);// 8
        gl.glVertex3f(-6.0f, 10.0f, -26.0f);// 7
        gl.glVertex3f(-6.5f, 10.0f, -26.0f);// 3

        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 10.0f, -26.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 6
        gl.glVertex3f(-6.0f, 10.0f, -26.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 10.0f, -34.0f);// 4
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 1
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 5
        gl.glVertex3f(-6.0f, 10.0f, -34.0f);// 8
        gl.glEnd();

        //CAJA 5
        //Suelo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -42.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 3
        gl.glVertex3f(-6.0f, 0.0f, -34.0f); // 4 

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 0.5f, -34.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 7
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -42.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 0.0f, -42.0f); // 4 
        gl.glVertex3f(-6.0f, 0.5f, -42.0f); // 8 
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 7
        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 3

        gl.glVertex3f(-6.0f, 0.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(-6.0f, 0.5f, -34.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 0.0f, -42.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -34.0f); // 1
        gl.glVertex3f(-6.0f, 0.5f, -34.0f); // 5
        gl.glVertex3f(-6.0f, 0.5f, -42.0f); // 8 
        gl.glEnd();

        //Suelo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -42.0f); // 2
        gl.glVertex3f(6.0f, 0.0f, -34.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 5
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(6.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -42.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 4 
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 8 
        gl.glVertex3f(6.0f, 0.5f, -42.0f); // 7
        gl.glVertex3f(6.0f, 0.0f, -42.0f); // 3

        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -34.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 0.0f, -42.0f); // 3
        gl.glVertex3f(6.0f, 0.0f, -34.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -42.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 5
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 8 
        gl.glEnd();

        //Techo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED
        gl.glVertex3f(-6.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -42.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -42.0f); // 4 

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 9.5f, -34.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -42.0f); // 7
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 9.5f, -42.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 10.0f, -42.0f); // 4 
        gl.glVertex3f(-6.0f, 9.5f, -42.0f); // 8 
        gl.glVertex3f(0.0f, 9.5f, -42.0f); // 7
        gl.glVertex3f(0.0f, 10.0f, -42.0f); // 3

        gl.glVertex3f(-6.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 6
        gl.glVertex3f(-6.0f, 9.5f, -34.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -42.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -42.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -42.0f); // 4 
        gl.glVertex3f(-6.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(-6.0f, 9.5f, -34.0f); // 5
        gl.glVertex3f(-6.0f, 9.5f, -42.0f); // 8 
        gl.glEnd();

        //Techo principal part2 ESPECIAL 2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 10.0f, -37.5f); // 1
        gl.glVertex3f(6.0f, 10.0f, -37.5f); // 2
        gl.glVertex3f(6.0f, 10.0f, -34.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 9.5f, -37.5f); // 5
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(6.0f, 9.5f, -37.5f); // 6
        gl.glVertex3f(6.0f, 9.5f, -34.0f); // 7

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 10.0f, -37.5f); // 4 
        gl.glVertex3f(0.0f, 9.5f, -37.5f); // 8 
        gl.glVertex3f(6.0f, 9.5f, -37.5f); // 7
        gl.glVertex3f(6.0f, 10.0f, -37.5f); // 3

        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -37.5f); // 3
        gl.glVertex3f(6.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -34.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -37.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -37.0f); // 4 
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 5
        gl.glVertex3f(0.0f, 9.5f, -37.5f); // 8 
        gl.glEnd();

        // Pared 2 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 0.0f, -42.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -34.0f);// 2
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 4

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.0f, -42.0f);// 5
        gl.glVertex3f(-6.0f, 0.0f, -34.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 7
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 4
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 8
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 7
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 3

        gl.glVertex3f(-6.5f, 0.0f, -42.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -34.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -34.0f);// 6
        gl.glVertex3f(-6.0f, 0.0f, -42.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 3
        gl.glVertex3f(-6.5f, 0.0f, -34.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -34.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 4
        gl.glVertex3f(-6.5f, 0.0f, -42.0f);// 1
        gl.glVertex3f(-6.0f, 0.0f, -42.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 8
        gl.glEnd();

        // Pared 2 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 2
        gl.glVertex3f(-6.5f, 10.0f, -34.0f);// 3
        gl.glVertex3f(-6.5f, 10.0f, -42.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 6
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 10.0f, -34.0f);// 7
        gl.glVertex3f(-6.0f, 10.0f, -42.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 10.0f, -42.0f);// 4
        gl.glVertex3f(-6.0f, 10.0f, -42.0f);// 8
        gl.glVertex3f(-6.0f, 10.0f, -34.0f);// 7
        gl.glVertex3f(-6.5f, 10.0f, -34.0f);// 3

        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 10.0f, -34.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 6
        gl.glVertex3f(-6.0f, 10.0f, -34.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 10.0f, -42.0f);// 4
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 1
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 5
        gl.glVertex3f(-6.0f, 10.0f, -42.0f);// 8
        gl.glEnd();

        //CAJA 6
        //Suelo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -49.5f); // 1
        gl.glVertex3f(0.0f, 0.0f, -49.5f); // 2
        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 3
        gl.glVertex3f(-6.0f, 0.0f, -42.0f); // 4 

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 0.5f, -42.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -49.5f); // 7

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -49.5f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 0.0f, -49.5f); // 4 
        gl.glVertex3f(-6.0f, 0.5f, -49.5f); // 8 
        gl.glVertex3f(0.0f, 0.5f, -49.5f); // 7
        gl.glVertex3f(0.0f, 0.0f, -49.5f); // 3

        gl.glVertex3f(-6.0f, 0.0f, -42.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 6
        gl.glVertex3f(-6.0f, 0.5f, -42.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 0.0f, -49.5f); // 3
        gl.glVertex3f(0.0f, 0.0f, -49.5f); // 2
        gl.glVertex3f(0.0f, 0.5f, -49.5f); // 6
        gl.glVertex3f(0.0f, 0.5f, -49.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 0.0f, -49.5f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -42.0f); // 1
        gl.glVertex3f(-6.0f, 0.5f, -42.0f); // 5
        gl.glVertex3f(-6.0f, 0.5f, -49.5f); // 8 
        gl.glEnd();

        //Suelo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -49.5f); // 1
        gl.glVertex3f(6.0f, 0.0f, -49.5f); // 2
        gl.glVertex3f(6.0f, 0.0f, -42.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 5
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(6.0f, 0.5f, -42.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -49.5f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 0.5f, -49.5f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 0.0f, -49.5f); // 4 
        gl.glVertex3f(0.0f, 0.5f, -49.5f); // 8 
        gl.glVertex3f(6.0f, 0.5f, -49.5f); // 7
        gl.glVertex3f(6.0f, 0.0f, -49.5f); // 3

        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -42.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -42.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 0.0f, -49.5f); // 3
        gl.glVertex3f(6.0f, 0.0f, -42.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -42.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -49.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 0.0f, -49.5f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 1
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 5
        gl.glVertex3f(0.0f, 0.5f, -49.5f); // 8 
        gl.glEnd();

        //Techo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED
        gl.glVertex3f(-6.0f, 10.0f, -42.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -42.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -49.5f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -49.5f); // 4 

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 9.5f, -42.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 9.5f, -42.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -49.5f); // 7
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 9.5f, -49.5f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 10.0f, -49.5f); // 4 
        gl.glVertex3f(-6.0f, 9.5f, -49.5f); // 8 
        gl.glVertex3f(0.0f, 9.5f, -49.5f); // 7
        gl.glVertex3f(0.0f, 10.0f, -49.5f); // 3

        gl.glVertex3f(-6.0f, 10.0f, -42.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -42.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -42.0f); // 6
        gl.glVertex3f(-6.0f, 9.5f, -42.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -49.5f); // 3
        gl.glVertex3f(0.0f, 10.0f, -42.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -42.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -49.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -49.5f); // 4 
        gl.glVertex3f(-6.0f, 10.0f, -42.0f); // 1
        gl.glVertex3f(-6.0f, 9.5f, -42.0f); // 5
        gl.glVertex3f(-6.0f, 9.5f, -49.5f); // 8 
        gl.glEnd();

        //Techo principal part2 ESPECIAL
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 10.0f, -49.5f); // 1
        gl.glVertex3f(6.5f, 10.0f, -49.5f); // 2
        gl.glVertex3f(6.5f, 10.0f, -37.5f); // 3
        gl.glVertex3f(0.0f, 10.0f, -37.5f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 9.5f, -37.5f); // 5
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(6.5f, 9.5f, -37.5f); // 6
        gl.glVertex3f(6.5f, 9.5f, -49.5f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 9.5f, -49.5f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 10.0f, -49.5f); // 4 
        gl.glVertex3f(0.0f, 9.5f, -49.5f); // 8 
        gl.glVertex3f(6.5f, 9.5f, -49.5f); // 7
        gl.glVertex3f(6.5f, 10.0f, -49.5f); // 3

        gl.glVertex3f(0.0f, 10.0f, -37.5f); // 1
        gl.glVertex3f(6.5f, 10.0f, -37.5f); // 2
        gl.glVertex3f(6.5f, 9.5f, -37.5f); // 6
        gl.glVertex3f(0.0f, 9.5f, -37.5f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -49.5f); // 3
        gl.glVertex3f(6.0f, 10.0f, -37.5f); // 2
        gl.glVertex3f(6.0f, 9.5f, -37.5f); // 6
        gl.glVertex3f(6.0f, 9.5f, -49.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -49.5f); // 4 
        gl.glVertex3f(0.0f, 10.0f, -37.5f); // 1
        gl.glVertex3f(0.0f, 9.5f, -37.5f); // 5
        gl.glVertex3f(0.0f, 9.5f, -49.5f); // 8 
        gl.glEnd();

        // Pared 1 part1 ESPECIAL
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 0.0f, -50f);// 1
        gl.glVertex3f(6.5f, 0.0f, -50f);// 2
        gl.glVertex3f(6.5f, 5.0f, -50f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -50f);// 4

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(-6.5f, 0.0f, -49.5f);// 5
        gl.glVertex3f(6.5f, 0.0f, -49.5f);// 6

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(6.5f, 5.0f, -49.5f);// 7
        gl.glVertex3f(-6.5f, 5.0f, -49.5f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.5f, 5.0f, -50f);// 4
        gl.glVertex3f(-6.5f, 5.0f, -49.5f);// 8
        gl.glVertex3f(6.5f, 5.0f, -49.5f);// 7
        gl.glVertex3f(6.5f, 5.0f, -50f);// 3

        gl.glVertex3f(-6.5f, 0.0f, -50f);// 1
        gl.glVertex3f(6.5f, 0.0f, -50f);// 2
        gl.glVertex3f(6.5f, 0.0f, -49.5f);// 6
        gl.glVertex3f(-6.5f, 0.0f, -49.5f);// 5
        gl.glEnd();

        // Pared 1 part2 ESPECIAL
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 5.0f, -50f);// 1
        gl.glVertex3f(6.5f, 5.0f, -50f);// 2
        gl.glVertex3f(6.5f, 10.0f, -50f);// 3
        gl.glVertex3f(-6.5f, 10.0f, -50f);// 4

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(-6.5f, 5.0f, -49.5f);// 5
        gl.glVertex3f(6.5f, 5.0f, -49.5f);// 6

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(6.5f, 10.0f, -49.5f);// 7
        gl.glVertex3f(-6.5f, 10.0f, -49.5f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.5f, 10.0f, -50f);// 4
        gl.glVertex3f(-6.5f, 10.0f, -49.5f);// 8
        gl.glVertex3f(6.5f, 10.0f, -49.5f);// 7
        gl.glVertex3f(6.5f, 10.0f, -50f);// 3

        gl.glVertex3f(-6.5f, 5.0f, -50f);// 1
        gl.glVertex3f(6.5f, 5.0f, -50f);// 2
        gl.glVertex3f(6.5f, 5.0f, -49.5f);// 6
        gl.glVertex3f(-6.5f, 5.0f, -49.5f);// 5
        gl.glEnd();

        // Pared 2 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 0.0f, -50.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -42.0f);// 2
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -50.0f);// 4

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.0f, -50.0f);// 5
        gl.glVertex3f(-6.0f, 0.0f, -42.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 7
        gl.glVertex3f(-6.0f, 5.0f, -50.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 5.0f, -50.0f);// 4
        gl.glVertex3f(-6.0f, 5.0f, -50.0f);// 8
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 7
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 3

        gl.glVertex3f(-6.5f, 0.0f, -50.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -42.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -42.0f);// 6
        gl.glVertex3f(-6.0f, 0.0f, -50.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 3
        gl.glVertex3f(-6.5f, 0.0f, -42.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -42.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 5.0f, -50.0f);// 4
        gl.glVertex3f(-6.5f, 0.0f, -50.0f);// 1
        gl.glVertex3f(-6.0f, 0.0f, -50.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -50.0f);// 8
        gl.glEnd();

        // Pared 2 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 5.0f, -50.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 2
        gl.glVertex3f(-6.5f, 10.0f, -42.0f);// 3
        gl.glVertex3f(-6.5f, 10.0f, -50.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(-6.0f, 5.0f, -50.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 6
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 10.0f, -42.0f);// 7
        gl.glVertex3f(-6.0f, 10.0f, -50.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 10.0f, -50.0f);// 4
        gl.glVertex3f(-6.0f, 10.0f, -50.0f);// 8
        gl.glVertex3f(-6.0f, 10.0f, -42.0f);// 7
        gl.glVertex3f(-6.5f, 10.0f, -42.0f);// 3

        gl.glVertex3f(-6.5f, 5.0f, -50.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -50.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 10.0f, -42.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 6
        gl.glVertex3f(-6.0f, 10.0f, -42.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 10.0f, -50.0f);// 4
        gl.glVertex3f(-6.5f, 5.0f, -50.0f);// 1
        gl.glVertex3f(-6.0f, 5.0f, -50.0f);// 5
        gl.glVertex3f(-6.0f, 10.0f, -50.0f);// 8
        gl.glEnd();

        //OBSTACULOS 1
        //Pared 1 part 1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 5.0f, -10.0f); // 3
        gl.glVertex3f(-6.0f, 5.0f, -10.0f); // 4 

        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 0.0f, -9.5f); // 5
        gl.glColor3f(0.0f, 1.0f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 0.0f, -9.5f); // 6
        gl.glVertex3f(0.0f, 5.0f, -9.5f); // 7
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 5.0f, -9.5f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 5.0f, -10.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -9.5f); // 6
        gl.glVertex3f(0.0f, 5.0f, -9.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 5.0f, -10.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(-6.0f, 0.0f, -9.5f); // 5
        gl.glVertex3f(-6.0f, 5.0f, -9.5f); // 8
        gl.glEnd();

        //Pared 1 part 2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 5.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 5.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 4 

        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 5.0f, -9.5f); // 5
        gl.glColor3f(0.0f, 1.0f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 5.0f, -9.5f); // 6
        gl.glVertex3f(0.0f, 10.0f, -9.5f); // 7
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 10.0f, -9.5f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 3
        gl.glVertex3f(0.0f, 5.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 5.0f, -9.5f); // 6
        gl.glVertex3f(0.0f, 10.0f, -9.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 4 
        gl.glVertex3f(-6.0f, 5.0f, -10.0f); // 1
        gl.glVertex3f(-6.0f, 5.0f, -9.5f); // 5
        gl.glVertex3f(-6.0f, 10.0f, -9.5f); // 8
        gl.glEnd();

        //Pared 2 part 1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -20.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -20.0f); // 2
        gl.glVertex3f(6.0f, 5.0f, -20.0f); // 3
        gl.glVertex3f(0.0f, 5.0f, -20.0f); // 4 

        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 0.0f, -19.5f); // 5
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(6.0f, 0.0f, -19.5f); // 6
        gl.glVertex3f(6.0f, 5.0f, -19.5f); // 7
        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 5.0f, -19.5f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 5.0f, -20.0f); // 3
        gl.glVertex3f(6.0f, 0.0f, -20.0f); // 2
        gl.glVertex3f(6.0f, 0.0f, -19.5f); // 6
        gl.glVertex3f(6.0f, 5.0f, -19.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 5.0f, -20.0f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -20.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -19.5f); // 5
        gl.glVertex3f(0.0f, 5.0f, -19.5f); // 8
        gl.glEnd();

        //Pared 2 part 2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 5.0f, -20.0f); // 1
        gl.glVertex3f(6.0f, 5.0f, -20.0f); // 2
        gl.glVertex3f(6.0f, 10.0f, -20.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -20.0f); // 4 

        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 5.0f, -19.5f); // 5
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(6.0f, 5.0f, -19.5f); // 6
        gl.glVertex3f(6.0f, 10.0f, -19.5f); // 7
        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 10.0f, -19.5f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -20.0f); // 3
        gl.glVertex3f(6.0f, 5.0f, -20.0f); // 2
        gl.glVertex3f(6.0f, 5.0f, -19.5f); // 6
        gl.glVertex3f(6.0f, 10.0f, -19.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -20.0f); // 4 
        gl.glVertex3f(0.0f, 5.0f, -20.0f); // 1
        gl.glVertex3f(0.0f, 5.0f, -19.5f); // 5
        gl.glVertex3f(0.0f, 10.0f, -19.5f); // 8
        gl.glEnd();

        //OBSTACULOS 2
        //Pared 1 part 1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -30.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -30.0f); // 2
        gl.glVertex3f(0.0f, 5.0f, -30.0f); // 3
        gl.glVertex3f(-6.0f, 5.0f, -30.0f); // 4 

        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 0.0f, -29.5f); // 5
        gl.glColor3f(0.0f, 1.0f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 0.0f, -29.5f); // 6
        gl.glVertex3f(0.0f, 5.0f, -29.5f); // 7
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 5.0f, -29.5f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 5.0f, -30.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -30.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -29.5f); // 6
        gl.glVertex3f(0.0f, 5.0f, -29.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 5.0f, -30.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -30.0f); // 1
        gl.glVertex3f(-6.0f, 0.0f, -29.5f); // 5
        gl.glVertex3f(-6.0f, 5.0f, -29.5f); // 8
        gl.glEnd();

        //Pared 1 part 2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 5.0f, -30.0f); // 1
        gl.glVertex3f(0.0f, 5.0f, -30.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -30.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -30.0f); // 4 

        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 5.0f, -29.5f); // 5
        gl.glColor3f(0.0f, 1.0f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 5.0f, -29.5f); // 6
        gl.glVertex3f(0.0f, 10.0f, -29.5f); // 7
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 10.0f, -29.5f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -30.0f); // 3
        gl.glVertex3f(0.0f, 5.0f, -30.0f); // 2
        gl.glVertex3f(0.0f, 5.0f, -29.5f); // 6
        gl.glVertex3f(0.0f, 10.0f, -29.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -30.0f); // 4 
        gl.glVertex3f(-6.0f, 5.0f, -30.0f); // 1
        gl.glVertex3f(-6.0f, 5.0f, -29.5f); // 5
        gl.glVertex3f(-6.0f, 10.0f, -29.5f); // 8
        gl.glEnd();

        //Pared 2 part 1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -37.5f); // 1
        gl.glVertex3f(6.0f, 0.0f, -37.5f); // 2
        gl.glVertex3f(6.0f, 5.0f, -37.5f); // 3
        gl.glVertex3f(0.0f, 5.0f, -37.5f); // 4 

        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 0.0f, -36.f); // 5
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(6.0f, 0.0f, -36.f); // 6
        gl.glVertex3f(6.0f, 5.0f, -36.f); // 7
        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 5.0f, -36.f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 5.0f, -37.5f); // 3
        gl.glVertex3f(6.0f, 0.0f, -37.5f); // 2
        gl.glVertex3f(6.0f, 0.0f, -36.0f); // 6
        gl.glVertex3f(6.0f, 5.0f, -36.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 5.0f, -37.5f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -37.5f); // 1
        gl.glVertex3f(0.0f, 0.0f, -36.f); // 5
        gl.glVertex3f(0.0f, 5.0f, -36.f); // 8
        gl.glEnd();

        //Pared 2 part 2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 5.0f, -37.5f); // 1
        gl.glVertex3f(6.0f, 5.0f, -37.5f); // 2
        gl.glVertex3f(6.0f, 10.0f, -37.5f); // 3
        gl.glVertex3f(0.0f, 10.0f, -37.5f); // 4 

        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 5.0f, -36.f); // 5

        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(6.0f, 5.0f, -36.f); // 6
        gl.glVertex3f(6.0f, 10.0f, -36.f); // 7

        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 10.0f, -36.f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -37.5f); // 3
        gl.glVertex3f(6.0f, 5.0f, -37.5f); // 2
        gl.glVertex3f(6.0f, 5.0f, -36.0f); // 6
        gl.glVertex3f(6.0f, 10.0f, -36.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -37.5f); // 4 
        gl.glVertex3f(0.0f, 5.0f, -37.5f); // 1
        gl.glVertex3f(0.0f, 5.0f, -36.f); // 5
        gl.glVertex3f(0.0f, 10.0f, -36.f); // 8
        gl.glEnd();
        gl.glPopMatrix();

//PATA 2
        gl.glPushMatrix();
        gl.glTranslated(4.5, 0, -43.5);
        gl.glRotatef(0, 1, 0, 0);
        gl.glRotatef(-90, 0, 1, 0);
        gl.glRotatef(0, 0, 0, 1);
        //CAJA 1
        //Suelo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -1.0f); // 3
        gl.glVertex3f(-6.0f, 0.0f, -1.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -1.0f); // 5
        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -1.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -10.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 4 
        gl.glVertex3f(-6.0f, 0.5f, -10.0f); // 8 
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 7
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 3

        gl.glVertex3f(-6.0f, 0.0f, -1.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -1.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -1.0f); // 6
        gl.glVertex3f(-6.0f, 0.5f, -1.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -1.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -1.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -1.0f); // 1
        gl.glVertex3f(-6.0f, 0.5f, -1.0f); // 5
        gl.glVertex3f(-6.0f, 0.5f, -10.0f); // 8 
        gl.glEnd();

        //Suelo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(6.0f, 0.0f, -1.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -1.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 0.5f, -1.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.5f, -1.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -10.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 4 
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 8 
        gl.glVertex3f(6.0f, 0.5f, -10.0f); // 7
        gl.glVertex3f(6.0f, 0.0f, -10.0f); // 3

        gl.glVertex3f(0.0f, 0.0f, -1.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -1.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -1.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -1.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 0.0f, -10.0f); // 3
        gl.glVertex3f(6.0f, 0.0f, -1.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -1.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -10.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -1.0f); // 1
        gl.glVertex3f(0.0f, 0.5f, -1.0f); // 5
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 8 
        gl.glEnd();

        //Techo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -2.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -2.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 9.5f, -2.0f); // 5 
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -2.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 9.5f, -10.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 4 
        gl.glVertex3f(-6.0f, 9.5f, -10.0f); // 8 
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 7
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 3

        gl.glVertex3f(-6.0f, 10.0f, -2.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -2.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -2.0f); // 6
        gl.glVertex3f(-6.0f, 9.5f, -2.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -2.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -2.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 4 
        gl.glVertex3f(-6.0f, 10.0f, -2.0f); // 1
        gl.glVertex3f(-6.0f, 9.5f, -2.0f); // 5
        gl.glVertex3f(-6.0f, 9.5f, -10.0f); // 8 
        gl.glEnd();

        //Techo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -10.0f); // 2
        gl.glVertex3f(6.0f, 10.0f, -2.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -2.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(0.0f, 9.5f, -2.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 9.5f, -2.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -10.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 4 
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 8 
        gl.glVertex3f(6.0f, 9.5f, -10.0f); // 7
        gl.glVertex3f(6.0f, 10.0f, -10.0f); // 3

        gl.glVertex3f(0.0f, 10.0f, -2.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -2.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -2.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -2.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -10.0f); // 3
        gl.glVertex3f(6.0f, 10.0f, -2.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -2.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -10.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 4 
        gl.glVertex3f(0.0f, 10.0f, -2.0f); // 1
        gl.glVertex3f(0.0f, 9.5f, -2.0f); // 5
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 8 
        gl.glEnd();

        // Pared 1 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 0.0f, -10.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -2.0f);// 2
        gl.glVertex3f(6.5f, 5.0f, -2.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 4

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.0f, -10.0f);// 5
        gl.glVertex3f(6.0f, 0.0f, -2.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(6.0f, 5.0f, -2.0f);// 7
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 4
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 8
        gl.glVertex3f(6.0f, 5.0f, -2.0f);// 7
        gl.glVertex3f(6.5f, 5.0f, -2.0f);// 3

        gl.glVertex3f(6.5f, 0.0f, -10.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -2.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -2.0f);// 6
        gl.glVertex3f(6.0f, 0.0f, -10.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 5.0f, -2.0f);// 3
        gl.glVertex3f(6.5f, 0.0f, -2.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -2.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -2.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 4
        gl.glVertex3f(6.5f, 0.0f, -10.0f);// 1
        gl.glVertex3f(6.0f, 0.0f, -10.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 8

        // Pared 1 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -2.0f);// 2
        gl.glVertex3f(6.5f, 10.0f, -2.0f);// 3
        gl.glVertex3f(6.5f, 10.0f, -10.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -2.0f);// 6
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 10.0f, -2.0f);// 7
        gl.glVertex3f(6.0f, 10.0f, -10.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 10.0f, -10.0f);// 4
        gl.glVertex3f(6.0f, 10.0f, -10.0f);// 8
        gl.glVertex3f(6.0f, 10.0f, -2.0f);// 7
        gl.glVertex3f(6.5f, 10.0f, -2.0f);// 3

        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -2.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -2.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 10.0f, -2.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -2.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -2.0f);// 6
        gl.glVertex3f(6.0f, 10.0f, -2.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 10.0f, -10.0f);// 4
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 1
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 5
        gl.glVertex3f(6.0f, 10.0f, -10.0f);// 8
        gl.glEnd();

        // Pared 2 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 0.0f, -10.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -2.0f);// 2
        gl.glVertex3f(-6.5f, 5.0f, -2.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 4

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.0f, -10.0f);// 5
        gl.glVertex3f(-6.0f, 0.0f, -2.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -2.0f);// 7
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 4
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 8
        gl.glVertex3f(-6.0f, 5.0f, -2.0f);// 7
        gl.glVertex3f(-6.5f, 5.0f, -2.0f);// 3

        gl.glVertex3f(-6.5f, 0.0f, -10.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -2.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -2.0f);// 6
        gl.glVertex3f(-6.0f, 0.0f, -10.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 5.0f, -2.0f);// 3
        gl.glVertex3f(-6.5f, 0.0f, -2.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -2.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -2.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 4
        gl.glVertex3f(-6.5f, 0.0f, -10.0f);// 1
        gl.glVertex3f(-6.0f, 0.0f, -10.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 8
        gl.glEnd();

        // Pared 2 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -2.0f);// 2
        gl.glVertex3f(-6.5f, 10.0f, -2.0f);// 3
        gl.glVertex3f(-6.5f, 10.0f, -10.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -2.0f);// 6
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 10.0f, -2.0f);// 7
        gl.glVertex3f(-6.0f, 10.0f, -10.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 10.0f, -10.0f);// 4
        gl.glVertex3f(-6.0f, 10.0f, -10.0f);// 8
        gl.glVertex3f(-6.0f, 10.0f, -2.0f);// 7
        gl.glVertex3f(-6.5f, 10.0f, -2.0f);// 3

        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -2.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -2.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 10.0f, -2.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -2.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -2.0f);// 6
        gl.glVertex3f(-6.0f, 10.0f, -2.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 10.0f, -10.0f);// 4
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 1
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 5
        gl.glVertex3f(-6.0f, 10.0f, -10.0f);// 8
        gl.glEnd();

        //CAJA 2
        //Suelo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -18.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 3
        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -10.0f); // 5
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -18.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 0.0f, -18.0f); // 4 
        gl.glVertex3f(-6.0f, 0.5f, -18.0f); // 8 
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 7
        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 3

        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 6
        gl.glVertex3f(-6.0f, 0.5f, -10.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 0.0f, -18.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(-6.0f, 0.5f, -10.0f); // 5
        gl.glVertex3f(-6.0f, 0.5f, -18.0f); // 8 
        gl.glEnd();

        //Suelo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -18.0f); // 2
        gl.glVertex3f(6.0f, 0.0f, -10.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.5f, -10.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -18.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 4 
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 8 
        gl.glVertex3f(6.0f, 0.5f, -18.0f); // 7
        gl.glVertex3f(6.0f, 0.0f, -18.0f); // 3

        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -10.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 0.0f, -18.0f); // 3
        gl.glVertex3f(6.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -10.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -18.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 0.5f, -10.0f); // 5
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 8 
        gl.glEnd();

        //Techo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 10.0f, -18.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 9.5f, -10.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 9.5f, -18.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 10.0f, -18.0f); // 4 
        gl.glVertex3f(-6.0f, 9.5f, -18.0f); // 8 
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 7
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 3

        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 6
        gl.glVertex3f(-6.0f, 9.5f, -10.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -18.0f); // 4 
        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 1
        gl.glVertex3f(-6.0f, 9.5f, -10.0f); // 5
        gl.glVertex3f(-6.0f, 9.5f, -18.0f); // 8 
        gl.glEnd();

        //Techo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -18.0f); // 2
        gl.glVertex3f(6.0f, 10.0f, -10.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 4 

        gl.glColor3f(0.0f, 1.0f, 0.0f);    // GREEN

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 9.5f, -10.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -18.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 4 
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 8 
        gl.glVertex3f(6.0f, 9.5f, -18.0f); // 7
        gl.glVertex3f(6.0f, 10.0f, -18.0f); // 3

        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -10.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -10.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -18.0f); // 3
        gl.glVertex3f(6.0f, 10.0f, -10.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -10.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -18.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 4 
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 9.5f, -10.0f); // 5
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 8 
        gl.glEnd();

        // Pared 1 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 0.0f, -18.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -10.0f);// 2
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 4

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.0f, -18.0f);// 5
        gl.glVertex3f(6.0f, 0.0f, -10.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 7
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 4
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 8
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 7
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 3

        gl.glVertex3f(6.5f, 0.0f, -18.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -10.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -10.0f);// 6
        gl.glVertex3f(6.0f, 0.0f, -18.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 3
        gl.glVertex3f(6.5f, 0.0f, -10.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -10.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 4
        gl.glVertex3f(6.5f, 0.0f, -18.0f);// 1
        gl.glVertex3f(6.0f, 0.0f, -18.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 8

        // Pared 1 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 2
        gl.glVertex3f(6.5f, 10.0f, -10.0f);// 3
        gl.glVertex3f(6.5f, 10.0f, -18.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 6
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 10.0f, -10.0f);// 7
        gl.glVertex3f(6.0f, 10.0f, -18.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 10.0f, -18.0f);// 4
        gl.glVertex3f(6.0f, 10.0f, -18.0f);// 8
        gl.glVertex3f(6.0f, 10.0f, -10.0f);// 7
        gl.glVertex3f(6.5f, 10.0f, -10.0f);// 3

        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 10.0f, -10.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -10.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -10.0f);// 6
        gl.glVertex3f(6.0f, 10.0f, -10.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 10.0f, -18.0f);// 4
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 1
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 5
        gl.glVertex3f(6.0f, 10.0f, -18.0f);// 8
        gl.glEnd();

        // Pared 2 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 0.0f, -18.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -10.0f);// 2
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 4

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.0f, -18.0f);// 5
        gl.glVertex3f(-6.0f, 0.0f, -10.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 7
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 4
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 8
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 7
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 3

        gl.glVertex3f(-6.5f, 0.0f, -18.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -10.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -10.0f);// 6
        gl.glVertex3f(-6.0f, 0.0f, -18.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 3
        gl.glVertex3f(-6.5f, 0.0f, -10.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -10.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 4
        gl.glVertex3f(-6.5f, 0.0f, -18.0f);// 1
        gl.glVertex3f(-6.0f, 0.0f, -18.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 8
        gl.glEnd();

        // Pared 2 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 2
        gl.glVertex3f(-6.5f, 10.0f, -10.0f);// 3
        gl.glVertex3f(-6.5f, 10.0f, -18.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 6
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 10.0f, -10.0f);// 7
        gl.glVertex3f(-6.0f, 10.0f, -18.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 10.0f, -18.0f);// 4
        gl.glVertex3f(-6.0f, 10.0f, -18.0f);// 8
        gl.glVertex3f(-6.0f, 10.0f, -10.0f);// 7
        gl.glVertex3f(-6.5f, 10.0f, -10.0f);// 3

        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 10.0f, -10.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -10.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -10.0f);// 6
        gl.glVertex3f(-6.0f, 10.0f, -10.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 10.0f, -18.0f);// 4
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 1
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 5
        gl.glVertex3f(-6.0f, 10.0f, -18.0f);// 8
        gl.glEnd();

        //CAJA 3
        //Suelo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 3
        gl.glVertex3f(-6.0f, 0.0f, -18.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -26.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 0.5f, -18.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 0.0f, -18.0f); // 4 
        gl.glVertex3f(-6.0f, 0.5f, -18.0f); // 8 
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 7
        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 3

        gl.glVertex3f(-6.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(-6.0f, 0.5f, -26.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 0.0f, -18.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(-6.0f, 0.5f, -18.0f); // 5
        gl.glVertex3f(-6.0f, 0.5f, -26.0f); // 8 
        gl.glEnd();

        //Suelo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(6.0f, 0.0f, -18.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.5f, -18.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -26.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 4 
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 8 
        gl.glVertex3f(6.0f, 0.5f, -26.0f); // 7
        gl.glVertex3f(6.0f, 0.0f, -26.0f); // 3

        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -18.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -18.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 0.0f, -26.0f); // 3
        gl.glVertex3f(6.0f, 0.0f, -18.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -18.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -26.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -18.0f); // 1
        gl.glVertex3f(0.0f, 0.5f, -18.0f); // 5
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 8 
        gl.glEnd();

        //Techo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 10.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -18.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 9.5f, -18.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 9.5f, -26.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 10.0f, -26.0f); // 4 
        gl.glVertex3f(-6.0f, 9.5f, -26.0f); // 8 
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 7
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 3

        gl.glVertex3f(-6.0f, 10.0f, -18.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 6
        gl.glVertex3f(-6.0f, 9.5f, -18.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -26.0f); // 4 
        gl.glVertex3f(-6.0f, 10.0f, -18.0f); // 1
        gl.glVertex3f(-6.0f, 9.5f, -18.0f); // 5
        gl.glVertex3f(-6.0f, 9.5f, -26.0f); // 8 
        gl.glEnd();

        //Techo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -26.0f); // 2
        gl.glVertex3f(6.0f, 10.0f, -18.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE DARK
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 9.5f, -18.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -26.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE DARK
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 4 
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 8 
        gl.glVertex3f(6.0f, 9.5f, -26.0f); // 7
        gl.glVertex3f(6.0f, 10.0f, -26.0f); // 3

        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -18.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -18.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -26.0f); // 3
        gl.glVertex3f(6.0f, 10.0f, -18.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -18.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -26.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 4 
        gl.glVertex3f(0.0f, 10.0f, -18.0f); // 1
        gl.glVertex3f(0.0f, 9.5f, -18.0f); // 5
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 8 
        gl.glEnd();

        // Pared 1 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 0.0f, -26.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -18.0f);// 2
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 4

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.0f, -26.0f);// 5
        gl.glVertex3f(6.0f, 0.0f, -18.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 7
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 4
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 8
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 7
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 3

        gl.glVertex3f(6.5f, 0.0f, -26.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -18.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -18.0f);// 6
        gl.glVertex3f(6.0f, 0.0f, -26.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 3
        gl.glVertex3f(6.5f, 0.0f, -18.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -18.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 4
        gl.glVertex3f(6.5f, 0.0f, -26.0f);// 1
        gl.glVertex3f(6.0f, 0.0f, -26.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 8
        gl.glEnd();

        // Pared 1 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 2
        gl.glVertex3f(6.5f, 10.0f, -18.0f);// 3
        gl.glVertex3f(6.5f, 10.0f, -26.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 6
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 10.0f, -18.0f);// 7
        gl.glVertex3f(6.0f, 10.0f, -26.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 10.0f, -26.0f);// 4
        gl.glVertex3f(6.0f, 10.0f, -26.0f);// 8
        gl.glVertex3f(6.0f, 10.0f, -18.0f);// 7
        gl.glVertex3f(6.5f, 10.0f, -18.0f);// 3

        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 10.0f, -18.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -18.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -18.0f);// 6
        gl.glVertex3f(6.0f, 10.0f, -18.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 10.0f, -26.0f);// 4
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 1
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 5
        gl.glVertex3f(6.0f, 10.0f, -26.0f);// 8
        gl.glEnd();

        // Pared 2 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 0.0f, -26.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -18.0f);// 2
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 4

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.0f, -26.0f);// 1
        gl.glVertex3f(-6.0f, 0.0f, -18.0f);// 2
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 3
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 4

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 4
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 8
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 7
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 3

        gl.glVertex3f(-6.5f, 0.0f, -26.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -18.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -18.0f);// 6
        gl.glVertex3f(-6.0f, 0.0f, -26.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 3
        gl.glVertex3f(-6.5f, 0.0f, -18.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -18.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 4
        gl.glVertex3f(-6.5f, 0.0f, -26.0f);// 1
        gl.glVertex3f(-6.0f, 0.0f, -26.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 8
        gl.glEnd();

        // Pared 2 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 2
        gl.glVertex3f(-6.5f, 10.0f, -18.0f);// 3
        gl.glVertex3f(-6.5f, 10.0f, -26.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 6
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 10.0f, -18.0f);// 7
        gl.glVertex3f(-6.0f, 10.0f, -26.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 10.0f, -26.0f);// 4
        gl.glVertex3f(-6.0f, 10.0f, -26.0f);// 8
        gl.glVertex3f(-6.0f, 10.0f, -18.0f);// 7
        gl.glVertex3f(-6.5f, 10.0f, -18.0f);// 3

        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 10.0f, -18.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -18.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -18.0f);// 6
        gl.glVertex3f(-6.0f, 10.0f, -18.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 10.0f, -26.0f);// 4
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 1
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 5
        gl.glVertex3f(-6.0f, 10.0f, -26.0f);// 8
        gl.glEnd();

        //CAJA 4
        //Suelo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 3
        gl.glVertex3f(-6.0f, 0.0f, -26.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 0.5f, -34.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -26.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 0.0f, -34.0f); // 4 
        gl.glVertex3f(-6.0f, 0.5f, -34.0f); // 8 
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 7
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 3

        gl.glVertex3f(-6.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(-6.0f, 0.5f, -26.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 0.0f, -34.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(-6.0f, 0.5f, -26.0f); // 5
        gl.glVertex3f(-6.0f, 0.5f, -34.0f); // 8 
        gl.glEnd();

        //Suelo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -34.0f); // 2
        gl.glVertex3f(6.0f, 0.0f, -26.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 5
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -34.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 4 
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 8 
        gl.glVertex3f(6.0f, 0.5f, -34.0f); // 7
        gl.glVertex3f(6.0f, 0.0f, -34.0f); // 3

        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 0.0f, -34.0f); // 3
        gl.glVertex3f(6.0f, 0.0f, -26.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -26.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -34.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 0.5f, -26.0f); // 5
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 8 
        gl.glEnd();

        //Techo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -26.0f); // 4 

        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 9.5f, -26.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 7
        gl.glColor3f(0.1f, 0.0f, 0.1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 9.5f, -34.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 10.0f, -34.0f); // 4 
        gl.glVertex3f(-6.0f, 9.5f, -34.0f); // 8 
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 7
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 3

        gl.glVertex3f(-6.0f, 10.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 6
        gl.glVertex3f(-6.0f, 9.5f, -26.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -34.0f); // 4 
        gl.glVertex3f(-6.0f, 10.0f, -26.0f); // 1
        gl.glVertex3f(-6.0f, 9.5f, -26.0f); // 5
        gl.glVertex3f(-6.0f, 9.5f, -34.0f); // 8 
        gl.glEnd();

        //Techo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(6.0f, 10.0f, -26.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE

        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 5
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 9.5f, -26.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -34.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 4 
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 8 
        gl.glVertex3f(6.0f, 9.5f, -34.0f); // 7
        gl.glVertex3f(6.0f, 10.0f, -34.0f); // 3

        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -26.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -26.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -34.0f); // 3
        gl.glVertex3f(6.0f, 10.0f, -26.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -26.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -34.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 4 
        gl.glVertex3f(0.0f, 10.0f, -26.0f); // 1
        gl.glVertex3f(0.0f, 9.5f, -26.0f); // 5
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 8 
        gl.glEnd();

        // Pared 1 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 0.0f, -34.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -26.0f);// 2
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -34.0f);// 4

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.0f, -34.0f);// 5
        gl.glVertex3f(6.0f, 0.0f, -26.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 7
        gl.glVertex3f(6.0f, 5.0f, -34.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 5.0f, -34.0f);// 4
        gl.glVertex3f(6.0f, 5.0f, -34.0f);// 8
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 7
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 3

        gl.glVertex3f(6.5f, 0.0f, -34.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -26.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -26.0f);// 6
        gl.glVertex3f(6.0f, 0.0f, -34.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 3
        gl.glVertex3f(6.5f, 0.0f, -26.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -26.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 5.0f, -34.0f);// 4
        gl.glVertex3f(6.5f, 0.0f, -34.0f);// 1
        gl.glVertex3f(6.0f, 0.0f, -34.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -34.0f);// 8

        // Pared 1 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 5.0f, -34.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 2
        gl.glVertex3f(6.5f, 10.0f, -26.0f);// 3
        gl.glVertex3f(6.5f, 10.0f, -34.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(6.0f, 5.0f, -34.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 6
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 10.0f, -26.0f);// 7
        gl.glVertex3f(6.0f, 10.0f, -34.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 10.0f, -34.0f);// 4
        gl.glVertex3f(6.0f, 10.0f, -34.0f);// 8
        gl.glVertex3f(6.0f, 10.0f, -26.0f);// 7
        gl.glVertex3f(6.5f, 10.0f, -26.0f);// 3

        gl.glVertex3f(6.5f, 5.0f, -34.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -34.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 10.0f, -26.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -26.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -26.0f);// 6
        gl.glVertex3f(6.0f, 10.0f, -26.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 10.0f, -34.0f);// 4
        gl.glVertex3f(6.5f, 5.0f, -34.0f);// 1
        gl.glVertex3f(6.0f, 5.0f, -34.0f);// 5
        gl.glVertex3f(6.0f, 10.0f, -34.0f);// 8
        gl.glEnd();

        // Pared 2 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 0.0f, -34.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -26.0f);// 2
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 4

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.0f, -34.0f);// 5
        gl.glVertex3f(-6.0f, 0.0f, -26.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 7
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 4
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 8
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 7
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 3

        gl.glVertex3f(-6.5f, 0.0f, -34.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -26.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -26.0f);// 6
        gl.glVertex3f(-6.0f, 0.0f, -34.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 3
        gl.glVertex3f(-6.5f, 0.0f, -26.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -26.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 4
        gl.glVertex3f(-6.5f, 0.0f, -34.0f);// 1
        gl.glVertex3f(-6.0f, 0.0f, -34.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 8
        gl.glEnd();

        // Pared 2 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -24.0f);// 2
        gl.glVertex3f(-6.5f, 10.0f, -24.0f);// 3
        gl.glVertex3f(-6.5f, 10.0f, -34.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 6
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 10.0f, -26.0f);// 7
        gl.glVertex3f(-6.0f, 10.0f, -34.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 10.0f, -34.0f);// 4
        gl.glVertex3f(-6.0f, 10.0f, -34.0f);// 8
        gl.glVertex3f(-6.0f, 10.0f, -26.0f);// 7
        gl.glVertex3f(-6.5f, 10.0f, -26.0f);// 3

        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 10.0f, -26.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -26.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -26.0f);// 6
        gl.glVertex3f(-6.0f, 10.0f, -26.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 10.0f, -34.0f);// 4
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 1
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 5
        gl.glVertex3f(-6.0f, 10.0f, -34.0f);// 8
        gl.glEnd();

        //CAJA 5
        //Suelo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -42.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 3
        gl.glVertex3f(-6.0f, 0.0f, -34.0f); // 4 

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 0.5f, -34.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 7
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.5f, -42.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 0.0f, -42.0f); // 4 
        gl.glVertex3f(-6.0f, 0.5f, -42.0f); // 8 
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 7
        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 3

        gl.glVertex3f(-6.0f, 0.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(-6.0f, 0.5f, -34.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 0.0f, -42.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -34.0f); // 1
        gl.glVertex3f(-6.0f, 0.5f, -34.0f); // 5
        gl.glVertex3f(-6.0f, 0.5f, -42.0f); // 8 
        gl.glEnd();

        //Suelo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -42.0f); // 2
        gl.glVertex3f(6.0f, 0.0f, -34.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 5
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(6.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -42.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 4 
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 8 
        gl.glVertex3f(6.0f, 0.5f, -42.0f); // 7
        gl.glVertex3f(6.0f, 0.0f, -42.0f); // 3

        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -34.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 0.0f, -42.0f); // 3
        gl.glVertex3f(6.0f, 0.0f, -34.0f); // 2
        gl.glVertex3f(6.0f, 0.5f, -34.0f); // 6
        gl.glVertex3f(6.0f, 0.5f, -42.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 0.0f, -42.0f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 0.5f, -34.0f); // 5
        gl.glVertex3f(0.0f, 0.5f, -42.0f); // 8 
        gl.glEnd();

        //Techo principal part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED
        gl.glVertex3f(-6.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -42.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -42.0f); // 4 

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 9.5f, -34.0f); // 5
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -42.0f); // 7
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 9.5f, -42.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(-6.0f, 10.0f, -42.0f); // 4 
        gl.glVertex3f(-6.0f, 9.5f, -42.0f); // 8 
        gl.glVertex3f(0.0f, 9.5f, -42.0f); // 7
        gl.glVertex3f(0.0f, 10.0f, -42.0f); // 3

        gl.glVertex3f(-6.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 6
        gl.glVertex3f(-6.0f, 9.5f, -34.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -42.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -42.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -42.0f); // 4 
        gl.glVertex3f(-6.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(-6.0f, 9.5f, -34.0f); // 5
        gl.glVertex3f(-6.0f, 9.5f, -42.0f); // 8 
        gl.glEnd();

        //Techo principal part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 10.0f, -42.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -42.0f); // 2
        gl.glVertex3f(6.0f, 10.0f, -34.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 4 

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 5
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(6.0f, 9.5f, -34.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -42.0f); // 7
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(0.0f, 9.5f, -42.0f); // 8 

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE

        gl.glVertex3f(0.0f, 10.0f, -42.0f); // 4 
        gl.glVertex3f(0.0f, 9.5f, -42.0f); // 8 
        gl.glVertex3f(6.0f, 9.5f, -42.0f); // 7
        gl.glVertex3f(6.0f, 10.0f, -42.0f); // 3

        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(6.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -34.0f); // 6
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -42.0f); // 3
        gl.glVertex3f(6.0f, 10.0f, -34.0f); // 2
        gl.glVertex3f(6.0f, 9.5f, -34.0f); // 6
        gl.glVertex3f(6.0f, 9.5f, -42.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -42.0f); // 4 
        gl.glVertex3f(0.0f, 10.0f, -34.0f); // 1
        gl.glVertex3f(0.0f, 9.5f, -34.0f); // 5
        gl.glVertex3f(0.0f, 9.5f, -42.0f); // 8 
        gl.glEnd();

        // Pared 1 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 0.0f, -42.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -34.0f);// 2
        gl.glVertex3f(6.5f, 5.0f, -34.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -42.0f);// 4

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(6.0f, 0.0f, -42.0f);// 5
        gl.glVertex3f(6.0f, 0.0f, -34.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(6.0f, 5.0f, -34.0f);// 7
        gl.glVertex3f(6.0f, 5.0f, -42.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 5.0f, -42.0f);// 4
        gl.glVertex3f(6.0f, 5.0f, -42.0f);// 8
        gl.glVertex3f(6.0f, 5.0f, -34.0f);// 7
        gl.glVertex3f(6.5f, 5.0f, -34.0f);// 3

        gl.glVertex3f(6.5f, 0.0f, -42.0f);// 1
        gl.glVertex3f(6.5f, 0.0f, -34.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -34.0f);// 6
        gl.glVertex3f(6.0f, 0.0f, -42.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 5.0f, -34.0f);// 3
        gl.glVertex3f(6.5f, 0.0f, -34.0f);// 2
        gl.glVertex3f(6.0f, 0.0f, -34.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -34.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 5.0f, -42.0f);// 4
        gl.glVertex3f(6.5f, 0.0f, -42.0f);// 1
        gl.glVertex3f(6.0f, 0.0f, -42.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -42.0f);// 8

        // Pared 1 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(6.5f, 5.0f, -42.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -34.0f);// 2
        gl.glVertex3f(6.5f, 10.0f, -34.0f);// 3
        gl.glVertex3f(6.5f, 10.0f, -42.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE
        gl.glVertex3f(6.0f, 5.0f, -42.0f);// 5
        gl.glVertex3f(6.0f, 5.0f, -34.0f);// 6
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(6.0f, 10.0f, -34.0f);// 7
        gl.glVertex3f(6.0f, 10.0f, -42.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(6.5f, 10.0f, -42.0f);// 4
        gl.glVertex3f(6.0f, 10.0f, -42.0f);// 8
        gl.glVertex3f(6.0f, 10.0f, -34.0f);// 7
        gl.glVertex3f(6.5f, 10.0f, -34.0f);// 3

        gl.glVertex3f(6.5f, 5.0f, -42.0f);// 1
        gl.glVertex3f(6.5f, 5.0f, -34.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -34.0f);// 6
        gl.glVertex3f(6.0f, 5.0f, -42.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(6.5f, 10.0f, -34.0f);// 3
        gl.glVertex3f(6.5f, 5.0f, -34.0f);// 2
        gl.glVertex3f(6.0f, 5.0f, -34.0f);// 6
        gl.glVertex3f(6.0f, 10.0f, -34.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(6.5f, 10.0f, -42.0f);// 4
        gl.glVertex3f(6.5f, 5.0f, -42.0f);// 1
        gl.glVertex3f(6.0f, 5.0f, -42.0f);// 5
        gl.glVertex3f(6.0f, 10.0f, -42.0f);// 8
        gl.glEnd();

        // Pared 2 part1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 0.0f, -42.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -34.0f);// 2
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 4

        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK
        gl.glVertex3f(-6.0f, 0.0f, -42.0f);// 5
        gl.glVertex3f(-6.0f, 0.0f, -34.0f);// 6
        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 

        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 7
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 4
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 8
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 7
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 3

        gl.glVertex3f(-6.5f, 0.0f, -42.0f);// 1
        gl.glVertex3f(-6.5f, 0.0f, -34.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -34.0f);// 6
        gl.glVertex3f(-6.0f, 0.0f, -42.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 3
        gl.glVertex3f(-6.5f, 0.0f, -34.0f);// 2
        gl.glVertex3f(-6.0f, 0.0f, -34.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 4
        gl.glVertex3f(-6.5f, 0.0f, -42.0f);// 1
        gl.glVertex3f(-6.0f, 0.0f, -42.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 8
        gl.glEnd();

        // Pared 2 part2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 2
        gl.glVertex3f(-6.5f, 10.0f, -34.0f);// 3
        gl.glVertex3f(-6.5f, 10.0f, -42.0f);// 4

        gl.glColor3f(1f, 0.0f, 1f);    // PURPLE 
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 5
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 6
        gl.glColor3f(.1f, 0.0f, .1f);    // PURPLE DARK

        gl.glVertex3f(-6.0f, 10.0f, -34.0f);// 7
        gl.glVertex3f(-6.0f, 10.0f, -42.0f);// 8

        gl.glColor3f(0.0f, 0.0f, 1.0f);    // BLUE
        gl.glVertex3f(-6.5f, 10.0f, -42.0f);// 4
        gl.glVertex3f(-6.0f, 10.0f, -42.0f);// 8
        gl.glVertex3f(-6.0f, 10.0f, -34.0f);// 7
        gl.glVertex3f(-6.5f, 10.0f, -34.0f);// 3

        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 1
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 6
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 5

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK
        gl.glVertex3f(-6.5f, 10.0f, -34.0f);// 3
        gl.glVertex3f(-6.5f, 5.0f, -34.0f);// 2
        gl.glVertex3f(-6.0f, 5.0f, -34.0f);// 6
        gl.glVertex3f(-6.0f, 10.0f, -34.0f);// 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        gl.glVertex3f(-6.5f, 10.0f, -42.0f);// 4
        gl.glVertex3f(-6.5f, 5.0f, -42.0f);// 1
        gl.glVertex3f(-6.0f, 5.0f, -42.0f);// 5
        gl.glVertex3f(-6.0f, 10.0f, -42.0f);// 8
        gl.glEnd();

        //OBSTACULOS 1
        //Pared 1 part 1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 5.0f, -10.0f); // 3
        gl.glVertex3f(-6.0f, 5.0f, -10.0f); // 4 

        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 0.0f, -9.5f); // 5
        gl.glColor3f(0.0f, 1.0f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 0.0f, -9.5f); // 6
        gl.glVertex3f(0.0f, 5.0f, -9.5f); // 7
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 5.0f, -9.5f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 5.0f, -10.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -9.5f); // 6
        gl.glVertex3f(0.0f, 5.0f, -9.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 5.0f, -10.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -10.0f); // 1
        gl.glVertex3f(-6.0f, 0.0f, -9.5f); // 5
        gl.glVertex3f(-6.0f, 5.0f, -9.5f); // 8
        gl.glEnd();

        //Pared 1 part 2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 5.0f, -10.0f); // 1
        gl.glVertex3f(0.0f, 5.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 4 

        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 5.0f, -9.5f); // 5
        gl.glColor3f(0.0f, 1.0f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 5.0f, -9.5f); // 6
        gl.glVertex3f(0.0f, 10.0f, -9.5f); // 7
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 10.0f, -9.5f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -10.0f); // 3
        gl.glVertex3f(0.0f, 5.0f, -10.0f); // 2
        gl.glVertex3f(0.0f, 5.0f, -9.5f); // 6
        gl.glVertex3f(0.0f, 10.0f, -9.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 10.0f, -10.0f); // 4 
        gl.glVertex3f(-6.0f, 5.0f, -10.0f); // 1
        gl.glVertex3f(-6.0f, 5.0f, -9.5f); // 5
        gl.glVertex3f(-6.0f, 10.0f, -9.5f); // 8
        gl.glEnd();

        //Pared 2 part 1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -20.0f); // 1
        gl.glVertex3f(6.0f, 0.0f, -20.0f); // 2
        gl.glVertex3f(6.0f, 5.0f, -20.0f); // 3
        gl.glVertex3f(0.0f, 5.0f, -20.0f); // 4 

        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 0.0f, -19.5f); // 5
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(6.0f, 0.0f, -19.5f); // 6
        gl.glVertex3f(6.0f, 5.0f, -19.5f); // 7
        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 5.0f, -19.5f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 5.0f, -20.0f); // 3
        gl.glVertex3f(6.0f, 0.0f, -20.0f); // 2
        gl.glVertex3f(6.0f, 0.0f, -19.5f); // 6
        gl.glVertex3f(6.0f, 5.0f, -19.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 5.0f, -20.0f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -20.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -19.5f); // 5
        gl.glVertex3f(0.0f, 5.0f, -19.5f); // 8
        gl.glEnd();

        //Pared 2 part 2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 5.0f, -20.0f); // 1
        gl.glVertex3f(6.0f, 5.0f, -20.0f); // 2
        gl.glVertex3f(6.0f, 10.0f, -20.0f); // 3
        gl.glVertex3f(0.0f, 10.0f, -20.0f); // 4 

        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 5.0f, -19.5f); // 5
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(6.0f, 5.0f, -19.5f); // 6
        gl.glVertex3f(6.0f, 10.0f, -19.5f); // 7
        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 10.0f, -19.5f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -20.0f); // 3
        gl.glVertex3f(6.0f, 5.0f, -20.0f); // 2
        gl.glVertex3f(6.0f, 5.0f, -19.5f); // 6
        gl.glVertex3f(6.0f, 10.0f, -19.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -20.0f); // 4 
        gl.glVertex3f(0.0f, 5.0f, -20.0f); // 1
        gl.glVertex3f(0.0f, 5.0f, -19.5f); // 5
        gl.glVertex3f(0.0f, 10.0f, -19.5f); // 8
        gl.glEnd();

        //OBSTACULOS 2
        //Pared 1 part 1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 0.0f, -30.0f); // 1
        gl.glVertex3f(0.0f, 0.0f, -30.0f); // 2
        gl.glVertex3f(0.0f, 5.0f, -30.0f); // 3
        gl.glVertex3f(-6.0f, 5.0f, -30.0f); // 4 

        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 0.0f, -29.5f); // 5
        gl.glColor3f(0.0f, 1.0f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 0.0f, -29.5f); // 6
        gl.glVertex3f(0.0f, 5.0f, -29.5f); // 7
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 5.0f, -29.5f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 5.0f, -30.0f); // 3
        gl.glVertex3f(0.0f, 0.0f, -30.0f); // 2
        gl.glVertex3f(0.0f, 0.0f, -29.5f); // 6
        gl.glVertex3f(0.0f, 5.0f, -29.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(-6.0f, 5.0f, -30.0f); // 4 
        gl.glVertex3f(-6.0f, 0.0f, -30.0f); // 1
        gl.glVertex3f(-6.0f, 0.0f, -29.5f); // 5
        gl.glVertex3f(-6.0f, 5.0f, -29.5f); // 8
        gl.glEnd();

        //Pared 1 part 2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(-6.0f, 5.0f, -30.0f); // 1
        gl.glVertex3f(0.0f, 5.0f, -30.0f); // 2
        gl.glVertex3f(0.0f, 10.0f, -30.0f); // 3
        gl.glVertex3f(-6.0f, 10.0f, -30.0f); // 4 

        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 5.0f, -29.5f); // 5
        gl.glColor3f(0.0f, 1.0f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 5.0f, -29.5f); // 6
        gl.glVertex3f(0.0f, 10.0f, -29.5f); // 7
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(-6.0f, 10.0f, -29.5f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(0.0f, 10.0f, -30.0f); // 3
        gl.glVertex3f(0.0f, 5.0f, -30.0f); // 2
        gl.glVertex3f(0.0f, 5.0f, -29.5f); // 6
        gl.glVertex3f(0.0f, 10.0f, -29.5f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW 

        gl.glVertex3f(-6.0f, 10.0f, -30.0f); // 4 
        gl.glVertex3f(-6.0f, 5.0f, -30.0f); // 1
        gl.glVertex3f(-6.0f, 5.0f, -29.5f); // 5
        gl.glVertex3f(-6.0f, 10.0f, -29.5f); // 8
        gl.glEnd();

        //Pared 2 part 1
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 0.0f, -37.5f); // 1
        gl.glVertex3f(6.0f, 0.0f, -37.5f); // 2
        gl.glVertex3f(6.0f, 5.0f, -37.5f); // 3
        gl.glVertex3f(0.0f, 5.0f, -37.5f); // 4 

        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 0.0f, -36.f); // 5
        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(6.0f, 0.0f, -36.f); // 6
        gl.glVertex3f(6.0f, 5.0f, -36.f); // 7
        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 5.0f, -36.f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 5.0f, -37.5f); // 3
        gl.glVertex3f(6.0f, 0.0f, -37.5f); // 2
        gl.glVertex3f(6.0f, 0.0f, -36.0f); // 6
        gl.glVertex3f(6.0f, 5.0f, -36.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 5.0f, -37.5f); // 4 
        gl.glVertex3f(0.0f, 0.0f, -37.5f); // 1
        gl.glVertex3f(0.0f, 0.0f, -36.f); // 5
        gl.glVertex3f(0.0f, 5.0f, -36.f); // 8
        gl.glEnd();

        //Pared 2 part 2
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);    // RED

        gl.glVertex3f(0.0f, 5.0f, -37.5f); // 
        gl.glVertex3f(6.0f, 5.0f, -37.5f); // 2
        gl.glVertex3f(6.0f, 10.0f, -37.5f); // 3
        gl.glVertex3f(0.0f, 10.0f, -37.5f); // 4 

        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 5.0f, -36.f); // 5

        gl.glColor3f(0.0f, 0.5f, 0.0f);    // GREEN DARK
        gl.glVertex3f(6.0f, 5.0f, -36.f); // 6
        gl.glVertex3f(6.0f, 10.0f, -36.f); // 7

        gl.glColor3f(0.0f, 1f, 0.0f);    // GREEN 
        gl.glVertex3f(0.0f, 10.0f, -36.f); // 8

        gl.glColor3f(1.0f, 0.0f, 1.0f);    // PINK

        gl.glVertex3f(6.0f, 10.0f, -37.5f); // 3
        gl.glVertex3f(6.0f, 5.0f, -37.5f); // 2
        gl.glVertex3f(6.0f, 5.0f, -36.0f); // 6
        gl.glVertex3f(6.0f, 10.0f, -36.0f); // 7

        gl.glColor3f(1.0f, 1.0f, 0.0f); // YELLOW

        gl.glVertex3f(0.0f, 10.0f, -37.5f); // 4 
        gl.glVertex3f(0.0f, 5.0f, -37.5f); // 1
        gl.glVertex3f(0.0f, 5.0f, -36.f); // 5
        gl.glVertex3f(0.0f, 10.0f, -36.f); // 8
        gl.glEnd();
        gl.glPopMatrix();

        gl.glPushMatrix();
        float sX = (float) (tx + cx * -4);
        float sZ = (float) (tz + cz * -4);

        gl.glPushMatrix();
        gl.glTranslatef(sX, (float) ty, sZ);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // WHITE
        glut.glutWireCube(1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef((float) ((float) sX - .2), (float) ty, sZ);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // WHITE
        glut.glutWireTorus(.025, 0.1, 5, 5);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef((float) ((float) sX + .2), (float) ty, sZ);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // WHITE
        glut.glutWireTorus(.025, 0.1, 5, 5);
        gl.glPopMatrix();
        gl.glPopMatrix();

        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            a -= 1.5; // Gira hacia la izquierda
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            a += 1.5; // Gira hacia la derecha
        }

        if (e.getKeyCode() == KeyEvent.VK_W) {
            tx += .5 * Math.cos(Math.toRadians(a));
            tz += .5 * Math.sin(Math.toRadians(a));
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            tx -= .5 * Math.cos(Math.toRadians(a));
            tz -= .5 * Math.sin(Math.toRadians(a));
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            tx -= 1;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            tx += 1;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            ty -= 1;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            ty += 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_T) {
            tz -= 1;
        }

        if (e.getKeyCode() == KeyEvent.VK_Y) {
            tz += 1;
        }

        if (e.getKeyCode() == KeyEvent.VK_U) {
            rx -= 1;
        }

        if (e.getKeyCode() == KeyEvent.VK_I) {
            rx += 1;
        }

        if (e.getKeyCode() == KeyEvent.VK_O) {
            ry -= 1;
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {
            ry += 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            rz -= 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_H) {
            rz += 1;
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            tx = tz = 3;
            ty = 2;
            rx = ry = rz = 0;
            a = -90;
        }

    }

    public void keyReleased(KeyEvent e) {

    }
}
