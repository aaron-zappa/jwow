package main

import "C"
import (
	"fmt"
)

//export GoFunction
func GoFunction(input *C.char) *C.char {
	goInput := C.GoString(input)
	goOutput := fmt.Sprintf("Hello from Go! You sent: %s", goInput)
	return C.CString(goOutput)
}

func main() {
	// Required for C export
}