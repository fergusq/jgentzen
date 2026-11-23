(include "lib/stdlib.ss")
(include "lib/iolib.ss")
;(import test)
;(import lib/stdlib)
;(import lib/iolib)

; helloworld : () / ($1(iosys) |- $2(++))
; helloworld = [x,y] cut "Hello world!" ([z] outtext x z y)
(define g/helloworld (lambda (x y) (g/cut (lambda (output) (output (string->list "Hello world!"))) (lambda (z) (g/outtext x z y)))))

; outtext : () / ($1(iosys), $2(.. |\|) |- $3(++))
; outtext = [io,s,z] cut true ([t] fold s t z ([ch,_,k] outbyte io ch k))
(define g/outtext (lambda (io s z) (g/cut g/true (lambda (t) (g/fold s t z (lambda (ch _ k) (g/outbyte io ch k)))))))

; main : () / ($1(iosys) |- $2(++))
; main = [i,o] cut ([p] twice i p helloworld) ([x] left/and x ([z] init z o))
(define g/main (lambda (i o) (g/cut (lambda (p) (g/twice i p g/helloworld)) (lambda (x) (g/left/and x (lambda (z) (g/init z o)))))))

; twice : ($1(iosys) |- $2(++)) / ($1(iosys) |- $2(++ /\ ++))
; twice = [r/i,p] split/and p ([o] r i o) ([o] r i o)
(define g/twice (lambda (i p g/r) (g/split/and p (lambda (o) (g/r i o)) (lambda (o) (g/r i o)))))

