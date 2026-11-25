(include "lib/stdlib.ss")
(include "lib/iolib.ss")
;(import alias2)
;(import stdlib)
;(import iolib)

; zero : () / (|- $1(nat))
; zero = [out] left/or out ([u] true u)
(define g/zero (lambda (out) (g/left/or out (lambda (u) (g/true u)))))

; two : () / (|- $1(nat))
; two = [out] right/or out ([n1] right/or n1 ([n0] zero n0))
(define g/two (lambda (out) (g/right/or out (lambda (n1) (g/right/or n1 (lambda (n0) (g/zero n0)))))))

; outtext : () / ($1(iosys), $2(.. |\|) |- $3(++))
; outtext = [io,s,z] cut true ([t] fold s t z ([ch,_,k] outbyte io ch k))
(define g/outtext (lambda (io s z) (g/cut g/true (lambda (t) (g/fold s t z (lambda (ch _@1 k) (g/outbyte io ch k)))))))

; add : () / ($1(nat), $2(nat) |- $3(@ (nat)))
; add = [a,b,out] cut ([p] split/and p ([left] init a left) ([right] init b right)) ([pair] loop pair out ([cur,next,done] left/and cur ([cur_a] right/and cur ([cur_b] split/or cur_a ([_] init cur_b done) ([pred] cut ([o1] right/or o1 ([o2] init cur_b o2)) ([succ] cut ([p] split/and p ([left] init pred left) ([right] init succ right)) ([new_pair] init new_pair next)))))))
(define g/add (lambda (a b out) (g/cut (lambda (p) (g/split/and p (lambda (left) (g/init a left)) (lambda (right) (g/init b right)))) (lambda (pair) (g/loop pair out (lambda (cur next done) (g/left/and cur (lambda (cur_a) (g/right/and cur (lambda (cur_b) (g/split/or cur_a (lambda (_@0) (g/init cur_b done)) (lambda (pred) (g/cut (lambda (o1) (g/right/or o1 (lambda (o2) (g/init cur_b o2)))) (lambda (succ) (g/cut (lambda (p) (g/split/and p (lambda (left) (g/init pred left)) (lambda (right) (g/init succ right)))) (lambda (new_pair) (g/init new_pair next)))))))))))))))))

; drain_nat : (*a |- $1(++), *b) / ($1(nat), *a |- $2(@ ++), *b)
; drain_nat = [f/n,out] loop n out ([cur,next,done] split/or cur ([_] true done) ([m] cut f ([_] init m next)))
(define g/drain_nat (lambda (n out g/f) (g/loop n out (lambda (cur next done) (g/split/or cur (lambda (_@0) (g/true done)) (lambda (m) (g/cut g/f (lambda (_@0) (g/init m next)))))))))

; out_star : () / ($1(iosys) |- $2(++))
; out_star = [io,done] cut 42 ([star] outbyte io star done)
(define g/out_star (lambda (io done) (g/cut (lambda (output) (output 42)) (lambda (star) (g/outbyte io star done)))))

; main : () / ($1(iosys) |- $2(@ ++))
; main = [io,done] cut two ([t1] cut two ([t2] cut ([o] add t1 t2 o) ([sum] lcopy sum done ([inner_sum,_] drain_nat inner_sum done ([out] out_star io out)))))
(define g/main (lambda (io done) (g/cut g/two (lambda (t1) (g/cut g/two (lambda (t2) (g/cut (lambda (o) (g/add t1 t2 o)) (lambda (sum) (g/lcopy sum done (lambda (inner_sum _@1) (g/drain_nat inner_sum done (lambda (out) (g/out_star io out)))))))))))))

