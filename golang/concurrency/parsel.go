package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	t := time.Now()
	rand.Seed(t.UnixNano())

	go parseURL("http://example.com/")
	parseURL("http://youtube.com/")

	fmt.Printf("Parsing complete. Time Elapesed: %.2f second\n", time.Since(t).Seconds())
}

func parseURL(url string) {
	for i := 0; i < 5; i++ {
		delay := rand.Intn(500) + 500
		time.Sleep(time.Duration(delay) * time.Millisecond)

		fmt.Printf("Parsing <%s> - Step %d - delay %d ms \n", url, i+1, delay)
	}
}
