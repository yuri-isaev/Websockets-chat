package main

import "fmt"

func main() {
	slice := []int{1, 2, 3, 4, 8}
	fmt.Println(getDoubleNumSlice(slice)) // [2 4 6 8 16]
}

// при известной длине входящего среза важно заранее аллоцировать длину под него
// чтобы избежать постоянного копирование одного массива в другой
func getDoubleNumSlice(slice []int) []int {
	res := make([]int, len(slice))

	for i, num := range slice {
		res[i] = num * 2
	}

	return res
}
