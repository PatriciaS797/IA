
;;;           MODULO MAIN    
;;; Patricia Siwinska , NIP:820115	
(defmodule MAIN
	(export deftemplate nodo)
	(export deffunction heuristica))

(deftemplate MAIN::nodo
   (multislot estado)
   (multislot camino)
   (slot heuristica)
   (slot coste)
   (slot clase (default abierto))) ;ok

(defglobal MAIN
   ?*estado-inicial* = (create$ B B B H V V V)
   ?*estado-final* = (create$ V V V H B B B)) ;ok

(deffunction MAIN::heuristica ($?estado)
   (bind ?res 0)
   (loop-for-count (?i 1 7)
    (if (neq (nth ?i $?estado)
             (nth ?i ?*estado-final*))
         then (bind ?res (+ ?res 1))
     )
    )
   ?res) ;ok

;;;     MODULO MAIN::INICIAL   
(defrule MAIN::inicial
   =>
   (assert (nodo
              (estado ?*estado-inicial*)
              (camino)
              (heuristica (heuristica ?*estado-inicial*))
              (coste 0)
              ))
 ); ok


;;; MODULO MAIN::CONTROL(PRIORIDAD) 
(defrule MAIN::pasa-el-mejor-a-cerrado
   ?nodo <- (nodo (clase abierto)
                  (heuristica ?h1)
                  (coste ?c1))
   (not (nodo (clase abierto)
              (heuristica ?h2)
              (coste ?c2&:(< (+ ?c2 ?h2) (+ ?c1 ?h1))))) ;para que sea a* también hay que tener en cuenta el coste
=>
   (modify ?nodo (clase cerrado))
   (focus OPERADORES)); ok

;;; MODULO OPERADORES  

(defmodule OPERADORES
   (import MAIN deftemplate nodo)
   (import MAIN deffunction heuristica)
); ok


(defrule OPERADORES::izquierda
   (nodo (estado $?a ?b $?y&:(<= (length$ ?y) 2) H $?d)
          (camino $?movimientos)
          (coste ?c)
          (clase cerrado))
 =>
   (bind $?nuevo-estado (create$ $?a H  $?y ?b $?d))
   (assert (nodo
		      (estado $?nuevo-estado)
              (camino $?movimientos (implode$ ?nuevo-estado))
              (heuristica (heuristica $?nuevo-estado))
              (coste (+ ?c 1))))); ok?


(defrule OPERADORES::derecha
   (nodo (estado $?a H $?y&:(<= (length$ ?y) 2) ?b $?d)
          (camino $?movimientos)
          (coste ?c)
          (clase cerrado))
 =>
   (bind $?nuevo-estado (create$ $?a ?b $?y  H $?d))
   (assert (nodo
		      (estado $?nuevo-estado)
              (camino $?movimientos (implode$ ?nuevo-estado))
              (heuristica (heuristica $?nuevo-estado))
              (coste (+ ?c 1))))); ok?

;;;      MODULO RESTRICCIONES 
(defmodule RESTRICCIONES
   (import MAIN deftemplate nodo))

(defrule RESTRICCIONES::repeticiones-de-nodo
	(declare (auto-focus TRUE))
      ?nodo1 <- (nodo (estado $?estado) (camino $?camino1))
      ?nodo2 <- (nodo (estado $?estado) 
        (camino $?camino2&:(>= (length$ ?camino1) (length$ ?camino2))))
      (test (neq ?nodo1 ?nodo2))
 =>
   (retract ?nodo1))



;;;    MODULO ::SOLUCION 

(defmodule SOLUCION
   (import MAIN deftemplate nodo))

(defrule SOLUCION::reconoce-solucion
   (declare (auto-focus TRUE))
   ?nodo <- (nodo (estado V V V H B B B)
               (camino $?movimientos))
 =>
   (retract ?nodo)
   (assert (solucion $?movimientos)))

(defrule SOLUCION::escribe-solucion
   (solucion $?movimientos)
  =>
   (printout t "La solucion tiene " (length$ ?movimientos) " pasos" crlf)
   (loop-for-count (?i 1 (length$ ?movimientos))
   (printout t (nth$ ?i ?movimientos) crlf))
   (halt))