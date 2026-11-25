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
