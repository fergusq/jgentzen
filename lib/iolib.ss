; iosys := ???;

; inbyte : () / ($1(iosys) |- $2(|\|));
(define (g/inbyte iosys output) (output (read-char)))

; outbyte : () / ($1(iosys), $2(|\|) |- $3(++));
(define (g/outbyte iosys char output)
    (write-char char)
    (output 'unit))
