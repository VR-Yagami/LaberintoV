# LaberintoV
Laberinto Personal, Materia Graficacion:
Lo importante del codigo es:

Descargar los archivos JOGL

Las variables para darle movimiento, ya se rotacion y traslacion:

static double tx = 3, ty = 2, tz = 3;//Lo trasladan y se le da una posicion fija, en este se le modificara dependiendo 
las coordenadas que se le quiera dar a demas de como este construido su laberinto.

static float rx, ry, rz, a = -90; 
Algo parecido es en esta parte, este es para rotal al personaje o la camara, en este caso se le roto -90 grados para que apareciera de frenete ala entrada, ustedes pueden ir rotando hasta que se muestre de frente

En la linea 75 glu.gluPerspective(45.0f, h, 1.0, 500.0);, en esta parte es la perspectiva de la camara en este caso lo que se debe de hacer es colocarle 500 para poder ver de lejos el laberinto, pueden colocarle mas o pueden hacerlo con menos.

En las lineas 83 y 84 Se ocupa colocar lo siguiente:  
GLU glu = new GLU();//Este lo tiene por defecto
GLUT glut = new GLUT();//Este se agrega para poder llamar atraer las figuras como son los cubos conos, etc, al agregar esta instancia se debe colocar su respectiva libreria si es que se esta trabajando en el IDE netbeans.

Es necesario colocar este trozo de codigo:

  gl.glTranslatef(0.0f, 0.0f, -6.0f);

//Se usa coseno y seno para poder darle direccion junto con un angulo, tambien par que tenga cierta restriccion la camara y no provoque bugs.
/       float cx = (float) Math.cos(Math.toRadians(a)),
                cz = (float) Math.sin(Math.toRadians(a));
        //Se colocan las funciones X y Z para que este pueda girar con las Teclas A y D (para que pueda rotar sin ningun problema dentro de la camara)
        
 /       glu.gluLookAt(tx, ty, tz,
                tx + cx,
                ty,
                tz + cz,
                0, 1, 0);//Camara, preferentemente se le deja en 0 en todo para que no haya ningun problema de coordenadas, para poder mover su direccion se recomienda colocarlas desde un inicio, aunque claro tambien se podria colocarlas desde aqui mismo, sin embargo, no es recomendable.

  /     gl.glRotatef(rx, 1, 0, 0);
        gl.glRotatef(ry, 0, 1, 0);
        gl.glRotatef(rz, 0, 0, 1);
        //Estas funciones son para que pueda rotar la camara sin ningun problema.

 Es muy importante tener en cuenta estos 2 trozos de codigo ya que daran posicion a tu personajq que quieras crear, si te das cuenta simplemente son distancias, se guardan en variables para X y Z de tipo flotante, se suma lo que es la camara de x y z, (cx y cz), que multiplica a -4, el -4 es para darle un acercamiento al personaje, este se puede ir probando con distintos numueros, incluso si se el numero no pasa nada pueden ser numeros positivos y negativos o simplemente quitar la multiplicacion y dicho numero:

 Ejemplo con numeros:
 /      float sX = (float) (tx + cx * -4);
        float sZ = (float) (tz + cz * -4);

Ejemplo sin numeros:
 /      float sX = (float) (tx + cx);
        float sZ = (float) (tz + cz);

//Se recomienda siempre poner sX y sZ donde pertenecen, en este caso se le asignaron numeros para darle forma de ojos al cuadro por ello tiene un -.2 y un +.2, para que esten separados.

/      gl.glPushMatrix();
       float sX = (float) (tx + cx * -4);
       float sZ = (float) (tz + cz * -4);
        
/       gl.glPushMatrix();
        gl.glTranslatef(sX, (float) ty, sZ);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // WHITE
        glut.glutWireCube(1);
        gl.glPopMatrix();

/       gl.glPushMatrix();
        gl.glTranslatef((float) ((float) sX - .2), (float) ty, sZ);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // WHITE
        glut.glutWireTorus(.025, 0.1, 5, 5);
        gl.glPopMatrix();

/       gl.glPushMatrix();
        gl.glTranslatef((float) ((float) sX + .2), (float) ty, sZ);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // WHITE
        glut.glutWireTorus(.025, 0.1, 5, 5);
        gl.glPopMatrix();
        gl.glPopMatrix();

/       Finalmente se agrega lo siguiente para poder moverlo:

/     if (e.getKeyCode() == KeyEvent.VK_A) {
            a -= 1.5; // Gira hacia la izquierda (con una velocidad de 1.5)
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            a += 1.5; // Gira hacia la derecha (con una velocidad de 1.5)
        }

  //En este caso se mueve con una velocidad de .5
/        if (e.getKeyCode() == KeyEvent.VK_W) {
            tx += .5 * Math.cos(Math.toRadians(a)); // No se reutiliza el CX ya que provoca un bug, claro se puede solucionar, pero es recomedable dejarlo asi.
            tz += .5 * Math.sin(Math.toRadians(a)); // No se reutiliza el CZ ya que provoca un bug, claro se puede solucionar, pero es recomedable dejarlo asi.
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            tx -= .5 * Math.cos(Math.toRadians(a)); // No se reutiliza el CX ya que provoca un bug, claro se puede solucionar, pero es recomedable dejarlo asi.
            tz -= .5 * Math.sin(Math.toRadians(a)); // No se reutiliza el CZ ya que provoca un bug, claro se puede solucionar, pero es recomedable dejarlo asi.
        }

//Apartir de este if se mueve con una velocidad de 1, esta parte solamente es para rotar de diferentes formas, acercar, alejar y mover de izquierda,derecha abajo y arriba la camara, asi para mostrar el laberinto construido con la inicial

  /      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            tx -= 1;
        }

  /    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            tx += 1;
        }

 /       if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            ty -= 1;
        }

 /       if (e.getKeyCode() == KeyEvent.VK_UP) {
            ty += 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_T) {
            tz -= 1;
        }

/        if (e.getKeyCode() == KeyEvent.VK_Y) {
            tz += 1;
        }

/        if (e.getKeyCode() == KeyEvent.VK_U) {
            rx -= 1;
        }

/        if (e.getKeyCode() == KeyEvent.VK_I) {
            rx += 1;
        }

/        if (e.getKeyCode() == KeyEvent.VK_O) {
            ry -= 1;
        }

/        if (e.getKeyCode() == KeyEvent.VK_P) {
            ry += 1;
        }
 /       if (e.getKeyCode() == KeyEvent.VK_G) {
            rz -= 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_H) {
            rz += 1;
        }

//En este ultimo if se recomienda colocar las variables acorde los tiene "configurado, establecido o inicializado desde un inicio"
/        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            tx = tz = 3;
            ty = 2;
            rx = ry = rz = 0;
            a = -90;
        }

NOTA: es necesario que lleve todas esas partes ya que es lo escencial del codigo y no importa que coordenadas tenga el laberinto siempre y cuando tenga estos trozos de codigo.
  

        
