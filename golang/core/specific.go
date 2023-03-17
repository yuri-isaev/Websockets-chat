package main

import "fmt"

func main() {
	// modifySlice()
	// copySlice()
	copyPartSlice()
}

func copyPartSlice() {
	nums := []int{1, 2, 3, 4, 5}
	copy(nums[1:3], []int{5, 6})
	fmt.Println(nums) // [1 5 6 4 5]
}

// правильное копирование
func copySlice() {
	nums := []int{1, 2, 3, 4, 5}
	newNums := make([]int, len(nums), len(nums))
	copy(newNums, nums)
	fmt.Println(nums, newNums)
}

func modifySlice() {
	nums := []int{1, 2, 3, 4, 5}

	// !! Особенность
	newNums := nums[:]
	newNums[0] = 9
	fmt.Println(nums, newNums) // изменятся оба среза -> [9 2 3 4 5] [9 2 3 4 5]

	newNums = append(newNums, 6)       // добавление в срез нового элемента
	fmt.Println(newNums, cap(newNums)) // создается новый массив с объемом 10 элементов [9 2 3 4 5 6] 10
	fmt.Println(nums, cap(nums))       // [9 2 3 4 5] 5
}
