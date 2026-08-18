/**
 * This creates the buffer and the producer and consumer threads.
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Sixth Edition
 * Copyright John Wiley & Sons - 2003.
 */
public class Factory
{
    public static void main(String args[]) {
        Buffer server = new BoundedBuffer();

        // 20 produtores e 20 consumidores brigando pelo mesmo buffer
        for (int i = 0; i < 20; i++) {
            new Thread(new Producer(server)).start();
            new Thread(new Consumer(server)).start();
        }
    }
}
