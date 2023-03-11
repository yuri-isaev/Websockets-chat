package main

import (
	"fmt"
	"math"
	"runtime"
	"unsafe"
)

func main() {
	// !! Все строки любой длины весят 16 байт
	// поэтому её выгодно просто передять по значению
	status := new(runtime.MemStats)
	runtime.ReadMemStats(status)
	fmt.Printf("status: %d\n", status.HeapAlloc) // status: 100080

	bytes := make([]byte, math.MaxInt32) // 24 byte

	runtime.ReadMemStats(status)
	fmt.Printf("status: %d\n", status.HeapAlloc) // status:  2147586488

	byteAsString := string(bytes) // 16 byte
	runtime.ReadMemStats(status)

	fmt.Printf("| bytes len: %d, cap: %d, size: %d |\n| string len: %d, size: %d \n",
		len(bytes),                  // bytes len: 2147483647
		cap(bytes),                  // cap: 2147483647
		unsafe.Sizeof(bytes),        // size: 24
		len(byteAsString),           // string len: 2147483647
		unsafe.Sizeof(byteAsString), // size: 16
	)
	fmt.Printf("| status after string created: %d \n", status.HeapAlloc)
}
