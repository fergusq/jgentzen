(include "lib/stdlib.ss")
(include "lib/iolib.ss")
;(import negation1)
;(import stdlib)

; notL : (*a |- $1(?x), *b) / ($1(~ ?x), *a |- *b)
; notL = [f/i] f i
(define g/notL (lambda (i g/f) (g/f i)))

; notR : ($1(?x), *a |- *b) / (*a |- $1(~ ?x), *b)
; notR = [f/o] f o
(define g/notR (lambda (o g/f) (o (lambda (o) (g/f o)))))

; lem : () / (|- $1(?x), $2(~ ?x))
; lem = [out,k] init k out
(define g/lem (lambda (out k) (k (lambda (k) (g/init k out)))))

; dne : () / ($1(~ (~ ?x)) |- $2(?x))
; dne = [kk,out] notR kk ([k2] init k2 out)
(define g/dne (lambda (kk out) (g/notR kk (lambda (k2) (g/init k2 out)))))

