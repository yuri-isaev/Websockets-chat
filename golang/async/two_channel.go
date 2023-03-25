package main

import (
	"fmt"
	"time"
)

func main() {
	channel1 := make(chan string)
	channel2 := make(chan string)

	go func() {
		for {
			time.Sleep(time.Second * 1)
			channel1 <- "Прошло 1 секунды"
		}
	}()

	go func() {
		for {
			time.Sleep(time.Second * 3)
			channel2 <- "Прошло 3 секунды"
		}
	}()

	for {
		select {
		case one := <-channel1:
			fmt.Println(one)
		case two := <-channel2:
			fmt.Println(two)
		}
	}
}
