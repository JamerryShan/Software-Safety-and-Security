default: parser.c json.c
	gcc -g -o parser -I.. parser.c json.c -lm

fsanitize: parser.c json.c
	gcc -g -fsanitize=address -o parser -I.. parser.c json.c -lm

fsanitize-all: parser.c json.c
	export ASAN_OPTIONS=halt_on_error=0 && \
	gcc -g -fsanitize=address -fsanitize-recover=address -o parser -I.. parser.c json.c -lm

clean:
	rm -f *.o parser

test-error:
	! ./parser test-comment-error.json
	# parser is expected to return non-zero error code for malformed input

test-crash:
	export ASAN_OPTIONS=halt_on_error=0 && \
	! ./parser test-comment-crash.json
	# parser is expected to return non-zero error code for malformed input

fuzz:
	./fuzz.sh

clean-fuzz:
	rm -f fuzz-crash-*.json input.json
