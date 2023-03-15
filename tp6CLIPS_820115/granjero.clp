
;;;           MODULO MAIN ;;; Patricia Siwinska , NIP:820115

(defmodule MAIN 
	(export deftemplate nodo casilla)
	(export deffunction heuristica)
    (export deffunction opuesta)
)

(deftemplate MAIN::nodo
    (slot localizacion-granjero )
    (slot localizacion-lobo)
    (slot localizacion-cabra)
    (slot localizacion-col)

   (multislot camino)
   (slot coste (default 0))
   (slot clase (default abierto)))

(deffunction MAIN::opuesta (?orilla)
	(if (eq ?orilla izquierda)
	then derecha
	else izquierda))

;;;     MODULO MAIN::INICIAL

(defrule MAIN::inicial
	=>
	(assert(nodo
	(localizacion-granjero izquierda)
	(localizacion-lobo izquierda)
	(localizacion-cabra izquierda)
	(localizacion-col izquierda)
	(camino))))


;;; MODULO MAIN::CONTROL 


(defrule MAIN::pasa-el-mejor-a-cerrado-CU
	?nodo <- (nodo (coste ?c1)
			(clase abierto)
		 )
	(not (nodo (clase abierto)
		    (coste ?c2&:(< ?c2 ?c1))
	     )
	)
	=>
	(modify ?nodo (clase cerrado))
	(focus OPERADORES)
)


;;; MODULO MAIN::OPERACIONES

(defmodule OPERADORES
   (import MAIN deftemplate nodo)
   (import MAIN deffunction opuesta))

(defrule OPERADORES::movimiento-solo
    ?nodo <- (nodo (localizacion-granjero ?orilla-actual)
    (coste ?cos)
    (camino $?movimientos))
    =>
    (duplicate ?nodo
    (localizacion-granjero (opuesta ?orilla-actual))
    (coste (+ ?cos 1))
    (camino $?movimientos solo)))

(defrule OPERADORES::movimiento-con-lobo
    ?nodo <- (nodo (localizacion-granjero ?orilla-actual)
    (localizacion-lobo ?orilla-actual)
    (coste ?cos)
    (camino $?movimientos))
    =>
    (duplicate ?nodo
    (localizacion-granjero (opuesta ?orilla-actual))
    (localizacion-lobo (opuesta ?orilla-actual))
    (coste (+ ?cos 1))
    (camino $?movimientos lobo)))

(defrule OPERADORES::movimiento-con-cabra
    ?nodo <- (nodo (localizacion-granjero ?orilla-actual)
    (localizacion-cabra ?orilla-actual)
    (coste ?cos)
    (camino $?movimientos))
    =>
    (duplicate ?nodo
    (localizacion-granjero (opuesta ?orilla-actual))
    (localizacion-cabra (opuesta ?orilla-actual))
    (coste (+ ?cos 1))
    (camino $?movimientos cabra)))

(defrule OPERADORES::movimiento-con-col
    ?nodo <- (nodo (localizacion-granjero ?orilla-actual)
    (localizacion-col ?orilla-actual)
    (coste ?cos)
    (camino $?movimientos))
    =>
    (duplicate ?nodo
    (localizacion-granjero (opuesta ?orilla-actual))
    (localizacion-col (opuesta ?orilla-actual))
    (coste (+ ?cos 1))
    (camino $?movimientos col)))


;;; MODULO RESTRICCIONES


(defmodule RESTRICCIONES
   (import MAIN deftemplate nodo))

(defrule RESTRICCIONES::lobo-come-cabra
    (declare (auto-focus TRUE))
    ?nodo <- (nodo (localizacion-granjero ?l1)
    (localizacion-lobo ?l2&~?l1)
    (localizacion-cabra ?l2))
    =>
    (retract ?nodo))

(defrule RESTRICCIONES::cabra-come-col
    (declare (auto-focus TRUE))
    ?nodo <- (nodo (localizacion-granjero ?l1)
    (localizacion-cabra ?l2&~?l1)
    (localizacion-col ?l2))
    =>
    (retract ?nodo))

(defrule RESTRICCIONES::camino-circular
    (declare (auto-focus TRUE))
    (nodo (localizacion-granjero ?granjero)
    (localizacion-lobo ?lobo)
    (localizacion-cabra ?cabra)
    (localizacion-col ?col)
    (camino $?movimientos-1))
    ?nodo <- (nodo (localizacion-granjero ?granjero)
    (localizacion-lobo ?lobo)
    (localizacion-cabra ?cabra)
    (localizacion-col ?col)
    (camino $?movimientos-1 ? $?movimientos-2))
    =>
    (retract ?nodo))

;;;    MODULO MAIN::SOLUCION 
(defmodule SOLUCION
(import MAIN deftemplate nodo)
(import MAIN deffunction opuesta))

(defrule SOLUCION::reconoce-solucion
    (declare (auto-focus TRUE))
    ?nodo <- (nodo (localizacion-granjero derecha)
    (localizacion-lobo derecha)
    (localizacion-cabra derecha)
    (localizacion-col derecha)
    (camino $?movimientos))
    =>
    (retract ?nodo)
    (assert (solucion $?movimientos)))

(defrule SOLUCION::escribe-solucion
	?mv <- (solucion $?m)
	=>
	(retract ?mv)
	(printout t crlf crlf "Solucion encontrada " crlf)
	(bind ?orilla derecha)
	(loop-for-count (?i 1 (length $?m))
	(bind ?cosa (nth ?i $?m))
	(printout t "El granjero se mueve "
	(switch ?cosa
	(case solo then "solo ")
	(case lobo then "con el lobo")
	(case cabra then "con la cabra ")
	(case col then "con la col "))
	" a la " ?orilla "." crlf)
	(bind ?orilla (opuesta ?orilla))))
