package main

import "fmt"

func main() {
	arr := []int{1, 2, 3, 4, 8}
	fmt.Println("before", arr) // before [1 2 3 4 8]
	handler1(arr[:1])
	handler2(arr[:1])
	fmt.Println("after", arr) // after [1 0 3 4 8]
	// длина и емкость внешнего среза не изменяется
}

func handler1(slice []int) {
	slice = append(slice, 0)
	fmt.Println("append", slice) // append [1 0]
}

func handler2(slice []int) {
	newSlice := make([]int, len(slice))
	copy(newSlice, slice)
	newSlice = append(newSlice, 23)
	fmt.Println("append", newSlice) // append [1 23]
}
