//HashTable.java
public class HashTable<Key, Value> {
    // these are parallel arrays, so indices in one correspond to the other
    private Value[] values;
    private Key[] keys;
    private int maxSize;
    private int size;
    public HashTable(int maxSize, int size) {
        this.maxSize = maxSize;
        this.size = size;

        // make the arrays (with generic array workaround)
        values = (Value[]) new Object[maxSize];
        keys = (Key[]) new Object[maxSize];
    }
    // insert a key/value pair
    public void insert(Key key, Value value) {

        float check = maxSize/2;

        if(size > check ){
            expand();
        }

        // get the starting index from hash function
        int index = Math.abs(key.hashCode()) % maxSize;

        // linear probe until spot is open
        while (values[index] != null) {
            index = (index + 1) % maxSize;
        }

        // now insert this pair here
        values[index] = value;
        keys[index] = key;
        size++;
    }
    // lookup a value by its key
    public Value lookup(Key key) {
        // get the starting index from hash function
        int index = Math.abs(key.hashCode()) % maxSize;

        // loop while there is data here
        while (keys[index] != null) {
            // if the key here matches target, return corresponding value
            if (keys[index].equals(key)) {
                return values[index];
            }
            // move to next index to search (with wrap around)
            index = (index + 1) % maxSize;
        }
        // if we fell off the end, the key is not here
        return null;
    }
    private void expand(){

        int newMax = maxSize * 2;

        Value NeWvalues[] = (Value[]) new Object[newMax];
        Key newKeys[] = (Key[]) new Object[newMax];

        for(int i=0; i < maxSize; i++){
            if(keys[i] != null) {
                Key newKey = keys[i];
                Value val = lookup(keys[i]);
                if(val != null) {
                    int index = Math.abs(newKey.hashCode()) % newMax;
                    // linear probe until spot is open
                    while (NeWvalues[index] != null) {
                        index = (index + 1) % newMax;
                    }
                    NeWvalues[index] = val;
                    newKeys[index] = newKey;
                }
            }
        }
        maxSize = newMax;
        keys = newKeys;
        values = NeWvalues;
    }
}