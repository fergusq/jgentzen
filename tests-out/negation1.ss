(include "lib/stdlib.ss")
(include "lib/iolib.ss")
;(import negation1)
;(import stdlib)
;(import iolib)

; lem : () / (|- $1(?x), $2(~ ?x))
; lem = [out,k] init k out
(define g/lem (lambda (out k) (k (lambda (k) (g/init k out)))))

; notL : (*a |- $1(?x), *b) / ($1(~ ?x), *a |- *b)
; notL = [f/i] f i
(define g/notL (lambda (i g/f) (g/f i)))

; outtext : () / ($1(iosys), $2(.. |\|) |- $3(++))
; outtext = [io,s,z] cut true ([t] fold s t z ([ch,_,k] outbyte io ch k))
(define g/outtext (lambda (io s z) (g/cut g/true (lambda (t) (g/fold s t z (lambda (ch _@1 k) (g/outbyte io ch k)))))))

; notR : ($1(?x), *a |- *b) / (*a |- $1(~ ?x), *b)
; notR = [f/o] f o
(define g/notR (lambda (o g/f) (o (lambda (o) (g/f o)))))

; dneL : () / ($1(~ (~ ?x)) |- $2(?x))
; dneL = [kk,out] notR kk ([k2] init k2 out)
(define g/dneL (lambda (kk out) (g/notR kk (lambda (k2) (g/init k2 out)))))

; dneR : () / ($1(?x) |- $2(~ (~ ?x)))
; dneR = [kk,out] notL out ([k2] init kk k2)
(define g/dneR (lambda (kk out) (out (lambda (out) (g/notL out (lambda (k2) (g/init kk k2)))))))

; main : () / ($1(iosys) |- $2(++))
; main = [x,y] cut "Hello world!" ([h1] cut ([part] dneR h1 part) ([h3] cut ([part] dneL h3 part) ([z] outtext x z y)))
(define g/main (lambda (x y) (g/cut (lambda (output) (output (map char->integer (string->list "Hello world!")))) (lambda (h1) (g/cut (lambda (part) (g/dneR h1 part)) (lambda (h3) (g/cut (lambda (part) (g/dneL h3 part)) (lambda (z) (g/outtext x z y)))))))))

