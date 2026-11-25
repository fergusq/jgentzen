(include "lib/stdlib.ss")
(include "lib/iolib.ss")
;(import negation2)
;(import stdlib)
;(import iolib)

; make_printer : () / ($1(iosys) |- $2(~ |\|), $3(++))
; make_printer = [io,k,done] outbyte io k done
(define g/make_printer (lambda (io k done) (k (lambda (k) (g/outbyte io k done)))))

; outtext : () / ($1(iosys), $2(.. |\|) |- $3(++))
; outtext = [io,s,z] cut true ([t] fold s t z ([ch,_,k] outbyte io ch k))
(define g/outtext (lambda (io s z) (g/cut g/true (lambda (t) (g/fold s t z (lambda (ch _ k) (g/outbyte io ch k)))))))

; main : () / ($1(iosys) |- $2(++))
; main = [io,done] cut ([k] make_printer io k done) ([k] cut 65 ([a] send_num a k))
(define g/main (lambda (io done) (g/cut (lambda (k) (g/make_printer io k done)) (lambda (k) (g/cut (lambda (output) (output 65)) (lambda (a) (g/send_num a k)))))))

; send_num : () / ($1(|\|), $2(~ |\|) |-)
; send_num = [n,k] init n k
(define g/send_num (lambda (n k) (g/init n k)))

