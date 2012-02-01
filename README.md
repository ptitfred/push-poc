Push POC

*Attention: ce projet utilise une vieille version de Jetty et de la spec websocket.*
*Il faut par conséquent faire une montée de version sous peine de ne pas pouvoir l'utiliser avec un browser récent.*

Content:
 - gwt/	GWT project : client, CTI (to simulate calls).
 - ws/	WebSocket server-side project - aka the Driver.

Install:
 - prepare 1 Jetty (version 7 at least) on port 7777
 - prepare 1 servlet container on any port (80/8080)
 - mvn package
 - start both servers
 - deploy wars to servers (manually)

Demo:
 - goto http://localhost:8080/pushgwt/ for client, many tabs to make it clear!
 - login with operateur1 and operateur2
 - goto http://localhost:8080/pushgwt/CTI.html to simulate calls to different operators

 - extra option : append ?ws.host=11.22.33.44:7777 to use a custom driver url.

