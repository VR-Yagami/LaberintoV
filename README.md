# LaberintoV
Laberinto Personal, Materia Graficacion:
Lo importante del codigo es:

Descargar los archivos JOGL

Las variables para darle movimiento, ya se rotacion y traslacion:

/*  static double tx = 3, ty = 2, tz = 3;//Lo trasladan y se le da una posicion fija, en este se le modificara dependiendo 
las coordenadas que se le quiera dar a demas de como este construido su laberinto. */
/*  static float rx, ry, rz, a = -90; 
algo parecido es en esta parte, este es para rotal al personaje o la camara, en este caso se le roto -90 grados para que apareciera de frenete ala entrada, ustedes pueden ir rotando hasta que se muestre de frente */

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

NOTA: es necesario que lleve todas esas partes ya que es lo escencial del codigo y no importa que coordenadas tenga el laberinto siempre y cuando tenga estos trozos de codigo.
  

        
