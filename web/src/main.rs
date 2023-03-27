use std::{
    {fs, thread},
    io::{BufReader, prelude::*},
    net::{TcpListener, TcpStream},
    time::Duration,
};

//use rust::ThreadPool;

fn main() {
    let listener = TcpListener::bind("192.168.0.103:3000").unwrap();
    //let pool = ThreadPool::new(4);

    for stream in listener.incoming() {
        let stream = stream.unwrap();

        // непредсказуемое поведение один запрос ждет пока завершиться другой
         // handle_connection(stream);

        // многопоточный через системный поток на каждый запрос
         // thread::spawn(|| { handle_connection(stream) });

        // многопоточный через пул потоков
        // pool.execute(|| { handle_connection(stream) });
    }
}

fn handle_connection(mut stream: TcpStream) {
    let buf_reader = BufReader::new(&mut stream);
    let request_line = buf_reader.lines().next().unwrap().unwrap();
    let (status_line, filename) = match &request_line[..] {
        "GET / HTTP/1.1" => ("HTTP/1.1 200 OK", "html/main.html"),
        "GET /sleep HTTP/1.1" => {
            thread::sleep(Duration::from_secs(5));
            ("HTTP/1.1 200 OK", "html/main.html")
        }
        _ => ("HTTP/1.1 404 NOT FOUND", "html/404.html"),
    };
    let contents = fs::read_to_string(filename).unwrap();
    let length = contents.len();
    let response = format!("{status_line}\r\nContent-Length: {length}\r\n\r\n{contents}");
    stream.write_all(response.as_bytes()).unwrap();
}