package main

import "fmt"

func main() {
	list := make([]int, 4, 4)
	list = Append(list, 1)
	fmt.Println(list, len(list), cap(list))
}

func Append(list []int, el int) []int {
	var res []int
	resLen := len(list) + 1

	if resLen <= cap(list) {
		res = list[:resLen]

	} else {
		resCap := resLen
		if resCap < 2*len(list) {
			resCap = 2 * len(list)
		}

		res = make([]int, resLen, resCap)
		copy(res, list)
	}

	res[len(list)] = el
	return res
}
