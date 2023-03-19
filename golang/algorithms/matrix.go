package main

import "fmt"

func main() {
	graph := make([][]int, 10)
	for x := 0; x < 10; x++ {
		for y := 0; y < 10; y++ {
			graph[y] = make([]int, 10)
			graph[y][x] = x
		}
		fmt.Println(graph[x])
	}
}
