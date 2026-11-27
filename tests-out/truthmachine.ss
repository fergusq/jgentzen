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
(library (truthmachine)
  (export g/main)
  (import (rnrs) (iolib) (stdlib))

  ; do_zero : () / ($1(iosys) |- $2(@ ++))
  ; do_zero = [io,lp] cut 48 ([x] loop x lp ([_,_,k] outbyte io x k))
  (define g/do_zero
    (lambda (io lp) (g/cut (lambda (output) (output 48)) (lambda (x) (g/loop x lp (lambda (_@0 _@1 k) (g/outbyte io x k)))))))

  ; do_one : () / ($1(iosys) |- $2(@ ++))
  ; do_one = [io,lp] cut 49 ([x] cut true ([t] loop t lp ([_,k,_] outbyte io x k)))
  (define g/do_one
    (lambda (io lp) (g/cut (lambda (output) (output 49)) (lambda (x) (g/cut g/true (lambda (t) (g/loop t lp (lambda (_@0 k _@2) (g/outbyte io x k)))))))))

  ; truth : () / ($1(iosys), $2(|\|) |- $3(@ ++))
  ; truth = [io,n,lp] cut 48 ([z] cmp n z (do_zero io lp) (do_zero io lp) (do_one io lp))
  (define g/truth
    (lambda (io n lp) (g/cut (lambda (output) (output 48)) (lambda (z) (g/cmp n z (lambda () (g/do_zero io lp)) (lambda () (g/do_zero io lp)) (lambda () (g/do_one io lp)))))))

  ; main : () / ($1(iosys) |- $2(@ ++))
  ; main = [x,y] cut ([z] inbyte x z) ([z] truth x z y)
  (define g/main
    (lambda (x y) (g/cut (lambda (z) (g/inbyte x z)) (lambda (z) (g/truth x z y)))))

)
(import (truthmachine))
(call/cc (lambda (c) (g/main 'iosys c)))
