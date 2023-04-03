use std::{
    sync::{ mpsc, Arc, Mutex },
    thread,
};

type Job = Box<dyn FnOnce() + Send + 'static>;

pub struct ThreadPool(Vec<Worker>, mpsc::Sender<Job>);

// Необходимо создать связанную функцию с именем new for ThreadPool.
// Мы также знаем, что new должен иметь один параметр который может приниматься 4 в качестве аргумента
// и должен возвращать ThreadPool экземпляр
impl ThreadPool {
    // --snip--
    pub fn new(size: usize) -> ThreadPool {
        assert!(size > 0);

        let (sender, receiver) = mpsc::channel();

        let receiver = Arc::new(Mutex::new(receiver));

        let mut workers = Vec::with_capacity(size);

        for id in 0..size {
            workers.push(Worker::new(id, Arc::clone(&receiver)));
        }

        ThreadPool(workers, sender)
    }

    pub fn execute<F>(&self, f: F) where F: FnOnce() + Send + 'static, {
        let job = Box::new(f);
        self.1.send(job).unwrap();
    }
}

struct Worker(usize, thread::JoinHandle<()>);

impl Worker {
    fn new(id: usize, receiver: Arc<Mutex<mpsc::Receiver<Job>>>) -> Worker {
        let thread = thread::spawn( move || loop {
            let job = receiver.lock().unwrap().recv().unwrap();
            println!("Receiver {} received a job", id);
            job();
        });

        Worker(id, thread)
    }
}
