package catering.util;

public class Pair<key, value> {
    private key key;
    private value value;


    public Pair(key first, value second) {
        this.key = first;
        this.value = second;
    }

    public key getKey() {
        return this.key;
    }

    public value getValue() {
        return this.value;
    }

    public String toString() {
        return this.key + "=" + this.value;
    }

    public int hashCode() {
        return this.key.hashCode()*13+(this.value==null ? 0 : this.value.hashCode());
    }

     public boolean equals(Object paramO){
        if (this == paramO) 
            return true;
        if ((paramO instanceof Pair)) {
            Pair localPair = (Pair)paramO;
            if (this.key != null ? !this.key.equals(localPair.key) : localPair.key != null) 
                return false;
            if (this.value != null ? !this.value.equals(localPair.value) : localPair.value != null) 
                return false;
            return true;
        }
        return false;
    }

}
