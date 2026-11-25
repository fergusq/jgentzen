(include "lib/stdlib.ss")
(include "lib/iolib.ss")
;(import helloworld)
;(import stdlib)
;(import iolib)

; outtext : () / ($1(iosys), $2(.. |\|) |- $3(++))
; outtext = [io,s,z] cut true ([t] fold s t z ([ch,_,k] outbyte io ch k))
(define g/outtext (lambda (io s z) (g/cut g/true (lambda (t) (g/fold s t z (lambda (ch _ k) (g/outbyte io ch k)))))))

; main : () / ($1(iosys) |- $2(++))
; main = [x,y] cut "Hello world!" ([z] outtext x z y)
(define g/main (lambda (x y) (g/cut (lambda (output) (output (string->list "Hello world!"))) (lambda (z) (g/outtext x z y)))))

