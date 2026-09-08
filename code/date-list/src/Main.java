public class Main {
    static class MyList {
        int value;
        MyList next;

        MyList(int value, MyList next) {
            this.value = value;
            this.next = next;
        }

        public int size() {
            if (next == null) {
                return 1;
            }
            return 1 + next.size();
        }

        public int other_size() {
            int num = 0;
            MyList p = this;

            while (p != null) {
                num++;
                p = p.next;
            }
            return num;
        }

        public int get(int index) {
            if (index == 0) {
                return value;
            }
            return next.get(index - 1);
        }
    }

    public static void main(String[] args) {
        MyList head = new MyList(0, null);
        head = new MyList(5, head);
        head = new MyList(10, head);
    }
}
