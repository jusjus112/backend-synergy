package usa.devrocoding.synergy.assets;

public class Cache<L, R> {

    private L left;
    private R right;

    public Cache() {
    }

    public Cache(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public void put(L key, R value){
        this.left = key;
        this.right = value;
    }

    public boolean isEmpty(){
        return this.left == null && this.right == null;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public boolean isFull() {
        return this.left != null && this.right != null;
    }
}
