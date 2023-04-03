

// use std::{thread, time};
// use std::thread::JoinHandle;

// fn main() {
//     println!("Hello, world!");
//
//     // функция
//     fn add_one_v1(x: u32) -> u32 {
//         x + 1
//     }
//
//     // предпочтительная функция с замыканием
//     let _var = |x: u32| -> u32 {
//         x + 1
//     };
//
//     let _r1 = add_one_v1(1);
//     let _r2 = _var(2);
//
//     println!("{}", _r1);
//     println!("{}", _r2);
//
//     // закрытия мо
//     let test = String::from("test");
//
//     let closure_ex = || {
//         println!("{}", test);
//     };
//     closure_ex();
// }

// consider -> main thread
// fn main() {
//     let now = time::Instant::now();
//     let one: i8 = do_delay_return(1);
//     let two: i8 = do_delay_return(2);
//     let three: i8 = do_delay_return(3);
//     println!("time elapsed {:?}", now.elapsed()); // time elapsed 6.0306347s
//     println!("result {}", one + two + three);
// }

// fn do_delay_return(number: i8) -> i8 {
//     println!("number {} is running", number);
//     let two_seconds = time::Duration::new(2, 0);
//     thread::sleep(two_seconds);
//     return 2;
// }
//
// fn main() {
//     let now = time::Instant::now();
//     let thread_1: JoinHandle<i8> = thread::spawn(|| do_delay_return(1));
//     let thread_2: JoinHandle<i8> = thread::spawn(|| do_delay_return(2));
//     let thread_3: JoinHandle<i8> = thread::spawn(|| do_delay_return(3));
//     let res_1 = thread_1.join();
//     let res_2 = thread_2.join();
//     let res_3 = thread_3.join();
//
//     println!("time elapsed {:?}", now.elapsed());  // time elapsed 2.0066511s
//     println!("result {}", res_1.unwrap() + res_2.unwrap() + res_3.unwrap());
// }