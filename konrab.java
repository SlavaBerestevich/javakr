import java.util.PriorityQueue;

public class ToyStore {
    private String[] ids;
    private String[] names;
    private int[] weights;
    private PriorityQueue<Toy> queue;
    
    public ToyStore(String[] ids, String[] names, int[] weights) {
        if (ids.length < 3 || names.length < 3 || weights.length < 3) {
            throw new IllegalArgumentException("Необходимо передать минимум 3 элемента для каждого массива");
        }
        
        this.ids = ids;
        this.names = names;
        this.weights = weights;
        
        queue = new PriorityQueue<>(ids.length, (t1, t2) -> Integer.compare(t1.getWeight(), t2.getWeight()));
        for (int i = 0; i < ids.length; i++) {
            Toy toy = new Toy(ids[i], names[i], weights[i]);
            queue.add(toy);
        }
    }
    
    public Toy getToy() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.poll();
    }
    
    public void saveToysToFile(String filePath, int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Количество должно быть больше 0");
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            Toy toy = getToy();
            if (toy == null) {
                break;
            }
            sb.append(toy.toString()).append("\n");
        }
        
        if (sb.length() > 0) {
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static class Toy {
        private String id;
        private String name;
        private int weight;
        
        public Toy(String id, String name, int weight) {
            this.id = id;
            this.name = name;
            this.weight = weight;
        }
        
        public int getWeight() {
            return weight;
        }
        
        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Weight: " + weight;
        }
    }
}
String[] ids = { "1", "2", "3" };
String[] names = { "Кукла", "Мяч", "Пазл" };
int[] weights = { 5, 3, 4 };

ToyStore toyStore = new ToyStore(ids, names, weights);

String filePath = "результат.txt";
toyStore.saveToysToFile(filePath, 10);