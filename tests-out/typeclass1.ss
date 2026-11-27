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
(library (typeclass1)
  (export g/no g/main g/test g/yes)
  (import (rnrs) (iolib) (stdlib))

  ; no : () / (|- $1(boolean))
  ; test : (*a |- *b; *a |- *b) / ($1(boolean), *a |- *b)
  ; yes : () / (|- $1(boolean))
  ; say_yes : () / ($1(iosys) |- $2(++))
  ; say_yes = [io,done] cut "YES" ([z] outtext io z done)
  (define g/say_yes
    (lambda (io done) (g/cut (lambda (output) (output (map char->integer (string->list "YES")))) (lambda (z) (g/outtext io z done)))))

  ; say_no : () / ($1(iosys) |- $2(++))
  ; say_no = [io,done] cut "NO" ([z] outtext io z done)
  (define g/say_no
    (lambda (io done) (g/cut (lambda (output) (output (map char->integer (string->list "NO")))) (lambda (z) (g/outtext io z done)))))

  ; main : () / ($1(iosys) |- $2(++))
  ; main = [io,done] cut yes ([b] test b (say_no io done) (say_yes io done))
  (define g/main
    (lambda (io done) (g/cut g/yes (lambda (b) (g/test b (lambda () (g/say_no io done)) (lambda () (g/say_yes io done)))))))

  ; no : () / (|- $1(boolean))
  (define (g/no p1)
    (p1 (list 'no (list ) (list ))))

  ; test : (*a |- *b; *a |- *b) / ($1(boolean), *a |- *b)
  (define gct/test$1 (vector '()))
  (define (g/test p1 f0 f1)
    (call/cc (lambda (exit)
      (let ((cer (assq (car p1) (vector-ref gct/test$1 0))))
        (when cer (exit (apply (cadr cer) (append (cadr p1) (list ) (caddr p1) (list f0 f1))))))
      (error (string-append "missing cut elimination rule for " (symbol->string p1) " test")))))

  ; yes : () / (|- $1(boolean))
  (define (g/yes p1)
    (p1 (list 'yes (list ) (list ))))

  ; no $1 test $1 = [x,_/] x;
  (define no$1/test$1
    (lambda (g/x g/_) (g/x)))

  ; yes $1 test $1 = [_,x/] x;
  (define yes$1/test$1
    (lambda (g/_ g/x) (g/x)))

  (vector-set! gct/test$1 0 (cons (list 'no no$1/test$1) (vector-ref gct/test$1 0)))
  (vector-set! gct/test$1 0 (cons (list 'yes yes$1/test$1) (vector-ref gct/test$1 0)))
)
(import (typeclass1))
(call/cc (lambda (c) (g/main 'iosys c)))
