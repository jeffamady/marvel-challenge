# Marvel

## It is an Android App

## This application is connected to the API: <https://developer.marvel.com/docs#!/>. With this project you can see a list of **Comics** and **Events**.

## Technologies
* material:1.5.0
* constraintlayout:2.1.3
* livedata-ktx:2.4.1
* dagger:hilt-android:2.39.1
* fragment-ktx:2.4.1
* navigation-ui-ktx:2.4.1
* coil:2.0.0-rc01
* customactivityoncrash:2.3.0
* junit:4.13.2
* espresso-core:3.4.0
* firebase-auth-ktx:29.0.4
* facebook-android-sdk:[4,5)

## Setup
To run this project, install Android Studio and run it on an android emulator or phone.

## Challenge
*SPANISH*

### Descripción del Challenge

Los fanáticos de los cómics están ansiosos por ver los avances en la nueva app de Marvel y poder así obtener información actualizada de sus personajes favoritos. Te parece que podes ser parte de esta app?
En este challenge lo que esperamos es que puedas hacer una aplicación que funcione en dispositivos Android usando el lenguaje Kotlin con la temática de superhéroes de Marvel. Tendrás que obtener la información de una API que te proveemos y respetar el diseño propuesto lo más fielmente que puedas.
En el próximo apartado, "Requerimientos", te detallamos las secciones con las que debería contar la app.

### Requerimientos
La aplicación que tenés que desarrollar debe constar de las siguientes secciones:
- Autenticación: Se debe integrar Firebase para realizar el registro/ingreso a través de Facebook o Email (sólo esas 2 vías). La sesión, una vez que el usuario entra, debe ser guardada para que una próxima vez no sea necesario autenticarse. Un usuario con credenciales debe ser re-dirigido a Pantalla Principal. Aclaración: se puede usar la interfaz que provee Firebase para la autenticación, no es necesario proveer una custom UI.
- Pantalla Principal: esta pantalla consta de un TabBar en la parte inferior, de la que se puede seleccionar entre Personajes y Eventos.
- Personajes: Listado de personajes de Marvel, con un paginado (estilo scroll infinito) de 15 elementos (es decir, se cargan 15, y al llegar al fondo del scroll, se cargan 15 más, y así). Al hacer click se abre la pantalla ​Detalle del Personaje.​
- Eventos: Listado de eventos futuros, con un límite de 25 elementos y ordenados por fecha de comienzo de manera ascendente. El diseño contiene una flecha arriba/abajo para expandir/colapsar la celda. Cuando se expande se muestra más información de los eventos.
- Detalle del Personaje: info del personaje (foto, nombre, descripción) + listado de cómics en los que figura.

### Diseño gráfico (UI)
Parte del objetivo de este Challenge es poder evaluar tus habilidades para respetar las pantallas y lineamientos de diseño tal como las plantean los equipos de UX / UI. Por eso es que te proveemos las pantallas que esperamos que puedas desarrollar. Podés acceder a las mismas desde el siguiente [enlace](https://xd.adobe.com/view/610ea5ae-9964-46d1-bdce-d456a63a2ed6-345d/grid). Tené en cuenta que no te mostramos la pantalla de autenticación ya que te permitimos usar la UI que te provee ​Firebase​ directamente. También contempla que necesitarás manejar imágenes que obtienes del servidor, y las mismas se deben ver con la relación de aspecto adecuada, no deben quedar márgenes sin imágenes ni estiradas para ninguna dirección.

### Datos útiles
API:​ https://developer.marvel.com/docs#!/

Los GETs de la API tienen que llevar 3 parámetros
- apikey la public key que te dan al registrarte y solicitarla
- ts timestamp (se puede usar 1, porque no se chequea)
- hash generada a partir de un md5 = timestamp+private_key+public_key

Se puede usar esta de ejemplo
Public Key: 3a783b25c80e1c44875356dd363f272d
Private Key: aa1141953df8c088f39a97de10008578e834580f

Ejemplo de llamado: https://gateway.marvel.com/v1/public/comics?apikey=3a783b25c80e1c44875356dd363f272d&hash=51a3ecf2f92a23817992a2663183325e&ts=1
