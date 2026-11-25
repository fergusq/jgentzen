(let ((program (cadr (command-line))))
    (load program)
    (call/cc (lambda (c) (g/main 'iosys c))))
