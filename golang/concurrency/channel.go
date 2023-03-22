package main

import (
	"fmt"
	"time"
)

func main() {
	// readAndWrite()
	// getChannelValue()
	bufferingChannel()
}

func readAndWrite() {
	channel := make(chan string) // создание канала

	go func() {
		time.Sleep(2 * time.Second)
		channel <- "hello in channel" // запись в канал после задержки
	}()

	fmt.Println(<-channel) // чтение из канала
}

func getChannelValue() {
	channel := make(chan string) // создание канала

	go func() {
		for i := 1; i < 10; i++ {
			channel <- fmt.Sprintf("%d", i)
			time.Sleep(time.Millisecond * 500)
		}
		close(channel)
	}()

	// for {
	// 	value, open := <-channel
	// 	if !open {
	// 		break
	// 	}

	for value := range channel {
		fmt.Println(value) // чтение из канала
	}
}

func bufferingChannel() { // буфуризированный канал не блокирует чтение и запись без коррутин
	channel := make(chan string, 2) // создание канала
	channel <- "hello"
	fmt.Println(<-channel)
}
