package myworld.battlefield;

import myworld.creatures.*;

public class Position<T extends Creature> {
    private T body;

    Position(){
        body=null;
    }

    public T getBody(){
        return body;
    }

    void setBody(T body){
        this.body=body;
    }
}
