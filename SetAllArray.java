class SetAllArray {
    private int id;
    private Entry all;
    private Entry[] array;
    
    public SetAllArray(int capacity) {
        this.id = 0;
        this.all = new Entry();
        this.array = new Entry[capacity];
    }
    
    public void setValue(int index, int value) {
        if (array[index] == null) {
            array[index] = new Entry();
        }
        array[index].setValue(value);
        this.id = this.id + 1;
        System.out.println("setValue id now is: "+id);
        array[index].setTimestamp(this.id);
    }
    
    public void setAll(int value) {
        this.all.setValue(value);
        this.id = this.id + 1;
        System.out.println("setAll id now is: "+id);
        all.setTimestamp(this.id);
    }
    
    public int getValue(int index) {
        if (all.getTimestamp() > array[index].getTimestamp()) {
            System.out.println("The value is from set all");
            return all.getValue();
        }
        System.out.println("The value is from the entry array item");
        return  array[index].getValue();
    }


    
    class Entry {
        private int timestamp;
        private int value;
        
        public Entry() {
           
        }
        
        public Entry(int timestamp, int value) {
            this.timestamp = timestamp;
            this.value = value;
        }
        
        public int getTimestamp() {
            return timestamp;
        }
        
        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }
        
        public int getValue() {
            return value;
        }
        
        public void setValue(int value) {
            this.value = value;
        }
    }
    
    public static void main(String[] args) {
        SetAllArray setAllArray = new SetAllArray(3);
        setAllArray.setValue(0,1);
        setAllArray.setValue(1,2);
        setAllArray.setValue(2,3);
        System.out.println("setAllArray.getValue(0): "+setAllArray.getValue(0));
        setAllArray.setAll(5);
        System.out.println("setAllArray.getValue(0): "+setAllArray.getValue(0));
        setAllArray.setValue(2,3);
        System.out.println("setAllArray.getValue(2): "+setAllArray.getValue(2));
        System.out.println("setAllArray.getValue(0): "+setAllArray.getValue(0));
    }
}
