# LaberintoV
Laberinto Personal, Materia Graficacion:
Lo importante del codigo es:

Las variables para darle movimiento, ya se rotacion y traslacion:

/*  static double tx = 3, ty = 2, tz = 3;//Lo trasladan y se le da una posicion fija, en este se le modificara dependiendo 
las coordenadas que se le quiera dar a demas de como este construido su laberinto. */
/*  static float rx, ry, rz, a = -90; 
algo parecido es en esta parte, este es para rotal al personaje o la camara, en este caso se le roto -90 grados para que apareciera de frenete ala entrada, ustedes pueden ir rotando hasta que se muestre de frente */

En la linea 75 glu.gluPerspective(45.0f, h, 1.0, 500.0);, en esta parte es la perspectiva de la camara en este caso lo que se debe de hacer es colocarle 500 para poder ver de lejos el laberinto, pueden colocarle mas o pueden hacerlo con menos.


