package main

import "fmt"

func main() {
	list := make([]int, 4, 5)
	list2 := append(list, 1) // list = append(list, 1)

	list[0] = 5
	list2[0] = 9

	fmt.Println(list, len(list), cap(list))    // [9 0 0 0] 4 5
	fmt.Println(list2, len(list2), cap(list2)) // [9 0 0 0 1] 5 5

	// для избежания непредсказуемого поведения необходимо использовать единый срез
}
