(library (stdlib)
  (export g/init g/lcopy g/cut g/left/or g/cmp g/false g/count g/split/and g/right/and g/right/or g/nil g/split/or g/fold g/left/and g/succ g/loop g/true g/cons g/neces)
  (import (rnrs) )

; left/and : ($1(?x), *a |- *b) / ($1(?x /\ ?_), *a |- *b);
(define (g/left/and pair above) (above (car pair)))

; right/and : ($1(?x), *a |- *b) / ($1(?_ /\ ?x), *a |- *b);
(define (g/right/and pair above) (above (cdr pair)))

; split/and : (*a |- $1(?x), *b; *a |- $2(?y), *b) / (*a |- $1(?x /\ ?y), *b);
(define (g/split/and output left right) (left (lambda (l) (right (lambda (r) (output (cons l r)))))))

; left/or : (*a |- $1(?x), *b) / (*a |- $1(?x \/ ?_), *b);
(define (g/left/or value above) (above (lambda (l) (value (list 'left l)))))

; right/or : (*a |- $1(?x), *b) / (*a |- $1(?_ \/ ?x), *b);
(define (g/right/or value above) (above (lambda (r) (value (list 'right r)))))

; split/or : ($1(?x), *a |- *b; $2(?y), *a |- *b) / ($1(?x \/ ?y), *a |- *b);
(define (g/split/or value left right) (if (eq? (car value) 'left)
                                          (left (cadr value))
                                          (right (cadr value))))

; cut : (*a |- *b, $1(?x); *c, $1(?x) |- *d) / (*a, *c |- *b, *d);
(define (g/cut left right) (left right))

; init : () / ($1(?x) |- $2(?x));
(define (g/init i o) (o i))

; true : () / (|- $1(++));
(define (g/true o) (o 'unit))

; false : () / ($1(_|_) |-);
(define (g/false i) 'bottom)

; succ : () / ($1(|\|) |- $2(|\|));
(define (g/succ i o) (o (+ i 1)))

; neces : ($1(*a) |- $2(?b)) / ($1([] *a) |- $2([] ?b));
(define g/neces (lambda args
    (let* ((r (reverse args))
           (f (car r))
           (a (reverse (cdr r))))
        (apply f a))))

; count : ($1(?x), *a |- $2(?x), *b) / ($1(|\|), $2(?x), *a |- $3(?x), *b);
(define (g/count times initial output above)
    (if (= times 0)
        (output initial)
        (above initial (lambda (next)
                               (g/count (- times 1) next output above)))))

; loop : ($1(?x), *a |- $2(?x), $3(?y)) / ($1(?x), *a |- $2(@ ?y));
(define (g/loop initial output above)
    (above initial
           (lambda (next)
                   (g/loop next output above))
           (lambda (stop)
                   (output stop))))

; lcopy : ($1(?x), *a |- $2(?y), *b) / ($1(@ ?x), *a |- $2(@ ?y), *b);
(define (g/lcopy input output above) (above input output))

; nil : () / (|- $1(.. ?_));
(define (g/nil o) (o '()))

; cons : () / ($1(?a), $2(.. ?a) |- $3(.. ?a));
(define (g/cons item list output) (output (cons item list)))

; fold : ($1(?x), $2(?y), *a |- $3(?y), *b) / ($1(.. ?x), $2(?y), *a |- $3(?y), *b);
(define (g/fold list initial output above)
    (if (null? list)
        (output initial)
        (above (car list) initial (lambda (next)
                                          (g/fold (cdr list) next output above)))))

; cmp : (*a |- *b; *a |- *b; *a |- *b) / ($1(|\|), $2(|\|), *a |- *b);
(define (g/cmp a b less eq greater)
    (cond ((< a b) (less))
          ((= a b) (eq))
          ((> a b) (greater))))


  ; left/and : ($1(?x), *a |- *b) / ($1(?x /\ ?_), *a |- *b)
  ; right/and : ($1(?x), *a |- *b) / ($1(?_ /\ ?x), *a |- *b)
  ; split/and : (*a |- $1(?x), *b; *a |- $1(?y), *b) / (*a |- $1(?x /\ ?y), *b)
  ; left/or : (*a |- $1(?x), *b) / (*a |- $1(?x \/ ?_), *b)
  ; right/or : (*a |- $1(?x), *b) / (*a |- $1(?_ \/ ?x), *b)
  ; split/or : ($1(?x), *a |- *b; $1(?y), *a |- *b) / ($1(?x \/ ?y), *a |- *b)
  ; cut : (*a |- $1(?x), *b; $1(?x), *c |- *d) / (*a, *c |- *b, *d)
  ; init : () / ($1(?x) |- $2(?x))
  ; true : () / (|- $1(++))
  ; false : () / ($1(_|_) |-)
  ; succ : () / ($1(|\|) |- $2(|\|))
  ; count : ($1(?x), *a |- $2(?x), *b) / ($1(|\|), $2(?x), *a |- $3(?x), *b)
  ; loop : ($1(?x), *a |- $2(?x), $3(?y)) / ($1(?x), *a |- $2(@ ?y))
  ; lcopy : ($1(?x), *a |- $2(?y), *b) / ($1(@ ?x), *a |- $2(@ ?y), *b)
  ; nil : () / (|- $1(.. ?_))
  ; cons : () / ($1(?a), $2(.. ?a) |- $3(.. ?a))
  ; fold : ($1(?x), $2(?y), *a |- $3(?y), *b) / ($1(.. ?x), $2(?y), *a |- $3(?y), *b)
  ; cmp : (*a |- *b; *a |- *b; *a |- *b) / ($1(|\|), $2(|\|), *a |- *b)
)
(library (iolib)
  (export g/inbyte g/outtext g/outbyte)
  (import (rnrs) (stdlib))

; iosys := ???;

; inbyte : () / ($1(iosys) |- $2(|\|));
(define (g/inbyte iosys output) (output (char->integer (read-char))))

; outbyte : () / ($1(iosys), $2(|\|) |- $3(++));
(define (g/outbyte iosys char output)
    (write-char (integer->char char))
    (output 'unit))


  ; inbyte : () / ($1(iosys) |- $2(|\|))
  ; outbyte : () / ($1(iosys), $2(|\|) |- $3(++))
  ; outtext : () / ($1(iosys), $2(.. |\|) |- $3(++))
  ; outtext = [io,s,z] cut true ([t] fold s t z ([ch,_,k] outbyte io ch k))
  (define g/outtext
    (lambda (io s z) (g/cut g/true (lambda (t) (g/fold s t z (lambda (ch _@1 k) (g/outbyte io ch k)))))))

)
(library (alias2)
  (export g/zero g/add g/make_pair g/two g/out_star g/drain_nat)
  (import (rnrs) (iolib) (stdlib))

  ; zero : () / (|- $1(nat))
  ; zero = [out] left/or out ([u] true u)
  (define g/zero
    (lambda (out) (g/left/or out (lambda (u) (g/true u)))))

  ; two : () / (|- $1(nat))
  ; two = [out] right/or out ([n1] right/or n1 ([n0] zero n0))
  (define g/two
    (lambda (out) (g/right/or out (lambda (n1) (g/right/or n1 (lambda (n0) (g/zero n0)))))))

  ; make_pair : () / ($1(?a), $2(?b) |- $3(?a /\ ?b))
  ; make_pair = [a,b,pair] split/and pair ([left] init a left) ([right] init b right)
  (define g/make_pair
    (lambda (a b pair) (g/split/and pair (lambda (left) (g/init a left)) (lambda (right) (g/init b right)))))

  ; add : () / ($1(nat), $2(nat) |- $3(@ nat))
  ; add = [a,b,out] cut ([o] make_pair a b o) ([pair] loop pair out ([cur,next,done] left/and cur ([cur_a] right/and cur ([cur_b] split/or cur_a ([_] init cur_b done) ([pred] cut ([o1] right/or o1 ([o2] init cur_b o2)) ([succ] cut ([o] make_pair pred succ o) ([new_pair] init new_pair next)))))))
  (define g/add
    (lambda (a b out) (g/cut (lambda (o) (g/make_pair a b o)) (lambda (pair) (g/loop pair out (lambda (cur next done) (g/left/and cur (lambda (cur_a) (g/right/and cur (lambda (cur_b) (g/split/or cur_a (lambda (_@0) (g/init cur_b done)) (lambda (pred) (g/cut (lambda (o1) (g/right/or o1 (lambda (o2) (g/init cur_b o2)))) (lambda (succ) (g/cut (lambda (o) (g/make_pair pred succ o)) (lambda (new_pair) (g/init new_pair next)))))))))))))))))

  ; drain_nat : (*a |- $1(++), *b) / ($1(nat), *a |- $2(@ ++), *b)
  ; drain_nat = [f/n,out] loop n out ([cur,next,done] split/or cur ([_] true done) ([m] cut f ([_] init m next)))
  (define g/drain_nat
    (lambda (n out g/f) (g/loop n out (lambda (cur next done) (g/split/or cur (lambda (_@0) (g/true done)) (lambda (m) (g/cut g/f (lambda (_@0) (g/init m next)))))))))

  ; out_star : () / ($1(iosys) |- $2(++))
  ; out_star = [io,done] cut 42 ([star] outbyte io star done)
  (define g/out_star
    (lambda (io done) (g/cut (lambda (output) (output 42)) (lambda (star) (g/outbyte io star done)))))

  ; main : () / ($1(iosys) |- $2(@ ++))
  ; main = [io,done] cut two ([t1] cut two ([t2] cut ([o] add t1 t2 o) ([sum] lcopy sum done ([inner_sum,_] drain_nat inner_sum done ([out] out_star io out)))))
  (define g/main
    (lambda (io done) (g/cut g/two (lambda (t1) (g/cut g/two (lambda (t2) (g/cut (lambda (o) (g/add t1 t2 o)) (lambda (sum) (g/lcopy sum done (lambda (inner_sum _@1) (g/drain_nat inner_sum done (lambda (out) (g/out_star io out)))))))))))))

)
(library (negation3)
  (export g/drain_nat_no_at g/main)
  (import (rnrs) (iolib) (alias2) (stdlib))

  ; notL : (*a |- $1(?x), *b) / ($1(~ ?x), *a |- *b)
  ; notL = [f/i] f i
  (define g/notL
    (lambda (i g/f) (g/f i)))

  ; notR : ($1(?x), *a |- *b) / (*a |- $1(~ ?x), *b)
  ; notR = [f/o] f o
  (define g/notR
    (lambda (o g/f) (o (lambda (o) (g/f o)))))

  ; step : () / ($1(self_nat), $2(nat), $3(~ ++) |-)
  ; step = [meL,n,retL] notL retL ([retR] notL meL ([meR] split/or n ([_] cut true ([u] init u retR)) ([m] cut ([p] split/and p ([o1] init meL o1) ([o1] make_pair m retL o1)) ([triple] init triple meR))))
  (define g/step
    (lambda (meL n retL) (g/notL retL (lambda (retR) (g/notL meL (lambda (meR) (g/split/or n (lambda (_@0) (g/cut g/true (lambda (u) (g/init u retR)))) (lambda (m) (g/cut (lambda (p) (g/split/and p (lambda (o1) (g/init meL o1)) (lambda (o1) (g/make_pair m retL o1)))) (lambda (triple) (g/init triple meR)))))))))))

  ; make_self : () / (|- $1(self_nat))
  ; make_self = [xR] notR xR ([x] left/and x ([me] right/and x ([rest] left/and rest ([n] right/and rest ([ret] step me n ret)))))
  (define g/make_self
    (lambda (xR) (g/notR xR (lambda (x) (g/left/and x (lambda (me) (g/right/and x (lambda (rest) (g/left/and rest (lambda (n) (g/right/and rest (lambda (ret) (g/step me n ret)))))))))))))

  ; drain_nat_no_at : (*a |- $1(++), *b) / ($1(nat), *a |- $2(++), *b)
  ; drain_nat_no_at = [f/n,ret] cut ([k] init k ret) ([k] cut f ([_] cut make_self ([me] step me n k)))
  (define g/drain_nat_no_at
    (lambda (n ret g/f) (g/cut (lambda (k) (k (lambda (k) (g/init k ret)))) (lambda (k) (g/cut g/f (lambda (_@0) (g/cut g/make_self (lambda (me) (g/step me n k)))))))))

  ; main : () / ($1(iosys) |- $2(++))
  ; main = [io,done] cut two ([t] drain_nat_no_at t done ([out] out_star io out))
  (define g/main
    (lambda (io done) (g/cut g/two (lambda (t) (g/drain_nat_no_at t done (lambda (out) (g/out_star io out)))))))

)
(import (negation3))
(call/cc (lambda (c) (g/main 'iosys c)))
