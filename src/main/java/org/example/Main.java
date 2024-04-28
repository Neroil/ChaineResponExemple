package org.example;

public class Main {
    public static void main(String[] args) {
        AttackHandler physicalAttackHandler = new PhysicalAttackHandler();
        AttackHandler magicalAttackHandler = new MagicalAttackHandler(physicalAttackHandler);

        Person[] fightingRing = new Person[3];

        Person Gimli = new Person(physicalAttackHandler);
        Mage Saruman = new Mage(magicalAttackHandler);

        fightingRing[0] = Gimli;
        fightingRing[2] = Saruman;

        Gimli.attack(Saruman); //PhysicalAttack request, will fail
        Saruman.attack(Gimli); //MagicalAttack request


        fightingRing[1] = Gimli;
    }
}

enum Kind {
    MagicalAttack,
    PhysicalAttack
}

class Request {

    private Kind requestKind;

    public Request(Kind resquestKind){
        this.requestKind = resquestKind;
    }

    public Kind getKind() {
        return requestKind;
    }
}


abstract class AttackHandler {
    protected AttackHandler _successor;

    public AttackHandler(AttackHandler _successor){
        this._successor = _successor;
    }

    public AttackHandler(){
        this._successor = null;
    }

    public void handleAttackRequest(Request request){
        if(this._successor != null) {
            this._successor.handleAttackRequest(request);
        }
    }
}

class PhysicalAttackHandler extends AttackHandler {

    public PhysicalAttackHandler(AttackHandler _successor){
        super(_successor);
    }

    public PhysicalAttackHandler(){
        super();
    }

    @Override
    public void handleAttackRequest(Request request) {
        if (request.getKind() == Kind.PhysicalAttack) {
            // Handle the request here
        } else super.handleAttackRequest(request);
    }
}

class RangeCheckerHandler extends AttackHandler{

    @Override
    public void handleAttackRequest(Request request){
        // Check la range
        super.handleAttackRequest(request);
    }
}

class ManaCheckerHandler extends AttackHandler{

    @Override
    public void handleAttackRequest(Request request){
        // Check la mana
        super.handleAttackRequest(request);
    }
}


class MagicalAttackHandler extends AttackHandler {

    public MagicalAttackHandler(AttackHandler _successor){
        super(_successor);
    }

    public MagicalAttackHandler(){
        super();
    }

    @Override
    public void handleAttackRequest(Request request) {
        switch(request.getKind()){
            case MagicalAttack:
                // Send to Magical Attack Handler

                break;
            case PhysicalAttack:
                // Send to Physical Attack Handler
                break;
            default:
                super.handleAttackRequest(request);
        }
    }
}

class Person {
    protected AttackHandler handler;

    public Person(AttackHandler handler) {
        this.handler = handler;
    }

    public int getMana() {
        return 0;
    }

    public void attack(Person p){
        Request request = new Request(Kind.PhysicalAttack);
        handler.handleAttackRequest(request);
    }
}

class Mage extends Person{
    public Mage(AttackHandler handler) {
        super(handler);
    }

    @Override
    public int getMana() {
        return 100;
    }

    @Override
    public void attack(Person p){
        Request request = new Request(Kind.MagicalAttack);
        handler.handleAttackRequest(request);
    }
}
