(include "lib/stdlib.ss")
(include "lib/iolib.ss")
;(import truthmachine)
;(import stdlib)
;(import iolib)

; main : () / ($1(iosys) |- $2(@ ++))
; main = [x,y] cut ([z] inbyte x z) ([z] truth x z y)
(define g/main (lambda (x y) (g/cut (lambda (z) (g/inbyte x z)) (lambda (z) (g/truth x z y)))))

; do_zero : () / ($1(iosys) |- $2(@ ++))
; do_zero = [io,lp] cut 48 ([x] loop x lp ([_,_,k] outbyte io x k))
(define g/do_zero (lambda (io lp) (g/cut (lambda (output) (output 48)) (lambda (x) (g/loop x lp (lambda (_ _ k) (g/outbyte io x k)))))))

; do_one : () / ($1(iosys) |- $2(@ ++))
; do_one = [io,lp] cut 49 ([x] cut true ([t] loop t lp ([_,k,_] outbyte io x k)))
(define g/do_one (lambda (io lp) (g/cut (lambda (output) (output 49)) (lambda (x) (g/cut g/true (lambda (t) (g/loop t lp (lambda (_ k _) (g/outbyte io x k)))))))))

; outtext : () / ($1(iosys), $2(.. |\|) |- $3(++))
; outtext = [io,s,z] cut true ([t] fold s t z ([ch,_,k] outbyte io ch k))
(define g/outtext (lambda (io s z) (g/cut g/true (lambda (t) (g/fold s t z (lambda (ch _ k) (g/outbyte io ch k)))))))

; truth : () / ($1(iosys), $2(|\|) |- $3(@ ++))
; truth = [io,n,lp] cut 48 ([z] cmp n z (do_zero io lp) (do_zero io lp) (do_one io lp))
(define g/truth (lambda (io n lp) (g/cut (lambda (output) (output 48)) (lambda (z) (g/cmp n z (lambda () (g/do_zero io lp)) (lambda () (g/do_zero io lp)) (lambda () (g/do_one io lp)))))))

